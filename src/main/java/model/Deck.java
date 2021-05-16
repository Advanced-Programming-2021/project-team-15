package model;

import controller.MenuController;

import java.util.ArrayList;

public class Deck {
    public transient final int mainDeckMinCardCount = 40;
    public transient final int mainDeckMaxCardCount = 60;
    public transient final int sideDeckMinCardCount = 0;
    public transient final int sideDeckMaxCardCount = 15;
    public transient final int DeckMaxSpecifiedCardCount = 3;
    private String name;
    private transient User owner;
    private ArrayList<Card> sideDeck;
    private ArrayList<Card> mainDeck;
    private boolean isActive;
    private boolean isValid;

    public Deck(User owner, String name) {
        this.name = name;
        this.owner = owner;
        sideDeck = new ArrayList<>();
        mainDeck = new ArrayList<>();
        this.owner.addDeck(this);
    }

    public void addCardToDeck(Card card, DeckType deckType) {
        if (deckType == DeckType.MAIN)
            mainDeck.add(card);
        else if (deckType == DeckType.SIDE)
            sideDeck.add(card);
    }

    public void removeCardFromDeckByName(String cardName, DeckType deckType) {
        if (deckType == DeckType.MAIN)
            mainDeck.remove(getCardByName(cardName, deckType));
        else if (deckType == DeckType.SIDE)
            sideDeck.remove(getCardByName(cardName, deckType));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Card> getSideDeck() {
        return sideDeck;
    }

    public void setSideDeck(ArrayList<Card> sideDeck) {
        this.sideDeck = sideDeck;
    }

    public ArrayList<Card> getMainDeck() {
        return mainDeck;
    }

    public void setMainDeck(ArrayList<Card> mainDeck) {
        this.mainDeck = mainDeck;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public Card getCardByName(String cardName, DeckType deckType) {
        ArrayList<Card> deck = new ArrayList<>();
        if (deckType == DeckType.MAIN)
            deck = getMainDeck();
        else if (deckType == DeckType.SIDE)
            deck = getSideDeck();
        for (Card card : deck) {
            if (card.getCardName().equals(cardName))
                return card;
        }
        return null;
    }

    public int getSpecifiedCardCountInDeckByName(String cardName) {
        int count = 0;
        ArrayList<ArrayList<Card>> mainAndSideDeck = new ArrayList<>();
        mainAndSideDeck.add(mainDeck);
        mainAndSideDeck.add(sideDeck);
        for (ArrayList<Card> deckToCountCards : mainAndSideDeck) {
            for (Card card : deckToCountCards) {
                if (card.getCardName().equals(cardName))
                    count++;
            }
        }
        return count;
    }

    public enum DeckType {
        MAIN,
        SIDE
    }
}
