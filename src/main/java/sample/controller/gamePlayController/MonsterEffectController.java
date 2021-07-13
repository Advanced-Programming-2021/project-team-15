package sample.controller.gamePlayController;

import sample.controller.Limit;
import sample.model.Player;
import sample.model.cards.Card;
import sample.model.cards.MonsterCard;
import sample.view.DuelMenu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import static sample.controller.responses.DuelMenuResponses.*;

public class MonsterEffectController {
    GamePlayController gamePlayController = GamePlayController.getInstance();
    DuelMenu duelMenu = DuelMenu.getInstance();

    public void commandKnight(Boolean x, MonsterCard commandKnight)  //flip and change pos to up
    {
        if (x)
            commandKnight.setActivated(true);
        int amount = 400;
        if (!x)
            amount = -amount;
        Map<Integer, MonsterCard> map = gamePlayController.getCurrentPlayer().getMonsterCardZone().getZoneCards();
        for (Map.Entry<Integer, MonsterCard> entry : map.entrySet()) {
            if (entry.getValue() != null && entry.getValue() != gamePlayController.getSelectedCard())
                entry.getValue().setGameATK(entry.getValue().getGameATK() + amount);
        }
    }

    public void commandKnightForNewAddedCard(MonsterCard monsterCard) {
        monsterCard.setGameATK(monsterCard.getGameATK() + 400);
    }

    public void Suijin(int num) throws IOException  //ATK
    {
        MonsterCard suijin = (MonsterCard) gamePlayController.getOpponentPlayer().getMonsterCardZone().getZoneCards().get(num);
        if (suijin.isActivated())
            return;
        suijin.setActivated(true);
        gamePlayController.getSuijinVictims().put((MonsterCard) gamePlayController.getSelectedCard(), ((MonsterCard) gamePlayController.getSelectedCard()).getGameATK());
        ((MonsterCard) gamePlayController.getSelectedCard()).setGameATK(0);
        duelMenu.printResponse(EFFECT_DONE_SUCCESSFULLY);
    }

    public void manEaterBug(MonsterCard manEaterBug) throws IOException  //flip or change pos from down to up
    {
        manEaterBug.setActivated(true);
        manEaterBug.setActivated(true);
        Player player = manEaterBug.getOwner();
        Player victim;
        if (player == gamePlayController.getCurrentPlayer())
            victim = gamePlayController.getOpponentPlayer();
        else victim = gamePlayController.getCurrentPlayer();
        duelMenu.printResponse(ENTER_ONE_NUMBER);
        while (true) {
            String string = duelMenu.getNum();
            if(string.equals("cancel"))
                return;
            int num = Integer.parseInt(string);
            if (victim.getMonsterCardZone().getZoneCards().get(num) != null) {
                victim.getMonsterCardZone().moveCardToGraveyard(num, victim);
                duelMenu.printResponse(EFFECT_DONE_SUCCESSFULLY);
                return;
            } else {
                duelMenu.printResponse(INVALID_CELL_NUMBER);
                duelMenu.printResponse(ENTER_ONE_NUMBER);
            }
        }
    }

    public boolean gateGuardian() throws IOException {
        if (gamePlayController.getCurrentPlayer().getMonsterCardZone().getNumberOfCard() < 3) {
            duelMenu.printResponse(CANT_SPECIAL_SUMMON);
            return false;
        }
        gamePlayController.getSelectedCard().setActivated(true);
        chooseThreeMonsterAndTribute();
        gamePlayController.doSummon();
        duelMenu.printResponse(EFFECT_DONE_SUCCESSFULLY);
        return true;
    }


