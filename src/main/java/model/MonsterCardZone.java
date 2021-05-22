package model;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public class MonsterCardZone extends NumericZone{

    public MonsterCardZone() {
        super(ZoneType.MONSTER_CARD , new TreeMap<>());
        for (int i = 1 ; i<5; i++)
            zoneCards.put(i, null);
    }

    public MonsterCard getCardByPlaceNumber(int number)
    {
        return (MonsterCard) zoneCards.get(number);
    }
    public void moveToFirstEmptyPlace(Card card) {
        for (int key= 1; key <=5  ; key ++) {
            if (zoneCards.get(key) == null) {
                zoneCards.get(key).setCardPlacedZone(this);
                zoneCards.put(key,card);
                return;
            }
        }
    }

    public void summonOrSetMonster(MonsterCard monsterCard, Player currentPlayer)
    {
        currentPlayer.getHand().removeCardFromHand(monsterCard);
        for(Integer key : zoneCards.keySet()) {
          if(zoneCards.get(key)==null) {
              zoneCards.put(key, monsterCard);
              monsterCard.setCardPlacedZone(this);
              return;
          }
        }
    }
    public Boolean isEmpty()
    {
        for(Integer key : zoneCards.keySet()){
            if(zoneCards.get(key)!=null)
                return false;
        }
        return true;
    }
    public String toStringPos(int i)
    {  if(zoneCards.get(i)==null)
        return "E";
        else return ((MonsterCard)zoneCards).toStringPosition();
    }


}