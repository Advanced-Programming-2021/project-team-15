package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Game {
    private static HashMap<Integer, Phase.PhaseLevel> phases;

    static {
        int i = 0;
        phases = new HashMap<>();
        for (Phase.PhaseLevel phaseLevel : Phase.PhaseLevel.values()) {
            i++;
            phases.put(i, phaseLevel);
        }
    }

    private Player firstPlayer;
    private Player secondPlayer;
    private int roundNumber;
    private int roundCount;
    private Player[] winnerOfEachRound ;
    private ArrayList<Integer> firstPlayerLifePointEachRound;
    private ArrayList<Integer> secondPlayerLifePointEachRound;
    private Player wholeGameWinner;
    private Player roundWinner;

    public Game(Player firstPlayer, Player secondPlayer, int roundNumber) {
        winnerOfEachRound = new Player[3];
        firstPlayerLifePointEachRound = new ArrayList<>();
        secondPlayerLifePointEachRound = new ArrayList<>();
        this.roundNumber = roundNumber;
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        roundCount = 0;

    }

    public static HashMap<Integer, Phase.PhaseLevel> getPhases() {
        return phases;
    }

    public static void setPhases(HashMap<Integer, Phase.PhaseLevel> phases) {
        Game.phases = phases;
    }

    public void increaseRoundCount() {
        roundCount++;
    }

    public void giveAwardThreeRounds(Player winner, Player loser) {
        if (firstPlayer == winner)
            winner.getUser().changeMoney(3000 + (3 * Collections.max(firstPlayerLifePointEachRound)));
        else winner.getUser().changeMoney(3000 + (3 * Collections.max(secondPlayerLifePointEachRound)));
        winner.getUser().setScore(winner.getUser().getScore() + 3000);
        loser.getUser().changeMoney(300);
    }

    public void giveAwardOneRound(Player winner, Player loser) {
        winner.getUser().changeMoney(1000 + winner.getLifePoint());
        winner.getUser().setScore(winner.getUser().getScore() + 1000);
        loser.getUser().changeMoney(100);
    }

    public Player getFirstPlayer() {
        return firstPlayer;
    }

    public void setFirstPlayer(Player firstPlayer) {
        this.firstPlayer = firstPlayer;
    }

    public Player getSecondPlayer() {
        return secondPlayer;
    }

    public void setSecondPlayer(Player secondPlayer) {
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

    public Player getRoundWinner() {
        return roundWinner;
    }

    public void setRoundWinner(Player roundWinner) {
        this.roundWinner = roundWinner;
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
