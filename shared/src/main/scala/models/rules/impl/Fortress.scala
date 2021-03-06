package models.rules.impl

import models.rules._

object Fortress extends GameRules(
  id = "fortress",
  completed = true,
  title = "Fortress",
  like = Some("beleagueredcastle"),
  related = Seq("bastion", "chessboard", "fortressofmercy", "beleagueredfortress"),
  links = Seq(
    Link("Wikipedia", "en.wikipedia.org/wiki/Fortress_(solitaire)"),
    Link("Pretty Good Solitaire", "www.goodsol.com/pgshelp/fortress.htm"),
    Link("Xolitaire", "www.escapedivision.com/xolitaire/en/games/fortress.html"),
    Link("Solitaire Game Rules.com", "solitaire-game-rules.com/games/fortress.htm"),
    Link("Lady Cadogan's Illustrated Games of Solitaire or Patience", "www.gutenberg.org/files/21642/21642-h/21642-h.htm#fortress"),
    Link("AisleRiot", "help.gnome.org/users/aisleriot/stable/Fortress.html.en"),
    Link("Jan Wolter's Experimental Analysis", "/article/fortress.html"),
    Link("Michael Keller's Strategy Guide", "solitairelaboratory.com/fortress.html")
  ),
  layout = ":::f|t",
  foundations = Seq(
    FoundationRules(
      numPiles = 4,
      autoMoveCards = true
    )
  ),
  tableaus = Seq(
    TableauRules(
      numPiles = 10,
      initialCards = InitialCards.RestOfDeck,
      cardsFaceDown = TableauFaceDownCards.Count(0),
      suitMatchRuleForBuilding = SuitMatchRule.SameSuit,
      rankMatchRuleForBuilding = RankMatchRule.UpOrDown,
      suitMatchRuleForMovingStacks = SuitMatchRule.None
    )
  )
)
