package model;
public class MonsterCard extends Card {
    @SerializedName("Atk")private int attackPoint;
    @SerializedName("Def")private int defensePoint;
    @SerializedName("Level") private int level;
    @SerializedName("Monster Type")private String Type;
    private MonsterType monsterType;
    @SerializedName("Attribute") private String attribute;
    private MonsterAttribute monsterAttribute;
    @SerializedName("Card Type") private String EffectType;
    private MonsterEffectType monsterEffectType;
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
        NORMAL,
        EFFECT,
        RITUAL
    }

    enum MonsterType {
        SPELL_CASTER,
        WARRIOR,
        BEAST_WARRIOR,
        FIEND,
        AQUA,
        BEAST,
        PYRO,
        THUNDER,
        DRAGON,
        MACHINE,
        ROCK,
        INSECT,
        CYBER,
        FAIRY,
        SEA_SERPENT
    }

    enum Mode {
        ATTACK,
        DEFENSE
    }

    enum MonsterAttribute {
        DARK,
        WATER,
        FIRE,
        EARTH,
        WIND,
        LIGHT
    }
}
