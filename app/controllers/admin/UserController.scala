package controllers.admin

import java.util.UUID

import controllers.BaseController
import models.queries.report.ReportQueries
import models.queries.user.UserQueries
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import services.audit.GameHistoryService
import services.database.Database
import services.user.{UserService, UserStatisticsService}
import utils.Application

import scala.concurrent.Future

@javax.inject.Singleton
class UserController @javax.inject.Inject() (override val app: Application) extends BaseController {
  def list(q: String, sortBy: String, page: Int) = withAdminSession("user.list") { implicit request =>
    Database.query(UserQueries.searchCount(q)).flatMap { count =>
      Database.query(UserQueries.search(q, getOrderClause(sortBy), Some(page))).flatMap { users =>
        Database.query(ReportQueries.GameCountForUsers(users.map(_.id))).flatMap { gameCounts =>
          Database.query(ReportQueries.WinCountForUsers(users.map(_.id))).map { winCounts =>
            Ok(views.html.admin.user.list(q, sortBy, count, page, users, gameCounts, winCounts))
          }
        }
      }
    }
  }

  def detail(id: UUID) = withAdminSession("user.detail") { implicit request =>
    UserService.getById(id).flatMap {
      case Some(user) => UserStatisticsService.getStatistics(id).map { stats =>
        Ok(views.html.admin.user.detail(user, stats))
      }
      case None => Future.successful(NotFound(s"User [$id] not found."))
    }
  }

  def remove(id: UUID) = withAdminSession("remove") { implicit request =>
    UserService.remove(id).map { result =>
      val msg = s"User [$id] removed."
      Redirect(controllers.admin.routes.UserController.list()).flashing("success" -> msg)
    }
  }

  private[this] def getOrderClause(orderBy: String) = orderBy match {
    case "created" => "created desc"
    case x => x
  }
}
