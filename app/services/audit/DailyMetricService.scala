package services.audit

import models.audit.DailyMetric
import models.audit.DailyMetric._
import models.queries.audit.DailyMetricQueries
import org.joda.time.LocalDate
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import services.database.Database
import utils.DateUtils

import scala.concurrent.Future

object DailyMetricService {
  def getMetric(d: LocalDate, m: DailyMetric.Metric) = Database.query(DailyMetricQueries.GetValue(d, m))

  def getMetrics(d: LocalDate) = Database.query(DailyMetricQueries.GetMetrics(d)).flatMap { m =>
    if (m.size == DailyMetric.all.size) {
      Future.successful((d, (m, 0)))
    } else {
      val missingMetrics = DailyMetric.all.filterNot(m.keySet.contains)
      calculateMetrics(d, missingMetrics).map { metrics =>
        val models = metrics.map(x => DailyMetric(d, x._1, x._2, DateUtils.now)).toSeq
        Database.execute(DailyMetricQueries.insertBatch(models))
        (d, (m ++ metrics, metrics.size))
      }
    }
  }

  def getAllMetrics = Database.query(DailyMetricQueries.GetAllMetrics)

  def setMetric(d: LocalDate, metric: DailyMetric.Metric, value: Long) = {
    val dm = DailyMetric(d, metric, value, DateUtils.now)
    Database.execute(DailyMetricQueries.UpdateMetric(dm)).flatMap { rowsAffected =>
      if (rowsAffected == 1) {
        Future.successful(dm)
      } else {
        Database.execute(DailyMetricQueries.insert(dm)).map(_ => dm)
      }
    }
  }

  def getTotals(last: LocalDate) = Database.query(DailyMetricQueries.GetTotals(last))

  def recalculateMetrics(d: LocalDate) = Database.execute(DailyMetricQueries.RemoveByDay(d)).flatMap { _ =>
    getMetrics(d)
  }

  private[this] def calculateMetrics(d: LocalDate, metrics: Seq[DailyMetric.Metric]) = {
    val futures = metrics.map { metric =>
      val f = getSql(metric) match {
        case Some(sql) => Database.query(DailyMetricQueries.CalculateMetric(metric, sql, d))
        case None => metric match {
          case DailyMetric.ServerFreeSpace => Future.successful(getFreeSpace)
          case _ => Future.successful(0L)
        }
      }
      f.map(metric -> _)
    }
    Future.sequence(futures).map(_.toMap)
  }

  private[this] def getSql(metric: Metric) = metric match {
    case GamesStarted => Some("select count(*) as c from analytics_events where created >= ? and created < ? and event_type = 'game-start'")
    case GamesWon => Some("select count(*) as c from analytics_events where created >= ? and created < ? and event_type = 'game-resigned'")
    case GamesAdandoned => Some("select count(*) as c from analytics_events where created >= ? and created < ? and event_type = 'game-won'")

    case Feedbacks => Some("select count(*) as c from user_feedback where occurred >= ? and occurred < ?")
    case _ => None
  }

  private[this] def getFreeSpace = {
    import scala.sys.process._
    val result = "df /".!!
    val lastLine = result.split("\n").last
    val endIndex = lastLine.indexOf('%')
    val startIndex = lastLine.indexOf(' ', endIndex - 5)
    val ret = 100 - lastLine.substring(startIndex, endIndex).trim.toLong
    ret
  }
}
