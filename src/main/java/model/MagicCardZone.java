package model;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public class MagicCardZone extends NumericZone{

    public MagicCardZone() {
        super(ZoneType.MAGIC_CARD , new TreeMap<>());
    }
    public void addCardToMagicCardZone(MagicCard magicCard)
    {
    }
    public void removeMagicCard(MagicCard magicCard)
    {
        //dont know
    }
    public MagicCard getCardByPlaceNumber(int number)
    {
        return (MagicCard) zoneCards.get(number);
    }
    public void moveToFirstEmptyPlaceForActivation(MagicCard magicCard , Player  currentPlayer) {
        currentPlayer.getHand().removeCardFromHand(magicCard);
        for (Integer key : zoneCards.keySet()) {
            if (zoneCards.get(key) == null) {
                zoneCards.put(key, magicCard);
                magicCard.setCardPlacedZone(this);
            }
        }

    }
    public void removeCardFromMagicCardZone(MagicCard card)
    {

        for(Map.Entry<Integer , Card> entry : this.zoneCards.entrySet()) {
            if(entry.getValue() ==card)
            { zoneCards.put(entry.getKey() , null);
                break;
            }
        }
    }
}