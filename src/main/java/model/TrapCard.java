package model;

public class TrapCard extends MagicCard {
    private TrapCardIcon trapCardIcon;

    public TrapCard(String cardDescription, String cardName, String cardNumber, CardType cardType) {
        super(cardDescription, cardName, cardNumber, cardType);
    }

    public TrapCardIcon getTrapCardIcon() {
        return trapCardIcon;
    }

    public void setTrapCardIcon(TrapCardIcon trapCardIcon) {
        this.trapCardIcon = trapCardIcon;
    }

    enum TrapCardIcon {
        CONTINUOUS,
        COUNTER,
        NORMAL
    }
}
