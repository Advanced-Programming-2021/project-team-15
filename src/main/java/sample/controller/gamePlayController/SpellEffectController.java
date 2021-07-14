package sample.controller.gamePlayController;

import sample.model.Player;
import sample.model.cards.Card;
import sample.model.cards.MagicCard;
import sample.model.cards.MonsterCard;
import sample.view.DuelMenu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import static sample.controller.responses.DuelMenuResponses.*;

public class SpellEffectController {

    GamePlayController gamePlayController = GamePlayController.getInstance();
    private boolean doIt = false;

    //TODO: put a while in sample.view ....

    static boolean isSubsetSum(ArrayList<MonsterCard> set,
                               int n, int sum) {
        if (sum == 0)
            return true;
        if (n == 0)
            return false;

        if (set.get(n - 1).getLevel() > sum)
            return isSubsetSum(set, n - 1, sum);

        return isSubsetSum(set, n - 1, sum)
                || isSubsetSum(set, n - 1, sum - set.get(n - 1).getLevel());
    }

    public boolean isDoIt() {
        return doIt;
    }
    //TODO : FIRST 4 EFFECTS SHOULD HE CHECKED DUHHHHHHH

    public void setDoIt(boolean doIt) {
        this.doIt = doIt;
    }

    public void yami(Boolean x, MagicCard card) throws IOException {
        if (!doIt) {
            if (x) gamePlayController.activateSelectedCard();
            return;
        }
        int amount = 200;
        if (!x)
            amount = -amount;
        GamePlayController.getEffectController().addATK(MonsterCard.MonsterType.FIEND, true, amount, card.getOwner());
        GamePlayController.getEffectController().addATK(MonsterCard.MonsterType.SPELL_CASTER, true, amount, card.getOwner());
        GamePlayController.getEffectController().addATK(MonsterCard.MonsterType.FAIRY, true, -amount, card.getOwner());
        GamePlayController.getEffectController().addDEF(MonsterCard.MonsterType.FIEND, true, amount, card.getOwner());
        GamePlayController.getEffectController().addDEF(MonsterCard.MonsterType.SPELL_CASTER, true, amount, card.getOwner());
        GamePlayController.getEffectController().addDEF(MonsterCard.MonsterType.FAIRY, true, -amount, card.getOwner());
    }

    public void spellAbsorption() throws IOException {
        if (!doIt) {
            gamePlayController.activateSelectedCard();
            return;
        }
        for (Map.Entry<Player, Card> entry : gamePlayController.getActivatedCards().entrySet()) {
            if (entry.getValue().getCardName().equals("Spell Absorption")) ;
            entry.getKey().increaseLifePoint(500);
            GamePlayController.getInstance().getDuelMenu().printResponse(EFFECT_DONE_SUCCESSFULLY);
        }
    }

    public void yamiForNewAddedCard(MonsterCard monsterCard) {
        GamePlayController.getInstance().getDuelMenu().printString("yami effect for new added card!");
        if (monsterCard.getMonsterType() == MonsterCard.MonsterType.FIEND ||
                monsterCard.getMonsterType() == MonsterCard.MonsterType.SPELL_CASTER) {
            monsterCard.setAttackPoint(monsterCard.getAttackPoint() + 200);
            monsterCard.setDefensePoint(monsterCard.getDefensePoint() + 200);
        } else if (monsterCard.getMonsterType() == MonsterCard.MonsterType.FAIRY) {
            monsterCard.setAttackPoint(monsterCard.getAttackPoint() - 200);
            monsterCard.setDefensePoint(monsterCard.getDefensePoint() - 200);
        }
    }

    public void forest(Boolean x, MagicCard card) throws IOException {
        if (!doIt) {
            if (x) gamePlayController.activateSelectedCard();
            return;
        }
        int amount = 200;
        if (!x)
            amount = -amount;
        GamePlayController.getEffectController().addATK(MonsterCard.MonsterType.BEAST, true, amount, card.getOwner());
        GamePlayController.getEffectController().addDEF(MonsterCard.MonsterType.BEAST, true, amount, card.getOwner());
        GamePlayController.getEffectController().addATK(MonsterCard.MonsterType.INSECT, true, amount, card.getOwner());
        GamePlayController.getEffectController().addDEF(MonsterCard.MonsterType.INSECT, true, amount, card.getOwner());
        GamePlayController.getEffectController().addATK(MonsterCard.MonsterType.BEAST_WARRIOR, true, amount, card.getOwner());
        GamePlayController.getEffectController().addDEF(MonsterCard.MonsterType.BEAST_WARRIOR, true, amount, card.getOwner());
    }

    public void forestForNewAddedCard(MonsterCard monsterCard) {
        GamePlayController.getInstance().getDuelMenu().printString("forest effect for new added card!");
        if (monsterCard.getMonsterType() == MonsterCard.MonsterType.BEAST ||
                monsterCard.getMonsterType() == MonsterCard.MonsterType.INSECT ||
                monsterCard.getMonsterType() == MonsterCard.MonsterType.BEAST_WARRIOR) {
            monsterCard.setGameATK(monsterCard.getGameATK() + 200);
            monsterCard.setGameDEF(monsterCard.getGameDEF() + 200);
        }
    }

