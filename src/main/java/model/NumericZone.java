package model;

import java.util.HashMap;

public class NumericZone extends Zone {
    protected HashMap<Integer, Card> zoneCards;

    public NumericZone(ZoneType zoneType , HashMap<Integer , Card> zoneCards) {
        super(zoneType);
        this.zoneCards = zoneCards;
    }
    public Card getCardByPlaceNumber(int number)
    {
        return zoneCards.get(number);
    }

    public HashMap getZoneCards() {
        return zoneCards;
    }

    public void setZoneCards(HashMap zoneCards) {
        this.zoneCards = zoneCards;
    }
}