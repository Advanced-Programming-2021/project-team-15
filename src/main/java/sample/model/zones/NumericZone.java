package sample.model.zones;

import sample.controller.gamePlayController.GamePlayController;
import sample.model.Player;
import sample.model.cards.Card;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

public class NumericZone extends Zone {
    protected Map<Integer, Card> zoneCards;

    public NumericZone(ZoneType zoneType, TreeMap<Integer, Card> zoneCards) {
        super(zoneType);
        this.zoneCards = zoneCards;
    }

    public void removeCardFromZone(Card card) {
        for (int key = 1; key <= 5; key++) {
            if (zoneCards.get(key) == card) {
                zoneCards.put(key, null);
                return;
            }
        }
    }

    public Card getThisCardActivated(String name) {
        for (int key = 1; key <= 5; key++) {
            if (zoneCards.get(key) != null && zoneCards.get(key).getCardName().equals(name) && zoneCards.get(key).isActivated())
                return zoneCards.get(key);
        }
        return null;
    }

    public Card getThisCardByName(String name) {
        for (int key = 1; key <= 5; key++) {
            if (zoneCards.get(key) != null && zoneCards.get(key).getCardName().equals(name))
                return zoneCards.get(key);
        }
        return null;
    }

    public void reset() {
        zoneCards.clear();
        for (int i = 1; i <= 5; i++)
            zoneCards.put(i, null);
    }

    public void moveCardToGraveyardWithoutAddress(Card card, Player player) throws IOException {
        for (Map.Entry<Integer, Card> entry : zoneCards.entrySet()) {
            if (card == entry.getValue()) {
                zoneCards.put(entry.getKey(), null);
                GamePlayController.getInstance().getDuelMenu().removeCard(card,entry.getKey());
                player.getGraveyardZone().addCardToGraveyardZone(card);
            }
        }
    }
    public int getPlace(Card card)
    {   for (int key = 1; key <= 5; key++)
    { if(zoneCards.get(key)==card)
        return key;
    }
    return 0;
    }
    public void moveCardToGraveyard(int address, Player player) throws IOException {
        player.getGraveyardZone().addCardToGraveyardZone(zoneCards.get(address));
        GamePlayController.getInstance().getDuelMenu().removeCard(zoneCards.get(address),address);
        zoneCards.put(address, null);
    }

    public Boolean isExist(Card card) {
        for (Map.Entry<Integer, Card> entry : zoneCards.entrySet()) {
            if (entry.getValue() == card)
                return true;
        }
        return false;
    }

    public int getNumberOfCard() {
        int i = 0;
        for (Map.Entry<Integer, Card> entry : zoneCards.entrySet()) {
            if (entry.getValue() != null)
                i++;
        }
        return i;
    }

    public Card getCardByPlaceNumber(int number) {
        return zoneCards.get(number);
    }

    public Map getZoneCards() {
        return zoneCards;
    }

    public void setZoneCards(TreeMap zoneCards) {
        this.zoneCards = zoneCards;
    }


}