    public void closedForest(Boolean x, Card card) throws IOException {
        if (!doIt) {
            if (x) gamePlayController.activateSelectedCard();
            return;
        }
        int i = GamePlayController.getEffectController().numberOfDeadMonsters();
        if (!x)
            i = -i;
        GamePlayController.getEffectController().addATK(MonsterCard.MonsterType.BEAST, false, i * 100, card.getOwner());
    }

    public void closedForestForNewAddedCard(MonsterCard monsterCard) {
        GamePlayController.getInstance().getDuelMenu().printString("closed forest effect for new added card!");
        int i = GamePlayController.getEffectController().numberOfDeadMonsters();
        if (monsterCard.getMonsterType() == MonsterCard.MonsterType.BEAST) {
            monsterCard.setGameATK(monsterCard.getGameATK() + i * 100);
        }
    }

    public void closedForestForNewCardAddedToGraveyard(MonsterCard monsterCard, MagicCard closedForest) {
        GamePlayController.getEffectController().addATK(MonsterCard.MonsterType.BEAST, false, 100, closedForest.getOwner());
    }

    public void umiiruka(Boolean x, Card card) throws IOException {
        if (!doIt) {
            if (x) gamePlayController.activateSelectedCard();
            return;
        }
        if (!x) {
            GamePlayController.getEffectController().addATK(MonsterCard.MonsterType.AQUA, true, -500, card.getOwner());
            GamePlayController.getEffectController().addATK(MonsterCard.MonsterType.AQUA, true, 400, card.getOwner());
        }
        GamePlayController.getEffectController().addATK(MonsterCard.MonsterType.AQUA, true, 500, card.getOwner());
        GamePlayController.getEffectController().addATK(MonsterCard.MonsterType.AQUA, true, -400, card.getOwner());
    }


    public void umiirukaForNewAddedCard(MonsterCard monsterCard) {
        GamePlayController.getInstance().getDuelMenu().printString("umiiruka effect for new added card!");
        if (monsterCard.getMonsterType() == MonsterCard.MonsterType.AQUA) {
            monsterCard.setGameATK(monsterCard.getGameATK() + 500);
            monsterCard.setGameDEF(monsterCard.getGameDEF() + -400);
        }
    }

    public boolean checkSwordOfDarkDestruction() {
        int i = 0;
        Map<Integer, MonsterCard> map = gamePlayController.getCurrentPlayer().getMonsterCardZone().getZoneCards();
        for (Map.Entry<Integer, MonsterCard> entry : map.entrySet()) {
            if ((entry.getValue() != null && entry.getValue().getMonsterType() == MonsterCard.MonsterType.FIEND && !entry.getValue().getHidden()) ||
                    (entry.getValue() != null && entry.getValue().getMonsterType() == MonsterCard.MonsterType.SPELL_CASTER && !entry.getValue().getHidden()))
                i++;
        }
        if (i == 0)
            return false;
        else return true;
    }

    public void swordOfDarkDestruction(MagicCard swordOfDarkDestruction) throws IOException {
        if (!doIt) {
            if (checkSwordOfDarkDestruction()) gamePlayController.activateSelectedCard();
            return;
        }
        Map<Integer, MonsterCard> map = gamePlayController.getCurrentPlayer().getMonsterCardZone().getZoneCards();
        GamePlayController.getInstance().getDuelMenu().printResponse(ENTER_ONE_NUMBER);
        while (true) {
            String s  = GamePlayController.getInstance().getDuelMenu().getNum("choose your monster");
            if(s.equals("cancel"))
                return;
            int num = Integer.parseInt(s);
            if ((map.get(num) != null && map.get(num).getMonsterType() != MonsterCard.MonsterType.FIEND) &&
                    (map.get(num) != null && map.get(num).getMonsterType() != MonsterCard.MonsterType.FAIRY)) {
                map.get(num).setGameATK(map.get(num).getGameATK() + 400);
                map.get(num).setGameDEF(map.get(num).getGameDEF() - 200);
                GamePlayController.getEffectController().getEquippedCardsBySpells().put(swordOfDarkDestruction, map.get(num));
                GamePlayController.getInstance().getDuelMenu().printResponse(CARD_EQUIPPED);
                return;
            } else {
                GamePlayController.getInstance().getDuelMenu().printResponse(INVALID_CELL_NUMBER);
                GamePlayController.getInstance().getDuelMenu().printResponse(ENTER_ONE_NUMBER);
            }

        }
    }

    //TODO : handle death !
    public Boolean checkBlackPendant() {
        if (GamePlayController.getEffectController().getNumberOfFaceUpMonstersOfCurrentPlayer() == 0)
            return false;
        else return true;
    }

