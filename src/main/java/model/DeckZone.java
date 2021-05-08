package model;

import java.util.ArrayList;

public class DeckZone extends Zone{
    ArrayList<Card> zoneCards;

    public DeckZone() {
        super(ZoneType.DECK);
        zoneCards = new ArrayList<>();
    }
    public void addCardToDeckZone(Card card)
    {
        zoneCards.add(card);
    }
    public void removeCardFromDeckZone(Card card)
    {
        zoneCards.remove(card);
    }

    public ArrayList<Card> getZoneCards() {
        return zoneCards;
    }

    public void setZoneCards(ArrayList<Card> zoneCards) {
        this.zoneCards = zoneCards;
    }
    public MagicCard searchForMagicCardByIcon(MagicCard.CardIcon cardIcon){
        for(Card card: zoneCards){
            if(card instanceof MagicCard && ((MagicCard) card).getCardIcon()==cardIcon){
                return (MagicCard)card;
            }
        }
        return null;
    }
    public MonsterCard searchForMonsterCardByType(MonsterCard.MonsterType cardType){
        for(Card card: zoneCards){
            if(card instanceof MonsterCard && ((MonsterCard) card).getMonsterType()==cardType){
                return (MonsterCard)card;
            }
        }
        return null;
    }
}