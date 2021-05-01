package model;

import com.google.gson.annotations.SerializedName;

public class MagicCard extends Card{

    @SerializedName("Type") private MagicType magicType;
    @SerializedName("Status") private Status status;
    @SerializedName("Icon (Property)") private CardIcon cardIcon;
    private boolean isActive;

    public MagicCard(String cardDescription, String cardName, String cardNumber, CardType cardType) {
        super(cardDescription, cardName, cardNumber, cardType);
    }

    public MagicType getMagicType() {
        return magicType;
    }

    public void setMagicType(MagicType magicType) {
        this.magicType = magicType;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
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

    public enum MagicType {
        @SerializedName("Spell") SPELL,
        @SerializedName("Trap") TRAP
    }

    enum Status {
        @SerializedName("Limited") LIMITED,
        @SerializedName("Unlimited") UNLIMITED
    }

    enum CardIcon {
        @SerializedName("Equip") EQUIP,
        @SerializedName("Field") FIELD,
        @SerializedName("Quick-play") QUICK_PLAY,
        @SerializedName("Ritual") RITUAL,
        @SerializedName("Continuous") CONTINUOUS,
        @SerializedName("Counter") COUNTER,
        @SerializedName("Normal") NORMAL
    }
}