    public void blackPendant(MagicCard blackPendant) throws IOException {
        if (!doIt) {
            if (checkBlackPendant()) gamePlayController.activateSelectedCard();
            return;
        }
        GamePlayController.getInstance().getDuelMenu().printResponse(ENTER_ONE_NUMBER);
        Map<Integer, MonsterCard> map = gamePlayController.getCurrentPlayer().getMonsterCardZone().getZoneCards();
        while (true) {
            String input = GamePlayController.getInstance().getDuelMenu().getNum("choose your monster");
            if (input.equals("cancel")) {
                GamePlayController.getInstance().getDuelMenu().printString("canceled");
                return;
            }
            int num = Integer.parseInt(input);
            if (map.get(num) != null && !map.get(num).getHidden()) {
                map.get(num).setGameATK(map.get(num).getGameATK() + 500);
                GamePlayController.getEffectController().getEquippedCardsBySpells().put(blackPendant, map.get(num));
                GamePlayController.getInstance().getDuelMenu().printResponse(CARD_EQUIPPED);
                return;
            } else {
                GamePlayController.getInstance().getDuelMenu().printResponse(INVALID_CELL_NUMBER);
                GamePlayController.getInstance().getDuelMenu().printResponse(ENTER_ONE_NUMBER);
            }
        }
    }

    public Boolean checkUnitedWeStand() {
        if (GamePlayController.getEffectController().getNumberOfFaceUpMonstersOfCurrentPlayer() == 0)
            return false;
        else return true;
    }

    public void unitedWeStand(MagicCard unitedWeStand) throws IOException {
        if (!doIt) {
            if (checkUnitedWeStand()) gamePlayController.activateSelectedCard();
            return;
        }
        GamePlayController.getInstance().getDuelMenu().printResponse(ENTER_ONE_NUMBER);
        Map<Integer, MonsterCard> map = gamePlayController.getCurrentPlayer().getMonsterCardZone().getZoneCards();
        int amount = GamePlayController.getEffectController().getNumberOfFaceUpMonstersOfCurrentPlayer();
        while (true) {
            String s  = GamePlayController.getInstance().getDuelMenu().getNum("choose your monster");
            if(s.equals("cancel"))
                return;
            int num = Integer.parseInt(s);
            if (map.get(num) != null) {
                map.get(num).setGameATK(map.get(num).getGameATK() + amount * 800);
                map.get(num).setGameDEF(map.get(num).getGameDEF() + amount * 800);
                GamePlayController.getEffectController().getEquippedCardsBySpells().put(unitedWeStand, map.get(num));
                GamePlayController.getInstance().getDuelMenu().printResponse(CARD_EQUIPPED);
                return;
            } else {
                GamePlayController.getInstance().getDuelMenu().printResponse(INVALID_CELL_NUMBER);
                GamePlayController.getInstance().getDuelMenu().printResponse(ENTER_ONE_NUMBER);
            }
        }

    }

    public Boolean checkMagnumShield() {
        int i = 0;
        Map<Integer, MonsterCard> map = gamePlayController.getCurrentPlayer().getMonsterCardZone().getZoneCards();
        for (Map.Entry<Integer, MonsterCard> entry : map.entrySet()) {
            if ((entry.getValue() != null && entry.getValue().getMonsterType() == MonsterCard.MonsterType.WARRIOR && !entry.getValue().getHidden()))
                i++;
        }
        if (i == 0)
            return false;
        else return true;
    }

    public void magnumShield(MagicCard magnumShield) throws IOException {
        if (!doIt) {
            if (checkMagnumShield())
                gamePlayController.activateSelectedCard();
            return;
        }
        Map<Integer, MonsterCard> map = gamePlayController.getCurrentPlayer().getMonsterCardZone().getZoneCards();
        GamePlayController.getInstance().getDuelMenu().printResponse(ENTER_ONE_NUMBER);
        while (true) {
            String s  = GamePlayController.getInstance().getDuelMenu().getNum("choose your monster");
            if(s.equals("cancel"))
                return;
            int num = Integer.parseInt(s);
            if (map.get(num) != null && map.get(num).getMonsterType() == MonsterCard.MonsterType.WARRIOR && !map.get(num).getHidden()) {
                if (map.get(num).getMode() == MonsterCard.Mode.ATTACK) {
                    map.get(num).setGameATK(map.get(num).getGameATK() + map.get(num).getDefensePoint());
                    GamePlayController.getEffectController().getEquippedCardsBySpells().put(magnumShield, map.get(num));
                    GamePlayController.getInstance().getDuelMenu().printResponse(CARD_EQUIPPED);
                    return;
                } else if (map.get(num).getMode() == MonsterCard.Mode.DEFENSE) {
                    map.get(num).setGameDEF(map.get(num).getGameDEF() + map.get(num).getGameATK());
                    GamePlayController.getEffectController().getEquippedCardsBySpells().put(magnumShield, map.get(num));
                    GamePlayController.getInstance().getDuelMenu().printResponse(CARD_EQUIPPED);
                    return;
                }
            } else {
                GamePlayController.getInstance().getDuelMenu().printResponse(INVALID_CELL_NUMBER);
                GamePlayController.getInstance().getDuelMenu().printResponse(ENTER_ONE_NUMBER);
            }
        }

    }

    public Boolean checkTerraforming() {
        ArrayList<Card> deck = gamePlayController.getCurrentPlayer().getDeckZone().getZoneCards();
        for (Card card : deck) {
            if (card instanceof MagicCard && ((MagicCard) card).getMagicType() == MagicCard.MagicType.SPELL
                    && ((MagicCard) card).getCardIcon() == MagicCard.CardIcon.FIELD)
                return true;
        }
        return false;
    }