    public boolean BeastKingBarbaros() throws IOException {
        DuelMenu.getInstance().printResponse(DO_YOU_WANNA_NORMAL_SUMMON);
        String string = duelMenu.yesNoQuestionAlert("do you wanna normal summon?");
        if (string.equals("yes")) {
            ((MonsterCard) gamePlayController.getSelectedCard()).setGameATK(1900);
            gamePlayController.doSummon();
            duelMenu.printResponse(CARD_SUMMONED);
            //gamePlayController.setSelectedCard(null);
            return true;
        }
        DuelMenu.getInstance().printResponse(DO_YOU_WANNA_TRIBUTE_TREE_MONSTERS);
        String ans = duelMenu.yesNoQuestionAlert("do you wanna tribute 3 monsters?");
        if (ans.equals("yes")) {
            if (gamePlayController.getCurrentPlayer().getMonsterCardZone().getNumberOfCard()<3) {
                duelMenu.printResponse(NOT_ENOUGH_CARD_TO_BE_TRIBUTE);
                return false;
            }
            chooseThreeMonsterAndTribute();
            GamePlayController.getEffectController().destroyCards(Card.CardType.MONSTER, false);
            GamePlayController.getEffectController().destroyCards(Card.CardType.MAGIC, false);
            gamePlayController.doSummon();
            duelMenu.printResponse(CARD_SUMMONED);
            //gamePlayController.setSelectedCard(null);
            return true;
        }
        return false;
    }


    public void chooseThreeMonsterAndTribute() throws IOException {
        int i = 0;
        duelMenu.printResponse(ENTER_ONE_NUMBER);
        while (true) {
          String string  = duelMenu.getNum();
          if(string.equals("cancel"))
              return;
          int num = Integer.parseInt(string);
            if (gamePlayController.getCurrentPlayer().getMonsterCardZone().getZoneCards().get(num) == null)
                duelMenu.printResponse(ENTER_ONE_NUMBER);
            else {
                gamePlayController.getCurrentPlayer().getMonsterCardZone().moveCardToGraveyard(num, gamePlayController.getCurrentPlayer());
                i++;
                if (i == 3) {
                    return;
                }
                duelMenu.printResponse(ENTER_ONE_NUMBER);
            }
        }
    }

    public void textChanger(MonsterCard textChanger) throws IOException {
        duelMenu.printResponse(ATTACK_CANCELED);
        gamePlayController.changeTurn();
        duelMenu.printResponse(DO_YOU_WANT_SUMMON_NORMAL_CYBERSE_CARD);
        String string = duelMenu.getString();
        if (string.equals("no")) {
            gamePlayController.changeTurn();
            return;
        }
        if (isNormalCyberseExists(gamePlayController.getCurrentPlayer().getHand().getZoneCards())) {
            DuelMenu.getInstance().printResponse(WANNA_CHOOSE_FROM_HAND);
            String ans = duelMenu.yesNoQuestionAlert("wanna choose from hand?");
            if (ans.equals("yes")) {
                checkFindAskTextChanger(gamePlayController.getCurrentPlayer().getHand().getZoneCards());
                gamePlayController.changeTurn();
                return;
            }
        }
        if (isNormalCyberseExists(gamePlayController.getCurrentPlayer().getDeckZone().getZoneCards())) {
            DuelMenu.getInstance().printResponse(WANNA_CHOOSE_FROM_DECK);
            String ans = duelMenu.yesNoQuestionAlert("wanna choose from deck?");
            if (ans.equals("yes")) {
                checkFindAskTextChanger(gamePlayController.getCurrentPlayer().getDeckZone().getZoneCards());
                gamePlayController.changeTurn();
                return;
            }
        }
        if (isNormalCyberseExists(gamePlayController.getCurrentPlayer().getGraveyardZone().getZoneCards())) {
            DuelMenu.getInstance().printResponse(WANNA_CHOOSE_FROM_GRAVEYARD);
            String ans = duelMenu.yesNoQuestionAlert("wanna choose from graveyard?");
            if (ans.equals("yes")) {
                checkFindAskTextChanger(gamePlayController.getCurrentPlayer().getGraveyardZone().getZoneCards());
                gamePlayController.changeTurn();
                return;
            }
        }
    }

    public void theCalculator(MonsterCard calculator) {
        calculator.setActivated(true);
        int sum = 0;
        for (int i = 1; i <= 5; i++) {
            if (gamePlayController.getCurrentPlayer().getMonsterCardZone().getZoneCards().get(i) != null) {
                int level = (calculator.getOwner()).getMonsterCardZone().getCardByPlaceNumber(i).getLevel();
                sum += level;
            }
        }
        calculator.setGameATK(sum * 300);
    }


