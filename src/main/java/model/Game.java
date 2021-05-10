package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Game {
    private Player firstPlayer ;
    private Player secondPlayer;
    private int roundNumber;
    private int roundCount;
    private Player[] winnerOfEachRound = new Player[3];
    private ArrayList<Integer> firstPlayerLifePointEachRound;
    private ArrayList<Integer> secondPlayerLifePointEachRound;
    private static HashMap<Integer , Phase.PhaseLevel> phases;
    private Player wholeGameWinner;
    private Player roundWinner;
    static {
        int i =0 ;
        for (Phase.PhaseLevel phaseLevel : Phase.PhaseLevel.values() )
        { i++;
          phases.put(i  , phaseLevel);
        }
    }
    public Game(Player firstPlayer, Player secondPlayer, int roundNumber)
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
    public void giveAwardThreeRounds(Player winner, Player loser)
    {   if(firstPlayer ==winner)
    winner.changeMoney(3000 +(3* Collections.max(firstPlayerLifePointEachRound)));
        else winner.changeMoney(3000+(3*Collections.max(secondPlayerLifePointEachRound)));
        winner.setScore(winner.getScore()+3000);
        loser.changeMoney(300);

    }
    public void giveAwardOneRound(Player winner, Player loser)
    { winner.changeMoney(1000+winner.getLifePoint());
        winner.setScore(winner.getScore()+1000);
        loser.changeMoney(100);
    }
    public Player getFirstPlayer() {
        return firstPlayer;
    }



    public Player getSecondPlayer() {
        return secondPlayer;
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


    public Player getRoundWinner() {
        return roundWinner;
    }

    public void setRoundWinner(Player roundWinner) {
        this.roundWinner = roundWinner;
    }

    public void setFirstPlayer(Player firstPlayer) {
        this.firstPlayer = firstPlayer;
    }

    public void setSecondPlayer(Player secondPlayer) {
        this.secondPlayer = secondPlayer;
    }

    public static HashMap<Integer, Phase.PhaseLevel> getPhases() {
        return phases;
    }

    public static void setPhases(HashMap<Integer, Phase.PhaseLevel> phases) {
        Game.phases = phases;
    }

    public Player[] getWinnerOfEachRound() {
        return winnerOfEachRound;
    }

    public void setWinnerOfEachRound(Player[] winnerOfEachRound) {
        this.winnerOfEachRound = winnerOfEachRound;
    }

    public ArrayList<Integer> getFirstPlayerLifePointEachRound() {
        return firstPlayerLifePointEachRound;
    }

    public void setFirstPlayerLifePointEachRound(ArrayList<Integer> firstPlayerLifePointEachRound) {
        this.firstPlayerLifePointEachRound = firstPlayerLifePointEachRound;
    }

    public ArrayList<Integer> getSecondPlayerLifePointEachRound() {
        return secondPlayerLifePointEachRound;
    }

    public void setSecondPlayerLifePointEachRound(ArrayList<Integer> secondPlayerLifePointEachRound) {
        this.secondPlayerLifePointEachRound = secondPlayerLifePointEachRound;
    }

    public Player getWholeGameWinner() {
        return wholeGameWinner;
    }

    public void setWholeGameWinner(Player wholeGameWinner) {
        this.wholeGameWinner = wholeGameWinner;
    }
}