    public void terraforming(MagicCard terraforming) throws IOException {
        if (!doIt) {
            if (checkTerraforming()) gamePlayController.activateSelectedCard();
            return;
        }
        for (Card card : gamePlayController.getCurrentPlayer().getDeckZone().getZoneCards()) {
            if (card instanceof MagicCard && ((MagicCard) card).getMagicType() == MagicCard.MagicType.SPELL
                    && ((MagicCard) card).getCardIcon() == MagicCard.CardIcon.FIELD) {
                String ans= GamePlayController.getInstance().getDuelMenu().yesNoQuestionAlertWithCardImage("do you wanna add this " + card.getCardName() + " to your had?", card);
                if(ans.equals("cancel")) return;
                if (ans.equals("yes")) {
                    gamePlayController.getCurrentPlayer().getHand().addCardToHand(card);
                    gamePlayController.getCurrentPlayer().getDeckZone().removeCardFromDeckZone(card);
                    GamePlayController.getInstance().getDuelMenu().printResponse(EFFECT_DONE_SUCCESSFULLY);
                    gamePlayController.getCurrentPlayer().getMagicCardZone().moveCardToGraveyardWithoutAddress(terraforming, gamePlayController.getCurrentPlayer());
                    return;
                } else continue;
            }
        }
    }

    public Boolean checkPotOfGReed() {
        if (gamePlayController.getCurrentPlayer().getDeckZone().getZoneCards().size() < 2)
            return false;
        else return true;
    }

    public void potOfGReed(MagicCard potOfGreed) throws IOException {
        if (!doIt) {
            if (checkPotOfGReed()) gamePlayController.activateSelectedCard();
            return;
        }

        for (int i = 0; i < 2; i++) {
            gamePlayController.getCurrentPlayer().getHand().addCardToHand(gamePlayController.getCurrentPlayer().getDeckZone().getZoneCards().get(0));
            gamePlayController.getCurrentPlayer().getDeckZone().removeCardFromDeckZone(gamePlayController.getCurrentPlayer().getDeckZone().getZoneCards().get(0));
        }
        GamePlayController.getInstance().getDuelMenu().printResponse(EFFECT_DONE_SUCCESSFULLY);
        gamePlayController.getCurrentPlayer().getMagicCardZone().moveCardToGraveyardWithoutAddress(potOfGreed, gamePlayController.getCurrentPlayer());

    }

    public Boolean checkRaigeki() {
        if (gamePlayController.getOpponentPlayer().getMonsterCardZone().getNumberOfCard() == 0)
            return false;
        else return true;
    }

    public void raigeki(MagicCard raigeki) throws IOException {
        if (!doIt) {
            if (checkRaigeki()) gamePlayController.activateSelectedCard();
            return;
        }
        GamePlayController.getEffectController().destroyCards(Card.CardType.MONSTER, false);
        GamePlayController.getInstance().getDuelMenu().printResponse(EFFECT_DONE_SUCCESSFULLY);
        gamePlayController.getCurrentPlayer().getMagicCardZone().moveCardToGraveyardWithoutAddress(raigeki, gamePlayController.getCurrentPlayer());
    }

    public Boolean checkHarpiesFeatherDuster() {
        if (gamePlayController.getOpponentPlayer().getMagicCardZone().getNumberOfCard() == 0)
            return false;
        else return true;
    }

    public void harpiesFeatherDuster(MagicCard harpiesFeatherDuster) throws IOException {
        if (!doIt) {
            if (checkHarpiesFeatherDuster()) gamePlayController.activateSelectedCard();
            return;
        }
        GamePlayController.getEffectController().destroyCards(Card.CardType.MAGIC, false);
        GamePlayController.getInstance().getDuelMenu().printResponse(EFFECT_DONE_SUCCESSFULLY);
        gamePlayController.getCurrentPlayer().getMagicCardZone().moveCardToGraveyardWithoutAddress(harpiesFeatherDuster, gamePlayController.getCurrentPlayer());
    }

    public Boolean checkDarkHole() {
        if (gamePlayController.getOpponentPlayer().getMonsterCardZone().getNumberOfCard() == 0 && gamePlayController.getCurrentPlayer().getMonsterCardZone().getNumberOfCard() == 0)
            return false;
        else return true;
    }

    public void darkHole(MagicCard darkHole) throws IOException {
        if (!doIt) {
            if (checkDarkHole()) gamePlayController.activateSelectedCard();
            return;
        }
        GamePlayController.getEffectController().destroyCards(Card.CardType.MONSTER, true);
        GamePlayController.getInstance().getDuelMenu().printResponse(EFFECT_DONE_SUCCESSFULLY);
        gamePlayController.getCurrentPlayer().getMagicCardZone().moveCardToGraveyardWithoutAddress(darkHole, gamePlayController.getCurrentPlayer());
    }

