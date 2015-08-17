package controllers.admin

import controllers.BaseController
import models.auth.AuthenticationEnvironment
import org.joda.time.{ Days, Period, LocalDate }
import play.api.i18n.MessagesApi
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import services.history.DataArchiveService
import utils.DateUtils

@javax.inject.Singleton
class DataArchiveController @javax.inject.Inject() (
    override val messagesApi: MessagesApi,
    override val env: AuthenticationEnvironment
) extends BaseController {

  def list() = withAdminSession("data.archive.list") { implicit request =>
    DataArchiveService.getAll.map { archives =>
      val startDate = new LocalDate("2015-07-01")

      val days = (0 to Days.daysBetween(startDate, DateUtils.today).getDays).map { i =>
        startDate.plusDays(i)
      }
      Ok(views.html.admin.dataArchive.archiveList(days, archives))
    }
  }

  def process() = withAdminSession("data.archive.process") { implicit request =>
    val startMs = System.currentTimeMillis
    DataArchiveService.process().map { result =>
      Redirect(routes.DataArchiveController.list()).flashing("success" -> s"Processed archives in [${System.currentTimeMillis - startMs}ms].")
    }
  }
}
