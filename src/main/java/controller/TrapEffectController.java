package controller;

import controller.responses.DuelMenuResponses;
import model.Card;
import model.MagicCard;
import model.MonsterCard;
import model.Player;
import view.DuelMenu;

import java.util.Map;

import static controller.responses.DuelMenuResponses.*;

public class TrapEffectController {
    GamePlayController gamePlayController = GamePlayController.getInstance();
    DuelMenu duelMenu = DuelMenu.getInstance();
    EffectController effectController = gamePlayController.getEffectController();

    public void magicCylinder() {
        gamePlayController.getCurrentPlayer().reduceLifePoint(((MonsterCard) gamePlayController.getSelectedCard()).getGameATK());
        duelMenu.printResponse(DuelMenuResponses.EFFECT_DONE_SUCCESSFULLY);
    }

    public void mirrorForce() {
        Map<Integer, MonsterCard> map = gamePlayController.getCurrentPlayer().getMonsterCardZone().getZoneCards();
        for (Map.Entry<Integer, MonsterCard> entry : map.entrySet()) {
            if (entry.getValue() != null && entry.getValue().getMode() == MonsterCard.Mode.ATTACK)
                gamePlayController.getCurrentPlayer().getMonsterCardZone().moveCardToGraveyard(entry.getKey(), gamePlayController.getCurrentPlayer());
        }
        duelMenu.printResponse(DuelMenuResponses.EFFECT_DONE_SUCCESSFULLY);
    }

    public void trapHole() {
        gamePlayController.getCurrentPlayer().getMonsterCardZone().moveCardToGraveyardWithoutAddress(gamePlayController.getSelectedCard(), gamePlayController.getCurrentPlayer());
    }

    public void torrentialTribute() {
        effectController.destroyCards(Card.CardType.MONSTER, true);
    }

    public void timeSeal()  //TODO CAN DRAW
    {
        gamePlayController.getOpponentPlayer().setCanDraw(false);
    }

    public Boolean negateAttack() {
        for (Map.Entry<Player, Card> entry : gamePlayController.getActivatedCards().entrySet())
        {
            if(entry.getValue().getCardName().equals("Negate Attack") && entry.getKey()==gamePlayController.getOpponentPlayer())
            {  if(effectController.askToBeActivatedInRivalsTurn())
                return true;

            }
        }
      return false;
    }

    public void majicJammer() {
        Player player;
        for (Map.Entry<Player, Card> entry : gamePlayController.getActivatedCards().entrySet()) {
            if (entry.getValue().getCardName().equals("Majic Jammer")) ;
            player = entry.getKey();
            while (true) {
                duelMenu.printResponse(ENTER_ONE_NUMBER);
                int num = duelMenu.scannerNum();
                if (player.getHand().getZoneCards().get(num) != null) {
                    player.getHand().removeCardFromHand(player.getHand().getZoneCards().get(num));
                    player.getGraveyardZone().addCardToGraveyardZone(player.getHand().getZoneCards().get(num));
                    duelMenu.printResponse(EFFECT_DONE_SUCCESSFULLY);
                    gamePlayController.getCurrentPlayer().getMagicCardZone().removeMagicCard((MagicCard) gamePlayController.getSelectedCard());
                    gamePlayController.getCurrentPlayer().getGraveyardZone().addCardToGraveyardZone(gamePlayController.getSelectedCard());
                    return;
                } else {
                    duelMenu.printResponse(INVALID_CELL_NUMBER);
                    duelMenu.printResponse(ENTER_ONE_NUMBER);
                }

            }

        }
    }

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
            }
            else {
                duelMenu.printResponse(INVALID_CELL_NUMBER);
                duelMenu.printResponse(ENTER_ONE_NUMBER);
            }
        }
    }
















}
