package model;

import java.util.HashMap;

public class MagicCardZone extends NumericZone{

    public MagicCardZone() {
        super(ZoneType.MAGIC_CARD , new HashMap<>());
    }
    public void addCardToMagicCardZone(MagicCard magicCard)
    { zoneCards.remove(magicCard);
     //dont know
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
