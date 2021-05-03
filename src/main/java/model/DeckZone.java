package model;

import java.util.ArrayList;

public class DeckZone extends Zone{
    ArrayList<Card> zoneCards;

    public DeckZone() {
        super(ZoneType.DECK);
        zoneCards = new ArrayList<>();
    }
    public void addCardToDeckZone(Card card)
    {
        zoneCards.add(card);
    }
    public void removeCardFromDeckZone(Card card)
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