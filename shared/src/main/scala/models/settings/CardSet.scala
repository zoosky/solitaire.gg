package models.settings

import enumeratum.values._

sealed abstract class CardSet(val value: String, val w: Int, val h: Int, val hOffset: Int, val vOffset: Int) extends StringEnumEntry

object CardSet extends StringEnum[CardSet] with StringCirceEnum[CardSet] {
  case object Default extends CardSet(value = "default", w = 400, h = 600, hOffset = 80, vOffset = 120)

  override val values = findValues
}

