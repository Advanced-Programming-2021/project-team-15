package controller;

import model.Card;
import model.MonsterCard;
import model.Player;

import java.util.Map;

public class EffectController {
    GamePlayController gamePlayController = GamePlayController.getInstance();

    public void addATK(MonsterCard.MonsterType type, Boolean both, int amount) {
        Map<Integer, MonsterCard> map = gamePlayController.getCurrentPlayer().getMonsterCardZone().getZoneCards();

        for (Map.Entry<Integer, MonsterCard> entry : map.entrySet()) {
            if (entry.getValue() != null && entry.getValue().getMonsterType() == type)
                entry.getValue().setAttackPoint(entry.getValue().getAttackPoint() + amount);
        }
        if (both) {
            map = gamePlayController.getOpponentPlayer().getMonsterCardZone().getZoneCards();
            for (Map.Entry<Integer, MonsterCard> entry : map.entrySet()) {
                if (entry.getValue() != null && entry.getValue().getMonsterType() == type)
                    entry.getValue().setAttackPoint(entry.getValue().getAttackPoint() + amount);
            }

        }
    }

    public void addDEF(MonsterCard.MonsterType type, Boolean both, int amount) {
        Map<Integer, MonsterCard> map = gamePlayController.getCurrentPlayer().getMonsterCardZone().getZoneCards();

        for (Map.Entry<Integer, MonsterCard> entry : map.entrySet()) {
            if (entry.getValue() != null && entry.getValue().getMonsterType() == type)
                entry.getValue().setDefensePoint(entry.getValue().getDefensePoint() + amount);
        }
        if (both) {
            map = gamePlayController.getOpponentPlayer().getMonsterCardZone().getZoneCards();
            for (Map.Entry<Integer, MonsterCard> entry : map.entrySet()) {
                if (entry.getValue() != null && entry.getValue().getMonsterType() == type)
                    entry.getValue().setDefensePoint(entry.getValue().getDefensePoint() + amount);
            }
        }
    }

    public int getNumberOfFaceUpMonstersOfCurrentPlayer() {
        int i = 0;
        Map<Integer, MonsterCard> map = gamePlayController.getCurrentPlayer().getMonsterCardZone().getZoneCards();
        for (Map.Entry<Integer, MonsterCard> entry : map.entrySet()) {
            if (entry.getValue() != null && !entry.getValue().getHidden()) {
                i++;
            }
        }
        return i;
    }

    public void destroyCards(Card.CardType type, Boolean both) {
        Map<Integer, MonsterCard> map = gamePlayController.getCurrentPlayer().getMonsterCardZone().getZoneCards();

        for (Map.Entry<Integer, MonsterCard> entry : map.entrySet()) {
            if (type == Card.CardType.MONSTER) {
                if (entry.getValue() != null) {
                    gamePlayController.getOpponentPlayer().getMonsterCardZone().moveCardToGraveyard(entry.getKey(), gamePlayController.getOpponentPlayer());
                }
            } else if (type == Card.CardType.MAGIC) {
                if (entry.getValue() != null) {
                    gamePlayController.getOpponentPlayer().getMagicCardZone().moveCardToGraveyard(entry.getKey(), gamePlayController.getOpponentPlayer());
                }
            }
        }
        if (both) {
            map = gamePlayController.getCurrentPlayer().getMonsterCardZone().getZoneCards();
            for (Map.Entry<Integer, MonsterCard> entry : map.entrySet()) {
                if (type == Card.CardType.MONSTER) {
                    if (entry.getValue() != null) {
                        gamePlayController.getCurrentPlayer().getMonsterCardZone().moveCardToGraveyard(entry.getKey(), gamePlayController.getCurrentPlayer());
                    }
                } else if (type == Card.CardType.MAGIC) {
                    if (entry.getValue() != null) {
                        gamePlayController.getCurrentPlayer().getMagicCardZone().moveCardToGraveyard(entry.getKey(), gamePlayController.getCurrentPlayer());
                    }
                }
            }
        }
    }
}






