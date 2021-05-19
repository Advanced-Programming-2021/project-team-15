package controller;

import controller.EffectController;
import controller.GamePlayController;
import controller.responses.DuelMenuResponses;
import model.Card;
import model.MagicCard;
import model.MonsterCard;
import utility.Utility;
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
        gamePlayController.getCurrentPlayer().getMagicCardZone().moveCardToGraveyardWithoutAddress(trap, gamePlayController.getCurrentPlayer());
    }

    public void mindCrush(Card trap) {
        Boolean did = false;
        String string = Utility.getNextLine();;
        for (Card card : gamePlayController.getOpponentPlayer().getDeckZone().getZoneCards()) {
            if (card.getCardName().equals(string)) {
                gamePlayController.getOpponentPlayer().getDeckZone().removeCardFromDeckZone(card);
                gamePlayController.getOpponentPlayer().getGraveyardZone().addCardToGraveyardZone(card);
                did = true;
            }
        }
        for (Card card : gamePlayController.getOpponentPlayer().getHand().getZoneCards()) {
            if (card.getCardName().equals(string)) {
                gamePlayController.getOpponentPlayer().getHand().removeCardFromHand(card);
                gamePlayController.getOpponentPlayer().getGraveyardZone().addCardToGraveyardZone(card);
                did = true;
            }
        }
        if (!did) {
            Random random = new Random();
            int index = random.nextInt(gamePlayController.getCurrentPlayer().getHand().getNumberOfCardsInHand());
            gamePlayController.getCurrentPlayer().getHand().removeCardFromHand(gamePlayController.getCurrentPlayer().getHand().getZoneCards().get(index));
            gamePlayController.getCurrentPlayer().getGraveyardZone().addCardToGraveyardZone(gamePlayController.getCurrentPlayer().getHand().getZoneCards().get(index));
        }
        duelMenu.printResponse(EFFECT_DONE_SUCCESSFULLY);
        gamePlayController.getCurrentPlayer().getMagicCardZone().moveCardToGraveyardWithoutAddress(trap, gamePlayController.getCurrentPlayer());
    }

    public void trapHole(Card trap) {
        gamePlayController.getOpponentPlayer().getMonsterCardZone().moveCardToGraveyardWithoutAddress(gamePlayController.getSelectedCard(), gamePlayController.getCurrentPlayer());
        gamePlayController.getCurrentPlayer().getMagicCardZone().moveCardToGraveyardWithoutAddress(trap, gamePlayController.getCurrentPlayer());
    }

    public void torrentialTribute(Card trap) {
        effectController.destroyCards(Card.CardType.MONSTER, true);
        gamePlayController.getCurrentPlayer().getMagicCardZone().moveCardToGraveyardWithoutAddress(trap, gamePlayController.getCurrentPlayer());

    }

    public void timeSeal(Card trap) {
        gamePlayController.getOpponentPlayer().setCanDraw(false);
        gamePlayController.getCurrentPlayer().getMagicCardZone().moveCardToGraveyardWithoutAddress(trap, gamePlayController.getCurrentPlayer());

    }

    public void negateAttack(Card trap) {
        gamePlayController.goNextPhase();
        gamePlayController.getCurrentPlayer().getMagicCardZone().moveCardToGraveyardWithoutAddress(trap, gamePlayController.getCurrentPlayer());
    }

    public void magicJammer(Card trap) {
        if (gamePlayController.getCurrentPlayer().getHand().getNumberOfCardsInHand() != 0) {
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
    //TODO THIS
//    public void callOfHunted()
//    {   duelMenu.printResponse(ENTER_ONE_NUMBER);
//        while (true)
}