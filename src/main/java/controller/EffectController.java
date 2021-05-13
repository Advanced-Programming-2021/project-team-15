package controller;

import model.Card;
import model.MagicCard;
import model.MonsterCard;
import model.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EffectController {
    GamePlayController gamePlayController = GamePlayController.getInstance();
    ArrayList<MonsterCard> monstersWeTookControl = new ArrayList<>();
    HashMap<MagicCard, Card> equippedCardsBySpells = new HashMap<>();


    public void addATK(MonsterCard.MonsterType type, Boolean both, int amount) {
        Map<Integer, MonsterCard> map = gamePlayController.getCurrentPlayer().getMonsterCardZone().getZoneCards();

        for (Map.Entry<Integer, MonsterCard> entry : map.entrySet()) {
            if (entry.getValue() != null && entry.getValue().getMonsterType() == type)
                entry.getValue().setGameATK(entry.getValue().getGameATK() + amount);
        }
        if (both) {
            map = gamePlayController.getOpponentPlayer().getMonsterCardZone().getZoneCards();
            for (Map.Entry<Integer, MonsterCard> entry : map.entrySet()) {
                if (entry.getValue() != null && entry.getValue().getMonsterType() == type)
                    entry.getValue().setGameATK(entry.getValue().getGameATK() + amount);
            }

        }
    }

    public void addDEF(MonsterCard.MonsterType type, Boolean both, int amount) {
        Map<Integer, MonsterCard> map = gamePlayController.getCurrentPlayer().getMonsterCardZone().getZoneCards();

        for (Map.Entry<Integer, MonsterCard> entry : map.entrySet()) {
            if (entry.getValue() != null && entry.getValue().getMonsterType() == type)
                entry.getValue().setGameDEF(entry.getValue().getGameDEF() + amount);
        }
        if (both) {
            map = gamePlayController.getOpponentPlayer().getMonsterCardZone().getZoneCards();
            for (Map.Entry<Integer, MonsterCard> entry : map.entrySet()) {
                if (entry.getValue() != null && entry.getValue().getMonsterType() == type)
                    entry.getValue().setGameDEF(entry.getValue().getGameDEF() + amount);
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

    public int numberOfDeadMonsters() {
        int i = 0;
        for (Card card : gamePlayController.getCurrentPlayer().getGraveyardZone().getZoneCards()) {
            if (card != null && card instanceof MonsterCard)
                i++;
        }
        return i;
    }

    public int numberOfWholeMagicCards() {
        return (gamePlayController.getCurrentPlayer().getMagicCardZone().getNumberOfCard() + gamePlayController.getOpponentPlayer().getMagicCardZone().getNumberOfCard());
    }

    public void getControl(MonsterCard monsterCard)
    { gamePlayController.getOpponentPlayer().getMonsterCardZone().getZoneCards().remove(monsterCard);
      gamePlayController.getCurrentPlayer().getMonsterCardZone().moveToFirstEmptyPlace(monsterCard);
        monstersWeTookControl.add(monsterCard);
    }
    public void removeControl()
    {   for( MonsterCard monsterCard : monstersWeTookControl)
    {  gamePlayController.getCurrentPlayer().getMonsterCardZone().getZoneCards().remove(monsterCard);
        gamePlayController.getOpponentPlayer().getMonsterCardZone().moveToFirstEmptyPlace(monsterCard);
        monstersWeTookControl.remove(monsterCard);
    }
    }

    public Player getOwnerOfMagicCard(Card card)
    { if( gamePlayController.getCurrentPlayer().getMagicCardZone().isExist(card))
        return gamePlayController.getCurrentPlayer();
        else return gamePlayController.getOpponentPlayer();
    }


    public GamePlayController getGamePlayController() {
        return gamePlayController;
    }

    public void setGamePlayController(GamePlayController gamePlayController) {
        this.gamePlayController = gamePlayController;
    }

    public ArrayList<MonsterCard> getMonstersWeTookControl() {
        return monstersWeTookControl;
    }

    public void setMonstersWeTookControl(ArrayList<MonsterCard> monstersWeTookControl) {
        this.monstersWeTookControl = monstersWeTookControl;
    }

    public HashMap<MagicCard, Card> getEquippedCardsBySpells() {
        return equippedCardsBySpells;
    }

    public void setEquippedCardsBySpells(HashMap<MagicCard, Card> equippedCardsBySpells) {
        this.equippedCardsBySpells = equippedCardsBySpells;
    }

    public Boolean askToBeActivatedInRivalsTurn()
    {

    }
}











