package model;

import java.util.HashMap;
import java.util.LinkedHashMap;
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
}