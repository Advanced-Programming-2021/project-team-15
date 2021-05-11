package controller;

import controller.responses.DeckMenuResponses;
import controller.responses.DuelMenuResponses;
import model.Card;
import model.MagicCard;
import model.MonsterCard;
import view.DuelMenu;

import java.util.Map;

import static controller.responses.DuelMenuResponses.*;

public class SpellEffectController extends GamePlayController {
    DuelMenu duelMenu = DuelMenu.getInstance();
    //TODO : FIRST 4 EFFECTS SHOULD HE CHECKED DUHHHHHHH

    public void yami() {
        effectController.addATK(MonsterCard.MonsterType.FIEND, true, 200);
        effectController.addATK(MonsterCard.MonsterType.SPELL_CASTER, true, 200);
        effectController.addATK(MonsterCard.MonsterType.FAIRY, true, -200);
        effectController.addDEF(MonsterCard.MonsterType.FIEND, true, 200);
        effectController.addDEF(MonsterCard.MonsterType.SPELL_CASTER, true, 200);
        effectController.addDEF(MonsterCard.MonsterType.FAIRY, true, -200);
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


    public void forest() {
        effectController.addATK(MonsterCard.MonsterType.BEAST, true, 200);
        effectController.addDEF(MonsterCard.MonsterType.BEAST, true, 200);
        effectController.addATK(MonsterCard.MonsterType.INSECT, true, 200);
        effectController.addDEF(MonsterCard.MonsterType.INSECT, true, 200);
        effectController.addATK(MonsterCard.MonsterType.BEAST_WARRIOR, true, 200);
        effectController.addDEF(MonsterCard.MonsterType.BEAST_WARRIOR, true, 200);
    }

    public void forestForNewAddedCard(MonsterCard monsterCard) {
        if (monsterCard.getMonsterType() == MonsterCard.MonsterType.BEAST ||
                monsterCard.getMonsterType() == MonsterCard.MonsterType.INSECT ||
                monsterCard.getMonsterType() == MonsterCard.MonsterType.BEAST_WARRIOR) {
            monsterCard.setAttackPoint(monsterCard.getAttackPoint() + 200);
            monsterCard.setDefensePoint(monsterCard.getDefensePoint() + 200);
        }
    }

    public void closedForest() {
        int i = 0;
        for (Card card : currentPlayer.getGraveyardZone().getZoneCards()) {
            if (card instanceof MonsterCard)
                i++;
        }
        effectController.addATK(MonsterCard.MonsterType.BEAST, false, i * 100);
    }

    public void closedForestForNewAddedCard(MonsterCard monsterCard) {
        int i = 0;
        for (Card card : currentPlayer.getGraveyardZone().getZoneCards()) {
            if (card instanceof MonsterCard)
                i++;
        }
        if (monsterCard.getMonsterType() == MonsterCard.MonsterType.BEAST) {
            monsterCard.setAttackPoint(monsterCard.getAttackPoint() + i * 100);
        }
    }

    public void closedForestForNewCardAddedToGraveyard(MonsterCard monsterCard) {
        effectController.addATK(MonsterCard.MonsterType.BEAST, false, 100);
    }


    public void umiiruka() {
        effectController.addATK(MonsterCard.MonsterType.AQUA, true, 500);
        effectController.addATK(MonsterCard.MonsterType.AQUA, true, -400);
    }

    // TODO check for invalid cell number just like magnum shield
    // TODO check magnum shield to be sure
    public void swordOfDarkDestruction() {
        int i = 0;

        Map<Integer, MonsterCard> map = currentPlayer.getMonsterCardZone().getZoneCards();
        for (Map.Entry<Integer, MonsterCard> entry : map.entrySet()) {
            if ((entry.getValue() != null && entry.getValue().getMonsterType() == MonsterCard.MonsterType.FIEND && !entry.getValue().getHidden()) ||
                    (entry.getValue() != null && entry.getValue().getMonsterType() == MonsterCard.MonsterType.SPELL_CASTER && !entry.getValue().getHidden()))
                i++;
        }
        if (i == 0)
            duelMenu.printResponse(CANT_ACTIVATE_SPELL);
        else duelMenu.printResponse(ENTER_ONE_NUMBER);
    }

    public DuelMenuResponses swordOfDarkDestructionChoose(int num) {
        Map<Integer, MonsterCard> map = currentPlayer.getMonsterCardZone().getZoneCards();
        if ((map.get(num).getMonsterType() != MonsterCard.MonsterType.FIEND) &&
                (map.get(num).getMonsterType() != MonsterCard.MonsterType.FAIRY))
            return DuelMenuResponses.NOT_WANTED_TYPE;
        else {
            map.get(num).setAttackPoint(map.get(num).getAttackPoint() + 400);
            map.get(num).setDefensePoint(map.get(num).getDefensePoint() - 200);
            return CARD_EQUIPPED;
        }
    }

    public void blackPendant() {
        if (currentPlayer.getMonsterCardZone().getNumberOfCard() == 0)
            duelMenu.printResponse(CANT_ACTIVATE_SPELL);
        else duelMenu.printResponse(ENTER_ONE_NUMBER);
    }

    public DuelMenuResponses blackPendantChoose(int num) {
        Map<Integer, MonsterCard> map = currentPlayer.getMonsterCardZone().getZoneCards();
        map.get(num).setAttackPoint(map.get(num).getAttackPoint() + 500);
        return CARD_EQUIPPED;
    }

    public void unitedWeStand() {

        if (getNumberOfFaceUpMonstersOfCurrentPlayer() == 0)
            duelMenu.printResponse(CANT_ACTIVATE_SPELL);
        else duelMenu.printResponse(ENTER_ONE_NUMBER);

    }

    public int getNumberOfFaceUpMonstersOfCurrentPlayer() {
        int i = 0;
        Map<Integer, MonsterCard> map = currentPlayer.getMonsterCardZone().getZoneCards();
        for (Map.Entry<Integer, MonsterCard> entry : map.entrySet()) {
            if (entry.getValue() != null && !entry.getValue().getHidden()) {
                i++;
            }
        }
        return i;
    }

    public DuelMenuResponses unitedWeStandChoose(int num) {
        int amount = getNumberOfFaceUpMonstersOfCurrentPlayer();
        Map<Integer, MonsterCard> map = currentPlayer.getMonsterCardZone().getZoneCards();
        map.get(num).setAttackPoint(map.get(num).getAttackPoint() + amount * 800);
        map.get(num).setDefensePoint(map.get(num).getDefensePoint() + amount * 800);
        return CARD_EQUIPPED;
    }

    public void magnumShield() {
        int i = 0;

        Map<Integer, MonsterCard> map = currentPlayer.getMonsterCardZone().getZoneCards();
        for (Map.Entry<Integer, MonsterCard> entry : map.entrySet()) {
            if ((entry.getValue() != null && entry.getValue().getMonsterType() == MonsterCard.MonsterType.WARRIOR && !entry.getValue().getHidden()))
                i++;
        }
        if (i == 0)
            duelMenu.printResponse(CANT_ACTIVATE_SPELL);
        else duelMenu.printResponse(ENTER_ONE_NUMBER);
    }

    public DuelMenuResponses magnumShieldChoose(int num) {
        Map<Integer, MonsterCard> map = currentPlayer.getMonsterCardZone().getZoneCards();
        if (map.get(num) != null && map.get(num).getMonsterType() == MonsterCard.MonsterType.WARRIOR && !map.get(num).getHidden()) {
            if (map.get(num).getMode() == MonsterCard.Mode.ATTACK) {
                map.get(num).setAttackPoint(map.get(num).getAttackPoint() + map.get(num).getDefensePoint());
                return CARD_EQUIPPED;
            } else if (map.get(num).getMode() == MonsterCard.Mode.DEFENSE) {
                map.get(num).setDefensePoint(map.get(num).getDefensePoint() + map.get(num).getAttackPoint());
                return CARD_EQUIPPED;
            }
        }
        return INVALID_CELL_NUMBER;
    }

    // TODO
    public void monsterReborn() {

    }

    public void terraforming() {
        for (Card card : currentPlayer.getDeckZone().getZoneCards()) {
            if (card instanceof MagicCard && ((MagicCard) card).getMagicType() == MagicCard.MagicType.SPELL
                    && ((MagicCard) card).getCardIcon() == MagicCard.CardIcon.FIELD) {
                currentPlayer.getHand().addCardToHand(card);
                currentPlayer.getDeckZone().removeCardFromDeckZone(card);
                currentPlayer.getMagicCardZone().moveCardToGraveyardWithoutAddress(selectedCard, currentPlayer);
                duelMenu.printResponse(EFFECT_DONE_SUCCESSFULLY);
                return;
            }
        }
        duelMenu.printResponse(CANT_ACTIVATE_SPELL);
    }

    public void potOfGReed() {
        for (int i = 0; i < 2; i++) {
            currentPlayer.getHand().addCardToHand(currentPlayer.getDeckZone().getZoneCards().get(0));
            currentPlayer.getDeckZone().removeCardFromDeckZone(currentPlayer.getDeckZone().getZoneCards().get(0));
        }
        currentPlayer.getMagicCardZone().moveCardToGraveyardWithoutAddress(selectedCard, currentPlayer);

    }

    public void raigeki() {
        if (opponentPlayer.getMonsterCardZone().getNumberOfCard() == 0)
            duelMenu.printResponse(CANT_ACTIVATE_SPELL);
        else {
            effectController.destroyCards(Card.CardType.MONSTER, false);
            currentPlayer.getMagicCardZone().moveCardToGraveyardWithoutAddress(selectedCard, currentPlayer);
        }
    }

    public void harpiesFeatherDuster() {
        if (opponentPlayer.getMagicCardZone().getNumberOfCard() == 0)
            duelMenu.printResponse(CANT_ACTIVATE_SPELL);
        else {
            effectController.destroyCards(Card.CardType.MAGIC, false);
            currentPlayer.getMagicCardZone().moveCardToGraveyardWithoutAddress(selectedCard, currentPlayer);
        }
    }


    public void darkHole() {
        if (opponentPlayer.getMonsterCardZone().getNumberOfCard() == 0 && currentPlayer.getMonsterCardZone().getNumberOfCard()==0)
            duelMenu.printResponse(CANT_ACTIVATE_SPELL);
        else {
            effectController.destroyCards(Card.CardType.MONSTER, true);
            currentPlayer.getMagicCardZone().moveCardToGraveyardWithoutAddress(selectedCard, currentPlayer);
        }
    }
}

