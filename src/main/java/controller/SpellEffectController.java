package controller;

import controller.responses.DeckMenuResponses;
import controller.responses.DuelMenuResponses;
import model.Card;
import model.MagicCard;
import model.MonsterCard;
import model.Player;
import view.DuelMenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import static controller.responses.DuelMenuResponses.*;

public class SpellEffectController {
    private Card cardEffectedBySpell;

    //TODO: put a while in view ....


    DuelMenu duelMenu = DuelMenu.getInstance();
    GamePlayController gamePlayController = GamePlayController.getInstance();
    EffectController effectController = gamePlayController.getEffectController();
    //TODO : FIRST 4 EFFECTS SHOULD HE CHECKED DUHHHHHHH

    public void yami(Boolean x) {
        gamePlayController.getSelectedCard().setHidden(false);
        duelMenu.printResponse(SPELL_ACTIVATED);
        int amount = 200;
        if (!x)
            amount = -amount;
        effectController.addATK(MonsterCard.MonsterType.FIEND, true, amount);
        effectController.addATK(MonsterCard.MonsterType.SPELL_CASTER, true, amount);
        effectController.addATK(MonsterCard.MonsterType.FAIRY, true, -amount);
        effectController.addDEF(MonsterCard.MonsterType.FIEND, true, amount);
        effectController.addDEF(MonsterCard.MonsterType.SPELL_CASTER, true, amount);
        effectController.addDEF(MonsterCard.MonsterType.FAIRY, true, -amount);
    }

    public void spellAbsorption() {
        for (Map.Entry<Player, Card> entry : gamePlayController.getActivatedCards().entrySet()) {
            if (entry.getValue().getCardName().equals("Spell Absorption")) ;
            entry.getKey().increaseLifePoint(500);
            duelMenu.printResponse(EFFECT_DONE_SUCCESSFULLY);
        }
    }

    public void yamiForNewAddedCard(MonsterCard monsterCard) {
        if (monsterCard.getMonsterType() == MonsterCard.MonsterType.FIEND ||
                monsterCard.getMonsterType() == MonsterCard.MonsterType.SPELL_CASTER) {
            monsterCard.setAttackPoint(monsterCard.getAttackPoint() + 200);
            monsterCard.setDefensePoint(monsterCard.getDefensePoint() + 200);
        } else if (monsterCard.getMonsterType() == MonsterCard.MonsterType.FAIRY) {
            monsterCard.setAttackPoint(monsterCard.getAttackPoint() - 200);
            monsterCard.setDefensePoint(monsterCard.getDefensePoint() - 200);
        }
    }


    public void forest(Boolean x) {
        gamePlayController.getSelectedCard().setHidden(false);
        duelMenu.printResponse(SPELL_ACTIVATED);
        int amount = 200;
        if (!x)
            amount = -amount;
        effectController.addATK(MonsterCard.MonsterType.BEAST, true, amount);
        effectController.addDEF(MonsterCard.MonsterType.BEAST, true, amount);
        effectController.addATK(MonsterCard.MonsterType.INSECT, true, amount);
        effectController.addDEF(MonsterCard.MonsterType.INSECT, true, amount);
        effectController.addATK(MonsterCard.MonsterType.BEAST_WARRIOR, true, amount);
        effectController.addDEF(MonsterCard.MonsterType.BEAST_WARRIOR, true, amount);
    }

    public void forestForNewAddedCard(MonsterCard monsterCard) {
        if (monsterCard.getMonsterType() == MonsterCard.MonsterType.BEAST ||
                monsterCard.getMonsterType() == MonsterCard.MonsterType.INSECT ||
                monsterCard.getMonsterType() == MonsterCard.MonsterType.BEAST_WARRIOR) {
            monsterCard.setGameATK(monsterCard.getGameATK() + 200);
            monsterCard.setGameDEF(monsterCard.getGameDEF() + 200);
        }
    }

