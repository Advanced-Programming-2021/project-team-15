package model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Card {

    private static ArrayList<Card> allCards;

    static {
        allCards = new ArrayList<>();
    }

    //    protected ArrayList<CardAction> cardActions ;
    @SerializedName("Name")
    protected String cardName;
    @SerializedName("Description")
    protected String cardDescription;
    protected String cardNumber;
    protected Boolean isSet = false;
    protected Boolean isSelected = false;
    protected CardType cardType;
    protected Zone placedZone;
    @SerializedName("Price")
    protected int price;

    public Card(String cardDescription, String cardName, String cardNumber, CardType cardType) {
        this.cardDescription = cardDescription;
        this.cardName = cardName;
        this.cardNumber = cardNumber;
        this.cardType = cardType;
    }

    public static void addCard(Card card) {
        allCards.add(card);
    }

    public static ArrayList<Card> getAllCards() {
        return allCards;
    }

    public static void setAllCards(ArrayList<Card> allCards) {
        Card.allCards = allCards;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCardDescription() {
        return cardDescription;
    }

    public void setCardDescription(String cardDescription) {
        this.cardDescription = cardDescription;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Boolean getSet() {
        return isSet;
    }

    public void setSet(Boolean set) {
        isSet = set;
    }

    public Boolean getSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public Zone getPlacedZone() {
        return placedZone;
    }

    public void setPlacedZone(Zone placedZone) {
        this.placedZone = placedZone;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public enum CardType {
        MONSTER,
        MAGIC
    }
}
