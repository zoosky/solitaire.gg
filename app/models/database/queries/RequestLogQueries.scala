package models.database.queries

import java.util.UUID

import com.github.mauricio.async.db.RowData
import models.audit.RequestLog
import models.database.Query
import org.joda.time.LocalDateTime

object RequestLogQueries extends BaseQueries[RequestLog] {
  override protected val tableName = "requests"
  override protected val columns = Seq(
    "id", "user_id", "auth_provider", "auth_key", "remote_address",
    "method", "host", "secure", "path", "query_string",
    "lang", "cookie", "referrer", "user_agent", "started", "duration", "status"
  )
  override protected val searchColumns = Seq("id::text", "user_id::text", "method", "host", "path", "referrer", "user_agent")

  case class FindRequestsByUser(id: UUID) extends Query[List[RequestLog]] {
    override val sql = getSql(Some("user_id = ?"))
    override val values = Seq(id)
    override def reduce(rows: Iterator[RowData]) = rows.map(fromRow).toList
  }

  override protected def fromRow(row: RowData) = {
    val id = row("id") match { case s: String => UUID.fromString(s) }
    val userId = row("user_id") match { case s: String => UUID.fromString(s) }
    val authProvider = row("auth_provider") match { case s: String => s }
    val authKey = row("auth_key") match { case s: String => s }
    val remoteAddress = row("remote_address") match { case s: String => s }

    val method = row("method") match { case s: String => s }
    val host = row("host") match { case s: String => s }
    val secure = row("secure") match { case b: Boolean => b }
    val path = row("path") match { case s: String => s }
    val queryString = row("query_string") match { case s: String => s }

    val lang = row("lang") match { case s: String => Some(s); case _ => None }
    val cookie = row("cookie") match { case s: String => Some(s); case _ => None }
    val referrer = row("referrer") match { case s: String => Some(s); case _ => None }
    val userAgent = row("user_agent") match { case s: String => Some(s); case _ => None }
    val started = row("started") match { case ldt: LocalDateTime => ldt }
    val duration = row("duration") match { case i: Int => i }
    val status = row("status") match { case i: Int => i }

    RequestLog(
      id, userId, authProvider, authKey, remoteAddress,
      method, host, secure, path, queryString,
      lang, cookie, referrer, userAgent, started, duration, status
    )
  }

  override protected def toDataSeq(r: RequestLog) = Seq[Any](
    r.id, r.userId, r.authProvider, r.authKey, r.remoteAddress,
    r.method, r.host, r.secure, r.path, r.queryString,
    r.lang, r.cookie, r.referrer, r.userAgent, r.started, r.duration, r.status
  )
}