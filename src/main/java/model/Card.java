package model;

import com.google.gson.annotations.SerializedName;
import controller.Limit;

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
    protected CardType cardType;
    public boolean isActivated() {
        return isActivated;
    }

    public void setActivated(boolean activated) {
        isActivated = activated;
    }

    private boolean isActivated;


    public Boolean getHidden() {
        return isHidden;
    }

    public void setHidden(Boolean hidden) {
        isHidden = hidden;
    }

    protected Boolean isHidden = true ;

    public Zone getCardPlacedZone() {
        return cardPlacedZone;
    }

    public void setCardPlacedZone(Zone cardPlacedZone) {
        this.cardPlacedZone = cardPlacedZone;
    }

    protected Zone  cardPlacedZone ;


//    public int getPlacedZoneNumber() {
//        return placedZoneNumber;
//    }
//
//    public void setPlacedZoneNumber(int placedZoneNumber) {
//        this.placedZoneNumber = placedZoneNumber;
//    }
    //protected int placedZoneNumber = 0; //if ==0 means not numerical zone
    @SerializedName("Price")
    protected int price;

    public Card(String cardDescription, String cardName, String cardNumber, CardType cardType) {
        this.cardDescription = cardDescription;
        this.cardName = cardName;
        this.cardNumber = cardNumber;
        this.cardType = cardType;
    }

    public static Card getCardByName(String cardName) {
        for (Card card : allCards) {
            if (card.getCardName().equals(cardName))
                return card;
        }
        return null;
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


    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }



    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public enum CardType {
        MONSTER(),
        MAGIC();
    }
}
