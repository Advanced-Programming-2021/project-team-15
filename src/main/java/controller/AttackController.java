package controller;

import controller.responses.DuelMenuResponses;
import model.Card;
import model.Game;
import model.MonsterCard;
import model.Phase;
import view.DuelMenu;

import java.util.ArrayList;
import java.util.HashMap;

import static controller.responses.DuelMenuResponses.*;

public class AttackController  {
    private boolean  isAttacking   =false;
    private GamePlayController gamePlayController = GamePlayController.getInstance();

    public ArrayList<Card> getAttackStoppersInTurn() {
        return attackStoppersInTurn;
    }

    public void setAttackStoppersInTurn(ArrayList<Card> attackStoppersInTurn) {
        this.attackStoppersInTurn = attackStoppersInTurn;
    }

    private ArrayList<Card> attackStoppersInTurn = new ArrayList();
    private static int damage;


    public ArrayList<MonsterCard> getCantBeAttacked() {
        return cantBeAttacked;
    }

    public void setCantBeAttacked(ArrayList<MonsterCard> cantBeAttacked) {
        this.cantBeAttacked = cantBeAttacked;
    }

    private ArrayList<MonsterCard> cantBeAttacked= new ArrayList<>();
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
        damage = 0;
        if (gamePlayController.getSelectedCard() == null)
            return DuelMenuResponses.NO_CARD_SELECTED;
        else if (!((MonsterCard) gamePlayController.getSelectedCard()).toStringPosition().equals("OO") || !(gamePlayController.getSelectedCard() instanceof MonsterCard)
                || (gamePlayController.getSelectedCard().getCardPlacedZone() !=gamePlayController.getCurrentPlayer().getMonsterCardZone()))
            return YOU_CANT_ATTACK_WITH_THIS_CARD;
        else if (Game.getPhases().get(gamePlayController.getCurrentPhaseNumber()) != Phase.PhaseLevel.BATTLE)
            return DuelMenuResponses.CANT_DO_THIS_ACTION_IN_THIS_PHASE;
        else if (alreadyAttackedThisTurn((MonsterCard) gamePlayController.getSelectedCard()))
            return DuelMenuResponses.ALREADY_ATTACKED;
        else if (gamePlayController.getOpponentPlayer().getMonsterCardZone().getCardByPlaceNumber(number) == null)
            return DuelMenuResponses.NO_CARD_TO_ATTACK;
        String position =gamePlayController.getOpponentPlayer().getMonsterCardZone().getCardByPlaceNumber(number).toStringPosition();
        MonsterCard target = gamePlayController.getOpponentPlayer().getMonsterCardZone().getCardByPlaceNumber(number);
        MonsterCard attacker = (MonsterCard) gamePlayController.getSelectedCard();
        isAttacking = true;
        checkerForEffects();
        if(!isAttacking)
            return ATTACK_CANCELED;
        if (position.equals("OO"))
            return attackAttackPos(attacker,target, number);
        else if (position.equals("DO"))
            return attackToDefencePos(attacker,target, number, false);
        else return attackToDefencePos(attacker,target, number, true);
    }

    public void checkerForEffects()
    {   if(gamePlayController.ifPlayerHasThisCardGiveIt(gamePlayController.getOpponentPlayer(), "Magic Cylinder")!=null)
       {  gamePlayController.changeTurn();
          DuelMenu.getInstance().doYouWannaActivateSpecialCard("Magic Cylinder");
          if (getAnswer()) {
              GamePlayController.getTrapEffectController().magicCylinder(gamePlayController.ifPlayerHasThisCardGiveIt(gamePlayController.getCurrentPlayer(), "Magic Cylinder"));
              isAttacking = false;
          }
           gamePlayController.changeTurn();
       }
       if(gamePlayController.ifPlayerHasThisCardGiveIt(gamePlayController.getOpponentPlayer(), "Mirror Force")!=null)
      {   gamePlayController.changeTurn();
        DuelMenu.getInstance().doYouWannaActivateSpecialCard("Mirror Force");
        if(getAnswer()) {
            GamePlayController.getTrapEffectController().mirrorForce(gamePlayController.ifPlayerHasThisCardGiveIt(gamePlayController.getCurrentPlayer(), "Mirror Force"));
            isAttacking = false; }
            gamePlayController.changeTurn();
      }
       if(gamePlayController.ifPlayerHasThisCardGiveIt(gamePlayController.getOpponentPlayer(),"Negate Attack" )!=null)
       {   if (getAnswer()) {
           GamePlayController.getTrapEffectController().negateAttack(gamePlayController.ifPlayerHasThisCardGiveIt(gamePlayController.getCurrentPlayer(), "Negate Attack"));
           isAttacking = false; }
       gamePlayController.changeTurn();
       }

    }
    public Boolean getAnswer()
    {   String ans =  DuelMenu.getInstance().getString();
        if(ans.equals("yes"))
            return true;
        else return false;
    }

    public DuelMenuResponses attackToDefencePos(MonsterCard attacker,MonsterCard target, int number, Boolean hidden) {
        target.setHidden(false);
        int difference = attacker.getGameATK() - target.getGameDEF();
        damage = difference;
        if (difference > 0) {
            gamePlayController.getOpponentPlayer().getMonsterCardZone().moveCardToGraveyard(number, gamePlayController.getOpponentPlayer());
            attackedCardsInTurn.add(attacker);
            if (!hidden)
                return DuelMenuResponses.DEFENCE_POSITION_MONSTER_DESTROYED;
            else DuelMenu.getInstance().hiddenDefensePositionMonsterDestroyed(target.getCardName());
        } else if (difference == 0) {
            attackedCardsInTurn.add(attacker);
            if (!hidden)
                return DuelMenuResponses.NO_CARD_DESTROYED;
            else DuelMenu.getInstance().hiddenDefensePosNoCardDestroyed(target.getCardName());
        } else {
            gamePlayController.getCurrentPlayer().reduceLifePoint(-difference);
            attackedCardsInTurn.add(attacker);
            if (!hidden)
                return DuelMenuResponses.NO_CARD_DESTROYED_CURRENT_DAMAGED;
            else DuelMenu.getInstance().hiddenDefensePosNoCardDestroyedWithDamage(target.getCardName());
        }
        return MONSTER_CARD_POSITION_CHANGED_SUCCESSFULLY;
    }

    public DuelMenuResponses attackAttackPos(MonsterCard attacker,MonsterCard target, int number) {
        target.setHidden(false);
        int difference = attacker.getGameATK() - target.getGameATK();
        damage = difference;
        if (difference > 0) {
            gamePlayController.getOpponentPlayer().reduceLifePoint(difference);
            gamePlayController.getOpponentPlayer().getMonsterCardZone().moveCardToGraveyard(number, gamePlayController.getOpponentPlayer());
            attackedCardsInTurn.add(attacker);
            return DuelMenuResponses.DESTROYED_OPPONENT_MONSTER_AND_OPPONENT_RECEIVED_DAMAGE;
        } else if (difference == 0) {
           gamePlayController.getOpponentPlayer().getMonsterCardZone().moveCardToGraveyard(number, gamePlayController.getOpponentPlayer());
          gamePlayController.getCurrentPlayer().getMonsterCardZone().moveCardToGraveyardWithoutAddress(attacker,gamePlayController.getCurrentPlayer());
            return DuelMenuResponses.BOTH_MONSTERS_ARE_DESTROYED;
        } else {
            gamePlayController.getCurrentPlayer().reduceLifePoint(-difference);
            gamePlayController.getCurrentPlayer().getMonsterCardZone().moveCardToGraveyardWithoutAddress(attacker,gamePlayController.getCurrentPlayer());
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
        MonsterCard attacker = (MonsterCard) gamePlayController.getSelectedCard();
        if (attacker == null)
            return DuelMenuResponses.NO_CARD_SELECTED;
        else if (!(attacker instanceof MonsterCard) || (attacker.getCardPlacedZone() != gamePlayController.getCurrentPlayer().getMonsterCardZone()))
            return YOU_CANT_ATTACK_WITH_THIS_CARD;
        else if (Game.getPhases().get(gamePlayController.getCurrentPhaseNumber()) != Phase.PhaseLevel.BATTLE)
            return DuelMenuResponses.CANT_DO_THIS_ACTION_IN_THIS_PHASE;
        else if (alreadyAttackedThisTurn(attacker))
            return DuelMenuResponses.ALREADY_ATTACKED;
        else if (gamePlayController.getOpponentPlayer().getMonsterCardZone().getZoneCards().isEmpty())
            return CANT_ATTACK_DIRECTLY;
        attackedCardsInTurn.add(attacker);
        isAttacking = true;
        checkerForEffects();
        if(!isAttacking)
            return ATTACK_CANCELED;
        gamePlayController.getOpponentPlayer().reduceLifePoint((attacker).getGameATK());
        return YOUR_OPPONENT_DAMAGED_DIRECT_ATTACK;
    }



    public GamePlayController getGamePlayController() {
        return gamePlayController;
    }

    public void setGamePlayController(GamePlayController gamePlayController) {
        this.gamePlayController = gamePlayController;
    }

    public Boolean checkCanBeDestroyed()
    {  //MASHAMLLON}
        return true;
    }
    public Boolean canBeaAttacked(){
        //COMMAND KNIGHT
        return true;
    }
    public Boolean haveBeenAttackStopper(Card card)
    { for(Card card1 : attackStoppersInTurn)
    { if(card1 ==card)
            return  true;
    }
    return false;
    }

    public boolean isAttacking() {
        return isAttacking;
    }

    public void setAttacking(boolean attacking) {
        isAttacking = attacking;
    }
}
