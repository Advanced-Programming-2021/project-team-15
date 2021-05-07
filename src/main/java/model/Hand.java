package model;

import java.util.Map;
import java.util.TreeMap;

public class Hand extends NumericZone{
    public Hand() {
        super(ZoneType.HAND, new TreeMap<Integer ,Card>());
    }
    public TreeMap getCardsInHand()
    {
        return zoneCards;
    }
    public Card getCardByPlaceNumber(int number)
    {
        return zoneCards.get(number);
    }
    public void addCardToHand(Card card)
    {
        //dont know yet
    }
    public void removeCardFromHand(Card card)
    {

        for(Map.Entry<Integer , Card> entry : zoneCards.entrySet()) {
          if(entry.getValue() ==card)
          { zoneCards.put(entry.getKey() , null);
           break;
          }
        }
//        if(card.getPlacedZoneNumber()!=0)
//        zoneCards.put(card.getPlacedZoneNumber(), null);
//        card.setPlacedZoneNumber(0);
    }


}