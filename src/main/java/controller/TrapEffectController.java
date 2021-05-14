package controller;

import controller.responses.DuelMenuResponses;
import model.Card;
import model.MagicCard;
import model.MonsterCard;
import model.Player;
import view.DuelMenu;

import java.util.Map;
import java.util.Random;

import static controller.responses.DuelMenuResponses.*;

public class TrapEffectController {
    GamePlayController gamePlayController = GamePlayController.getInstance();
    DuelMenu duelMenu = DuelMenu.getInstance();
    EffectController effectController = gamePlayController.getEffectController();


    public void magicCylinder(MagicCard trap) {
        gamePlayController.getOpponentPlayer().reduceLifePoint(((MonsterCard) gamePlayController.getSelectedCard()).getGameATK());
        duelMenu.printResponse(DuelMenuResponses.EFFECT_DONE_SUCCESSFULLY);
        gamePlayController.getCurrentPlayer().getMagicCardZone().moveCardToGraveyardWithoutAddress(trap, gamePlayController.getCurrentPlayer());
    }

    public void mirrorForce(Card trap) {
        Map<Integer, MonsterCard> map = gamePlayController.getOpponentPlayer().getMonsterCardZone().getZoneCards();
        for (Map.Entry<Integer, MonsterCard> entry : map.entrySet()) {
            if (entry.getValue() != null && entry.getValue().getMode() == MonsterCard.Mode.ATTACK)
                gamePlayController.getOpponentPlayer().getMonsterCardZone().moveCardToGraveyard(entry.getKey(), gamePlayController.getCurrentPlayer());
        }
        duelMenu.printResponse(DuelMenuResponses.EFFECT_DONE_SUCCESSFULLY);
        gamePlayController.getCurrentPlayer().getMagicCardZone().moveCardToGraveyardWithoutAddress(trap,gamePlayController.getCurrentPlayer());
    }
    public void  mindCrush(Card trap)
    {   Boolean did = false;
        String string =  duelMenu.scannerLine();
        for(Card card : gamePlayController.getOpponentPlayer().getDeckZone().getZoneCards())
        {  if(card.getCardName().equals(string))
        {   gamePlayController.getOpponentPlayer().getDeckZone().removeCardFromDeckZone(card);
            gamePlayController.getOpponentPlayer().getGraveyardZone().addCardToGraveyardZone(card);
            did =true;
        }
        }
        for(Card card : gamePlayController.getOpponentPlayer().getHand().getZoneCards())
        {
            if(card.getCardName().equals(string))
            { gamePlayController.getOpponentPlayer().getHand().removeCardFromHand(card);
              gamePlayController.getOpponentPlayer().getGraveyardZone().addCardToGraveyardZone(card);
               did= true;
            }
        }
        if(!did)
        {
            Random random  = new Random();
            int index = random.nextInt(gamePlayController.getCurrentPlayer().getHand().getNumberOfCardsInHand());
            gamePlayController.getCurrentPlayer().getHand().removeCardFromHand(gamePlayController.getCurrentPlayer().getHand().getZoneCards().get(index));
            gamePlayController.getCurrentPlayer().getGraveyardZone().addCardToGraveyardZone(gamePlayController.getCurrentPlayer().getHand().getZoneCards().get(index));
        }
        duelMenu.printResponse(EFFECT_DONE_SUCCESSFULLY);
        gamePlayController.getCurrentPlayer().getMagicCardZone().moveCardToGraveyardWithoutAddress(trap,gamePlayController.getCurrentPlayer());
    }

    public void trapHole(Card  trap) {
        gamePlayController.getOpponentPlayer().getMonsterCardZone().moveCardToGraveyardWithoutAddress(gamePlayController.getSelectedCard(), gamePlayController.getCurrentPlayer());
        gamePlayController.getCurrentPlayer().getMagicCardZone().moveCardToGraveyardWithoutAddress(trap ,gamePlayController.getCurrentPlayer() );
    }

    public void torrentialTribute(Card trap) {
        effectController.destroyCards(Card.CardType.MONSTER, true);
        gamePlayController.getCurrentPlayer().getMagicCardZone().moveCardToGraveyardWithoutAddress(trap ,gamePlayController.getCurrentPlayer() );

    }

    public void timeSeal(Card trap)
    {
        gamePlayController.getOpponentPlayer().setCanDraw(false);
        gamePlayController.getCurrentPlayer().getMagicCardZone().moveCardToGraveyardWithoutAddress(trap ,gamePlayController.getCurrentPlayer() );

<<<<<<< HEAD
    }

    public void negateAttack(Card trap) {
      gamePlayController.goNextPhase();
        gamePlayController.getCurrentPlayer().getMagicCardZone().moveCardToGraveyardWithoutAddress(trap ,gamePlayController.getCurrentPlayer() );
    }

