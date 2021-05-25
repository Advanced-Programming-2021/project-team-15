package model;

import com.google.gson.annotations.SerializedName;

public class MagicCard extends Card {

    @SerializedName("Type")
    private MagicType magicType;
    @SerializedName("Status")
    private Status status;
    @SerializedName("Icon (Property)")
    private CardIcon cardIcon;

    public MagicCard(String cardDescription, String cardName, String cardNumber, CardType cardType) {
        super(cardDescription, cardName, cardNumber, cardType);
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
        @SerializedName("Trap") TRAP
    }

    enum Status {
        @SerializedName("Limited") LIMITED,
        @SerializedName("Unlimited") UNLIMITED
    }

    public enum CardIcon {
        @SerializedName("Equip") EQUIP("Equip"),
        @SerializedName("Field") FIELD("Field"),
        @SerializedName("Quick-play") QUICK_PLAY("Quick-play"),
        @SerializedName("Ritual") RITUAL("Ritual"),
        @SerializedName("Continuous") CONTINUOUS("Continuous"),
        @SerializedName("Counter") COUNTER("Counter"),
        @SerializedName("Normal") NORMAL("Normal");
        private String name;

        CardIcon(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }
    }
}
