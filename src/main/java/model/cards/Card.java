package model.cards;

import com.google.gson.annotations.SerializedName;
import model.Player;
import model.zones.Zone;

import java.util.ArrayList;

public class Card {

    private static ArrayList<Card> allCards;

    static {
        allCards = new ArrayList<>();
    }

    public Boolean isSummoned = false;
    protected transient Player owner;
    //    protected ArrayList<CardAction> cardActions ;
    @SerializedName("Name")
    protected String cardName;
    @SerializedName("Description")
    protected String cardDescription;
    protected String cardNumber;
    @SerializedName("Card Type")
    protected CardType cardType;
    protected String type = "type";
    protected transient Boolean isSet = false;
    protected transient Boolean isHidden = true;
    protected transient Zone cardPlacedZone;
    @SerializedName("Price")
    protected int price;
    private transient boolean isActivated;
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

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null)
            return false;
        if (object == this)
            return true;
        if (!(object instanceof Card)) return false;
        Card card = (Card) object;
        return card.cardName.equals(cardName);
    }

    public Boolean getSummoned() {
        return isSummoned;
    }

    public void setSummoned(Boolean summoned) {
        isSummoned = summoned;
    }

    public Boolean getSet() {
        return isSet;
    }

    public void setSet(Boolean set) {
        isSet = set;
    }

    public boolean isActivated() {
        return isActivated;
    }

    public void setActivated(boolean activated) {
        isActivated = activated;
    }

    public Boolean getHidden() {
        return isHidden;
    }

    public void setHidden(Boolean hidden) {
        isHidden = hidden;
    }

    public Zone getCardPlacedZone() {
        return cardPlacedZone;
    }

    public void setCardPlacedZone(Zone cardPlacedZone) {
        this.cardPlacedZone = cardPlacedZone;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String cardShow() {
        StringBuilder details = new StringBuilder();
        if (this instanceof MonsterCard) {
            details.append("Name : ").append(this.getCardName()).append("\n");
            details.append("Level : ").append(((MonsterCard) this).getLevel()).append("\n");
            details.append("Type : ").append(((MonsterCard) this).getMonsterType()).append("\n");
            details.append("Attack : ").append(((MonsterCard) this).getAttackPoint()).append("\n");
            details.append("Defense : ").append(((MonsterCard) this).getDefensePoint()).append("\n");
            details.append("Description : ").append(this.getCardDescription());
        } else if (this instanceof MagicCard) {
            details.append("Name : ").append(this.getCardName()).append("\n");
            details.append(((MagicCard) this).getMagicType()).append("\n");
            details.append("Type : ").append(((MagicCard) this).getCardIcon()).append("\n");
            details.append("Description : ").append(this.getCardDescription());
        }
        return details.toString();
    }

    public enum CardType {
        MONSTER,
        MAGIC;
    }
}