    //BE ROO DAR ZAMIN BASHAD MirageDragon()
    public void MirageDragon(Card card) {
        if (card.getCardName().equals("Mirage Dragon"))
            gamePlayController.getOpponentPlayer().getLimits().add(Limit.CANT_ACTIVATE_TRAP);
    }

    public void yomiShip(MonsterCard attacker, MonsterCard yomiShip) throws IOException {
        yomiShip.setActivated(true);
        gamePlayController.getCurrentPlayer().getMonsterCardZone().moveCardToGraveyardWithoutAddress(attacker, gamePlayController.getCurrentPlayer());
        duelMenu.printResponse(EFFECT_DONE_SUCCESSFULLY);
    }

    public Boolean isNormalCyberseExists(ArrayList<Card> zoneCards) {
        for (Card card : zoneCards) {
            if ((card instanceof MonsterCard) && ((MonsterCard) card).getMonsterType() == MonsterCard.MonsterType.CYBER
                    && ((MonsterCard) card).getMonsterEffectType() == MonsterCard.MonsterEffectType.NORMAL) {
                return true;
            }
        }
        return false;
    }

    public void checkFindAskTextChanger(ArrayList<Card> zoneCards) throws IOException {
        int i = 0;
        while (i<zoneCards.size()) {
            if (zoneCards.get(i) != null && zoneCards.get(i).getCardType() == Card.CardType.MONSTER
                    && ((MonsterCard) zoneCards.get(i)).getMonsterType()== MonsterCard.MonsterType.CYBER
                    && !zoneCards.get(i).getCardName().equals("Texchanger")) {
                duelMenu.printString("wanna special summon this " + zoneCards.get(i).getCardName());
                String ans = duelMenu.getString();
                if (ans.equals("yes")) {
                    duelMenu.printResponse(ENTER_POS);
                    String ans1 = duelMenu.getString();
                    if (ans1.equals("ATK"))
                        ((MonsterCard) zoneCards.get(i)).setMode(MonsterCard.Mode.ATTACK);
                    else
                        ((MonsterCard) zoneCards.get(i)).setMode(MonsterCard.Mode.DEFENSE);
                    (zoneCards.get(i)).setHidden(false);
                    gamePlayController.getCurrentPlayer().getMonsterCardZone().
                            summonOrSetMonster((MonsterCard) zoneCards.get(i), gamePlayController.getCurrentPlayer());
                    return;
                }
            }
            i++;
        }
    }

    public void heraldOfCreation() throws IOException {
        duelMenu.printResponse(ENTER_ONE_NUMBER);
        while (true) {
            String string  = duelMenu.getNum();
            if(string.equals("cancel"))
                return;
            int n = Integer.parseInt(string);
            Card card = gamePlayController.getCurrentPlayer().getHand().getZoneCards().get(n - 1);
            if (card != null) {
                gamePlayController.getCurrentPlayer().getHand().removeCardFromHand(card);
                gamePlayController.getCurrentPlayer().getGraveyardZone().addCardToGraveyardZone(card);
                duelMenu.printResponse(ENTER_ONE_NUMBER);
                while (true) {
                    String o = duelMenu.getNum();
                    if(o.equals("cancel"))
                        return;
                    int num = Integer.parseInt(o);
                    if (gamePlayController.getCurrentPlayer().getGraveyardZone().getZoneCards().get(num - 1) != null &&
                            (gamePlayController.getCurrentPlayer().getGraveyardZone().getZoneCards().get(num - 1) instanceof MonsterCard) &&
                            ((MonsterCard) gamePlayController.getCurrentPlayer().getGraveyardZone().getZoneCards().get(num - 1)).getLevel() >= 7) {
                        MonsterCard monsterCard = ((MonsterCard) gamePlayController.getCurrentPlayer().getGraveyardZone().getZoneCards().get(num - 1));
                        gamePlayController.getCurrentPlayer().getGraveyardZone().removeCardFromGraveyardZone(monsterCard);
                        gamePlayController.getCurrentPlayer().getHand().addCardToHand(monsterCard);
                        duelMenu.printResponse(EFFECT_DONE_SUCCESSFULLY);
                        return;
                    } else {
                        duelMenu.printResponse(INVALID_CELL_NUMBER);
                        duelMenu.printResponse(ENTER_ONE_NUMBER);
                    }
                }

            } else {
                duelMenu.printResponse(INVALID_CELL_NUMBER);
                duelMenu.printResponse(ENTER_ONE_NUMBER);
            }
        }
    }

