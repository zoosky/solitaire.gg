package models.rules

case class Link(title: String, url: String)

case class GameRules(
  id: String,
  completed: Boolean = false,
  title: String,
  aka: Map[String, String] = Map.empty,
  layout: String,
  like: Option[String] = None,
  related: Seq[String] = Nil,
  links: Seq[Link] = Nil,
  victoryCondition: VictoryCondition = VictoryCondition.default,
  cardRemovalMethod: CardRemovalMethod = CardRemovalMethod.default,
  deckOptions: DeckOptions = DeckOptions(),
  stock: Option[StockRules] = None,
  waste: Option[WasteRules] = None,
  reserves: Option[ReserveRules] = None,
  cells: Option[CellRules] = None,
  foundations: Seq[FoundationRules] = Nil,
  tableaus: Seq[TableauRules] = Nil,
  pyramids: Seq[PyramidRules] = Nil,
  special: Option[SpecialRules] = None
) extends GameRulesHelper
