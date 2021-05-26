package model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MonsterCard extends Card {
    @SerializedName("Attack")private int attackPoint;
    private int gameATK = attackPoint;
    @SerializedName("Defence")private int defensePoint;
    private int gameDEF = defensePoint;
    @SerializedName("Level") private int level;
    @SerializedName("Monster Type") private MonsterType monsterType;
    @SerializedName("Monster Attribute") private MonsterAttribute monsterAttribute;
    @SerializedName("Monster Effect Type") private MonsterEffectType monsterEffectType;
    private transient Boolean isSummoned = false;
    private transient Mode mode;

    public MonsterCard(String cardDescription, String cardName, String cardNumber, CardType cardType) {
        super(cardDescription, cardName, cardNumber, cardType);
        super.type = "MONSTER";
        this.cardType = CardType.MONSTER;
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
        if(attackPoint<0)
            this.attackPoint = 0;
        else
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

    public int getGameATK() {
        return gameATK;
    }

    public void setGameATK(int gameATK) {
        this.gameATK = gameATK;
    }

    public int getGameDEF() {
        return gameDEF;
    }

    public void setGameDEF(int gameDEF) {
        this.gameDEF = gameDEF;
    }

    public enum MonsterEffectType {
        @SerializedName("Normal") NORMAL,
        @SerializedName("Effect") EFFECT,
        @SerializedName("Ritual") RITUAL;

        public static MonsterEffectType getMonsterEffectType(String monsterEffectType) {
            switch (monsterEffectType) {
                case "Normal" : return NORMAL;
                case "Effect" : return EFFECT;
                case "Ritual" : return RITUAL;
                default:return null;
            }
        }
    }

    public enum MonsterType {
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
        @SerializedName("Cyberse") CYBER("Cyberse"),
        @SerializedName("Fairy") FAIRY("Fairy"),
        @SerializedName("Sea Serpent") SEA_SERPENT("Sea Serpent");
        private final String name;

        public static MonsterType getMonsterTypeByName(String monsterType) {
            switch (monsterType) {
                case "Spellcaster" : return SPELL_CASTER;
                case "Warrior" : return WARRIOR;
                case "Beast-Warrior" : return BEAST_WARRIOR;
                case "Fiend" : return FIEND;
                case "Aqua" : return AQUA;
                case "Beast" : return BEAST;
                case "Pyro" : return PYRO;
                case "Thunder" : return THUNDER;
                case "Dragon" : return DRAGON;
                case "Machine" : return MACHINE;
                case "Rock" : return ROCK;
                case "Insect" : return INSECT;
                case "Cyberse" : return CYBER;
                case "Fairy" : return FAIRY;
                case "Sea Serpent" : return SEA_SERPENT;
                default: return null;
            }
        }

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

    public enum MonsterAttribute {
        @SerializedName("DARK") DARK,
        @SerializedName("WATER") WATER,
        @SerializedName("FIRE") FIRE,
        @SerializedName("EARTH") EARTH,
        @SerializedName("WIND") WIND,
        @SerializedName("LIGHT") LIGHT;

        public static MonsterAttribute getAttribute(String attribute) {
            switch (attribute) {
                case "EARTH":
                    return EARTH;
                case "WATER":
                    return WATER;
                case "DARK":
                    return DARK;
                case "LIGHT":
                    return LIGHT;
                case "FIRE":
                    return FIRE;
                case "WIND":
                    return WIND;
                default:
                    return null;
            }
        }
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
