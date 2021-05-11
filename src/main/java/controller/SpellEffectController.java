package controller;

import controller.responses.DeckMenuResponses;
import controller.responses.DuelMenuResponses;
import model.Card;
import model.MagicCard;
import model.MonsterCard;
import view.DuelMenu;

import java.util.Map;

import static controller.responses.DuelMenuResponses.*;

public class SpellEffectController {
    DuelMenu duelMenu = DuelMenu.getInstance();
    EffectController effectController = new EffectController();
    GamePlayController gamePlayController = GamePlayController.getInstance();
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
        for (Card card :gamePlayController.getCurrentPlayer().getGraveyardZone().getZoneCards()) {
            if (card instanceof MonsterCard)
                i++;
        }
        effectController.addATK(MonsterCard.MonsterType.BEAST, false, i * 100);
    }

    public void closedForestForNewAddedCard(MonsterCard monsterCard) {
        int i = 0;
        for (Card card :gamePlayController.getCurrentPlayer().getGraveyardZone().getZoneCards()) {
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

        Map<Integer, MonsterCard> map = gamePlayController.getCurrentPlayer().getMonsterCardZone().getZoneCards();
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
        Map<Integer, MonsterCard> map = gamePlayController.getCurrentPlayer().getMonsterCardZone().getZoneCards();
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
        if (gamePlayController.getCurrentPlayer().getMonsterCardZone().getNumberOfCard() == 0)
            duelMenu.printResponse(CANT_ACTIVATE_SPELL);
        else duelMenu.printResponse(ENTER_ONE_NUMBER);
    }

    public DuelMenuResponses blackPendantChoose(int num) {
        Map<Integer, MonsterCard> map = gamePlayController.getCurrentPlayer().getMonsterCardZone().getZoneCards();
        map.get(num).setAttackPoint(map.get(num).getAttackPoint() + 500);
        return CARD_EQUIPPED;
    }

    public void unitedWeStand() {

        if (effectController.getNumberOfFaceUpMonstersOfCurrentPlayer() == 0)
            duelMenu.printResponse(CANT_ACTIVATE_SPELL);
        else duelMenu.printResponse(ENTER_ONE_NUMBER);

    }


    public DuelMenuResponses unitedWeStandChoose(int num) {
        int amount = effectController.getNumberOfFaceUpMonstersOfCurrentPlayer();
        Map<Integer, MonsterCard> map = gamePlayController.getCurrentPlayer().getMonsterCardZone().getZoneCards();
        map.get(num).setAttackPoint(map.get(num).getAttackPoint() + amount * 800);
        map.get(num).setDefensePoint(map.get(num).getDefensePoint() + amount * 800);
        return CARD_EQUIPPED;
    }

    public void magnumShield() {
        int i = 0;

        Map<Integer, MonsterCard> map = gamePlayController.getCurrentPlayer().getMonsterCardZone().getZoneCards();
        for (Map.Entry<Integer, MonsterCard> entry : map.entrySet()) {
            if ((entry.getValue() != null && entry.getValue().getMonsterType() == MonsterCard.MonsterType.WARRIOR && !entry.getValue().getHidden()))
                i++;
        }
        if (i == 0)
            duelMenu.printResponse(CANT_ACTIVATE_SPELL);
        else duelMenu.printResponse(ENTER_ONE_NUMBER);
    }

    public DuelMenuResponses magnumShieldChoose(int num) {
        Map<Integer, MonsterCard> map =gamePlayController.getCurrentPlayer().getMonsterCardZone().getZoneCards();
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

    public void terraforming() {  //TODO WTF  WHY DON'T WE CHOOSE ANY CARD?
        for (Card card : gamePlayController.getCurrentPlayer().getDeckZone().getZoneCards()) {
            if (card instanceof MagicCard && ((MagicCard) card).getMagicType() == MagicCard.MagicType.SPELL
                    && ((MagicCard) card).getCardIcon() == MagicCard.CardIcon.FIELD) {
                gamePlayController.getCurrentPlayer().getHand().addCardToHand(card);
                gamePlayController.getCurrentPlayer().getDeckZone().removeCardFromDeckZone(card);
                gamePlayController.getCurrentPlayer().getMagicCardZone().moveCardToGraveyardWithoutAddress(gamePlayController.getSelectedCard(), gamePlayController.getCurrentPlayer());
                duelMenu.printResponse(EFFECT_DONE_SUCCESSFULLY);
                return;
            }
        }
        duelMenu.printResponse(CANT_ACTIVATE_SPELL);
    }

    public void potOfGReed() {
        if(gamePlayController.getCurrentPlayer().getDeckZone().getZoneCards().size()<2)
            duelMenu.printResponse(CANT_ACTIVATE_SPELL);
        for (int i = 0; i < 2; i++) {
            gamePlayController.getCurrentPlayer().getHand().addCardToHand(gamePlayController.getCurrentPlayer().getDeckZone().getZoneCards().get(0));
           gamePlayController.getCurrentPlayer().getDeckZone().removeCardFromDeckZone(gamePlayController.getCurrentPlayer().getDeckZone().getZoneCards().get(0));
        }
        gamePlayController.getCurrentPlayer().getMagicCardZone().moveCardToGraveyardWithoutAddress(gamePlayController.getSelectedCard(), gamePlayController.getCurrentPlayer());

    }

    public void raigeki() {
        if (gamePlayController.getOpponentPlayer().getMonsterCardZone().getNumberOfCard() == 0)
            duelMenu.printResponse(CANT_ACTIVATE_SPELL);
        else {
            effectController.destroyCards(Card.CardType.MONSTER, false);
            gamePlayController.getCurrentPlayer().getMagicCardZone().moveCardToGraveyardWithoutAddress(gamePlayController.getSelectedCard(), gamePlayController.getCurrentPlayer());
        }
    }

    public void harpiesFeatherDuster() {
        if (gamePlayController.getOpponentPlayer().getMagicCardZone().getNumberOfCard() == 0)
            duelMenu.printResponse(CANT_ACTIVATE_SPELL);
        else {
            effectController.destroyCards(Card.CardType.MAGIC, false);
            gamePlayController.getCurrentPlayer().getMagicCardZone().moveCardToGraveyardWithoutAddress(gamePlayController.getSelectedCard(), gamePlayController.getCurrentPlayer());
        }
    }


    public void darkHole() {
        if (gamePlayController.getOpponentPlayer().getMonsterCardZone().getNumberOfCard() == 0 && gamePlayController.getCurrentPlayer().getMonsterCardZone().getNumberOfCard()==0)
            duelMenu.printResponse(CANT_ACTIVATE_SPELL);
        else {
            effectController.destroyCards(Card.CardType.MONSTER, true);
            gamePlayController.getCurrentPlayer().getMagicCardZone().moveCardToGraveyardWithoutAddress(gamePlayController.getSelectedCard(),gamePlayController.getCurrentPlayer());
        }
    }
}

