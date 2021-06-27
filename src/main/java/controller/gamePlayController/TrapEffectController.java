package controller.gamePlayController;

import controller.responses.DuelMenuResponses;
import model.Player;
import model.cards.Card;
import model.cards.MagicCard;
import model.cards.MonsterCard;
import view.DuelMenu;

import java.util.Map;
import java.util.Random;

import static controller.responses.DuelMenuResponses.*;

public class TrapEffectController {
    GamePlayController gamePlayController = GamePlayController.getInstance();
    DuelMenu duelMenu = DuelMenu.getInstance();
    private boolean doIt = false;

    public void magicCylinder(Card trap) {
        gamePlayController.activateCard(trap);
        gamePlayController.getOpponentPlayer().reduceLifePoint(((MonsterCard) gamePlayController.getSelectedCard()).getGameATK());
        duelMenu.printResponse(DuelMenuResponses.EFFECT_DONE_SUCCESSFULLY);
        gamePlayController.getCurrentPlayer().getMagicCardZone().moveCardToGraveyardWithoutAddress(trap, gamePlayController.getCurrentPlayer());
    }

    public void mirrorForce(Card trap) {
        gamePlayController.activateCard(trap);
        Map<Integer, MonsterCard> map = gamePlayController.getOpponentPlayer().getMonsterCardZone().getZoneCards();
        for (Map.Entry<Integer, MonsterCard> entry : map.entrySet()) {
            if (entry.getValue() != null && entry.getValue().getMode() == MonsterCard.Mode.ATTACK)
                gamePlayController.getOpponentPlayer().getMonsterCardZone().moveCardToGraveyard(entry.getKey(), gamePlayController.getCurrentPlayer());
        }
        duelMenu.printResponse(DuelMenuResponses.EFFECT_DONE_SUCCESSFULLY);
        gamePlayController.getCurrentPlayer().getMagicCardZone().moveCardToGraveyardWithoutAddress(trap, gamePlayController.getCurrentPlayer());
    }

    public void mindCrush(Card trap) {
        if (!doIt) {
            gamePlayController.activateCard(trap);
            return;
        }
        boolean did = false;
        duelMenu.printResponse(GIVE_A_NAME);
        String string = duelMenu.getString();
        for (Card card : gamePlayController.getOpponentPlayer().getDeckZone().getZoneCards()) {
            if (card != null && card.getCardName().equals(string)) {
                gamePlayController.getOpponentPlayer().getDeckZone().removeCardFromDeckZone(card);
                gamePlayController.getOpponentPlayer().getGraveyardZone().addCardToGraveyardZone(card);
                did = true;
            }
        }
        for (Card card : gamePlayController.getOpponentPlayer().getHand().getZoneCards()) {
            if (card != null && card.getCardName().equals(string)) {
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

    public void trapHole(Card trap, MonsterCard victim) {
        gamePlayController.activateCard(trap);
        gamePlayController.getOpponentPlayer().getMonsterCardZone().moveCardToGraveyardWithoutAddress(victim, gamePlayController.getOpponentPlayer());
        duelMenu.printResponse(EFFECT_DONE_SUCCESSFULLY);
        gamePlayController.getCurrentPlayer().getMagicCardZone().moveCardToGraveyardWithoutAddress(trap, gamePlayController.getCurrentPlayer());
    }

    public void torrentialTribute(Card trap) {
        gamePlayController.activateCard(trap);
        GamePlayController.getEffectController().destroyCards(Card.CardType.MONSTER, true);
        duelMenu.printResponse(EFFECT_DONE_SUCCESSFULLY);
        gamePlayController.getCurrentPlayer().getMagicCardZone().moveCardToGraveyardWithoutAddress(trap, gamePlayController.getCurrentPlayer());
    }

    public void timeSeal(Card trap) {
        if (!doIt) {
            gamePlayController.activateCard(trap);
            return;
        }
        gamePlayController.getOpponentPlayer().setCanDraw(false);
        duelMenu.printResponse(EFFECT_DONE_SUCCESSFULLY);
        gamePlayController.getCurrentPlayer().getMagicCardZone().moveCardToGraveyardWithoutAddress(trap, gamePlayController.getCurrentPlayer());

    }

    public void negateAttack(Card trap) {
        gamePlayController.activateCard(trap);
        duelMenu.printResponse(EFFECT_DONE_SUCCESSFULLY);
        gamePlayController.goNextPhase();
        gamePlayController.getCurrentPlayer().getMagicCardZone().moveCardToGraveyardWithoutAddress(trap, gamePlayController.getCurrentPlayer());
    }

    public void magicJammer(Card trap) {
        if (!doIt) {
            if (gamePlayController.getCurrentPlayer().getHand().getNumberOfCardsInHand() != 0 && !gamePlayController.getChainCards().isEmpty())
                gamePlayController.activateCard(trap);
            return;
        }
        while (true) {
            duelMenu.printResponse(ENTER_ONE_NUMBER);
            int num = duelMenu.getNum();
            if (gamePlayController.getCurrentPlayer().getHand().getZoneCards().get(num) != null) {
                gamePlayController.getCurrentPlayer().getHand().removeCardFromHand(gamePlayController.getCurrentPlayer().getHand().getZoneCards().get(num));
                gamePlayController.getCurrentPlayer().getGraveyardZone().addCardToGraveyardZone(gamePlayController.getCurrentPlayer().getHand().getZoneCards().get(num));
                MagicCard magicCard = gamePlayController.getChainCards().get(gamePlayController.getChainCards().indexOf(trap) - 1);
                Player player = gamePlayController.getChainPlayers().get(gamePlayController.getChainCards().indexOf(trap) - 1);
                if (magicCard.getCardPlacedZone() == player.getFieldZone()) {
                    player.getFieldZone().removeCardFromFieldZone(magicCard);
                    player.getGraveyardZone().addCardToGraveyardZone(magicCard);
                } else {
                    gamePlayController.getCurrentPlayer().getMagicCardZone().moveCardToGraveyardWithoutAddress(magicCard, player);
                }
                gamePlayController.getChainCards().remove(magicCard);
                gamePlayController.getChainPlayers().remove(player);
                duelMenu.printString(magicCard.getCardName() + " action got canceled");
                duelMenu.printResponse(EFFECT_DONE_SUCCESSFULLY);
                gamePlayController.getCurrentPlayer().getMagicCardZone().moveCardToGraveyardWithoutAddress(trap, gamePlayController.getCurrentPlayer());
                return;
            } else {
                duelMenu.printResponse(INVALID_CELL_NUMBER);
            }
        }

    }


    public boolean isDoIt() {
        return doIt;
    }

    public void setDoIt(boolean doIt) {
        this.doIt = doIt;
    }
}