    public Boolean checkMysticalSpaceTyphoon(Player ownerOfCard) {
        int num = gamePlayController.getTheOtherPlayer(ownerOfCard).getMagicCardZone().getNumberOfCard();
        num += ownerOfCard.getMagicCardZone().getNumberOfCard();
        num += ownerOfCard.getFieldZone().getZoneCards().size();
        num += gamePlayController.getTheOtherPlayer(ownerOfCard).getFieldZone().getZoneCards().size();
        if (num == 0)
            return false;
        else return true;
    }

    public void mysticalSpaceTyphoon(MagicCard mysticalSpaceTyphoon, Player ownerOfCard) throws IOException {
        if (!doIt) {
            if (checkMysticalSpaceTyphoon(ownerOfCard) && !gamePlayController.isSpellSSetInThisTurn(mysticalSpaceTyphoon))
                gamePlayController.activateSelectedCard();
            return;
        }
        Map<Integer, MagicCard> map;
        Player player;
        String rivalOrNot = GamePlayController.getInstance().getDuelMenu().chooseQuestion("choose player",ownerOfCard.getUser().getNickName(),gamePlayController.getTheOtherPlayer(ownerOfCard).getUser().getNickName());
        if (rivalOrNot == gamePlayController.getTheOtherPlayer(ownerOfCard).getUser().getNickName()) {
            player = gamePlayController.getTheOtherPlayer(ownerOfCard);
            map = gamePlayController.getTheOtherPlayer(ownerOfCard).getMagicCardZone().getZoneCards();
        } else {
            map = ownerOfCard.getMagicCardZone().getZoneCards();
            player = ownerOfCard;
        }
        GamePlayController.getInstance().getDuelMenu().printResponse(ENTER_ONE_NUMBER);
        while (true) {
            String s  = GamePlayController.getInstance().getDuelMenu().getNum("choose your magic");
            if(s.equals("cancel"))
                return;
            int num = Integer.parseInt(s);
            if (num <= 5 && map.get(num) != null) {
                player.getMagicCardZone().moveCardToGraveyard(num, player);
                GamePlayController.getInstance().getDuelMenu().printResponse(EFFECT_DONE_SUCCESSFULLY);
                if (mysticalSpaceTyphoon.getCardPlacedZone() != ownerOfCard.getGraveyardZone())
                    ownerOfCard.getMagicCardZone().moveCardToGraveyardWithoutAddress(mysticalSpaceTyphoon, ownerOfCard);
                return;
            } else if (num == 6 && !player.getFieldZone().getZoneCards().isEmpty()) {
                player.getGraveyardZone().addCardToGraveyardZone(player.getFieldZone().getZoneCards().get(0));
                GamePlayController.getInstance().getDuelMenu().removeFromField( player.getFieldZone().getZoneCards().get(0));
                player.getFieldZone().getZoneCards().remove(0);
                GamePlayController.getInstance().getDuelMenu().printResponse(EFFECT_DONE_SUCCESSFULLY);
                ownerOfCard.getMagicCardZone().moveCardToGraveyardWithoutAddress(mysticalSpaceTyphoon, ownerOfCard);
            } else {
                GamePlayController.getInstance().getDuelMenu().printResponse(INVALID_CELL_NUMBER);
                GamePlayController.getInstance().getDuelMenu().printResponse(ENTER_ONE_NUMBER);
            }
        }
    }

    public boolean checkRingOfDefense() {
        if (!gamePlayController.getChainCards().isEmpty() && gamePlayController.getChainCards().get(gamePlayController.getChainCards().size() - 1).getCardName().equals("Magic Jammer"))
            return true;
        else return false;
    }

    public void ringOfDefense(MagicCard ringOfDefense) throws IOException {
        if (!doIt) {
            if (checkRingOfDefense() && !gamePlayController.isSpellSSetInThisTurn(ringOfDefense))
                gamePlayController.activateSelectedCard();
            return;
        }
        if (gamePlayController.getChainCards().get(gamePlayController.getChainCards().size() - 1).getCardName().equals("Magic Jammer")) {
            gamePlayController.getChainCards().remove(gamePlayController.getChainCards().size() - 1);
            gamePlayController.getChainPlayers().remove(gamePlayController.getChainCards().size() - 1);
            GamePlayController.getInstance().getDuelMenu().printResponse(EFFECT_DONE_SUCCESSFULLY);
        }
    }

    public Boolean checkTwinTwisters(Player ownerOfCard) {
        if (((ownerOfCard.getMagicCardZone().getNumberOfCard() + gamePlayController.getTheOtherPlayer(ownerOfCard).getMagicCardZone().getNumberOfCard() + ownerOfCard.getFieldZone().getZoneCards().size() +
                gamePlayController.getTheOtherPlayer(ownerOfCard).getFieldZone().getZoneCards().size()) <= 1) ||
                ownerOfCard.getHand().getNumberOfCardsInHand() == 0)
            return false;
        else return true;
    }

