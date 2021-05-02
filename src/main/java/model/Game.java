package model;

import java.util.HashMap;

public class Game {
    private User firstPlayer ;
    private User secondPlayer;
    private int roundNumber;
    private int roundCount;
    private static HashMap<Integer , Phase.PhaseLevel> phases;
    private Player gameWinner;
    private Player roundWinner;
    static {
        int i =0 ;
        for (Phase.PhaseLevel phaseLevel : Phase.PhaseLevel.values() )
        { i++;
          phases.put(i  , phaseLevel);
        }
    }
    Game(User firstPlayer , User secondPlayer , int roundNumber)
    {
        this.roundNumber = roundNumber;
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        roundCount = 0;
    }
    public void increaseRoundCount()
    {
        roundCount++;
    }
    public void giveAwardToWinner()
    {  // if(roundNumber==1)
       // gameWinner.setScore(1000+gameWinner.getScore());
       //gameWinner.setMoney(1000+gameWinner.getLifePoint()+gameWinner.getMoney());
       // if(roundNumber>1)
           //******* to game play na bayad bashe??
    }
    public User getFirstPlayer() {
        return firstPlayer;
    }

    public void setFirstPlayer(User firstPlayer) {
        this.firstPlayer = firstPlayer;
    }

    public User getSecondPlayer() {
        return secondPlayer;
    }

    public void setSecondPlayer(User secondPlayer) {
        this.secondPlayer = secondPlayer;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public void setRoundNumber(int roundNumber) {
        this.roundNumber = roundNumber;
    }

    public int getRoundCount() {
        return roundCount;
    }

    public void setRoundCount(int roundCount) {
        this.roundCount = roundCount;
    }

    public Player getGameWinner() {
        return gameWinner;
    }

    public void setGameWinner(Player gameWinner) {
        this.gameWinner = gameWinner;
    }

    public Player getRoundWinner() {
        return roundWinner;
    }

    public void setRoundWinner(Player roundWinner) {
        this.roundWinner = roundWinner;
    }
}
