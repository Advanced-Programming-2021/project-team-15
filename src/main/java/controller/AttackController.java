package controller;

import controller.responses.DuelMenuResponses;
import model.Game;
import model.MonsterCard;
import model.Phase;

import static controller.responses.DuelMenuResponses.*;

public class AttackController extends  GamePlayController {
    private static int damage;
Game game;
 public AttackController(Game game){
     this.game=game;
 }



 public DuelMenuResponses normalAttack(int number) //TODO RESET CARD IN NEEDED PLACES
 {  if(selectedCard==null)
     return DuelMenuResponses.NO_CARD_SELECTED;
     else if(!((MonsterCard)selectedCard).toStringPosition().equals("OO")|| !(selectedCard instanceof  MonsterCard)
 || !(selectedCard.getCardPlacedZone()!=currentPlayer.getMonsterCardZone()))
         return YOU_CANT_ATTACK_WITH_THIS_CARD;
     else if(Game.getPhases().get(currentPhaseNumber) != Phase.PhaseLevel.BATTLE)
         return DuelMenuResponses.CANT_DO_THIS_ACTION_IN_THIS_PHASE;
     else if(((MonsterCard) selectedCard).getHaveAttacked())
         return DuelMenuResponses.ALREADY_ATTACKED;
     else if(opponentPlayer.getMonsterCardZone().getCardByPlaceNumber(number)==null)
         return DuelMenuResponses.NO_CARD_TO_ATTACK;
     String position =  opponentPlayer.getMonsterCardZone().getCardByPlaceNumber(number).toStringPosition();
     MonsterCard target= opponentPlayer.getMonsterCardZone().getCardByPlaceNumber(number);
      if(position.equals("OO"))
         return attackAttackPos(target ,number);
      else if(position.equals("DO"))
          return attackToDefencePos(target, number , false);
      else return attackToDefencePos(target, number , true);
 }
 public DuelMenuResponses attackToDefencePos(MonsterCard target , int number , Boolean hidden )
 {
     int difference = ((MonsterCard)selectedCard).getAttackPoint() - target.getDefensePoint();
     damage = difference;
     if(difference>0)
     {
         opponentPlayer.getMonsterCardZone().moveCardToGraveyard(number, opponentPlayer);
         ((MonsterCard) selectedCard).setHaveAttacked(true);
         if(!hidden)
         return DuelMenuResponses.DEFENCE_POSITION_MONSTER_DESTROYED;
         else return DuelMenuResponses.DEFENCE_POSITION_MONSTER_DESTROYED_WITH_NAME;
     }
     else if(difference ==0)
     {
         ((MonsterCard) selectedCard).setHaveAttacked(true);
         if(!hidden)
         return DuelMenuResponses.NO_CARD_DESTROYED;
         else return DuelMenuResponses.NO_CARD_DESTROYED_WITH_NAME;
     }
     else {
         currentPlayer.reduceLifePoint(-difference);
         ((MonsterCard) selectedCard).setHaveAttacked(true);
         if(!hidden)
         return DuelMenuResponses.NO_CARD_DESTROYED_CURRENT_DAMAGED;
         return DuelMenuResponses.NO_CARD_DESTROYED_CURRENT_DAMAGED_WITH_NAME;
     }
 }
 public DuelMenuResponses attackAttackPos(MonsterCard target , int number)
 {
     int difference =  ((MonsterCard)selectedCard).getAttackPoint() - target.getAttackPoint();
     damage = difference;
     if(difference>0 )
     {   opponentPlayer.reduceLifePoint(difference);
         opponentPlayer.getMonsterCardZone().moveCardToGraveyard(number , opponentPlayer);
         ((MonsterCard) selectedCard).setHaveAttacked(true);
         return DuelMenuResponses.DESTROYED_OPPONENT_MONSTER_AND_OPPONENT_RECEIVED_DAMAGE;
     }
     else if(difference==0)
     {
         opponentPlayer.getMonsterCardZone().moveCardToGraveyard(number, opponentPlayer);
         currentPlayer.getMonsterCardZone().moveCardToGraveyard(number, currentPlayer);
         return DuelMenuResponses.BOTH_MONSTERS_ARE_DESTROYED;
     }
     else {  currentPlayer.reduceLifePoint(-difference);
         currentPlayer.getMonsterCardZone().moveCardToGraveyard(number, currentPlayer);
         return DuelMenuResponses.DESTROYED_CURRENT_MONSTER_AFTER_ATTACK;
     }

 }
    public DuelMenuResponses directAttack()
    { if(selectedCard==null)
        return DuelMenuResponses.NO_CARD_SELECTED;
        else if(!(selectedCard instanceof  MonsterCard) || !(selectedCard.getCardPlacedZone()!=currentPlayer.getMonsterCardZone()))
            return YOU_CANT_ATTACK_WITH_THIS_CARD;
            else if(Game.getPhases().get(currentPhaseNumber) != Phase.PhaseLevel.BATTLE)
        return DuelMenuResponses.CANT_DO_THIS_ACTION_IN_THIS_PHASE;
            else if(((MonsterCard) selectedCard).getHaveAttacked())
        return DuelMenuResponses.ALREADY_ATTACKED;
            else if(opponentPlayer.getMonsterCardZone().getZoneCards().isEmpty())
                return CANT_ATTACK_DIRECTLY;
        opponentPlayer.reduceLifePoint(((MonsterCard) selectedCard).getAttackPoint());
        return YOUR_OPPONENT_DAMAGED_DIRECT_ATTACK;
    }







}