    public void closedForest(Boolean x) {
        gamePlayController.getSelectedCard().setHidden(false);
        duelMenu.printResponse(SPELL_ACTIVATED);
        int i = effectController.numberOfDeadMonsters();
        if (!x)
            i = -i;
        effectController.addATK(MonsterCard.MonsterType.BEAST, false, i * 100);
    }


    public void closedForestForNewAddedCard(MonsterCard monsterCard) {
        int i = effectController.numberOfDeadMonsters();
        if (monsterCard.getMonsterType() == MonsterCard.MonsterType.BEAST) {
            monsterCard.setGameATK(monsterCard.getGameATK() + i * 100);
        }
    }

    public void closedForestForNewCardAddedToGraveyard(MonsterCard monsterCard) {
        effectController.addATK(MonsterCard.MonsterType.BEAST, false, 100);
    }


    public void umiiruka(Boolean x) {
        gamePlayController.getSelectedCard().setHidden(false);
        duelMenu.printResponse(SPELL_ACTIVATED);
        if (!x) {
            effectController.addATK(MonsterCard.MonsterType.AQUA, true, -500);
            effectController.addATK(MonsterCard.MonsterType.AQUA, true, 400);
        }
        effectController.addATK(MonsterCard.MonsterType.AQUA, true, 500);
        effectController.addATK(MonsterCard.MonsterType.AQUA, true, -400);
    }

    public void umiirukaForNewAddedCard(MonsterCard monsterCard) {
        if (monsterCard.getMonsterType() == MonsterCard.MonsterType.AQUA) {
            monsterCard.setGameATK(monsterCard.getGameATK() + 500);
            monsterCard.setGameDEF(monsterCard.getGameDEF() + -400);
        }
    }

    // TODO check for invalid cell number just like magnum shield
    // TODO check magnum shield to be sure


