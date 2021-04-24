package model;

public class MagicCard extends Card{

    protected MagicType magicType;
    protected boolean isActive;

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

    enum MagicType {
        SPELL,
        TRAP
    }
}
