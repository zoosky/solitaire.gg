package models.database.queries.auth

import com.github.mauricio.async.db.RowData
import com.mohiva.play.silhouette.api.LoginInfo
import com.mohiva.play.silhouette.impl.providers.OAuth1Info
import models.database.queries.BaseQueries
import models.database.{ FlatSingleRowQuery, Statement }
import org.joda.time.LocalDateTime

object OAuth1InfoQueries extends BaseQueries[OAuth1Info] {
  override protected val tableName = "oauth1_info"
  override protected val columns = Seq("provider", "key", "token", "secret", "created")
  override protected val idColumns = Seq("provider", "key")
  override protected val searchColumns = Seq("key", "token")

  case class CreateOAuth1Info(l: LoginInfo, o: OAuth1Info) extends Statement {
    override val sql = insertSql
    override val values = Seq(l.providerID, l.providerKey) ++ toDataSeq(o)
  }

  case class UpdateOAuth1Info(l: LoginInfo, o: OAuth1Info) extends Statement {
    override val sql = s"update $tableName set token = ?, secret = ?, created = ? where provider = ? and key = ?"
    override val values = toDataSeq(o) ++ Seq(l.providerID, l.providerKey)
  }

  override protected def fromRow(row: RowData) = {
    val token = row("token") match { case s: String => s }
    val secret = row("secret") match { case s: String => s }
    OAuth1Info(token, secret)
  }

  override protected def toDataSeq(o: OAuth1Info) = Seq(o.token, o.secret, new LocalDateTime())
}