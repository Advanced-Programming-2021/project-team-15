package model;

public class SpellCard extends MagicCard {

    private SpellCardIcon spellCardIcon;

    public SpellCard(String cardDescription, String cardName, String cardNumber, CardType cardType) {
        super(cardDescription, cardName, cardNumber, cardType);
    }

    public SpellCardIcon getSpellCardIcon() {
        return spellCardIcon;
    }

    public void setSpellCardIcon(SpellCardIcon spellCardIcon) {
        this.spellCardIcon = spellCardIcon;
    }

    enum SpellCardIcon {
        EQUIP,
        FIELD,
        QUICK_PLAY,
        RITUAL,
        CONTINUOUS,
        COUNTER,
        NORMAL
    }
}
