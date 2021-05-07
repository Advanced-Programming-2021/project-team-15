package model;

import com.google.gson.annotations.SerializedName;

public class MonsterCard extends Card {
    @SerializedName("Atk")private int attackPoint;
    @SerializedName("Def")private int defensePoint;
    @SerializedName("Level") private int level;
    @SerializedName("Monster Type") private MonsterType monsterType;
    @SerializedName("Attribute") private MonsterAttribute monsterAttribute;
    @SerializedName("Card Type") private MonsterEffectType monsterEffectType;

    private Boolean isSummoned = false;
    private Mode mode;

    public MonsterCard(String cardDescription, String cardName, String cardNumber, CardType cardType) {
        super(cardDescription, cardName, cardNumber, cardType);
    }

    public MonsterType getMonsterType() {
        return monsterType;
    }

    public void setMonsterType(MonsterType monsterType) {
        this.monsterType = monsterType;
    }

    public Boolean getSummoned() {
        return isSummoned;
    }

    public void setSummoned(Boolean summoned) {
        if(summoned)
        {
            setMode( Mode.ATTACK);
            setHidden(true);
        }
        isSummoned = summoned;
    }


    private void addAttackPoint(int point) {
        this.setAttackPoint(this.getAttackPoint() + point);
    }

    private void addDefensePoint(int point) {
        this.setDefensePoint(this.getDefensePoint() + point);
    }

    private void reduceAttackPoint(int point) {
        this.setAttackPoint(this.getAttackPoint() - point);
    }

    private void reduceDefensePoint(int point) {
        this.setDefensePoint(this.getDefensePoint() - point);
    }

    public int getAttackPoint() {
        return attackPoint;
    }

    public void setAttackPoint(int attackPoint) {
        this.attackPoint = attackPoint;
    }

    public int getDefensePoint() {
        return defensePoint;
    }

    public void setDefensePoint(int defensePoint) {
        this.defensePoint = defensePoint;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }


    public MonsterAttribute getMonsterAttribute() {
        return monsterAttribute;
    }

    public void setMonsterAttribute(MonsterAttribute monsterAttribute) {
        this.monsterAttribute = monsterAttribute;
    }

    public MonsterEffectType getMonsterEffectType() {
        return monsterEffectType;
    }

    public void setMonsterEffectType(MonsterEffectType monsterEffectType) {
        this.monsterEffectType = monsterEffectType;
    }


    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public String toString() {
        StringBuilder cardInfo = new StringBuilder();
        cardInfo.append("Name: ").append(cardName).append("\n");
        cardInfo.append("Level: ").append(level).append("\n");
        cardInfo.append("Type: ").append(monsterType.getName()).append("\n");
        cardInfo.append("ATK: ").append(attackPoint).append("\n");
        cardInfo.append("DEF: ").append(defensePoint).append("\n");
        cardInfo.append("Description: ").append(cardDescription);
        return cardInfo.toString();
    }

    public enum MonsterEffectType {
        @SerializedName("Normal") NORMAL,
        @SerializedName("Effect") EFFECT,
        @SerializedName("Ritual") RITUAL
    }

    enum MonsterType {
        @SerializedName("Spellcaster") SPELL_CASTER("Spellcaster"),
        @SerializedName("Warrior") WARRIOR("Warrior"),
        @SerializedName("Beast-Warrior") BEAST_WARRIOR("Beast-Warrior"),
        @SerializedName("Fiend") FIEND("Fiend"),
        @SerializedName("Aqua") AQUA("Aqua"),
        @SerializedName("Beast") BEAST("Beast"),
        @SerializedName("Pyro") PYRO("Pyro"),
        @SerializedName("Thunder") THUNDER("Thunder"),
        @SerializedName("Dragon") DRAGON("Dragon"),
        @SerializedName("Machine") MACHINE("Machine"),
        @SerializedName("Rock") ROCK("Rock"),
        @SerializedName("Insect") INSECT("Insect"),
        @SerializedName("Cyber") CYBER("Cyber"),
        @SerializedName("Fairy") FAIRY("Fairy"),
        @SerializedName("Sea Serpent") SEA_SERPENT("Sea Serpent");
        private String name;

        MonsterType(String name) {
            this.name = name;
        }


        public String getName() {
            return this.name;
        }
    }

    public enum Mode {
        ATTACK,
        DEFENSE,
    }

    enum MonsterAttribute {
        @SerializedName("DARK") DARK,
        @SerializedName("WATER") WATER,
        @SerializedName("FIRE") FIRE,
        @SerializedName("EARTH") EARTH,
        @SerializedName("WIND") WIND,
        @SerializedName("LIGHT") LIGHT
    }

    public String toStringPosition()
    {  String first = "";
        String second= "";
        if(!isHidden)
            second = "O";
        else second = "H";
        if(mode.equals(Mode.ATTACK))
            first = "O";
        else first="D";
        return first+second;
    }
}