    public void twinTwisters(MagicCard twinTwisters, Player ownerOfCard) throws IOException {
        if (!doIt) {
            if (checkTwinTwisters(ownerOfCard) && !gamePlayController.isSpellSSetInThisTurn(twinTwisters))
                gamePlayController.activateSelectedCard();
            return;
        }
        int i = 0;
        GamePlayController.getInstance().getDuelMenu().printResponse(ENTER_ONE_NUMBER);  // of your hand !
        while (true) {
            String ans = GamePlayController.getInstance().getDuelMenu().getNum("choose from hand");
            if (ans.equals("cancel")) {
                GamePlayController.getInstance().getDuelMenu().printResponse(CANCELED);
                return;
            }
            int num = Integer.parseInt(ans);
            if (ownerOfCard.getHand().getNumberOfCardsInHand() >= num) {
                ownerOfCard.getMagicCardZone().moveCardToGraveyardWithoutAddress(twinTwisters, ownerOfCard);
                ownerOfCard.getHand().removeCardFromHand(ownerOfCard.getHand().getZoneCards().get(num-1));
                break;
            } else {
                GamePlayController.getInstance().getDuelMenu().printResponse(INVALID_CELL_NUMBER);
                GamePlayController.getInstance().getDuelMenu().printResponse(ENTER_ONE_NUMBER);
            }
        }
        GamePlayController.getInstance().getDuelMenu().printResponse(ENTER_ONE_NUMBER);
        while (true) {
            String ans = GamePlayController.getInstance().getDuelMenu().getNum("choose from magic");
            if (ans.equals("cancel")) {
                GamePlayController.getInstance().getDuelMenu().printResponse(CANCELED);
                return;
            }
            int num = Integer.parseInt(ans);
            if (num <= 5 && gamePlayController.getTheOtherPlayer(ownerOfCard).getMagicCardZone().getZoneCards().get(num) != null) {
                gamePlayController.getTheOtherPlayer(ownerOfCard).getMagicCardZone().moveCardToGraveyard(num, gamePlayController.getTheOtherPlayer(ownerOfCard));
                i++;
            } else if (num == 6 && !gamePlayController.getTheOtherPlayer(ownerOfCard).getFieldZone().getZoneCards().isEmpty()) {
                gamePlayController.getTheOtherPlayer(ownerOfCard).getGraveyardZone().addCardToGraveyardZone(gamePlayController.getTheOtherPlayer(ownerOfCard).getFieldZone().getZoneCards().get(0));
                GamePlayController.getInstance().getDuelMenu().removeFromField( gamePlayController.getTheOtherPlayer(ownerOfCard).getFieldZone().getZoneCards().get(0));
                gamePlayController.getTheOtherPlayer(ownerOfCard).getFieldZone().getZoneCards().remove(0);
                i++;
                if (i == 1) {
                    GamePlayController.getInstance().getDuelMenu().printResponse(WANNA_ADD_ANOTHER_CARD);
                    String answer = GamePlayController.getInstance().getDuelMenu().yesNoQuestionAlert("wanna add another card?");
                    if(answer.equals("cancel")) return;
                    if (answer.equals("yes")) {
                        GamePlayController.getInstance().getDuelMenu().printResponse(ENTER_ONE_NUMBER);
                        continue;
                    } else {
                        GamePlayController.getInstance().getDuelMenu().printResponse(EFFECT_DONE_SUCCESSFULLY);
                        ownerOfCard.getMagicCardZone().moveCardToGraveyardWithoutAddress(twinTwisters, ownerOfCard);
                        return;
                    }
                } else if (i == 2) {
                    GamePlayController.getInstance().getDuelMenu().printResponse(EFFECT_DONE_SUCCESSFULLY);
                    ownerOfCard.getMagicCardZone().moveCardToGraveyardWithoutAddress(twinTwisters, ownerOfCard);
                    return;
                }
            } else {
                GamePlayController.getInstance().getDuelMenu().printResponse(INVALID_CELL_NUMBER);
                GamePlayController.getInstance().getDuelMenu().printResponse(ENTER_ONE_NUMBER);
            }
        }
    }

    public Boolean checkChangeOfHeart() {
        if (gamePlayController.getOpponentPlayer().getMonsterCardZone().getNumberOfCard() == 0)
            return false;
        else return true;
    }
//
//    public void swordOfRevealingLight(Player player) {
//        Map<Integer, MonsterCard> map = gamePlayController.getOpponentPlayer().getMonsterCardZone().getZoneCards();
//        for (Map.Entry<Integer, MonsterCard> entry : map.entrySet()) {
//            if (entry.getValue() != null && entry.getValue().getHidden())
//                entry.getValue().setHidden(false);
//        }
//        GamePlayController.getInstance().getDuelMenu().printResponse(EFFECT_DONE_SUCCESSFULLY);
//    }

    public void changeOfHeart(MagicCard magicCard) throws IOException {
        if (!doIt) {
            if (checkChangeOfHeart()) gamePlayController.activateSelectedCard();
            return;
        }
        Map<Integer, MonsterCard> map = gamePlayController.getOpponentPlayer().getMonsterCardZone().getZoneCards();
        GamePlayController.getInstance().getDuelMenu().printResponse(ENTER_ONE_NUMBER);
        while (true) {
            String s  = GamePlayController.getInstance().getDuelMenu().getNum("choose from opponent monster");
            if(s.equals("cancel"))
                return;
            int num = Integer.parseInt(s);
            if (map.get(num) != null) {
                GamePlayController.getEffectController().getControl(map.get(num));
                GamePlayController.getInstance().getDuelMenu().printResponse(EFFECT_DONE_SUCCESSFULLY);
                gamePlayController.getCurrentPlayer().getMagicCardZone().moveCardToGraveyardWithoutAddress(magicCard, gamePlayController.getCurrentPlayer());
                return;
            } else {
                GamePlayController.getInstance().getDuelMenu().printResponse(INVALID_CELL_NUMBER);
                GamePlayController.getInstance().getDuelMenu().printResponse(ENTER_ONE_NUMBER);
            }
        }
    }

