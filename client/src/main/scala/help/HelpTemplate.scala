package help

import models.rules.GameRules
import utils.Messages

import scalatags.Text.all._

object HelpTemplate {
  def help(rules: GameRules) = {
    val titleDiv = HelpTemplateContent.getTitleDiv(rules)
    val descriptionDiv = div(cls := "description")(em(GameRulesHelpService.description(rules.id)))
    val akaDiv = HelpTemplateContent.getAkaDiv(rules)
    val objectiveDiv = div(h3(Messages("help.objective")), div(cls := "objective")(ObjectiveHelpService.objective(rules)))
    val deckDiv = div(h3(Messages("help.deck")), div(cls := "deck")(DeckOptionsHelpService.deck(rules.deckOptions)))
    val layoutDiv = HelpTemplateContent.getLayoutDiv(rules)
    val similarDiv = HelpTemplateContent.getSimilarDiv(rules)
    val relDiv = HelpTemplateContent.getRelDiv(rules)
    val linksDiv = HelpTemplateContent.getLinksDiv(rules)

    div(cls := "theme striped")(titleDiv, descriptionDiv, akaDiv, objectiveDiv, deckDiv, layoutDiv, similarDiv, relDiv, linksDiv)
  }

  lazy val generalHelp = div(
    div(cls := "theme striped with-margin")(
      div("General Help for Solitaire.gg")
    ),
    div(cls := "theme striped")("I need to write some help files...")
  )
}
