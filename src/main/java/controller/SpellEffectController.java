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
    private Card cardEffectedBySpell;

    //TODO: put a while in view ....


    DuelMenu duelMenu = DuelMenu.getInstance();
    EffectController effectController = new EffectController();
    GamePlayController gamePlayController = GamePlayController.getInstance();
    //TODO : FIRST 4 EFFECTS SHOULD HE CHECKED DUHHHHHHH

    public void yami(Boolean x) {
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
        else {
            gamePlayController.getSelectedCard().setHidden(false);
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
    }


    public void blackPendant() {
        if (gamePlayController.getCurrentPlayer().getMonsterCardZone().getNumberOfCard() == 0)
            duelMenu.printResponse(CANT_ACTIVATE_SPELL);
        else {
            gamePlayController.getSelectedCard().setHidden(false);
            duelMenu.printResponse(ENTER_ONE_NUMBER);
            Map<Integer, MonsterCard> map = gamePlayController.getCurrentPlayer().getMonsterCardZone().getZoneCards();
            while (true) {
                int num = duelMenu.scannerNum();
                if (map.get(num) != null && !map.get(num).getHidden()) {
                    map.get(num).setGameATK(map.get(num).getGameATK() + 500);
                    duelMenu.printResponse(CARD_EQUIPPED);
                    return;
                } else {
                    duelMenu.printResponse(INVALID_CELL_NUMBER);
                    duelMenu.printResponse(ENTER_ONE_NUMBER);
                }
            }
        }
    }


    public void unitedWeStand() {
        if (effectController.getNumberOfFaceUpMonstersOfCurrentPlayer() == 0)
            duelMenu.printResponse(CANT_ACTIVATE_SPELL);
        else {
            gamePlayController.getSelectedCard().setHidden(false);
            duelMenu.printResponse(ENTER_ONE_NUMBER);
            Map<Integer, MonsterCard> map = gamePlayController.getCurrentPlayer().getMonsterCardZone().getZoneCards();
            int amount = effectController.getNumberOfFaceUpMonstersOfCurrentPlayer();
            while (true)
            { int num = duelMenu.scannerNum();
              if(map.get(num)!=null){
                  map.get(num).setGameATK(map.get(num).getGameATK() + amount * 800);
                  map.get(num).setGameDEF(map.get(num).getGameDEF() + amount * 800);
                   duelMenu.printResponse(CARD_EQUIPPED);
                   return;
              } else {
                  duelMenu.printResponse(INVALID_CELL_NUMBER);
                  duelMenu.printResponse(ENTER_ONE_NUMBER);
              }
            }

        }
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
        else {
            gamePlayController.getSelectedCard().setHidden(false);
            duelMenu.printResponse(ENTER_ONE_NUMBER);
            while (true)
            { int num =  duelMenu.scannerNum();
                if (map.get(num) != null && map.get(num).getMonsterType() == MonsterCard.MonsterType.WARRIOR && !map.get(num).getHidden())
                {  if (map.get(num).getMode() == MonsterCard.Mode.ATTACK) {
                        map.get(num).setGameATK(map.get(num).getGameATK() + map.get(num).getDefensePoint());
                        duelMenu.printResponse(CARD_EQUIPPED);
                        return;
                    } else if (map.get(num).getMode() == MonsterCard.Mode.DEFENSE) {
                        map.get(num).setGameDEF(map.get(num).getGameDEF() + map.get(num).getGameATK());
                        duelMenu.printResponse(CARD_EQUIPPED);
                       return;}
                }
                else {  duelMenu.printResponse(INVALID_CELL_NUMBER);
                        duelMenu.printResponse(ENTER_ONE_NUMBER);
                }
            }

        }
    }



    public void terraforming() {  //TODO WTF  WHY DON'T WE CHOOSE ANY CARD?
        for (Card card : gamePlayController.getCurrentPlayer().getDeckZone().getZoneCards()) {
            if (card instanceof MagicCard && ((MagicCard) card).getMagicType() == MagicCard.MagicType.SPELL
                    && ((MagicCard) card).getCardIcon() == MagicCard.CardIcon.FIELD) {
                gamePlayController.getSelectedCard().setHidden(false);
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
        if (gamePlayController.getCurrentPlayer().getDeckZone().getZoneCards().size() < 2) {
            duelMenu.printResponse(CANT_ACTIVATE_SPELL);
            return;
        }
        gamePlayController.getSelectedCard().setHidden(false);
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
            gamePlayController.getSelectedCard().setHidden(false);
            effectController.destroyCards(Card.CardType.MONSTER, false);
            gamePlayController.getCurrentPlayer().getMagicCardZone().moveCardToGraveyardWithoutAddress(gamePlayController.getSelectedCard(), gamePlayController.getCurrentPlayer());
        }
    }

    public void harpiesFeatherDuster() {
        if (gamePlayController.getOpponentPlayer().getMagicCardZone().getNumberOfCard() == 0)
            duelMenu.printResponse(CANT_ACTIVATE_SPELL);
        else {
            gamePlayController.getSelectedCard().setHidden(false);
            effectController.destroyCards(Card.CardType.MAGIC, false);
            gamePlayController.getCurrentPlayer().getMagicCardZone().moveCardToGraveyardWithoutAddress(gamePlayController.getSelectedCard(), gamePlayController.getCurrentPlayer());
        }
    }


    public void darkHole() {
        if (gamePlayController.getOpponentPlayer().getMonsterCardZone().getNumberOfCard() == 0 && gamePlayController.getCurrentPlayer().getMonsterCardZone().getNumberOfCard() == 0)
            duelMenu.printResponse(CANT_ACTIVATE_SPELL);
        else {
            gamePlayController.getSelectedCard().setHidden(false);
            effectController.destroyCards(Card.CardType.MONSTER, true);
            gamePlayController.getCurrentPlayer().getMagicCardZone().moveCardToGraveyardWithoutAddress(gamePlayController.getSelectedCard(), gamePlayController.getCurrentPlayer());
        }
    }

}

