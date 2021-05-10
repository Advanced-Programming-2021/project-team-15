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
     public void reset()
     {    zoneCards.clear();
         for (int i = 1 ; i<5; i++)
             zoneCards.put(i, null);
     }

    public void moveCardToGraveyard(int address ,Player player)
    {
        //zoneCards.get(address).setPlacedZoneNumber(0);

        zoneCards.get(address).setCardPlacedZone(player.getZoneByZoneType(ZoneType.GRAVEYARD));
        player.getGraveyardZone().addCardToGraveyardZone(zoneCards.get(address));
        zoneCards.put(address , null);
    }
    public int  getNumberOfCard()
    {    int i =0 ;
        for(Map.Entry entry : zoneCards.entrySet())
        {   if(entry != null)
            i++;
        }
        return i;
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