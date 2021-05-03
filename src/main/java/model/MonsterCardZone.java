package model;

import java.util.HashMap;

public class MonsterCardZone extends NumericZone{

    public MonsterCardZone() {
        super(ZoneType.MONSTER_CARD , new HashMap<>());
    }
    public void addCardToMagicCardZone(MonsterCard monsterCard)
    {
        //dont know
    }
    public void removeMagicCard(MonsterCard monsterCard)
    {
        //dont know
    }
    public MonsterCard getCardByPlaceNumber(int number)
    {
        return (MonsterCard) zoneCards.get(number);
    }
}
