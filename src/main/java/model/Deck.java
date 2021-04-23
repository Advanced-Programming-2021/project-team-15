package model;

import java.util.ArrayList;

public class Deck {
    private String name;
    private String owner;
    private ArrayList<Card> sideDeck;
    private ArrayList<Card> mainDeck;
    private Boolean isActive = false;
    public Deck( String owner , String  name)
    {
        this.name= name;
        this.owner= owner;
        sideDeck = new ArrayList<>();
        mainDeck = new ArrayList<>();
    }
    public void addCardToSideDeck(Card card)
    {
        sideDeck.add(card);
    }
    public void addCardToMainDeck(Card card)
    {
        mainDeck.add(card);
    }
    public void removeCardFromMainDeck(Card card)
    {
        mainDeck.remove(card);
    }
    public void removeCardFromSideDeck(Card card)
    {
        sideDeck.remove(card);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
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

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}
