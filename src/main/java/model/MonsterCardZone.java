package model;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public class MonsterCardZone extends NumericZone{

    public MonsterCardZone() {
        super(ZoneType.MONSTER_CARD , new TreeMap<>());
    }

    public MonsterCard getCardByPlaceNumber(int number)
    {
        return (MonsterCard) zoneCards.get(number);
    }


    public void summonOrSetMonster(MonsterCard monsterCard, Player currentPlayer)
    {
        currentPlayer.getHand().removeCardFromHand(monsterCard);
        for(Integer key : zoneCards.keySet()) {
          if(zoneCards.get(key)==null) {
              zoneCards.put(key, monsterCard);
              monsterCard.setCardPlacedZone(currentPlayer.getZoneByZoneType(ZoneType.MONSTER_CARD));
              //monsterCard.setPlacedZoneNumber(key);
              break;
          }
        }
    }
    public Boolean isEmpty()
    {
        for(Integer key : zoneCards.keySet()){
            if(zoneCards.get(key)==null)
                return false;
        }
        return true;
    }


}