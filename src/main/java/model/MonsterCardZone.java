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
    public int  getNumberOfMonsterCard()
    {    int i =0 ;
        for(Map.Entry entry : zoneCards.entrySet())
        {   if(entry != null)
            i++;
        }
        return i;
    }
    public void summonOrSetMonster(MonsterCard monsterCard, Player currentPlayer)
    {
        currentPlayer.getHand().removeCardFromHand(monsterCard);
        for(Integer key : zoneCards.keySet()) {
          if(zoneCards.get(key)==null) {
              zoneCards.put(key, monsterCard);
              monsterCard.setPlacedZone(ZoneType.MONSTER_CARD);
              //monsterCard.setPlacedZoneNumber(key);
              break;
          }
        }
    }
    public void moveMonsterCardToGraveyard(int address ,Player player)
    {
        //zoneCards.get(address).setPlacedZoneNumber(0);
        zoneCards.get(address).setPlacedZone(ZoneType.GRAVEYARD);
        player.getGraveyardZone().addCardToGraveyardZone(zoneCards.get(address));
        zoneCards.put(address , null);
    }
}