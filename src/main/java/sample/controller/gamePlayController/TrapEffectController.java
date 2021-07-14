package sample.controller.gamePlayController;

import sample.controller.responses.DuelMenuResponses;
import sample.model.cards.Card;
import sample.model.cards.MagicCard;
import sample.model.cards.MonsterCard;
import sample.model.Player;
import sample.view.DuelMenu;

import java.io.IOException;
import java.util.Map;
import java.util.Random;

import static sample.controller.responses.DuelMenuResponses.*;

public class TrapEffectController {
    GamePlayController gamePlayController = GamePlayController.getInstance();
    private boolean doIt = false;

    public void magicCylinder(Card trap) throws IOException {
        gamePlayController.activateCard(trap);
        gamePlayController.getOpponentPlayer().reduceLifePoint(((MonsterCard) gamePlayController.getSelectedCard()).getGameATK());
        GamePlayController.getInstance().getDuelMenu().printResponse(DuelMenuResponses.EFFECT_DONE_SUCCESSFULLY);
        gamePlayController.getCurrentPlayer().getMagicCardZone().moveCardToGraveyardWithoutAddress(trap, gamePlayController.getCurrentPlayer());
    }

    public void mirrorForce(Card trap) throws IOException {
        gamePlayController.activateCard(trap);
        Map<Integer, MonsterCard> map = gamePlayController.getOpponentPlayer().getMonsterCardZone().getZoneCards();
        for (Map.Entry<Integer, MonsterCard> entry : map.entrySet()) {
            if (entry.getValue() != null && entry.getValue().getMode() == MonsterCard.Mode.ATTACK)
                gamePlayController.getOpponentPlayer().getMonsterCardZone().moveCardToGraveyard(entry.getKey(), gamePlayController.getCurrentPlayer());
        }
        GamePlayController.getInstance().getDuelMenu().printResponse(DuelMenuResponses.EFFECT_DONE_SUCCESSFULLY);
        gamePlayController.getCurrentPlayer().getMagicCardZone().moveCardToGraveyardWithoutAddress(trap, gamePlayController.getCurrentPlayer());
    }

    public void mindCrush(Card trap) throws IOException {
        if (!doIt) {
            gamePlayController.activateCard(trap);
            return;
        }
        boolean did = false;
        GamePlayController.getInstance().getDuelMenu().printResponse(GIVE_A_NAME);
        String string = GamePlayController.getInstance().getDuelMenu().getStringAndAsk("give a card name : ");
        for (Card card : gamePlayController.getOpponentPlayer().getDeckZone().getZoneCards()) {
            if ( card!=null && card.getCardName().equals(string)) {
                gamePlayController.getOpponentPlayer().getDeckZone().removeCardFromDeckZone(card);
                gamePlayController.getOpponentPlayer().getGraveyardZone().addCardToGraveyardZone(card);
                did = true;
            }
        }
        for (Card card : gamePlayController.getOpponentPlayer().getHand().getZoneCards()) {
            if ( card!=null && card.getCardName().equals(string)) {
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
        GamePlayController.getInstance().getDuelMenu().printResponse(EFFECT_DONE_SUCCESSFULLY);
        gamePlayController.getCurrentPlayer().getMagicCardZone().moveCardToGraveyardWithoutAddress(trap, gamePlayController.getCurrentPlayer());
    }

    public void trapHole(Card trap, MonsterCard victim) throws IOException {
        gamePlayController.activateCard(trap);
        gamePlayController.getOpponentPlayer().getMonsterCardZone().moveCardToGraveyardWithoutAddress(victim, gamePlayController.getOpponentPlayer());
        GamePlayController.getInstance().getDuelMenu().printResponse(EFFECT_DONE_SUCCESSFULLY);
        gamePlayController.getCurrentPlayer().getMagicCardZone().moveCardToGraveyardWithoutAddress(trap, gamePlayController.getCurrentPlayer());
    }

    public void torrentialTribute(Card trap) throws IOException {
        gamePlayController.activateCard(trap);
        GamePlayController.getEffectController().destroyCards(Card.CardType.MONSTER, true);
        GamePlayController.getInstance().getDuelMenu().printResponse(EFFECT_DONE_SUCCESSFULLY);
        gamePlayController.getCurrentPlayer().getMagicCardZone().moveCardToGraveyardWithoutAddress(trap, gamePlayController.getCurrentPlayer());
    }

    public void timeSeal(Card trap) throws IOException {
        if (!doIt) {
            gamePlayController.activateCard(trap);
            return;
        }
        gamePlayController.getOpponentPlayer().setCanDraw(false);
        GamePlayController.getInstance().getDuelMenu().printResponse(EFFECT_DONE_SUCCESSFULLY);
        gamePlayController.getCurrentPlayer().getMagicCardZone().moveCardToGraveyardWithoutAddress(trap, gamePlayController.getCurrentPlayer());

    }

    public void negateAttack(Card trap) throws IOException {
        gamePlayController.activateCard(trap);
        GamePlayController.getInstance().getDuelMenu().printResponse(EFFECT_DONE_SUCCESSFULLY);
        gamePlayController.goNextPhase();
        gamePlayController.getCurrentPlayer().getMagicCardZone().moveCardToGraveyardWithoutAddress(trap, gamePlayController.getCurrentPlayer());
    }

    public void magicJammer(Card trap) throws IOException {
        if(!doIt)
        {  if(gamePlayController.getCurrentPlayer().getHand().getNumberOfCardsInHand() != 0 && !gamePlayController.getChainCards().isEmpty())
            gamePlayController.activateCard(trap);
            return; }
        while (true) {
                GamePlayController.getInstance().getDuelMenu().printResponse(ENTER_ONE_NUMBER);
            String s  = GamePlayController.getInstance().getDuelMenu().getNum();
            if(s.equals("cancel"))
                return;
            int num = Integer.parseInt(s);
                if (gamePlayController.getCurrentPlayer().getHand().getZoneCards().get(num) != null) {
                    gamePlayController.getCurrentPlayer().getHand().removeCardFromHand(gamePlayController.getCurrentPlayer().getHand().getZoneCards().get(num));
                    gamePlayController.getCurrentPlayer().getGraveyardZone().addCardToGraveyardZone(gamePlayController.getCurrentPlayer().getHand().getZoneCards().get(num));
                    MagicCard magicCard = gamePlayController.getChainCards().get(gamePlayController.getChainCards().indexOf(trap)-1);
                    Player    player  = gamePlayController.getChainPlayers().get(gamePlayController.getChainCards().indexOf(trap)-1);
                    if(magicCard.getCardPlacedZone()==player.getFieldZone())
                    {  player.getFieldZone().removeCardFromFieldZone(magicCard);
                        player.getGraveyardZone().addCardToGraveyardZone(magicCard);
                    }
                    else {
                        gamePlayController.getCurrentPlayer().getMagicCardZone().moveCardToGraveyardWithoutAddress(magicCard,player);
                    }
                    gamePlayController.getChainCards().remove(magicCard);
                    gamePlayController.getChainPlayers().remove(player);
                    GamePlayController.getInstance().getDuelMenu().printString(magicCard.getCardName()+" action got canceled");
                    GamePlayController.getInstance().getDuelMenu().printResponse(EFFECT_DONE_SUCCESSFULLY);
                    gamePlayController.getCurrentPlayer().getMagicCardZone().moveCardToGraveyardWithoutAddress(trap , gamePlayController.getCurrentPlayer());
                    return;
                } else {
                    GamePlayController.getInstance().getDuelMenu().printResponse(INVALID_CELL_NUMBER);
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

