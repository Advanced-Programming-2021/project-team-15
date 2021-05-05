package model;

import controller.GamePlayController;

import java.util.HashMap;
import java.util.Map;

public class Hand extends NumericZone{
    public Hand() {
        super(ZoneType.HAND, new HashMap<Integer ,Card>());
    }
    public HashMap getCardsInHand()
    {
        return zoneCards;
    }
    public Card getCardByPlaceNumber(int number)
    {
        return zoneCards.get(number);
    }
    public void addCardToHand(Card card)
    {
        //dont know
    }
    public void removeCardFromHand(Card card)
    {
        //dont know
    }
    public int numberOfCardsInaHand() {
        int count= 0 ;
        for(Map.Entry<Integer,Card> entry : zoneCards.entrySet())
        { if(entry!=null)
            count++;
        }
        return count;
    }


}