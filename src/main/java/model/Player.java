package model;

public class Player extends User {
    private int lifePoint;
    private Deck activeDeck;
    private Zone gameZone;
    private Boolean haveSelectedCard;
    private int winCount;

    public Player(Deck activeDeck, String userName, String passWord, String nickName) {
        super(userName, nickName, passWord);
        this.activeDeck = activeDeck;
    }

    public void reduceLifePoint(int point) {
        this.lifePoint -= point;
    }

    public void increaseLifePoint(int point) {
        this.lifePoint += point;
    }

    public void increasePlayerWinCount(int number) {
        winCount += number;
    }

    public int getLifePoint() {
        return lifePoint;
    }

    public void setLifePoint(int lifePoint) {
        this.lifePoint = lifePoint;
    }

    public Deck getActiveDeck() {
        return activeDeck;
    }

    public void setActiveDeck(Deck activeDeck) {
        this.activeDeck = activeDeck;
    }

    public Zone getGameZone() {
        return gameZone;
    }

    public void setGameZone(Zone gameZone) {
        this.gameZone = gameZone;
    }

    public Boolean getHaveSelectedCard() {
        return haveSelectedCard;
    }

    public void setHaveSelectedCard(Boolean haveSelectedCard) {
        this.haveSelectedCard = haveSelectedCard;
    }

    public int getWinCount() {
        return winCount;
    }

    public void setWinCount(int winCount) {
        this.winCount = winCount;
    }
}
