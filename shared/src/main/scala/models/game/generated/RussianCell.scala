// Generated rules for Scalataire.
package models.game.generated

import models.game._
import models.game.rules._

object RussianCell extends GameRules(
  id = "russiancell",
  title = "Russian Cell",
  description = "Thomas Warfield's variant of ^russian^ Solitaire adds a couple cells.",
  foundations = Seq(
    FoundationRules(
      numPiles = 4,
      wrapFromKingToAce = true,
      canMoveFrom = FoundationCanMoveFrom.Never,
      autoMoveCards = true
    )
  ),
  tableaus = Seq(
    TableauRules(
      initialCards = InitialCards.Custom,
      cardsFaceDown = TableauFaceDownCards.Count(0),
      suitMatchRuleForBuilding = SuitMatchRule.SameSuit,
      suitMatchRuleForMovingStacks = SuitMatchRule.Any,
      rankMatchRuleForMovingStacks = RankMatchRule.Any,
      emptyFilledWith = TableauFillEmptyWith.Kings
    )
  ),
  cells = Some(
    CellRules(

      numPiles = 2
    )
  ),
  complete = false
)
