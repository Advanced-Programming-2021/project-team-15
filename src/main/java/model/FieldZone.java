package model;

import java.util.ArrayList;

public class FieldZone extends Zone{
    ArrayList<MagicCard> zoneCards;

    public FieldZone() {
        super(ZoneType.FIELD);
        zoneCards = new ArrayList<>();
    }
    public void addCardToFieldZone(MagicCard magicCard)
    {
        zoneCards.add(magicCard);
    }
    public void removeCardFromFieldZone(MagicCard magicCard)
    {
        zoneCards.remove(magicCard);
    }

    public ArrayList<MagicCard> getZoneCards() {
        return zoneCards;
    }

    public void setZoneCards(ArrayList<MagicCard> zoneCards) {
        this.zoneCards = zoneCards;
    }
}