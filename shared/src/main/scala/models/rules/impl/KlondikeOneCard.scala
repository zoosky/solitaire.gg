package models.rules.impl

import models.rules._

object KlondikeOneCard extends GameRules(
  id = "klondike1card",
  completed = true,
  title = "Klondike (One Card)",
  layout = "swf|t",
  like = Some("klondike"),
  stock = Some(StockRules()),
  waste = Some(WasteRules()),
  foundations = Seq(FoundationRules(numPiles = 4)),
  tableaus = Seq(TableauRules(emptyFilledWith = FillEmptyWith.HighRank))
)
