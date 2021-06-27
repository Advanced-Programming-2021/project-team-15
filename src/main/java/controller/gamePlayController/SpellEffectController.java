package controller.gamePlayController;

import model.Player;
import model.cards.Card;
import model.cards.MagicCard;
import model.cards.MonsterCard;
import view.DuelMenu;

import java.util.ArrayList;
import java.util.Map;

import static controller.responses.DuelMenuResponses.*;

public class SpellEffectController {

    DuelMenu duelMenu = DuelMenu.getInstance();
    GamePlayController gamePlayController = GamePlayController.getInstance();
    private boolean doIt = false;

    //TODO: put a while in view ....

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

    public void yami(Boolean x, MagicCard card) {
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

    public void spellAbsorption() {
        if (!doIt) {
            gamePlayController.activateSelectedCard();
            return;
        }
        for (Map.Entry<Player, Card> entry : gamePlayController.getActivatedCards().entrySet()) {
            if (entry.getValue().getCardName().equals("Spell Absorption")) ;
            entry.getKey().increaseLifePoint(500);
            duelMenu.printResponse(EFFECT_DONE_SUCCESSFULLY);
        }
    }

    public void yamiForNewAddedCard(MonsterCard monsterCard) {
        duelMenu.printString("yami effect for new added card!");
        if (monsterCard.getMonsterType() == MonsterCard.MonsterType.FIEND ||
                monsterCard.getMonsterType() == MonsterCard.MonsterType.SPELL_CASTER) {
            monsterCard.setAttackPoint(monsterCard.getAttackPoint() + 200);
            monsterCard.setDefensePoint(monsterCard.getDefensePoint() + 200);
        } else if (monsterCard.getMonsterType() == MonsterCard.MonsterType.FAIRY) {
            monsterCard.setAttackPoint(monsterCard.getAttackPoint() - 200);
            monsterCard.setDefensePoint(monsterCard.getDefensePoint() - 200);
        }
    }

    public void forest(Boolean x, MagicCard card) {
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
        duelMenu.printString("forest effect for new added card!");
        if (monsterCard.getMonsterType() == MonsterCard.MonsterType.BEAST ||
                monsterCard.getMonsterType() == MonsterCard.MonsterType.INSECT ||
                monsterCard.getMonsterType() == MonsterCard.MonsterType.BEAST_WARRIOR) {
            monsterCard.setGameATK(monsterCard.getGameATK() + 200);
            monsterCard.setGameDEF(monsterCard.getGameDEF() + 200);
        }
    }

    public void closedForest(Boolean x, Card card) {
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
        duelMenu.printString("closed forest effect for new added card!");
        int i = GamePlayController.getEffectController().numberOfDeadMonsters();
        if (monsterCard.getMonsterType() == MonsterCard.MonsterType.BEAST) {
            monsterCard.setGameATK(monsterCard.getGameATK() + i * 100);
        }
    }

    public void closedForestForNewCardAddedToGraveyard(MonsterCard monsterCard, MagicCard closedForest) {
        GamePlayController.getEffectController().addATK(MonsterCard.MonsterType.BEAST, false, 100, closedForest.getOwner());
    }

    public void umiiruka(Boolean x, Card card) {
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

    // TODO check for invalid cell number just like magnum shield
    // TODO check magnum shield to be sure

    public void umiirukaForNewAddedCard(MonsterCard monsterCard) {
        duelMenu.printString("umiiruka effect for new added card!");
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

    public void swordOfDarkDestruction(MagicCard swordOfDarkDestruction) {
        if (!doIt) {
            if (checkSwordOfDarkDestruction()) gamePlayController.activateSelectedCard();
            return;
        }
        Map<Integer, MonsterCard> map = gamePlayController.getCurrentPlayer().getMonsterCardZone().getZoneCards();
        duelMenu.printResponse(ENTER_ONE_NUMBER);
        while (true) {
            int num = duelMenu.getNum();
            if ((map.get(num) != null && map.get(num).getMonsterType() != MonsterCard.MonsterType.FIEND) &&
                    (map.get(num) != null && map.get(num).getMonsterType() != MonsterCard.MonsterType.FAIRY)) {
                map.get(num).setGameATK(map.get(num).getGameATK() + 400);
                map.get(num).setGameDEF(map.get(num).getGameDEF() - 200);
                GamePlayController.getEffectController().getEquippedCardsBySpells().put(swordOfDarkDestruction, map.get(num));
                duelMenu.printResponse(CARD_EQUIPPED);
                return;
            } else {
                duelMenu.printResponse(INVALID_CELL_NUMBER);
                duelMenu.printResponse(ENTER_ONE_NUMBER);
            }

        }
    }

    //TODO : handle death !
    public Boolean checkBlackPendant() {
        if (GamePlayController.getEffectController().getNumberOfFaceUpMonstersOfCurrentPlayer() == 0)
            return false;
        else return true;
    }

    public void blackPendant(MagicCard blackPendant) {
        if (!doIt) {
            if (checkBlackPendant()) gamePlayController.activateSelectedCard();
            return;
        }
        duelMenu.printResponse(ENTER_ONE_NUMBER);
        Map<Integer, MonsterCard> map = gamePlayController.getCurrentPlayer().getMonsterCardZone().getZoneCards();
        while (true) {
            String input = duelMenu.getString();
            if (input.equals("cancel")) {
                duelMenu.printString("canceled");
                return;
            }
            int num = Integer.parseInt(input);
            if (map.get(num) != null && !map.get(num).getHidden()) {
                map.get(num).setGameATK(map.get(num).getGameATK() + 500);
                GamePlayController.getEffectController().getEquippedCardsBySpells().put(blackPendant, map.get(num));
                duelMenu.printResponse(CARD_EQUIPPED);
                return;
            } else {
                duelMenu.printResponse(INVALID_CELL_NUMBER);
                duelMenu.printResponse(ENTER_ONE_NUMBER);
            }
        }
    }

    public Boolean checkUnitedWeStand() {
        if (GamePlayController.getEffectController().getNumberOfFaceUpMonstersOfCurrentPlayer() == 0)
            return false;
        else return true;
    }

    public void unitedWeStand(MagicCard unitedWeStand) {
        if (!doIt) {
            if (checkUnitedWeStand()) gamePlayController.activateSelectedCard();
            return;
        }
        duelMenu.printResponse(ENTER_ONE_NUMBER);
        Map<Integer, MonsterCard> map = gamePlayController.getCurrentPlayer().getMonsterCardZone().getZoneCards();
        int amount = GamePlayController.getEffectController().getNumberOfFaceUpMonstersOfCurrentPlayer();
        while (true) {
            int num = duelMenu.getNum();
            if (map.get(num) != null) {
                map.get(num).setGameATK(map.get(num).getGameATK() + amount * 800);
                map.get(num).setGameDEF(map.get(num).getGameDEF() + amount * 800);
                GamePlayController.getEffectController().getEquippedCardsBySpells().put(unitedWeStand, map.get(num));
                duelMenu.printResponse(CARD_EQUIPPED);
                return;
            } else {
                duelMenu.printResponse(INVALID_CELL_NUMBER);
                duelMenu.printResponse(ENTER_ONE_NUMBER);
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

    public void magnumShield(MagicCard magnumShield) {
        if (!doIt) {
            if (checkMagnumShield())
                gamePlayController.activateSelectedCard();
            return;
        }
        Map<Integer, MonsterCard> map = gamePlayController.getCurrentPlayer().getMonsterCardZone().getZoneCards();
        duelMenu.printResponse(ENTER_ONE_NUMBER);
        while (true) {
            int num = duelMenu.getNum();
            if (map.get(num) != null && map.get(num).getMonsterType() == MonsterCard.MonsterType.WARRIOR && !map.get(num).getHidden()) {
                if (map.get(num).getMode() == MonsterCard.Mode.ATTACK) {
                    map.get(num).setGameATK(map.get(num).getGameATK() + map.get(num).getDefensePoint());
                    GamePlayController.getEffectController().getEquippedCardsBySpells().put(magnumShield, map.get(num));
                    duelMenu.printResponse(CARD_EQUIPPED);
                    return;
                } else if (map.get(num).getMode() == MonsterCard.Mode.DEFENSE) {
                    map.get(num).setGameDEF(map.get(num).getGameDEF() + map.get(num).getGameATK());
                    GamePlayController.getEffectController().getEquippedCardsBySpells().put(magnumShield, map.get(num));
                    duelMenu.printResponse(CARD_EQUIPPED);
                    return;
                }
            } else {
                duelMenu.printResponse(INVALID_CELL_NUMBER);
                duelMenu.printResponse(ENTER_ONE_NUMBER);
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

    public void terraforming(MagicCard terraforming) {
        if (!doIt) {
            if (checkTerraforming()) gamePlayController.activateSelectedCard();
            return;
        }
        for (Card card : gamePlayController.getCurrentPlayer().getDeckZone().getZoneCards()) {
            if (card instanceof MagicCard && ((MagicCard) card).getMagicType() == MagicCard.MagicType.SPELL
                    && ((MagicCard) card).getCardIcon() == MagicCard.CardIcon.FIELD) {
<<<<<<< Updated upstream
                duelMenu.printString("do you wanna add this "+ card.getCardName()+" to your had?");
=======
                duelMenu.printString("do you wanna add this" + card.getCardName() + " to your had?");
>>>>>>> Stashed changes
                String ans = duelMenu.getString();
                if (ans.equals("yes")) {
                    gamePlayController.getCurrentPlayer().getHand().addCardToHand(card);
                    gamePlayController.getCurrentPlayer().getDeckZone().removeCardFromDeckZone(card);
                    duelMenu.printResponse(EFFECT_DONE_SUCCESSFULLY);
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

    public void potOfGReed(MagicCard potOfGreed) {
        if (!doIt) {
            if (checkPotOfGReed()) gamePlayController.activateSelectedCard();
            return;
        }

        for (int i = 0; i < 2; i++) {
            gamePlayController.getCurrentPlayer().getHand().addCardToHand(gamePlayController.getCurrentPlayer().getDeckZone().getZoneCards().get(0));
            gamePlayController.getCurrentPlayer().getDeckZone().removeCardFromDeckZone(gamePlayController.getCurrentPlayer().getDeckZone().getZoneCards().get(0));
        }
        gamePlayController.getCurrentPlayer().getMagicCardZone().moveCardToGraveyardWithoutAddress(potOfGreed, gamePlayController.getCurrentPlayer());

    }

    public Boolean checkRaigeki() {
        if (gamePlayController.getOpponentPlayer().getMonsterCardZone().getNumberOfCard() == 0)
            return false;
        else return true;
    }

    public void raigeki(MagicCard raigeki) {
        if (!doIt) {
            if (checkRaigeki()) gamePlayController.activateSelectedCard();
            return;
        }
        GamePlayController.getEffectController().destroyCards(Card.CardType.MONSTER, false);
        gamePlayController.getCurrentPlayer().getMagicCardZone().moveCardToGraveyardWithoutAddress(raigeki, gamePlayController.getCurrentPlayer());
    }

    public Boolean checkHarpiesFeatherDuster() {
        if (gamePlayController.getOpponentPlayer().getMagicCardZone().getNumberOfCard() == 0)
            return false;
        else return true;
    }

    public void harpiesFeatherDuster(MagicCard harpiesFeatherDuster) {
        if (!doIt) {
            if (checkHarpiesFeatherDuster()) gamePlayController.activateSelectedCard();
            return;
        }
        GamePlayController.getEffectController().destroyCards(Card.CardType.MAGIC, false);
        duelMenu.printResponse(EFFECT_DONE_SUCCESSFULLY);
        gamePlayController.getCurrentPlayer().getMagicCardZone().moveCardToGraveyardWithoutAddress(harpiesFeatherDuster, gamePlayController.getCurrentPlayer());
    }

    public Boolean checkDarkHole() {
        if (gamePlayController.getOpponentPlayer().getMonsterCardZone().getNumberOfCard() == 0 && gamePlayController.getCurrentPlayer().getMonsterCardZone().getNumberOfCard() == 0)
            return false;
        else return true;
    }

    public void darkHole(MagicCard darkHole) {
        if (!doIt) {
            if (checkDarkHole()) gamePlayController.activateSelectedCard();
            return;
        }
        GamePlayController.getEffectController().destroyCards(Card.CardType.MONSTER, true);
        duelMenu.printResponse(EFFECT_DONE_SUCCESSFULLY);
        gamePlayController.getCurrentPlayer().getMagicCardZone().moveCardToGraveyardWithoutAddress(darkHole, gamePlayController.getCurrentPlayer());
    }

    public Boolean checkMysticalSpaceTyphoon(Player ownerOfCard) {
        int num = gamePlayController.getTheOtherPlayer(ownerOfCard).getMagicCardZone().getNumberOfCard();
        num += ownerOfCard.getMagicCardZone().getNumberOfCard();
        num+= ownerOfCard.getFieldZone().getZoneCards().size();
        num+= gamePlayController.getTheOtherPlayer(ownerOfCard).getFieldZone().getZoneCards().size();
        if (num ==0)
            return false;
        else return true;
    }

    public void mysticalSpaceTyphoon(MagicCard mysticalSpaceTyphoon, Player ownerOfCard) {
        if (!doIt) {
            if (checkMysticalSpaceTyphoon(ownerOfCard) && !gamePlayController.isSpellSSetInThisTurn(mysticalSpaceTyphoon))
                gamePlayController.activateSelectedCard();
            return;
        }
        Map<Integer, MagicCard> map;
        Player player;
        duelMenu.printResponse(ENTER_PLAYER);
        String rivalOrNot = duelMenu.getString();
        if (rivalOrNot.equals("rival")) {
            player = gamePlayController.getTheOtherPlayer(ownerOfCard);
            map = gamePlayController.getTheOtherPlayer(ownerOfCard).getMagicCardZone().getZoneCards();
        } else {
            map = ownerOfCard.getMagicCardZone().getZoneCards();
            player = ownerOfCard;
        }
        duelMenu.printResponse(ENTER_ONE_NUMBER);
        while (true) {
            int num = duelMenu.getNum();
            if (num <= 5 && map.get(num) != null) {
                player.getMagicCardZone().moveCardToGraveyard(num, player);
                duelMenu.printResponse(EFFECT_DONE_SUCCESSFULLY);
                if(mysticalSpaceTyphoon.getCardPlacedZone()!=ownerOfCard.getGraveyardZone())
                ownerOfCard.getMagicCardZone().moveCardToGraveyardWithoutAddress(mysticalSpaceTyphoon, ownerOfCard);
                return;
            } else if (num == 6 && !player.getFieldZone().getZoneCards().isEmpty()) {
                player.getGraveyardZone().addCardToGraveyardZone(player.getFieldZone().getZoneCards().get(0));
                player.getFieldZone().getZoneCards().remove(0);
                duelMenu.printResponse(EFFECT_DONE_SUCCESSFULLY);
                ownerOfCard.getMagicCardZone().moveCardToGraveyardWithoutAddress(mysticalSpaceTyphoon, ownerOfCard);
            } else {
                duelMenu.printResponse(INVALID_CELL_NUMBER);
                duelMenu.printResponse(ENTER_ONE_NUMBER);
            }
        }
    }

    public boolean checkRingOfDefense() {
        if (!gamePlayController.getChainCards().isEmpty() && gamePlayController.getChainCards().get(gamePlayController.getChainCards().size() - 1).getCardName().equals("Magic Jammer"))
            return true;
        else return false;
    }
<<<<<<< Updated upstream
    public void ringOfDefense(MagicCard ringOfDefense)
    {   if(!doIt) {
        if( checkRingOfDefense() && !gamePlayController.isSpellSSetInThisTurn(ringOfDefense) )
            gamePlayController.activateSelectedCard();
        return;
    }
    if(gamePlayController.getChainCards().get(gamePlayController.getChainCards().size()-1).getCardName().equals("Magic Jammer")){
    gamePlayController.getChainCards().remove(gamePlayController.getChainCards().size()-1);
    gamePlayController.getChainPlayers().remove(gamePlayController.getChainCards().size()-1);
      duelMenu.printResponse(EFFECT_DONE_SUCCESSFULLY);}
    }

    public Boolean checkTwinTwisters(Player ownerOfCard) {
        if (( (ownerOfCard.getMagicCardZone().getNumberOfCard() + gamePlayController.getTheOtherPlayer(ownerOfCard).getMagicCardZone().getNumberOfCard()+ownerOfCard.getFieldZone().getZoneCards().size()+
        gamePlayController.getTheOtherPlayer(ownerOfCard).getFieldZone().getZoneCards().size()) <= 1) ||
                        ownerOfCard.getHand().getNumberOfCardsInHand()==0)
=======

    public void ringOfDefense(MagicCard ringOfDefense) {
        if (!doIt) {
            if (checkRingOfDefense() && !gamePlayController.isSpellSSetInThisTurn(ringOfDefense))
                gamePlayController.activateSelectedCard();
            return;
        }
        gamePlayController.getChainCards().remove(gamePlayController.getChainCards().size() - 1);
        gamePlayController.getChainPlayers().remove(gamePlayController.getChainCards().size() - 1);
    }

    public Boolean checkTwinTwisters(Player ownerOfCard) {
        if (ownerOfCard.getMagicCardZone().getNumberOfCard()
                + gamePlayController.getTheOtherPlayer(ownerOfCard).getMagicCardZone().getNumberOfCard() <= 1 &&
                ownerOfCard.getFieldZone().getZoneCards().get(0) != null &&
                gamePlayController.getTheOtherPlayer(ownerOfCard).getFieldZone().getZoneCards().get(0) != null)
>>>>>>> Stashed changes
            return false;
        else return true;
    }

    public void twinTwisters(MagicCard twinTwisters, Player ownerOfCard) {
        if (!doIt) {
            if (checkTwinTwisters(ownerOfCard) && !gamePlayController.isSpellSSetInThisTurn(twinTwisters))
                gamePlayController.activateSelectedCard();
            return;
        }
        int i = 0;
        duelMenu.printResponse(ENTER_ONE_NUMBER);  // of your hand !
        while (true) {
            String ans=duelMenu.getString();
            if(ans.equals("cancel")) {
                duelMenu.printResponse(CANCELED);
                return;
            }
            int num = Integer.parseInt(ans);
            if (ownerOfCard.getHand().getNumberOfCardsInHand() >= num) {
                ownerOfCard.getMagicCardZone().moveCardToGraveyardWithoutAddress(twinTwisters, ownerOfCard);
                break;
            } else {
                duelMenu.printResponse(INVALID_CELL_NUMBER);
                duelMenu.printResponse(ENTER_ONE_NUMBER);
            }
        }
        duelMenu.printResponse(ENTER_ONE_NUMBER);
        while (true) {
            String ans=duelMenu.getString();
            if(ans.equals("cancel")) {
                duelMenu.printResponse(CANCELED);
                return;
            }
            int num = Integer.parseInt(ans);
            if (num <= 5 && gamePlayController.getTheOtherPlayer(ownerOfCard).getMagicCardZone().getZoneCards().get(num) != null) {
                gamePlayController.getTheOtherPlayer(ownerOfCard).getMagicCardZone().moveCardToGraveyard(num, gamePlayController.getTheOtherPlayer(ownerOfCard));
                i++;
            } else if (num == 6 && !gamePlayController.getTheOtherPlayer(ownerOfCard).getFieldZone().getZoneCards().isEmpty()) {
                gamePlayController.getTheOtherPlayer(ownerOfCard).getGraveyardZone().addCardToGraveyardZone(gamePlayController.getTheOtherPlayer(ownerOfCard).getFieldZone().getZoneCards().get(0));
                gamePlayController.getTheOtherPlayer(ownerOfCard).getFieldZone().getZoneCards().remove(0);
                i++;
                if (i == 1) {
                    duelMenu.printResponse(WANNA_ADD_ANOTHER_CARD);
<<<<<<< Updated upstream
                    String answer =duelMenu.getString();
                    if (answer.equals("yes")) {
=======
                    String ans = duelMenu.getString();
                    if (ans.equals("yes")) {
>>>>>>> Stashed changes
                        duelMenu.printResponse(ENTER_ONE_NUMBER);
                        continue;
                    } else {
                        duelMenu.printResponse(EFFECT_DONE_SUCCESSFULLY);
<<<<<<< Updated upstream
                     ownerOfCard.getMagicCardZone().moveCardToGraveyardWithoutAddress(twinTwisters, ownerOfCard);
                        return;
                    }
                } else if (i == 2) {
                    duelMenu.printResponse(EFFECT_DONE_SUCCESSFULLY);
                  ownerOfCard.getMagicCardZone().moveCardToGraveyardWithoutAddress(twinTwisters, ownerOfCard);
                    return;
=======
                        ownerOfCard.getMagicCardZone().moveCardToGraveyardWithoutAddress(twinTwisters, ownerOfCard);
                        break;
                    }
                } else if (i == 2) {
                    duelMenu.printResponse(EFFECT_DONE_SUCCESSFULLY);
                    ownerOfCard.getMagicCardZone().moveCardToGraveyardWithoutAddress(twinTwisters, ownerOfCard);
                    break;
>>>>>>> Stashed changes
                }
            } else {
                duelMenu.printResponse(INVALID_CELL_NUMBER);
                duelMenu.printResponse(ENTER_ONE_NUMBER);
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
//        duelMenu.printResponse(EFFECT_DONE_SUCCESSFULLY);
//    }

    public void changeOfHeart(MagicCard magicCard) {
        if (!doIt) {
            if (checkChangeOfHeart()) gamePlayController.activateSelectedCard();
            return;
        }
        Map<Integer, MonsterCard> map = gamePlayController.getOpponentPlayer().getMonsterCardZone().getZoneCards();
        duelMenu.printResponse(ENTER_ONE_NUMBER);
        while (true) {
            int num = duelMenu.getNum();
            if (map.get(num) != null) {
                GamePlayController.getEffectController().getControl(map.get(num));
                duelMenu.printResponse(EFFECT_DONE_SUCCESSFULLY);
                gamePlayController.getCurrentPlayer().getMagicCardZone().moveCardToGraveyardWithoutAddress(magicCard, gamePlayController.getCurrentPlayer());
                return;
            } else {
                duelMenu.printResponse(INVALID_CELL_NUMBER);
                duelMenu.printResponse(ENTER_ONE_NUMBER);
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

    public void monsterReborn(MagicCard monsterReborn) {
        if (!doIt) {
            if (checkMonsterReborn()) gamePlayController.activateSelectedCard();
            return;
        }
        Player player;
        duelMenu.printResponse(ENTER_PLAYER);
        String rivalOrNot = duelMenu.getString();
        if (rivalOrNot.equals("cancel")) {
            duelMenu.printResponse(CANCELED);
            return;
        }
        if (rivalOrNot.equals("rival"))
            player = gamePlayController.getOpponentPlayer();
        else player = gamePlayController.getCurrentPlayer();
        duelMenu.printResponse(ENTER_ONE_NUMBER);
        while (true) {
            String ans = duelMenu.getString();
            if (rivalOrNot.equals("cancel")) {
                duelMenu.printResponse(CANCELED);
                return;
            }
            int num = Integer.parseInt(ans);
            if (num <= player.getGraveyardZone().getZoneCards().size() && (player.getGraveyardZone().getZoneCards().get(num - 1) instanceof MonsterCard)) {
                Card card = player.getGraveyardZone().getZoneCards().get(num - 1);
                player.getMonsterCardZone().summonOrSetMonster((MonsterCard) card, player);
                card.setHidden(false);
                duelMenu.printResponse(ENTER_POS);
                String answer = duelMenu.getString();
                if (answer.equals("ATK"))
                    ((MonsterCard) card).setMode(MonsterCard.Mode.ATTACK);
                else
                    ((MonsterCard) card).setMode(MonsterCard.Mode.DEFENSE);
                gamePlayController.getCurrentPlayer().getMagicCardZone().moveCardToGraveyardWithoutAddress(monsterReborn, gamePlayController.getCurrentPlayer());
                duelMenu.printResponse(EFFECT_DONE_SUCCESSFULLY);
                break;
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

    public boolean checkAdvancedRitualArt() {
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
            sum = sum + (getWholeMonsters(player)).get(out[i]).getLevel();
        }
        return sum == level;
    }

    public void showWholeMonsters(ArrayList<MonsterCard> monsterCards) {
        StringBuilder out = new StringBuilder();
        int i = 0;
        for (MonsterCard monsterCard : monsterCards) {
            out.append(i).append(" : ").append(monsterCard.getCardName()).append(" level : ").append(monsterCard.getLevel()).append("\n");
        }
        duelMenu.printString(out.toString());
    }


    public void advancedRitualArt(MagicCard advancedRitualArt) {
        if (!doIt) {
            if (checkAdvancedRitualArt()) gamePlayController.activateSelectedCard();
            return;
        }
        int[] out;
        if (getRitualMonsterOfHand(gamePlayController.getCurrentPlayer()) == null) {
            duelMenu.printString("you dont have ritual monster ...");
            return;
        }
        duelMenu.printString("you can ritual summon " + getRitualMonsterOfHand(gamePlayController.getCurrentPlayer()).getCardName() + "with level :" + getRitualMonsterOfHand(gamePlayController.getCurrentPlayer()).getLevel());
        showWholeMonsters(getWholeMonsters(gamePlayController.getCurrentPlayer()));
        while (true) {
            duelMenu.printResponse(ENTER_SOME_NUMBERS);
            String string = duelMenu.getString();
<<<<<<< Updated upstream
            if(string.equals("cancel")) {duelMenu.printResponse(CANCELED);
                return;}
=======
            if (string.equals("cancel")) {
                duelMenu.printString("canceled!");
                return;
            }
>>>>>>> Stashed changes
            if (!string.matches("(\\d+\\s)+\\d+"))
                duelMenu.printResponse(SHOULD_RIVAL_SUMMON_RIGHT_NOW);
            else {
                String[] nums = string.trim().split(" ");
                out = new int[nums.length];
                for (int i = 0; i < nums.length; i++) {
                    out[i] = Integer.parseInt(nums[i]);
                }
                if (checkSummon(out, gamePlayController.getCurrentPlayer(), getRitualMonsterOfHand(gamePlayController.getCurrentPlayer()).getLevel())) {
                    for (int j : out) {
                        gamePlayController.getCurrentPlayer().getGraveyardZone().addCardToGraveyardZone(getWholeMonsters(gamePlayController.getCurrentPlayer()).get(j));
                        gamePlayController.getCurrentPlayer().getDeckZone().getZoneCards().remove(getWholeMonsters(gamePlayController.getCurrentPlayer()).get(j));
                    }
                    duelMenu.printResponse(ENTER_POS);
                    String ans = duelMenu.getString();
                    if (ans.equals("ATK"))
                        getRitualMonsterOfHand(gamePlayController.getCurrentPlayer()).setMode(MonsterCard.Mode.ATTACK);
                    else
                        getRitualMonsterOfHand(gamePlayController.getCurrentPlayer()).setMode(MonsterCard.Mode.DEFENSE);
                    gamePlayController.getCurrentPlayer().getMonsterCardZone().summonOrSetMonster(getRitualMonsterOfHand(gamePlayController.getCurrentPlayer()), gamePlayController.getCurrentPlayer());
                    getRitualMonsterOfHand(gamePlayController.getCurrentPlayer()).setHidden(false);
                    duelMenu.printResponse(EFFECT_DONE_SUCCESSFULLY);
                    gamePlayController.getCurrentPlayer().getMagicCardZone().moveCardToGraveyardWithoutAddress(advancedRitualArt, gamePlayController.getCurrentPlayer());
                    break;
                } else {
                    duelMenu.printResponse(LEVELS_DONT_MATCH);
                }

            }
        }

    }
}