    public boolean checkMonsterReborn() {
        int count = 0;
        for (int i = 0; i < gamePlayController.getCurrentPlayer().getGraveyardZone().getZoneCards().size(); i++) {
            if (gamePlayController.getCurrentPlayer().getGraveyardZone().getZoneCards().get(i) instanceof MonsterCard) {
                count++;
            }
        }
        for (int j = 0; j < gamePlayController.getOpponentPlayer().getGraveyardZone().getZoneCards().size(); j++) {
            if (gamePlayController.getCurrentPlayer().getGraveyardZone().getZoneCards().get(j) instanceof MonsterCard) {
                count++;
            }
        }
        if (count == 0) {
            return false;
        } else return true;
    }

    public void monsterReborn(MagicCard monsterReborn) throws IOException {
        if (!doIt) {
            if (checkMonsterReborn()) gamePlayController.activateSelectedCard();
            return;
        }
        Player player;
        GamePlayController.getInstance().getDuelMenu().printResponse(ENTER_PLAYER);
        String rivalOrNot = GamePlayController.getInstance().getDuelMenu().chooseQuestion("choose player :",gamePlayController.getCurrentPlayer().getUser().getNickName(),gamePlayController.getOpponentPlayer().getUser().getNickName());
        if(rivalOrNot.equals(gamePlayController.getCurrentPlayer().getUser().getNickName()))
            rivalOrNot ="player";
        if(rivalOrNot.equals(gamePlayController.getOpponentPlayer().getUser().getNickName()))
            rivalOrNot ="rival";

        if (rivalOrNot.equals("cancel")) {
            GamePlayController.getInstance().getDuelMenu().printResponse(CANCELED);
            return;
        }
        if (rivalOrNot.equals("rival"))
            player = gamePlayController.getOpponentPlayer();
        else player = gamePlayController.getCurrentPlayer();
        GamePlayController.getInstance().getDuelMenu().printResponse(ENTER_ONE_NUMBER);
        while (true) {
             String ans=  GamePlayController.getInstance().getDuelMenu().getNum("choose a random monster from grave yard");
              if (rivalOrNot.equals("cancel")) {
                GamePlayController.getInstance().getDuelMenu().printResponse(CANCELED);
                return;
            }
            int num = Integer.parseInt(ans);
            if (num <= player.getGraveyardZone().getZoneCards().size() && (player.getGraveyardZone().getZoneCards().get(num - 1) instanceof MonsterCard)) {
                Card card = player.getGraveyardZone().getZoneCards().get(num - 1);
                card.setHidden(false);
                GamePlayController.getInstance().getDuelMenu().printResponse(ENTER_POS);
                String answer = GamePlayController.getInstance().getDuelMenu().chooseQuestion("choose position : (ATK/DEF)","ATK","DEF");
                if(answer.equals("cancel"))
                    return;
                if (answer.equals("ATK"))
                    ((MonsterCard) card).setMode(MonsterCard.Mode.ATTACK);
                else
                    ((MonsterCard) card).setMode(MonsterCard.Mode.DEFENSE);
                player.getMonsterCardZone().summonOrSetMonster((MonsterCard) card, player);
                gamePlayController.getCurrentPlayer().getMagicCardZone().moveCardToGraveyardWithoutAddress(monsterReborn, gamePlayController.getCurrentPlayer());
                GamePlayController.getInstance().getDuelMenu().printResponse(EFFECT_DONE_SUCCESSFULLY);
                break;
            } else {
                GamePlayController.getInstance().getDuelMenu().printResponse(INVALID_CELL_NUMBER);
                GamePlayController.getInstance().getDuelMenu().printResponse(ENTER_ONE_NUMBER);
            }
        }
    }

    public MonsterCard getRitualMonsterOfHand(Player player) {
        for (int i = 0; i < 5; i++) {
            if (player.getHand().getZoneCards().get(i) != null && (player.getHand().getZoneCards().get(i) instanceof MonsterCard)
                    && ((MonsterCard) player.getHand().getZoneCards().get(i)).getMonsterEffectType() == MonsterCard.MonsterEffectType.RITUAL)
                return (MonsterCard) player.getHand().getZoneCards().get(i);
        }
        return null;
    }

    public boolean canWeRitualSummon(Player player) {
        if (getRitualMonsterOfHand(player) != null && isSubsetSum(getWholeMonsters(player), getWholeMonsters(player).size(), getRitualMonsterOfHand(player).getLevel()))
            return true;
        else return false;

    }

