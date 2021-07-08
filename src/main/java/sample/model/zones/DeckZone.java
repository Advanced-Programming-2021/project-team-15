package sample.model.zones;

import sample.controller.gamePlayController.GamePlayController;
import sample.model.cards.Card;

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
       if(GamePlayController.getInstance().checkIfGameIsFinished())
           GamePlayController.getInstance().defineWinner();
    }

    public ArrayList<Card> getZoneCards() {
        return zoneCards;
    }

    public void setZoneCards(ArrayList<Card> zoneCards) {
        this.zoneCards = zoneCards;
    }
}