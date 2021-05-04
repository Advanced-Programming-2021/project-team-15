package model;

import java.util.ArrayList;

public class GraveyardZone extends Zone {
    ArrayList<Card> zoneCards;
    public GraveyardZone() {
        super(ZoneType.GRAVEYARD);
        zoneCards = new ArrayList<>();
    }
    public void addCardToGraveyardZone(Card card)
    {
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