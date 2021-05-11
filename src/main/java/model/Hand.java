package model;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class Hand extends Zone{
    ArrayList<Card> zoneCards;

    public Hand() {
        super(ZoneType.HAND);
        zoneCards = new ArrayList<>();
        for(int i = 0 ; i <4 ; i ++)
            zoneCards.add(null);
    }
    public void reset()
    {  zoneCards.clear();
        for(int i = 0 ; i <4 ; i ++)
            zoneCards.add(null);
    }
    public ArrayList getCardsInHand()
    {
        return zoneCards;
    }
    public Card getCardByPlaceNumber(int number)
    {
        return zoneCards.get(number);
    }
    public void addCardToHand(Card card)
    {   for(int i=0 ;  i < zoneCards.size() ; i ++)
    {
        if(zoneCards.get(i) ==null)
        {
            card.setCardPlacedZone(this);
            zoneCards.set(i , card);
            return;
        }
    }
        card.setCardPlacedZone(this);
    zoneCards.add(card);

    }
    public void removeCardFromHand(Card card)
    {
       zoneCards.remove(card);
       zoneCards.add(null);
    }


}