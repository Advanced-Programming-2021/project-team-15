package model;

import com.google.gson.annotations.SerializedName;

public class MonsterCard extends Card {
    @SerializedName("Atk")private int attackPoint;
    @SerializedName("Def")private int defensePoint;
    @SerializedName("Level") private int level;
    @SerializedName("Monster Type") private MonsterType monsterType;
    @SerializedName("Attribute") private MonsterAttribute monsterAttribute;
    @SerializedName("Card Type") private MonsterEffectType monsterEffectType;
    private Mode mode;
    public MonsterCard(String cardDescription, String cardName, String cardNumber, CardType cardType) {
        super(cardDescription, cardName, cardNumber, cardType);
    }

    private void addAttackPoint(int point) {
        this.setAttackPoint(this.getAttackPoint()+point);
    }

    private void addDefensePoint(int point) {
        this.setDefensePoint(this.getDefensePoint()+point);
    }

    private void reduceAttackPoint(int point) {
        this.setAttackPoint(this.getAttackPoint()-point);
    }

    private void reduceDefensePoint(int point) {
        this.setDefensePoint(this.getDefensePoint()-point);
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

    public MonsterType getMonsterType() {
        return monsterType;
    }

    public void setMonsterType(MonsterType monsterType) {
        this.monsterType = monsterType;
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

    enum MonsterEffectType {
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


        public String getName()
        {
            return this.name;
        }
    }

    enum Mode {
        ATTACK,
        DEFENSE
    }

    enum MonsterAttribute {
        @SerializedName("DARK") DARK,
        @SerializedName("WATER") WATER,
        @SerializedName("FIRE") FIRE,
        @SerializedName("EARTH") EARTH,
        @SerializedName("WIND") WIND,
        @SerializedName("LIGHT") LIGHT
    }
    public String toString() {
        StringBuilder cardInfo = new StringBuilder();
        cardInfo.append("Name: " + cardName + "\n");
        cardInfo.append("Level: " + level + "\n");
        cardInfo.append("Type: " + monsterType.getName() + "\n");
        cardInfo.append("ATK: " +attackPoint + "\n");
        cardInfo.append("DEF: " + defensePoint + "\n");
        cardInfo.append("Description: " + cardDescription);
        return cardInfo.toString();
    }
}
