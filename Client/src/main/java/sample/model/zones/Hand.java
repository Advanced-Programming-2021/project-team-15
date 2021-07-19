package sample.model.zones;

import sample.controller.gamePlayController.GamePlayController;
import sample.model.cards.Card;

import java.util.ArrayList;

public class Hand extends Zone {
    ArrayList<Card> zoneCards;

    public Hand() {
        super(ZoneType.HAND);
        zoneCards = new ArrayList<>();
    }


    public ArrayList getCardsInHand() {
        return zoneCards;
    }

    public Card getCardByPlaceNumber(int number) {
        return zoneCards.get(number);
    }

    public void addCardToHand(Card card) {
        GamePlayController.getInstance().getDuelMenu().addToHand(zoneCards.size(),card);
        zoneCards.add(card);
        card.setCardPlacedZone(this);

    }

    public int getNumberOfCardsInHand() {
      return zoneCards.size();
    }

    public void removeCardFromHand(Card card) {
        int i = zoneCards.indexOf(card);
        zoneCards.remove(card);
        GamePlayController.getInstance().getDuelMenu().removeFromHand(i,card.getOwner());
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