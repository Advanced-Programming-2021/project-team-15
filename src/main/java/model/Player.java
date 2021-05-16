package model;

import controller.Limit;

import java.util.ArrayList;

public class Player  {
    private  User user;
    private int lifePoint;
    private Hand hand;
    private FieldZone fieldZone;
    private GraveyardZone graveyardZone;
    private DeckZone deckZone;
    private MonsterCardZone monsterCardZone;
    private MagicCardZone magicCardZone;
    private Boolean haveSelectedCard;

    public ArrayList<Limit> getLimits() {
        return limits;
    }

    public void setLimits(ArrayList<Limit> limits) {
        this.limits = limits;
    }

    private ArrayList<Limit> limits = new ArrayList<>();

    public Boolean getCanDraw() {
        return canDraw;
    }

    public void setCanDraw(Boolean canDraw) {
        this.canDraw = canDraw;
    }

    private Boolean canDraw= true;

    public Boolean getFirstTurn() {
        return firstTurn;
    }

    public void setFirstTurn(Boolean firstTurn) {
        this.firstTurn = firstTurn;
    }

    private Boolean firstTurn = false;

    public Player( User user) {
        this.user =user;
        fieldZone = new FieldZone();
        graveyardZone = new GraveyardZone();
        deckZone = new DeckZone();
        magicCardZone= new MagicCardZone();
        monsterCardZone= new MonsterCardZone();
        hand = new Hand();
        haveSelectedCard = false;
    }
    public void startNewGame()
    {    fieldZone = new FieldZone();
        graveyardZone = new GraveyardZone();
        deckZone = new DeckZone();
        magicCardZone= new MagicCardZone();
        monsterCardZone= new MonsterCardZone();
        hand = new Hand();
        haveSelectedCard = false;
    }

    public void reduceLifePoint(int point) {
        this.lifePoint -= point;
    }

    public void increaseLifePoint(int point) {
        this.lifePoint += point;
    }

    public int getLifePoint() {
        return lifePoint;
    }

    public void setLifePoint(int lifePoint) {
        this.lifePoint = lifePoint;
    }

    public Boolean getHaveSelectedCard() {
        return haveSelectedCard;
    }

    public void setHaveSelectedCard(Boolean haveSelectedCard) {
        this.haveSelectedCard = haveSelectedCard;
    }

    public Hand getHand() {
        return hand;
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }

    public FieldZone getFieldZone() {
        return fieldZone;
    }

    public void setFieldZone(FieldZone fieldZone) {
        this.fieldZone = fieldZone;
    }

    public GraveyardZone getGraveyardZone() {
        return graveyardZone;
    }

    public void setGraveyardZone(GraveyardZone graveyardZone) {
        this.graveyardZone = graveyardZone;
    }

    public DeckZone getDeckZone() {
        return deckZone;
    }

    public void setDeckZone(DeckZone deckZone) {
        this.deckZone = deckZone;
    }

    public MonsterCardZone getMonsterCardZone() {
        return monsterCardZone;
    }

    public void setMonsterCardZone(MonsterCardZone monsterCardZone) {
        this.monsterCardZone = monsterCardZone;
    }

    public MagicCardZone getMagicCardZone() {
        return magicCardZone;
    }

    public void setMagicCardZone(MagicCardZone magicCardZone) {
        this.magicCardZone = magicCardZone;
    }

    public Zone getZoneByZoneType(Zone.ZoneType zoneType)
    { Zone zone = null;
        switch (zoneType)
        {
            case HAND:
                zone= hand;
                break;
            case MONSTER_CARD:
                zone = monsterCardZone;
                break;
            case MAGIC_CARD:
                zone= magicCardZone;
                break;
            case DECK:
                zone = deckZone;
                break;
            case FIELD:
                zone = fieldZone;
                break;
            case GRAVEYARD:
                zone = graveyardZone;
                break;
            default:
                break;
        }
        return zone;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
