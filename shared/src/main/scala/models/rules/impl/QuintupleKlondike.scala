package models.rules.impl

import models.rules._

object QuintupleKlondike extends GameRules(
  id = "quintupleklondike",
  completed = true,
  title = "Quintuple Klondike",
  layout = "swf|:.t",
  deckOptions = DeckOptions(numDecks = 5),
  stock = Some(
    StockRules(
      cardsDealt = StockCardsDealt.Count(3)
    )
  ),
  waste = Some(WasteRules()),
  foundations = Seq(
    FoundationRules(
      numPiles = 20,
      autoMoveCards = true
    )
  ),
  tableaus = Seq(
    TableauRules(
      numPiles = 20,
      emptyFilledWith = FillEmptyWith.HighRank
    )
  )
)
