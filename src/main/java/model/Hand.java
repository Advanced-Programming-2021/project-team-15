package model;

import java.util.HashMap;

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


}
