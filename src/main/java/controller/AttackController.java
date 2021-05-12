package controller;

import controller.responses.DuelMenuResponses;
import model.Game;
import model.MonsterCard;
import model.Phase;

import java.util.ArrayList;

import static controller.responses.DuelMenuResponses.*;

public class AttackController  {
    private GamePlayController gamePlayController = GamePlayController.getInstance();
    private static int damage;

    public ArrayList<MonsterCard> getAttackedCardsInTurn() {
        return attackedCardsInTurn;
    }

    public void setAttackedCardsInTurn(ArrayList<MonsterCard> attackedCardsInTurn) {
        this.attackedCardsInTurn = attackedCardsInTurn;
    }

    private ArrayList<MonsterCard> attackedCardsInTurn = new ArrayList<>();

    public static int getDamage() {
        return damage;
    }

    public static void setDamage(int damage) {
        AttackController.damage = damage;
    }


    public DuelMenuResponses normalAttack(int number) //TODO RESET CARD IN NEEDED PLACES
    {
        if (gamePlayController.getSelectedCard() == null)
            return DuelMenuResponses.NO_CARD_SELECTED;
        else if (!((MonsterCard) gamePlayController.getSelectedCard()).toStringPosition().equals("OO") || !(gamePlayController.getSelectedCard() instanceof MonsterCard)
                || !(gamePlayController.getSelectedCard().getCardPlacedZone() !=gamePlayController.getCurrentPlayer().getMonsterCardZone()))
            return YOU_CANT_ATTACK_WITH_THIS_CARD;
        else if (Game.getPhases().get(gamePlayController.getCurrentPhaseNumber()) != Phase.PhaseLevel.BATTLE)
            return DuelMenuResponses.CANT_DO_THIS_ACTION_IN_THIS_PHASE;
        else if (alreadyAttackedThisTurn((MonsterCard) gamePlayController.getSelectedCard()))
            return DuelMenuResponses.ALREADY_ATTACKED;
        else if (gamePlayController.getOpponentPlayer().getMonsterCardZone().getCardByPlaceNumber(number) == null)
            return DuelMenuResponses.NO_CARD_TO_ATTACK;
        String position =gamePlayController.getOpponentPlayer().getMonsterCardZone().getCardByPlaceNumber(number).toStringPosition();
        MonsterCard target = gamePlayController.getOpponentPlayer().getMonsterCardZone().getCardByPlaceNumber(number);
        if (position.equals("OO"))
            return attackAttackPos(target, number);
        else if (position.equals("DO"))
            return attackToDefencePos(target, number, false);
        else return attackToDefencePos(target, number, true);
    }

    public DuelMenuResponses attackToDefencePos(MonsterCard target, int number, Boolean hidden) {
        int difference = ((MonsterCard) gamePlayController.getSelectedCard()).getGameATK() - target.getGameDEF();
        damage = difference;
        if (difference > 0) {
            gamePlayController.getOpponentPlayer().getMonsterCardZone().moveCardToGraveyard(number, gamePlayController.getOpponentPlayer());
            attackedCardsInTurn.add((MonsterCard) gamePlayController.getSelectedCard());
            if (!hidden)
                return DuelMenuResponses.DEFENCE_POSITION_MONSTER_DESTROYED;
            else return DuelMenuResponses.DEFENCE_POSITION_MONSTER_DESTROYED_WITH_NAME;
        } else if (difference == 0) {
            attackedCardsInTurn.add((MonsterCard) gamePlayController.getSelectedCard());
            if (!hidden)
                return DuelMenuResponses.NO_CARD_DESTROYED;
            else return DuelMenuResponses.NO_CARD_DESTROYED_WITH_NAME;
        } else {
            gamePlayController.getCurrentPlayer().reduceLifePoint(-difference);
            attackedCardsInTurn.add((MonsterCard)  gamePlayController.getSelectedCard());
            if (!hidden)
                return DuelMenuResponses.NO_CARD_DESTROYED_CURRENT_DAMAGED;
            return DuelMenuResponses.NO_CARD_DESTROYED_CURRENT_DAMAGED_WITH_NAME;
        }
    }

    public DuelMenuResponses attackAttackPos(MonsterCard target, int number) {
        int difference = ((MonsterCard) gamePlayController.getSelectedCard()).getGameATK() - target.getGameATK();
        damage = difference;
        if (difference > 0) {
            gamePlayController.getOpponentPlayer().reduceLifePoint(difference);
            gamePlayController.getOpponentPlayer().getMonsterCardZone().moveCardToGraveyard(number, gamePlayController.getOpponentPlayer());
            attackedCardsInTurn.add((MonsterCard) gamePlayController.getSelectedCard());
            return DuelMenuResponses.DESTROYED_OPPONENT_MONSTER_AND_OPPONENT_RECEIVED_DAMAGE;
        } else if (difference == 0) {
           gamePlayController.getOpponentPlayer().getMonsterCardZone().moveCardToGraveyard(number, gamePlayController.getOpponentPlayer());
          gamePlayController.getCurrentPlayer().getMonsterCardZone().moveCardToGraveyard(number,gamePlayController.getCurrentPlayer());
            return DuelMenuResponses.BOTH_MONSTERS_ARE_DESTROYED;
        } else {
            gamePlayController.getCurrentPlayer().reduceLifePoint(-difference);
            gamePlayController.getCurrentPlayer().getMonsterCardZone().moveCardToGraveyard(number, gamePlayController.getCurrentPlayer());
            return DuelMenuResponses.DESTROYED_CURRENT_MONSTER_AFTER_ATTACK;
        }

    }

    public boolean alreadyAttackedThisTurn(MonsterCard card) {
        for (MonsterCard monsterCard : attackedCardsInTurn) {
            if (card == monsterCard) {
                return true;
            }
        }
        return false;
    }

    public DuelMenuResponses directAttack() {
        if (gamePlayController.getSelectedCard() == null)
            return DuelMenuResponses.NO_CARD_SELECTED;
        else if (!(gamePlayController.getSelectedCard() instanceof MonsterCard) || !(gamePlayController.getSelectedCard().getCardPlacedZone() != gamePlayController.getCurrentPlayer().getMonsterCardZone()))
            return YOU_CANT_ATTACK_WITH_THIS_CARD;
        else if (Game.getPhases().get(gamePlayController.getCurrentPhaseNumber()) != Phase.PhaseLevel.BATTLE)
            return DuelMenuResponses.CANT_DO_THIS_ACTION_IN_THIS_PHASE;
        else if (alreadyAttackedThisTurn((MonsterCard) gamePlayController.getSelectedCard()))
            return DuelMenuResponses.ALREADY_ATTACKED;
        else if (gamePlayController.getOpponentPlayer().getMonsterCardZone().getZoneCards().isEmpty())
            return CANT_ATTACK_DIRECTLY;
        gamePlayController.getOpponentPlayer().reduceLifePoint(((MonsterCard) gamePlayController.getSelectedCard()).getGameATK());
        return YOUR_OPPONENT_DAMAGED_DIRECT_ATTACK;
    }


}
