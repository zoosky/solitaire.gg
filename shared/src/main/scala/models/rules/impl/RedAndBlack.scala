package models.rules.impl

import models.rules._

object RedAndBlack extends GameRules(
  id = "redandblack",
  completed = false,
  title = "Red and Black",
  links = Seq(
    Link("Wikipedia", "en.wikipedia.org/wiki/Red_and_Black_(solitaire)"),
    Link("Pretty Good Solitaire", "www.goodsol.com/pgshelp/red_and_black.htm"),
    Link("Solsuite Solitaire", "www.solsuite.com/games/red_and_black.htm"),
    Link("Solavant Solitaire", "www.solavant.com/solitaire/red_and_black.php"),
    Link("Rapture Technologies KingSol", "www.rapturetech.com/KingSol/Rules/RedandBlack.htm"),
    Link("Zonora", "www.zonora.com/mysolitaire/rules/forty_thieves/red_and_black.htm"),
    Link("AisleRiot", "help.gnome.org/users/aisleriot/stable/Zebra.html.en")
  ),
  layout = "swf|t",
  deckOptions = DeckOptions(numDecks = 2),
  stock = Some(
    StockRules(
      maximumDeals = Some(1)
    )
  ),
  waste = Some(WasteRules()),
  foundations = Seq(
    FoundationRules(
      numPiles = 8,
      initialCards = 8,
      suitMatchRule = SuitMatchRule.AlternatingColors,
      autoMoveCards = true
    )
  ),
  tableaus = Seq(
    TableauRules(
      numPiles = 8,
      initialCards = InitialCards.Count(1),
      cardsFaceDown = TableauFaceDownCards.Count(0)
    )
  )
)