    public Boolean checkHeraldOfCreation() {
        for (Card card : gamePlayController.getCurrentPlayer().getGraveyardZone().getZoneCards()) {
            if ((card instanceof MonsterCard) && ((MonsterCard) card).getLevel() >= 7 &&
                    gamePlayController.getCurrentPlayer().getHand().getNumberOfCardsInHand() != 0)
                return true;
        }
        return false;
    }

    public Boolean checkTerratigerTheEmpoweredWarrior() {
        for (Card card : gamePlayController.getCurrentPlayer().getHand().getZoneCards()) {
            if ((card instanceof MonsterCard) && ((MonsterCard) card).getLevel() <= 4 &&
                    ((MonsterCard) card).getMonsterEffectType() == MonsterCard.MonsterEffectType.NORMAL)
                return true;
        }
        return false;
    }

    public void terratigertheEmpoweredWarrior() throws IOException {
        duelMenu.printResponse(ENTER_ONE_NUMBER);
        while (true) {
            String string  = duelMenu.getNum();
            if(string.equals("cancel"))
                return;
            int num = Integer.parseInt(string);
            Card card = gamePlayController.getCurrentPlayer().getHand().getZoneCards().get(num - 1);
            if ((card instanceof MonsterCard) && ((MonsterCard) card).getLevel() <= 4 &&
                    ((MonsterCard) card).getMonsterEffectType() == MonsterCard.MonsterEffectType.NORMAL) {
                ((MonsterCard) card).setMode(MonsterCard.Mode.DEFENSE);
                card.setHidden(false);
                gamePlayController.getCurrentPlayer().getMonsterCardZone().
                        summonOrSetMonster((MonsterCard) card, gamePlayController.getCurrentPlayer());
                duelMenu.printResponse(EFFECT_DONE_SUCCESSFULLY);
                return;
            } else {
                duelMenu.printResponse(INVALID_CELL_NUMBER);
                duelMenu.printResponse(ENTER_ONE_NUMBER);
            }
        }
    }

    public boolean theTricky(MonsterCard theTricky) throws IOException    //WHEN CAN I CALL ?
    {
        duelMenu.printResponse(DO_YOU_WANNA_SPECIAL_SUMMON);
        String string = duelMenu.yesNoQuestionAlert("do you wanna special summon this monster?");
        if (!string.equals("yes"))
            return false;
        if (gamePlayController.getCurrentPlayer().getHand().getZoneCards().isEmpty()) {
            duelMenu.printResponse(NOT_ENOUGH_CARD_TO_BE_TRIBUTE);
            return false;
        }
        duelMenu.printResponse(ENTER_ONE_NUMBER);
        while (true) {
            String s  = duelMenu.getNum();
            int num = Integer.parseInt(s);
            Card card = gamePlayController.getCurrentPlayer().getHand().getZoneCards().get(num - 1);
            if (card != null) {
                gamePlayController.getCurrentPlayer().getHand().removeCardFromHand(card);
                gamePlayController.getCurrentPlayer().getGraveyardZone().addCardToGraveyardZone(card);
                theTricky.setHidden(false);
                duelMenu.printResponse(ENTER_POS);  //special summon?
                String ans = duelMenu.getString();
                if (ans.equals("ATK"))
                    theTricky.setMode(MonsterCard.Mode.ATTACK);
                else
                    theTricky.setMode(MonsterCard.Mode.DEFENSE);
                gamePlayController.getCurrentPlayer().getMonsterCardZone().
                        summonOrSetMonster(theTricky, gamePlayController.getCurrentPlayer());
                return true;
            } else {
                duelMenu.printResponse(INVALID_CELL_NUMBER);
                duelMenu.printResponse(ENTER_ONE_NUMBER);
            }
        }
    }


}