    public void magicJammer(Card trap) {
        if(gamePlayController.getCurrentPlayer().getHand().getNumberOfCardsInHand()!=0) {
=======
    public Boolean negateAttack() {
        for (Map.Entry<Player, Card> entry : gamePlayController.getActivatedCards().entrySet()) {
            if (entry.getValue().getCardName().equals("Negate Attack") && entry.getKey() == gamePlayController.getOpponentPlayer()) {
                if (effectController.askToBeActivatedInRivalsTurn())
                    return true;

            }
        }
        return false;
    }

    public void magicJammer() {
        Player player;
        for (Map.Entry<Player, Card> entry : gamePlayController.getActivatedCards().entrySet()) {
            if (entry.getValue().getCardName().equals("Magic Jammer")) ;
            player = entry.getKey();
>>>>>>> working_on_gamePlay
            while (true) {
                duelMenu.printResponse(ENTER_ONE_NUMBER);
                int num = duelMenu.scannerNum();
                if (gamePlayController.getCurrentPlayer().getHand().getZoneCards().get(num) != null) {
                    gamePlayController.getCurrentPlayer().getHand().removeCardFromHand(gamePlayController.getCurrentPlayer().getHand().getZoneCards().get(num));
                    gamePlayController.getCurrentPlayer().getGraveyardZone().addCardToGraveyardZone(gamePlayController.getCurrentPlayer().getHand().getZoneCards().get(num));
                    gamePlayController.getCurrentPlayer().getMagicCardZone().removeMagicCard((MagicCard) trap);
                    gamePlayController.getCurrentPlayer().getGraveyardZone().addCardToGraveyardZone(trap);
                    duelMenu.printResponse(EFFECT_DONE_SUCCESSFULLY);
                    return;
                } else {
                    duelMenu.printResponse(INVALID_CELL_NUMBER);
                    duelMenu.printResponse(ENTER_ONE_NUMBER);
                }

            }
        }
    }
<<<<<<< HEAD
     //TODO THIS
    public void callOfHunted()
    {   duelMenu.printResponse(ENTER_ONE_NUMBER);
        while (true)
        {
            int num = duelMenu.scannerNum();
            if(num <= gamePlayController.getCurrentPlayer().getGraveyardZone().getZoneCards().size() &&
                    ( gamePlayController.getCurrentPlayer().getGraveyardZone().getZoneCards().get(num-1) instanceof MonsterCard))
            {   gamePlayController.getCurrentPlayer().getMonsterCardZone().summonOrSetMonster((MonsterCard) gamePlayController.getCurrentPlayer().getGraveyardZone().getZoneCards().get(num-1), gamePlayController.getCurrentPlayer());
                gamePlayController.getCurrentPlayer().getGraveyardZone().getZoneCards().remove(num-1);
               duelMenu.printResponse(EFFECT_DONE_SUCCESSFULLY);
               return;
=======

    public void canActivateCallOfTheHaunted() {
        int count = 0;
        for (int i = 0; i < gamePlayController.getCurrentPlayer().getGraveyardZone().getZoneCards().size(); i++) {
            if (gamePlayController.getCurrentPlayer().getGraveyardZone().getZoneCards().get(i) instanceof MonsterCard) {
                count++;
>>>>>>> working_on_gamePlay
            }
        }
        if (count == 0)
            duelMenu.printResponse(PREPARATIONS_OF_THIS_TRAP_ARE_NOT_DONE_YET);
        else {
            callOfTheHunted();
        }
    }

    // TODO summon the monster here in attack mode
    public void callOfTheHunted() {
        duelMenu.printResponse(ENTER_ONE_NUMBER);
        while (true) {
            int num = duelMenu.scannerNum();
            if (num <= gamePlayController.getCurrentPlayer().getGraveyardZone().getZoneCards().size() &&
                    (gamePlayController.getCurrentPlayer().getGraveyardZone().getZoneCards().get(num - 1) instanceof MonsterCard)) {
                gamePlayController.getCurrentPlayer().getMonsterCardZone().summonOrSetMonster((MonsterCard) gamePlayController.getCurrentPlayer().getGraveyardZone().getZoneCards().get(num - 1), gamePlayController.getCurrentPlayer());
                gamePlayController.getCurrentPlayer().getGraveyardZone().getZoneCards().remove(num - 1);
                duelMenu.printResponse(EFFECT_DONE_SUCCESSFULLY);
                return;
            } else {
                duelMenu.printResponse(INVALID_CELL_NUMBER);
                duelMenu.printResponse(ENTER_ONE_NUMBER);
            }
        }
    }

    public void solemnWarning() {
        gamePlayController.getCurrentPlayer().reduceLifePoint(2000);
        gamePlayController.getOpponentPlayer().getMonsterCardZone().moveCardToGraveyardWithoutAddress(gamePlayController.getSelectedCard(), gamePlayController.getOpponentPlayer());
    }
}



