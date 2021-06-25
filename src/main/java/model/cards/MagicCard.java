package model.cards;

import com.google.gson.annotations.SerializedName;
import model.cards.Card;

public class MagicCard extends Card {

    @SerializedName("Type")
    private MagicType magicType;
    @SerializedName("Status")
    private Status status;
    @SerializedName("Icon (Property)")
    private CardIcon cardIcon;

    public MagicCard(String cardDescription, String cardName, String cardNumber, CardType cardType) {
        super(cardDescription, cardName, cardNumber, cardType);
        super.type = "MAGIC";
        this.cardType = CardType.MAGIC;
    }

    public MagicType getMagicType() {
        return magicType;
    }

    public void setMagicType(MagicType magicType) {
        this.magicType = magicType;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public CardIcon getCardIcon() {
        return cardIcon;
    }

    public void setCardIcon(CardIcon cardIcon) {
        this.cardIcon = cardIcon;
    }

    public String toString() {
        StringBuilder info = new StringBuilder();
        info.append("Name: ").append(cardName).append("\n");
        info.append(magicType).append("\n");
        //SerializedName a = cardIcon.getAnnotation(SerializedName.class);
        //a.value()
        info.append("Type: ").append(cardIcon.getName()).append("\n");
        info.append("Description: ").append(cardDescription);
        return info.toString();
    }

    public enum MagicType {
        @SerializedName("Spell") SPELL,
        @SerializedName("Trap") TRAP;

        public static MagicType getMagicType(String magicType) {
            switch (magicType) {
                case "Spell" : return SPELL;
                case "Trap" : return TRAP;
                default:return null;
            }
        }
    }

    public enum Status {
        @SerializedName("Limited") LIMITED,
        @SerializedName("Unlimited") UNLIMITED;

        public static Status getStatus(String status) {
            switch (status) {
                case "Limited" : return LIMITED;
                case "unLimited" : return UNLIMITED;
                default:return null;
            }
        }
    }

    public enum CardIcon {
        @SerializedName("Equip") EQUIP("Equip"),
        @SerializedName("Field") FIELD("Field"),
        @SerializedName("Quick-play") QUICK_PLAY("Quick-play"),
        @SerializedName("Ritual") RITUAL("Ritual"),
        @SerializedName("Continuous") CONTINUOUS("Continuous"),
        @SerializedName("Counter") COUNTER("Counter"),
        @SerializedName("Normal") NORMAL("Normal");
        private final String name;

        public static CardIcon getCardIcon(String cardIcon) {
            switch (cardIcon) {
                case "Normal":
                    return NORMAL;
                case "Counter":
                    return COUNTER;
                case "Continuous":
                    return CONTINUOUS;
                case "Quick-play":
                    return QUICK_PLAY;
                case "Field":
                    return FIELD;
                case "Equip":
                    return EQUIP;
                case "Ritual":
                    return RITUAL;
                default:
                    return null;
            }
        }

        CardIcon(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }
    }
}
