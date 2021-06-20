package model;

import controller.GamePlayController;
import controller.SpellEffectController;

import java.util.ArrayList;

public class GraveyardZone extends Zone {
    private SpellEffectController spellEffectController = new SpellEffectController();
    ArrayList<Card> zoneCards;
    public GraveyardZone() {
        super(ZoneType.GRAVEYARD);
        zoneCards = new ArrayList<>();
    }
    public void reset()
    {
        zoneCards.clear();
    }
    public void addCardToGraveyardZone(Card card)
    {  if(card.getCardName().equals("Yami") && card.isActivated())
           spellEffectController.yami(false);
        else if(card.getCardName().equals("Forest") &&  card.isActivated())
            spellEffectController.forest(false);
//        else if(card.getCardName().equals("Closed Forest") &&  card.isActivated())
//            spellEffectController.closedForest(false);
          else if(card.getCardName().equals("Umiiruka") &&  card.isActivated())
              spellEffectController.forest(false);
        GamePlayController.getInstance().getActivatedCards().values().remove(card);
        if(card instanceof  MonsterCard)
    {   ((MonsterCard)card).setGameDEF(((MonsterCard) card).getDefensePoint());
        ((MonsterCard)card).setGameATK(((MonsterCard) card).getAttackPoint());
    }
        resetCard(card);
        card.setCardPlacedZone(this);
        zoneCards.add(card);
    }
    public void resetCard(Card card)
    {   if(card instanceof MonsterCard)
        ((MonsterCard) card).setSummoned(false);
        card.setSet(false);
        card.setActivated(false);

    }
    public void removeCardFromGraveyardZone(Card card)
    {
        zoneCards.remove(card);
    }

    public ArrayList<Card> getZoneCards() {
        return zoneCards;
    }

    public void setZoneCards(ArrayList<Card> zoneCards) {
        this.zoneCards = zoneCards;
    }
}