package model;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public class NumericZone extends Zone {
    protected TreeMap<Integer, Card> zoneCards;

    public NumericZone(ZoneType zoneType , TreeMap<Integer , Card> zoneCards) {
        super(zoneType);
        this.zoneCards = zoneCards;
    }
    public Card getCardByPlaceNumber(int number)
    {
        return zoneCards.get(number);
    }

    public TreeMap getZoneCards() {
        return zoneCards;
    }

    public void setZoneCards(TreeMap zoneCards) {
        this.zoneCards = zoneCards;
    }
}