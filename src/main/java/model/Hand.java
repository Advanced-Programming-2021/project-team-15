package model;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class Hand extends Zone{
    ArrayList<Card> zoneCards;

    public Hand() {
        super(ZoneType.HAND);
        zoneCards = new ArrayList<>();
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
    {
        //dont know yet
    }
    public void removeCardFromHand(Card card)
    {
       zoneCards.remove(card);
    }


}