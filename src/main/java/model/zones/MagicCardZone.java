package model.zones;

import model.Player;
import model.cards.Card;
import model.cards.MagicCard;

import java.util.TreeMap;

public class MagicCardZone extends NumericZone {

    public MagicCardZone() {
        super(ZoneType.MAGIC_CARD, new TreeMap<>());
        for (int i = 1; i <= 5; i++)
            zoneCards.put(i, null);
    }

    public void moveToFirstEmptyPlace(Card card) {
        for (int key = 1; key <= 5; key++) {
            if (zoneCards.get(key) == null) {
                zoneCards.get(key).setCardPlacedZone(this);
                zoneCards.put(key, card);
                return;
            }
        }
    }

    public void removeMagicCard(MagicCard magicCard) {
        //dont know
    }

    public MagicCard getCardByPlaceNumber(int number) {
        return (MagicCard) zoneCards.get(number);
    }

    public void moveToFirstEmptyPlaceFromHand(MagicCard magicCard, Player currentPlayer) {
        currentPlayer.getHand().removeCardFromHand(magicCard);
        for (int key = 1; key <= 5; key++) {
            if (zoneCards.get(key) == null) {
                zoneCards.put(key, magicCard);
                magicCard.setCardPlacedZone(this);
                return;
            }
        }
    }

    public String toStringPos(int i) {
        if (zoneCards.get(i) == null)
            return "E";
        else if (zoneCards.get(i).getHidden())
            return "H";
        else return "O";
    }

}