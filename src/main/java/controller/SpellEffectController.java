package controller;

import controller.responses.DeckMenuResponses;
import controller.responses.DuelMenuResponses;
import model.Card;
import model.MagicCard;
import model.MonsterCard;
import model.Player;
import view.DuelMenu;

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

    public void yami(Boolean x) {
        //check the full of fieldZone
        gamePlayController.getSelectedCard().setHidden(false);
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
    public void canActivateSwordOfDestruction() {
        int i = 0;
        Map<Integer, MonsterCard> map = gamePlayController.getCurrentPlayer().getMonsterCardZone().getZoneCards();
        for (Map.Entry<Integer, MonsterCard> entry : map.entrySet()) {
            if ((entry.getValue() != null && entry.getValue().getMonsterType() == MonsterCard.MonsterType.FIEND && !entry.getValue().getHidden()) ||
                    (entry.getValue() != null && entry.getValue().getMonsterType() == MonsterCard.MonsterType.SPELL_CASTER && !entry.getValue().getHidden()))
                i++;
        }
        if (i == 0)
            duelMenu.printResponse(PREPARATIONS_OF_THIS_SPELL_ARE_NOT_DONE_YET);
        else {
            swordOfDarkDestruction(map);
        }
    }

    public void swordOfDarkDestruction(Map<Integer, MonsterCard> map) {
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

    public void canActivateBlackPendant() {
        if (gamePlayController.getCurrentPlayer().getMonsterCardZone().getNumberOfCard() == 0)
            duelMenu.printResponse(PREPARATIONS_OF_THIS_SPELL_ARE_NOT_DONE_YET);
        else {
            blackPendant();
        }
    }

    //TODO : handle death !
    public void blackPendant() {
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

    public void canActivateUnitedWeStand() {
        if (effectController.getNumberOfFaceUpMonstersOfCurrentPlayer() == 0)
            duelMenu.printResponse(PREPARATIONS_OF_THIS_SPELL_ARE_NOT_DONE_YET);
        else {
            unitedWeStand();
        }
    }


    public void unitedWeStand() {

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

    public void canActivateMagnumShield() {
        int i = 0;
        Map<Integer, MonsterCard> map = gamePlayController.getCurrentPlayer().getMonsterCardZone().getZoneCards();
        for (Map.Entry<Integer, MonsterCard> entry : map.entrySet()) {
            if ((entry.getValue() != null && entry.getValue().getMonsterType() == MonsterCard.MonsterType.WARRIOR && !entry.getValue().getHidden()))
                i++;
        }
        if (i == 0)
            duelMenu.printResponse(PREPARATIONS_OF_THIS_SPELL_ARE_NOT_DONE_YET);
        else {
            magnumShield(map);
        }
    }


    public void magnumShield(Map<Integer, MonsterCard> map) {

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


    public void terraforming() {
        gamePlayController.getSelectedCard().setHidden(false);
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
        duelMenu.printResponse(PREPARATIONS_OF_THIS_SPELL_ARE_NOT_DONE_YET);
    }

    public void canActivatePotOfGreed() {
        if (gamePlayController.getCurrentPlayer().getDeckZone().getZoneCards().size() < 2) {
            duelMenu.printResponse(PREPARATIONS_OF_THIS_SPELL_ARE_NOT_DONE_YET);
        } else {
            potOfGReed();
        }
    }

    public void potOfGReed() {

        gamePlayController.getSelectedCard().setHidden(false);
        duelMenu.printResponse(SPELL_ACTIVATED);
        for (int i = 0; i < 2; i++) {
            gamePlayController.getCurrentPlayer().getHand().addCardToHand(gamePlayController.getCurrentPlayer().getDeckZone().getZoneCards().get(0));
            gamePlayController.getCurrentPlayer().getDeckZone().removeCardFromDeckZone(gamePlayController.getCurrentPlayer().getDeckZone().getZoneCards().get(0));
        }
        gamePlayController.getCurrentPlayer().getMagicCardZone().moveCardToGraveyardWithoutAddress(gamePlayController.getSelectedCard(), gamePlayController.getCurrentPlayer());

    }

    public void canActivateRaigeki() {
        if (gamePlayController.getOpponentPlayer().getMonsterCardZone().getNumberOfCard() == 0)
            duelMenu.printResponse(PREPARATIONS_OF_THIS_SPELL_ARE_NOT_DONE_YET);
        else {
            raigeki();
        }
    }

    public void raigeki() {

        gamePlayController.getSelectedCard().setHidden(false);
        duelMenu.printResponse(SPELL_ACTIVATED);
        effectController.destroyCards(Card.CardType.MONSTER, false);
        gamePlayController.getCurrentPlayer().getMagicCardZone().moveCardToGraveyardWithoutAddress(gamePlayController.getSelectedCard(), gamePlayController.getCurrentPlayer());
    }

    public void canActivateHarpiesFeatherDuster() {
        if (gamePlayController.getOpponentPlayer().getMagicCardZone().getNumberOfCard() == 0 && gamePlayController.getOpponentPlayer().getFieldZone().getZoneCards().get(0) == null)
            duelMenu.printResponse(PREPARATIONS_OF_THIS_SPELL_ARE_NOT_DONE_YET);
        else {
            harpiesFeatherDuster();
        }
    }

    public void harpiesFeatherDuster() {

        gamePlayController.getSelectedCard().setHidden(false);
        duelMenu.printResponse(SPELL_ACTIVATED);
        effectController.destroyCards(Card.CardType.MAGIC, false);
        duelMenu.printResponse(EFFECT_DONE_SUCCESSFULLY);
        gamePlayController.getCurrentPlayer().getMagicCardZone().moveCardToGraveyardWithoutAddress(gamePlayController.getSelectedCard(), gamePlayController.getCurrentPlayer());
    }

    public void canActivateDarkHole() {
        if (gamePlayController.getOpponentPlayer().getMonsterCardZone().getNumberOfCard() == 0 && gamePlayController.getCurrentPlayer().getMonsterCardZone().getNumberOfCard() == 0)
            duelMenu.printResponse(PREPARATIONS_OF_THIS_SPELL_ARE_NOT_DONE_YET);
        else {
            darkHole();
        }
    }


    public void darkHole() {

        gamePlayController.getSelectedCard().setHidden(false);
        duelMenu.printResponse(SPELL_ACTIVATED);
        effectController.destroyCards(Card.CardType.MONSTER, true);
        duelMenu.printResponse(EFFECT_DONE_SUCCESSFULLY);
        gamePlayController.getCurrentPlayer().getMagicCardZone().moveCardToGraveyardWithoutAddress(gamePlayController.getSelectedCard(), gamePlayController.getCurrentPlayer());
    }

    public void canActivateMysticalSpaceTyphoon() {
        if (gamePlayController.getCurrentPlayer().getMagicCardZone().getNumberOfCard()
                + gamePlayController.getOpponentPlayer().getMagicCardZone().getNumberOfCard() <= 1 &&
                gamePlayController.getCurrentPlayer().getFieldZone().getZoneCards().get(0) != null &&
                gamePlayController.getOpponentPlayer().getFieldZone().getZoneCards().get(0) != null) {
            duelMenu.printResponse(PREPARATIONS_OF_THIS_SPELL_ARE_NOT_DONE_YET);
        } else {
            mysticalSpaceTyphoon();
        }
    }

    public void mysticalSpaceTyphoon() {
        gamePlayController.getSelectedCard().setHidden(false);
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
            if (map.get(num) != null) {
                player.getMagicCardZone().moveCardToGraveyard(num, player);
                duelMenu.printResponse(EFFECT_DONE_SUCCESSFULLY);
                gamePlayController.getCurrentPlayer().getMagicCardZone().moveCardToGraveyardWithoutAddress(gamePlayController.getSelectedCard(), gamePlayController.getCurrentPlayer());
                return;
            } else if (num == 6 && player.getFieldZone().getZoneCards().get(0) != null) {
                player.getGraveyardZone().getZoneCards().add(player.getFieldZone().getZoneCards().get(0));
                player.getFieldZone().getZoneCards().remove(0);
            } else {
                duelMenu.printResponse(INVALID_CELL_NUMBER);
                duelMenu.printResponse(ENTER_ONE_NUMBER);
            }
        }
    }

    public void canActivateTwinTwisters() {
        if (gamePlayController.getCurrentPlayer().getMagicCardZone().getNumberOfCard()
                + gamePlayController.getOpponentPlayer().getMagicCardZone().getNumberOfCard() <= 1 &&
                gamePlayController.getCurrentPlayer().getFieldZone().getZoneCards().get(0) != null &&
                gamePlayController.getOpponentPlayer().getFieldZone().getZoneCards().get(0) != null) {
            duelMenu.printResponse(PREPARATIONS_OF_THIS_SPELL_ARE_NOT_DONE_YET);
        } else {
            twinTwisters();
        }
    }

    public void twinTwisters() {
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
            if (gamePlayController.getOpponentPlayer().getMagicCardZone().getZoneCards().get(num) != null) {
                gamePlayController.getOpponentPlayer().getMagicCardZone().moveCardToGraveyard(num, gamePlayController.getCurrentPlayer());
                i++;
            } else if (num == 6 && gamePlayController.getOpponentPlayer().getFieldZone().getZoneCards().get(0) != null) {
                gamePlayController.getOpponentPlayer().getGraveyardZone().addCardToGraveyardZone(gamePlayController.getOpponentPlayer().getFieldZone().getZoneCards().get(0));
                gamePlayController.getOpponentPlayer().getFieldZone().getZoneCards().remove(0);
                i++;
            } else {
                duelMenu.printResponse(INVALID_CELL_NUMBER);
                duelMenu.printResponse(ENTER_ONE_NUMBER);
                continue;
            }
            if (i == 1) {
                duelMenu.printResponse(WANNA_ADD_ANOTHER_CARD);
                String ans = duelMenu.scannerLine();
                if (ans.equals("yes")) {
                    duelMenu.printResponse(ENTER_ONE_NUMBER);
                    continue;
                } else {
                    gamePlayController.getOpponentPlayer().getMagicCardZone().moveCardToGraveyardWithoutAddress(gamePlayController.getSelectedCard(), gamePlayController.getCurrentPlayer());
                    duelMenu.printResponse(EFFECT_DONE_SUCCESSFULLY);
                    break;
                }
            } else if (i == 2) {
                duelMenu.printResponse(EFFECT_DONE_SUCCESSFULLY);
                break;
            }
        }
    }


    public void canActivateChangeOfHeart() {
        if (gamePlayController.getOpponentPlayer().getMonsterCardZone().getNumberOfCard() == 0) {
            duelMenu.printResponse(PREPARATIONS_OF_THIS_SPELL_ARE_NOT_DONE_YET);
        } else {
            changeOfHeart();
        }
    }

    public void changeOfHeart() {
        gamePlayController.getSelectedCard().setHidden(false);

        Map<Integer, MonsterCard> map = gamePlayController.getOpponentPlayer().getMonsterCardZone().getZoneCards();
        duelMenu.printResponse(ENTER_ONE_NUMBER);
        while (true) {
            int num = duelMenu.scannerNum();
            if (map.get(num) != null) {
                effectController.getControl(map.get(num));
                duelMenu.printResponse(EFFECT_DONE_SUCCESSFULLY);
                gamePlayController.getCurrentPlayer().getMagicCardZone().moveCardToGraveyardWithoutAddress(gamePlayController.getSelectedCard(),gamePlayController.getCurrentPlayer());
                return;
            } else {
                duelMenu.printResponse(INVALID_CELL_NUMBER);
                duelMenu.printResponse(ENTER_ONE_NUMBER);
            }
        }
    }
    //TODO GET ACTIVATED CARDS

    public void spellAbsorption() {
        gamePlayController.getSelectedCard().setHidden(false);
        for (Map.Entry<Player, Card> entry : gamePlayController.getActivatedCards().entrySet()) {
            if (entry.getValue().getCardName().equals("Spell Absorption")) ;
            entry.getKey().increaseLifePoint(500);
            duelMenu.printResponse(EFFECT_DONE_SUCCESSFULLY);
        }
    }

    //TODO DESTROY CHECKER
// TODO counter for sords of reavealing light
    public void swordOfRevealingLight() {
        gamePlayController.getSelectedCard().setHidden(false);
        Map<Integer, MonsterCard> map = gamePlayController.getOpponentPlayer().getMonsterCardZone().getZoneCards();
        for (Map.Entry<Integer, MonsterCard> entry : map.entrySet()) {
            if (entry.getValue() != null && entry.getValue().getHidden())
                entry.getValue().setHidden(false);
        }
        duelMenu.printResponse(EFFECT_DONE_SUCCESSFULLY);
    }

    public void canActivateMonsterReborn() {
        int count=0;
        for(int i=0 ; i<gamePlayController.getCurrentPlayer().getGraveyardZone().getZoneCards().size() ; i++){
            if(gamePlayController.getCurrentPlayer().getGraveyardZone().getZoneCards().get(i) instanceof MonsterCard){
               count++;
            }
        }
        for(int j=0 ; j<gamePlayController.getOpponentPlayer().getGraveyardZone().getZoneCards().size();j++){
            if(gamePlayController.getCurrentPlayer().getGraveyardZone().getZoneCards().get(j) instanceof MonsterCard){
                count++;
            }
        }
        if(count==0){
            duelMenu.printResponse(PREPARATIONS_OF_THIS_SPELL_ARE_NOT_DONE_YET);
        }
        else{
            monsterReborn();
        }

    }
//TODO set the position of monster reborned
    public void monsterReborn() {
        gamePlayController.getSelectedCard().setHidden(false);
        Player player;
        duelMenu.printResponse(ENTER_PLAYER);
        String rivalOrNot = duelMenu.scannerLine();
        if(rivalOrNot.equals("rival")){
            player=gamePlayController.getOpponentPlayer();
            duelMenu.printResponse(ENTER_ONE_NUMBER);
            while (true)
            {
                int num = duelMenu.scannerNum();
                if(num <= gamePlayController.getOpponentPlayer().getGraveyardZone().getZoneCards().size() &&
                        ( gamePlayController.getOpponentPlayer().getGraveyardZone().getZoneCards().get(num-1) instanceof MonsterCard))
                {   gamePlayController.getOpponentPlayer().getMonsterCardZone().summonOrSetMonster((MonsterCard) gamePlayController.getOpponentPlayer().getGraveyardZone().getZoneCards().get(num-1), gamePlayController.getCurrentPlayer());
                    gamePlayController.getOpponentPlayer().getGraveyardZone().getZoneCards().remove(num-1);
                    duelMenu.printResponse(EFFECT_DONE_SUCCESSFULLY);
                    return;
                }
                else {
                    duelMenu.printResponse(INVALID_CELL_NUMBER);
                    duelMenu.printResponse(ENTER_ONE_NUMBER);
                }
            }
        }else{
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
    public void supplySquad(Player player){
        gamePlayController.getSelectedCard().setHidden(false);
        player.getHand().addCardToHand(player.getDeckZone().getZoneCards().get(0));
        player.getDeckZone().removeCardFromDeckZone(player.getDeckZone().getZoneCards().get(0));
    }
}




