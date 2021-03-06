package controllers.admin

import java.util.UUID

import controllers.BaseController
import models.audit.UserFeedback
import models.queries.audit.{UserFeedbackNoteQueries, UserFeedbackQueries}
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import services.database.Database
import utils.{DateUtils, Application}

@javax.inject.Singleton
class FeedbackController @javax.inject.Inject() (override val app: Application) extends BaseController {
  def list(key: String, q: String, sortBy: String, page: Int) = withAdminSession("list-" + key) { implicit request =>
    val feedbackList = Database.query(UserFeedbackQueries.searchCount(q)).flatMap { count =>
      Database.query(UserFeedbackQueries.search(q, getOrderClause(sortBy), Some(page))).flatMap { feedbacks =>
        Database.query(UserFeedbackNoteQueries.GetUserFeedbackNotes(feedbacks.map(_.id))).map { notes =>
          count -> feedbacks.map(f => f -> notes.filter(_.feedbackId == f.id).sortBy(x => DateUtils.toMillis(x.occurred)))
        }
      }
    }

    feedbackList.map { fn =>
      val filtered = key match {
        case "all" => fn._2
        case "open" => fn._2.filterNot(_._2.exists(_.content.toLowerCase.startsWith("close")))
        case "resolved" => fn._2.filter(_._2.exists { x =>
          val lc = x.content.toLowerCase
          lc.startsWith("close") && lc.contains("resolved")
        })
        case "praise" => fn._2.filter(_._2.exists { x =>
          val lc = x.content.toLowerCase
          lc.startsWith("close") && lc.contains("praise")
        })
        case _ => throw new IllegalStateException()
      }
      Ok(views.html.admin.feedback.feedbackList(key, q, sortBy, if (key == "all") { fn._1 } else { filtered.length }, page, filtered))
    }
  }

  def feedbackNoteForm(feedbackId: UUID) = withAdminSession("note.form") { implicit request =>
    Database.query(UserFeedbackQueries.getById(feedbackId)).map { feedback =>
      Ok(views.html.admin.feedback.feedbackForm(feedback.getOrElse(throw new IllegalStateException())))
    }
  }

  def feedbackNotePost(feedbackId: UUID) = withAdminSession("note.post") { implicit request =>
    val body = request.body.asFormUrlEncoded.getOrElse(throw new IllegalStateException())
    val contentField = body.get("content")
    val content = contentField.flatMap(_.headOption).getOrElse(throw new IllegalStateException())
    val note = UserFeedback.FeedbackNote(UUID.randomUUID, feedbackId, UUID.randomUUID, content, DateUtils.now)
    Database.execute(UserFeedbackNoteQueries.insert(note)).map { _ =>
      Redirect(controllers.admin.routes.FeedbackController.list("all"))
    }
  }

  def removeFeedback(id: UUID) = withAdminSession("remove") { implicit request =>
    Database.execute(UserFeedbackQueries.remove(Seq(id))).map { _ =>
      Redirect(controllers.admin.routes.FeedbackController.list("all"))
    }
  }

  private[this] def getOrderClause(orderBy: String) = orderBy
}
