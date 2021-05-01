//package model;
//
//import com.google.gson.annotations.SerializedName;
//
//public class SpellCard extends MagicCard {
//
//    @SerializedName("Icon (Property)") private SpellCardIcon spellCardIcon;
//
////    public SpellCard(String cardDescription, String cardName, String cardNumber, CardType cardType) {
////        super(cardDescription, cardName, cardNumber, cardType);
////        this.setMagicType(MagicType.SPELL);
////    }
//
//    public SpellCardIcon getSpellCardIcon() {
//        return spellCardIcon;
//    }
//
//    public void setSpellCardIcon(SpellCardIcon spellCardIcon) {
//        this.spellCardIcon = spellCardIcon;
//    }
//
//    enum SpellCardIcon {
//        @SerializedName("Equip") EQUIP,
//        @SerializedName("Field") FIELD,
//        @SerializedName("Quick-play") QUICK_PLAY,
//        @SerializedName("Ritual") RITUAL,
//        @SerializedName("Continuous") CONTINUOUS,
//        @SerializedName("Counter") COUNTER,
//        @SerializedName("Normal") NORMAL
//    }
//}
