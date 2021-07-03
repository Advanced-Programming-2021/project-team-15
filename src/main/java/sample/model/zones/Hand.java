package sample.model.zones;

import sample.model.cards.Card;

import java.util.ArrayList;

public class Hand extends Zone {
    ArrayList<Card> zoneCards;

    public Hand() {
        super(ZoneType.HAND);
        zoneCards = new ArrayList<>();
        for (int i = 0; i <= 4; i++)
            zoneCards.add(null);
    }

    public void reset() {
        zoneCards.clear();
        for (int i = 0; i <= 4; i++)
            zoneCards.add(null);
    }

    public ArrayList getCardsInHand() {
        return zoneCards;
    }

    public Card getCardByPlaceNumber(int number) {
        return zoneCards.get(number);
    }

    public void addCardToHand(Card card) {
        for (int i = 0; i < zoneCards.size(); i++) {
            if (zoneCards.get(i) == null) {
                card.setCardPlacedZone(this);
                zoneCards.set(i, card);
                return;
            }
        }
        card.setCardPlacedZone(this);
        zoneCards.add(card);

    }

    public int getNumberOfCardsInHand() {
        int i = 0;
        for (Card card : zoneCards) {
            if (card != null)
                i++;
        }
        return i;
    }

    public void removeCardFromHand(Card card) {
        zoneCards.remove(card);
        zoneCards.add(null);
    }

    public Boolean isExist(Card card) {
        for (Card card1 : zoneCards) {
            if (card == card1)
                return true;
        }
        return false;

    }


    public ArrayList<Card> getZoneCards() {
        return zoneCards;
    }

    public void setZoneCards(ArrayList<Card> zoneCards) {
        this.zoneCards = zoneCards;
    }
}