    public ArrayList<MonsterCard> getWholeMonsters(Player player) {
        ArrayList<MonsterCard> monsters = new ArrayList<>();
//        for (int i = 1; i <= 5; i++) {
//            if (player.getMonsterCardZone().getZoneCards().get(i) != null && player.getMonsterCardZone().getZoneCards().get(i) instanceof MonsterCard)
//                monsters.add((MonsterCard) player.getMonsterCardZone().getZoneCards().get(i));
//        }
        for (Card card : player.getDeckZone().getZoneCards()) {
            if (card instanceof MonsterCard && ((MonsterCard) card).getMonsterEffectType() == MonsterCard.MonsterEffectType.NORMAL)
                monsters.add((MonsterCard) card);
        }
        return monsters;
    }

    public boolean checkAdvancedRitualArt() throws IOException {
        if (canWeRitualSummon(gamePlayController.getCurrentPlayer()))
            return true;
        else {
            GamePlayController.getInstance().getDuelMenu().printResponse(NO_WAY_TO_RITUAL_SUMMON);
            return false;
        }
    }

    public boolean checkSummon(int[] numbers, Player player, int level) {
        int out[] = numbers;
        int sum = 0;
        for (int i = 0; i < out.length; i++) {
            sum = sum + (getWholeMonsters(player)).get(out[i]).getLevel();
        }
        return sum == level;
    }

    public void showWholeMonsters(ArrayList<MonsterCard> monsterCards) throws IOException {
      GamePlayController.getInstance().getDuelMenu().showListOfCards(monsterCards);
    }


    public void advancedRitualArt(MagicCard advancedRitualArt) throws IOException {
        if (!doIt) {
            if (checkAdvancedRitualArt()) gamePlayController.activateSelectedCard();
            return;
        }
        int[] out;
        if (getRitualMonsterOfHand(gamePlayController.getCurrentPlayer()) == null) {
            GamePlayController.getInstance().getDuelMenu().printResponse(YOU_DONT_HAVE_RITUAL_MONSTER);
            return;
        }
        GamePlayController.getInstance().getDuelMenu().printString("you can ritual summon " + getRitualMonsterOfHand(gamePlayController.getCurrentPlayer()).getCardName() + "with level :" + getRitualMonsterOfHand(gamePlayController.getCurrentPlayer()).getLevel());
        showWholeMonsters(getWholeMonsters(gamePlayController.getCurrentPlayer()));
        while (true) {
            GamePlayController.getInstance().getDuelMenu().printResponse(ENTER_SOME_NUMBERS);
            String string = GamePlayController.getInstance().getDuelMenu().getStringAndAsk("enter some numbers:");
            if (string.equals("cancel")) {
                GamePlayController.getInstance().getDuelMenu().printResponse(CANCELED);
                return;
            }
            if (!string.matches("(\\d+\\s)+\\d+"))
                GamePlayController.getInstance().getDuelMenu().printResponse(SHOULD_RIVAL_SUMMON_RIGHT_NOW);
            else {
                String[] nums = string.trim().split(" ");
                out = new int[nums.length];
                for (int i = 0; i < nums.length; i++) {
                    out[i] = Integer.parseInt(nums[i]);
                }
                if (checkSummon(out, gamePlayController.getCurrentPlayer(), getRitualMonsterOfHand(gamePlayController.getCurrentPlayer()).getLevel())) {
                    for (int j =0 ; j<out.length;j++) {
                        gamePlayController.getCurrentPlayer().getGraveyardZone().addCardToGraveyardZone(getWholeMonsters(gamePlayController.getCurrentPlayer()).get(out[j]));
                        gamePlayController.getCurrentPlayer().getDeckZone().getZoneCards().remove(getWholeMonsters(gamePlayController.getCurrentPlayer()).get(out[j]));
                    }
                    GamePlayController.getInstance().getDuelMenu().printResponse(ENTER_POS);
                    String ans = GamePlayController.getInstance().getDuelMenu().chooseQuestion("choose position : (ATK/DEF)","ATK","DEF");
                    if (ans.equals("ATK"))
                        getRitualMonsterOfHand(gamePlayController.getCurrentPlayer()).setMode(MonsterCard.Mode.ATTACK);
                    else
                        getRitualMonsterOfHand(gamePlayController.getCurrentPlayer()).setMode(MonsterCard.Mode.DEFENSE);
                    gamePlayController.getCurrentPlayer().getMonsterCardZone().summonOrSetMonster(getRitualMonsterOfHand(gamePlayController.getCurrentPlayer()), gamePlayController.getCurrentPlayer());
                    getRitualMonsterOfHand(gamePlayController.getCurrentPlayer()).setHidden(false);
                    gamePlayController.getCurrentPlayer().getMonsterCardZone().summonOrSetMonster(getRitualMonsterOfHand(gamePlayController.getCurrentPlayer()), gamePlayController.getCurrentPlayer());
                    GamePlayController.getInstance().getDuelMenu().printResponse(EFFECT_DONE_SUCCESSFULLY);
                    gamePlayController.getCurrentPlayer().getMagicCardZone().moveCardToGraveyardWithoutAddress(advancedRitualArt, gamePlayController.getCurrentPlayer());
                    break;
                } else {
                    GamePlayController.getInstance().getDuelMenu().printResponse(LEVELS_DONT_MATCH);
                }

            }
        }

    }
}




