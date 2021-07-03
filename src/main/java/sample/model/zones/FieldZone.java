package sample.model.zones;

import sample.model.Player;
import sample.model.cards.MagicCard;

import java.util.ArrayList;

public class FieldZone extends Zone {
    ArrayList<MagicCard> zoneCards;

    public FieldZone() {
        super(ZoneType.FIELD);
        zoneCards = new ArrayList<>();
    }

    public void moveCardToFieldZone(MagicCard card, Player player) {
        if (zoneCards.isEmpty())
            zoneCards.add(card);
        else {
            player.getGraveyardZone().addCardToGraveyardZone(zoneCards.get(0));
            zoneCards.remove(0);
            zoneCards.add(card);
        }
        card.setCardPlacedZone(this);
    }


    public void removeCardFromFieldZone(MagicCard magicCard) {
        zoneCards.remove(magicCard);
    }

    public ArrayList<MagicCard> getZoneCards() {
        return zoneCards;
    }

    public void setZoneCards(ArrayList<MagicCard> zoneCards) {
        this.zoneCards = zoneCards;
    }

    public String toStringPos() {
        if (zoneCards.size()==0)
            return "E";
        else return "O";
    }
}