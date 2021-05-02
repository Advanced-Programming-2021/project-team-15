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
        @SerializedName("Spellcaster") SPELL_CASTER,
        @SerializedName("Warrior") WARRIOR,
        @SerializedName("Beast-Warrior") BEAST_WARRIOR,
        @SerializedName("Fiend") FIEND,
        @SerializedName("Aqua") AQUA,
        @SerializedName("Beast") BEAST,
        @SerializedName("Pyro") PYRO,
        @SerializedName("Thunder") THUNDER,
        @SerializedName("Dragon") DRAGON,
        @SerializedName("Machine") MACHINE,
        @SerializedName("Rock") ROCK,
        @SerializedName("Insect") INSECT,
        @SerializedName("Cyber") CYBER,
        @SerializedName("Fairy") FAIRY,
        @SerializedName("Sea Serpent") SEA_SERPENT
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
}
