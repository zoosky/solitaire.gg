package services.sandbox

import models.queries.audit.AnalyticsEventQueries
import org.joda.time.LocalDate
import services.audit.DailyMetricService
import services.database.Database
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import utils.{Application, DateUtils}

import scala.concurrent.Future

object BackfillMetrics extends SandboxTask {
  override def id = "backfill-metrics"
  override def description = "Backfill missing daily metrics."
  override def run(ctx: Application) = {
    def getDays(d: LocalDate) = {
      val today = DateUtils.today
      val start = (d.getYear * 365) + d.getDayOfYear
      val end = (today.getYear * 365) + today.getDayOfYear
      val numDays = end - start
      Future.successful((0 to numDays).map(i => today.minusDays(i)))
    }

    Database.query(AnalyticsEventQueries.GetEarliestDay).flatMap { startDay =>
      getDays(startDay).flatMap { days =>
        Future.sequence(days.map(d => DailyMetricService.getMetrics(d))).map { result =>
          result.map(x => s"${x._1}: ${x._2._1.size} metrics, ${x._2._2} calculated.").mkString("\n")
        }
      }
    }
  }
}
