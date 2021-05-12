package model;

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
    {  if(card.getCardName().equals("Yami") && !card.getHidden())
           spellEffectController.yami(false);
        else if(card.getCardName().equals("Forest") && !card.getHidden())
            spellEffectController.forest(false);
          else if(card.getCardName().equals("Umiiruka") && !card.getHidden())
              spellEffectController.forest(false);





        if(card instanceof  MonsterCard)
    {   ((MonsterCard)card).setGameDEF(((MonsterCard) card).getDefensePoint());
        ((MonsterCard)card).setGameATK(((MonsterCard) card).getAttackPoint());

    }

        card.setCardPlacedZone(this);
        zoneCards.add(card);
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