    public boolean SwordOfDarkDestruction() {
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

    public void doSwordOfDarkDestruction() {
        Map<Integer, MonsterCard> map = gamePlayController.getCurrentPlayer().getMonsterCardZone().getZoneCards();
        gamePlayController.getSelectedCard().setHidden(false);
        duelMenu.printResponse(SPELL_ACTIVATED);
        duelMenu.printResponse(ENTER_ONE_NUMBER);
        while (true) {
            int num = duelMenu.scannerNum();
            if ((map.get(num) != null && map.get(num).getMonsterType() != MonsterCard.MonsterType.FIEND) &&
                    (map.get(num) != null && map.get(num).getMonsterType() != MonsterCard.MonsterType.FAIRY)) {
                map.get(num).setGameATK(map.get(num).getGameATK() + 400);
                map.get(num).setGameDEF(map.get(num).getGameDEF() - 200);
                duelMenu.printResponse(CARD_EQUIPPED);
                return;
            } else {
                duelMenu.printResponse(INVALID_CELL_NUMBER);
                duelMenu.printResponse(ENTER_ONE_NUMBER);
            }

        }
    }


    //TODO : handle death !
    public Boolean BlackPendant() {
        if (gamePlayController.getCurrentPlayer().getMonsterCardZone().getNumberOfCard() == 0)
            return false;
        else return true;
    }


    public void doBlackPendant() {
        gamePlayController.getSelectedCard().setHidden(false);
        duelMenu.printResponse(SPELL_ACTIVATED);
        duelMenu.printResponse(ENTER_ONE_NUMBER);
        Map<Integer, MonsterCard> map = gamePlayController.getCurrentPlayer().getMonsterCardZone().getZoneCards();
        while (true) {
            int num = duelMenu.scannerNum();
            if (map.get(num) != null && !map.get(num).getHidden()) {
                map.get(num).setGameATK(map.get(num).getGameATK() + 500);
                effectController.getEquippedCardsBySpells().put((MagicCard) gamePlayController.getSelectedCard(), map.get(num));
                duelMenu.printResponse(CARD_EQUIPPED);
                return;
            } else {
                duelMenu.printResponse(INVALID_CELL_NUMBER);
                duelMenu.printResponse(ENTER_ONE_NUMBER);
            }
        }
    }

    public Boolean UnitedWeStand() {
        if (effectController.getNumberOfFaceUpMonstersOfCurrentPlayer() == 0)
            return false;
        else return true;
    }


    public void doUnitedWeStand() {
        gamePlayController.getSelectedCard().setHidden(false);
        duelMenu.printResponse(SPELL_ACTIVATED);
        duelMenu.printResponse(ENTER_ONE_NUMBER);
        Map<Integer, MonsterCard> map = gamePlayController.getCurrentPlayer().getMonsterCardZone().getZoneCards();
        int amount = effectController.getNumberOfFaceUpMonstersOfCurrentPlayer();
        while (true) {
            int num = duelMenu.scannerNum();
            if (map.get(num) != null) {
                map.get(num).setGameATK(map.get(num).getGameATK() + amount * 800);
                map.get(num).setGameDEF(map.get(num).getGameDEF() + amount * 800);
                effectController.getEquippedCardsBySpells().put((MagicCard) gamePlayController.getSelectedCard(), map.get(num));
                duelMenu.printResponse(CARD_EQUIPPED);
                return;
            } else {
                duelMenu.printResponse(INVALID_CELL_NUMBER);
                duelMenu.printResponse(ENTER_ONE_NUMBER);
            }
        }

    }


    public Boolean MagnumShield() {
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

    public void doMagnumShield() {
        Map<Integer, MonsterCard> map = gamePlayController.getCurrentPlayer().getMonsterCardZone().getZoneCards();
        gamePlayController.getSelectedCard().setHidden(false);
        duelMenu.printResponse(SPELL_ACTIVATED);
        duelMenu.printResponse(ENTER_ONE_NUMBER);
        while (true) {
            int num = duelMenu.scannerNum();
            if (map.get(num) != null && map.get(num).getMonsterType() == MonsterCard.MonsterType.WARRIOR && !map.get(num).getHidden()) {
                if (map.get(num).getMode() == MonsterCard.Mode.ATTACK) {
                    map.get(num).setGameATK(map.get(num).getGameATK() + map.get(num).getDefensePoint());
                    effectController.getEquippedCardsBySpells().put((MagicCard) gamePlayController.getSelectedCard(), map.get(num));
                    duelMenu.printResponse(CARD_EQUIPPED);
                    return;
                } else if (map.get(num).getMode() == MonsterCard.Mode.DEFENSE) {
                    map.get(num).setGameDEF(map.get(num).getGameDEF() + map.get(num).getGameATK());
                    effectController.getEquippedCardsBySpells().put((MagicCard) gamePlayController.getSelectedCard(), map.get(num));
                    duelMenu.printResponse(CARD_EQUIPPED);
                    return;
                }
            } else {
                duelMenu.printResponse(INVALID_CELL_NUMBER);
                duelMenu.printResponse(ENTER_ONE_NUMBER);
            }
        }

    }

    public Boolean Terraforming() {
        ArrayList<Card> deck = gamePlayController.getCurrentPlayer().getDeckZone().getZoneCards();
        for (Card card : deck) {
            if (card instanceof MagicCard && ((MagicCard) card).getMagicType() == MagicCard.MagicType.SPELL
                    && ((MagicCard) card).getCardIcon() == MagicCard.CardIcon.FIELD)
                return true;
        }
        return false;
    }


    public void doTerraforming() {  //TODO WTF  WHY DON'T WE CHOOSE ANY CARD?
        for (Card card : gamePlayController.getCurrentPlayer().getDeckZone().getZoneCards()) {
            if (card instanceof MagicCard && ((MagicCard) card).getMagicType() == MagicCard.MagicType.SPELL
                    && ((MagicCard) card).getCardIcon() == MagicCard.CardIcon.FIELD) {
                gamePlayController.getSelectedCard().setHidden(false);
                duelMenu.printResponse(SPELL_ACTIVATED);
                gamePlayController.getCurrentPlayer().getHand().addCardToHand(card);
                gamePlayController.getCurrentPlayer().getDeckZone().removeCardFromDeckZone(card);
                gamePlayController.getCurrentPlayer().getMagicCardZone().moveCardToGraveyardWithoutAddress(gamePlayController.getSelectedCard(), gamePlayController.getCurrentPlayer());
                duelMenu.printResponse(EFFECT_DONE_SUCCESSFULLY);
                return;
            }
        }
        duelMenu.printResponse(CANT_ACTIVATE_SPELL);
    }

    public Boolean PotOfGReed() {
        if (gamePlayController.getCurrentPlayer().getDeckZone().getZoneCards().size() < 2)
            return false;
        else return true;
    }

    public void doPotOfGReed() {
        gamePlayController.getSelectedCard().setHidden(false);
        duelMenu.printResponse(SPELL_ACTIVATED);
        for (int i = 0; i < 2; i++) {
            gamePlayController.getCurrentPlayer().getHand().addCardToHand(gamePlayController.getCurrentPlayer().getDeckZone().getZoneCards().get(0));
            gamePlayController.getCurrentPlayer().getDeckZone().removeCardFromDeckZone(gamePlayController.getCurrentPlayer().getDeckZone().getZoneCards().get(0));
        }
        gamePlayController.getCurrentPlayer().getMagicCardZone().moveCardToGraveyardWithoutAddress(gamePlayController.getSelectedCard(), gamePlayController.getCurrentPlayer());

    }

    public Boolean Raigeki() {
        if (gamePlayController.getOpponentPlayer().getMonsterCardZone().getNumberOfCard() == 0)
            return true;
        else return false;
    }

    public void doRaigeki() {
        gamePlayController.getSelectedCard().setHidden(false);
        duelMenu.printResponse(SPELL_ACTIVATED);
        effectController.destroyCards(Card.CardType.MONSTER, false);
        gamePlayController.getCurrentPlayer().getMagicCardZone().moveCardToGraveyardWithoutAddress(gamePlayController.getSelectedCard(), gamePlayController.getCurrentPlayer());
    }

    public Boolean HarpiesFeatherDuster() {
        if (gamePlayController.getOpponentPlayer().getMagicCardZone().getNumberOfCard() == 0)
            return true;
        else return false;
    }

    public void doHarpiesFeatherDuster() {
        gamePlayController.getSelectedCard().setHidden(false);
        duelMenu.printResponse(SPELL_ACTIVATED);
        effectController.destroyCards(Card.CardType.MAGIC, false);
        duelMenu.printResponse(EFFECT_DONE_SUCCESSFULLY);
        gamePlayController.getCurrentPlayer().getMagicCardZone().moveCardToGraveyardWithoutAddress(gamePlayController.getSelectedCard(), gamePlayController.getCurrentPlayer());
    }

    public Boolean DarkHole() {
        if (gamePlayController.getOpponentPlayer().getMonsterCardZone().getNumberOfCard() == 0 && gamePlayController.getCurrentPlayer().getMonsterCardZone().getNumberOfCard() == 0)
            return true;
        else return false;
    }


    public void doDarkHole() {
        gamePlayController.getSelectedCard().setHidden(false);
        duelMenu.printResponse(SPELL_ACTIVATED);
        effectController.destroyCards(Card.CardType.MONSTER, true);
        duelMenu.printResponse(EFFECT_DONE_SUCCESSFULLY);
        gamePlayController.getCurrentPlayer().getMagicCardZone().moveCardToGraveyardWithoutAddress(gamePlayController.getSelectedCard(), gamePlayController.getCurrentPlayer());
    }


    public Boolean MysticalSpaceTyphoon() {
        int num = gamePlayController.getOpponentPlayer().getMagicCardZone().getNumberOfCard();
        num += gamePlayController.getCurrentPlayer().getMagicCardZone().getNumberOfCard();
        if (num == 1)
            return false;
        else return true;
    }

    public void doMysticalSpaceTyphoon(MagicCard card) {
        Map<Integer, MagicCard> map;
        Player player;
        duelMenu.printResponse(ENTER_PLAYER);
        String rivalOrNot = duelMenu.scannerLine();
        if (rivalOrNot.equals("rival")) {
            map = gamePlayController.getOpponentPlayer().getMagicCardZone().getZoneCards();
            player = gamePlayController.getOpponentPlayer();
        } else {
            map = gamePlayController.getCurrentPlayer().getMagicCardZone().getZoneCards();
            player = gamePlayController.getCurrentPlayer();
        }
        duelMenu.printResponse(ENTER_ONE_NUMBER);
        while (true) {
            int num = duelMenu.scannerNum();
            if (num <= 5 && map.get(num) != null) {
                player.getMagicCardZone().moveCardToGraveyard(num, player);
                duelMenu.printResponse(EFFECT_DONE_SUCCESSFULLY);
                gamePlayController.getCurrentPlayer().getMagicCardZone().moveCardToGraveyardWithoutAddress(card, gamePlayController.getCurrentPlayer());
                return;
            } else if (num == 6 && player.getFieldZone().getZoneCards().get(0) != null) {
                player.getGraveyardZone().addCardToGraveyardZone(player.getFieldZone().getZoneCards().get(0));
                player.getFieldZone().getZoneCards().remove(0);
            } else {
                duelMenu.printResponse(INVALID_CELL_NUMBER);
                duelMenu.printResponse(ENTER_ONE_NUMBER);
            }
        }
    }

    public Boolean TwinTwisters() {
        if (gamePlayController.getCurrentPlayer().getMagicCardZone().getNumberOfCard()
                + gamePlayController.getOpponentPlayer().getMagicCardZone().getNumberOfCard() <= 1 &&
                gamePlayController.getCurrentPlayer().getFieldZone().getZoneCards().get(0) != null &&
                gamePlayController.getOpponentPlayer().getFieldZone().getZoneCards().get(0) != null)
            return false;
        else return true;

    }

    public void doTwinTwisters(Card card) {
        gamePlayController.getSelectedCard().setHidden(false);
        int i = 0;
        duelMenu.printResponse(ENTER_ONE_NUMBER);  // of your hand !
        while (true) {
            int num = duelMenu.scannerNum();
            if (gamePlayController.getCurrentPlayer().getHand().getNumberOfCardsInHand() >= num) {
                gamePlayController.getCurrentPlayer().getMagicCardZone().moveCardToGraveyardWithoutAddress(gamePlayController.getSelectedCard(), gamePlayController.getCurrentPlayer());
                break;
            } else {
                duelMenu.printResponse(INVALID_CELL_NUMBER);
                duelMenu.printResponse(ENTER_ONE_NUMBER);
            }
        }
        duelMenu.printResponse(ENTER_ONE_NUMBER);
        while (true) {
            int num = duelMenu.scannerNum();
            if (num <= 5 && gamePlayController.getOpponentPlayer().getMagicCardZone().getZoneCards().get(num) != null) {
                gamePlayController.getOpponentPlayer().getMagicCardZone().moveCardToGraveyard(num, gamePlayController.getCurrentPlayer());
                i++;
            } else if (num == 6 && gamePlayController.getOpponentPlayer().getFieldZone().getZoneCards().get(0) != null) {
                gamePlayController.getOpponentPlayer().getGraveyardZone().addCardToGraveyardZone(gamePlayController.getOpponentPlayer().getFieldZone().getZoneCards().get(0));
                gamePlayController.getOpponentPlayer().getFieldZone().getZoneCards().remove(0);
                i++;
                if (i == 1) {
                    duelMenu.printResponse(WANNA_ADD_ANOTHER_CARD);
                    String ans = duelMenu.scannerLine();
                    if (ans.equals("yes")) {
                        duelMenu.printResponse(ENTER_ONE_NUMBER);
                        continue;
                    } else {
                        duelMenu.printResponse(EFFECT_DONE_SUCCESSFULLY);
                        gamePlayController.getCurrentPlayer().getMagicCardZone().moveCardToGraveyardWithoutAddress(card, gamePlayController.getCurrentPlayer());

                        break;
                    }
                } else if (i == 2) {
                    duelMenu.printResponse(EFFECT_DONE_SUCCESSFULLY);
                    gamePlayController.getCurrentPlayer().getMagicCardZone().moveCardToGraveyardWithoutAddress(card, gamePlayController.getCurrentPlayer());
                    break;
                }
            } else {
                duelMenu.printResponse(INVALID_CELL_NUMBER);
                duelMenu.printResponse(ENTER_ONE_NUMBER);
            }
        }
    }

    public Boolean ChangeOfHeart() {
        if (gamePlayController.getOpponentPlayer().getMonsterCardZone().getNumberOfCard() == 0)
            return false;
        else return true;
    }


    public void doChangeOfHeart() {
        Map<Integer, MonsterCard> map = gamePlayController.getOpponentPlayer().getMonsterCardZone().getZoneCards();
        duelMenu.printResponse(ENTER_ONE_NUMBER);
        while (true) {
            int num = duelMenu.scannerNum();
            if (map.get(num) != null) {
                effectController.getControl(map.get(num));
                duelMenu.printResponse(EFFECT_DONE_SUCCESSFULLY);
                gamePlayController.getCurrentPlayer().getMagicCardZone().moveCardToGraveyardWithoutAddress(gamePlayController.getSelectedCard(), gamePlayController.getCurrentPlayer());
                return;
            } else {
                duelMenu.printResponse(INVALID_CELL_NUMBER);
                duelMenu.printResponse(ENTER_ONE_NUMBER);
            }
        }
    }
    //TODO GET ACTIVATED CARDS


    //TODO DESTROY CHECKER

    public void swordOfRevealingLight() {
        Map<Integer, MonsterCard> map = gamePlayController.getOpponentPlayer().getMonsterCardZone().getZoneCards();
        for (Map.Entry<Integer, MonsterCard> entry : map.entrySet()) {
            if (entry.getValue() != null && entry.getValue().getHidden())
                entry.getValue().setHidden(false);
        }
        duelMenu.printResponse(EFFECT_DONE_SUCCESSFULLY);
    }

    public boolean MonsterReborn() {
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

    public void doMonsterReborn() {
        gamePlayController.getSelectedCard().setHidden(false);
        Player player;
        duelMenu.printResponse(ENTER_PLAYER);
        String rivalOrNot = duelMenu.scannerLine();
        if (rivalOrNot.equals("rival"))
            player = gamePlayController.getOpponentPlayer();
        else player = gamePlayController.getCurrentPlayer();
        duelMenu.printResponse(ENTER_ONE_NUMBER);
        while (true) {
            int num = duelMenu.scannerNum();
            if (num <= player.getGraveyardZone().getZoneCards().size() && (player.getGraveyardZone().getZoneCards().get(num) instanceof MonsterCard)) {

                player.getMonsterCardZone().summonOrSetMonster((MonsterCard) player.getGraveyardZone().getZoneCards().get(num - 1), player);
                (player.getGraveyardZone().getZoneCards().get(num - 1)).setHidden(true); //?
                duelMenu.printResponse(EFFECT_DONE_SUCCESSFULLY);
                gamePlayController.getCurrentPlayer().getMagicCardZone().moveCardToGraveyardWithoutAddress(gamePlayController.getSelectedCard(), gamePlayController.getCurrentPlayer());
            } else {
                duelMenu.printResponse(INVALID_CELL_NUMBER);
                duelMenu.printResponse(ENTER_ONE_NUMBER);
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
        if (getRitualMonsterOfHand(player) != null &&
                doesSumExist(getRitualMonsterOfHand(player).getLevel(), getWholeMonsters(player).size(), player))
            return true;
        else return false;

    }

    public ArrayList<MonsterCard> getWholeMonsters(Player player) {
        ArrayList<MonsterCard> monsters = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            if (player.getMonsterCardZone().getZoneCards().get(i) != null && player.getMonsterCardZone().getZoneCards().get(i) instanceof MonsterCard)
                monsters.add((MonsterCard) player.getMonsterCardZone().getZoneCards().get(i));
        }
        for (Card card : player.getDeckZone().getZoneCards()) {
            if (card instanceof MonsterCard)
                monsters.add((MonsterCard) card);
        }
        return monsters;
    }

    public boolean doesSumExist(int sum, int n, Player player) {
        if (sum > 0 && n == 0)
            return false;
        if (sum == 0)
            return true;
        return doesSumExist(sum, n - 1, player) ||
                doesSumExist(sum - getWholeMonsters(player).get(n - 1).getLevel(), n - 1, player);
    }

    public boolean AdvancedRitualArt() {
        if (canWeRitualSummon(gamePlayController.getCurrentPlayer()))
            return true;
        else {
            duelMenu.printResponse(NO_WAY_TO_RITUAL_SUMMON);
            return false;
        }
    }

    public boolean checkSummon(int[] numbers, Player player, int level) {
        int out[] = numbers;
        int sum = 0;
        for (int i = 0; i < out.length; i++) {
            sum = sum + ((MonsterCard) player.getDeckZone().getZoneCards().get(out[i])).getLevel();
        }
        return sum == level;
    }


    public void doAdvancedRitualArt() {
        gamePlayController.getSelectedCard().setHidden(false);
        int[] out;
        duelMenu.printResponse(ENTER_SOME_NUMBERS);
        while (true) {
            String string = duelMenu.scannerLine().trim();
            if (!string.matches("(\\d+\\s)+\\d+"))
                duelMenu.printResponse(SHOULD_RIVAL_SUMMON_RIGHT_NOW);
            else {
                String nums[] = string.split(" ");
                out = new int[nums.length];
                if (checkSummon(out, gamePlayController.getCurrentPlayer(), getRitualMonsterOfHand(gamePlayController.getCurrentPlayer()).getLevel())) {
                    for (int i = 0; i < out.length; i++) {
                        gamePlayController.getCurrentPlayer().getGraveyardZone().addCardToGraveyardZone(gamePlayController.getCurrentPlayer().getDeckZone().getZoneCards().get(i));
                        gamePlayController.getCurrentPlayer().getDeckZone().getZoneCards().remove(out[i]);
                    }
                    duelMenu.printResponse(ENTER_POS);
                    String ans = duelMenu.scannerLine();
                    if (ans.equals("ATK"))
                        getRitualMonsterOfHand(gamePlayController.getCurrentPlayer()).setMode(MonsterCard.Mode.ATTACK);
                    else
                        getRitualMonsterOfHand(gamePlayController.getCurrentPlayer()).setMode(MonsterCard.Mode.DEFENSE);
                    gamePlayController.getCurrentPlayer().getMonsterCardZone().summonOrSetMonster(getRitualMonsterOfHand(gamePlayController.getCurrentPlayer()), gamePlayController.getCurrentPlayer());
                    getRitualMonsterOfHand(gamePlayController.getCurrentPlayer()).setHidden(false);
                    duelMenu.printResponse(EFFECT_DONE_SUCCESSFULLY);
                    gamePlayController.getCurrentPlayer().getMagicCardZone().moveCardToGraveyardWithoutAddress(gamePlayController.getSelectedCard(), gamePlayController.getCurrentPlayer());
                } else {
                    duelMenu.printResponse(LEVELS_DONT_MATCH);
                    duelMenu.printResponse(ENTER_SOME_NUMBERS);
                }

            }
        }

    }
}




