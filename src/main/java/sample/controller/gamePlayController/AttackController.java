package sample.controller.gamePlayController;

import sample.controller.responses.DuelMenuResponses;
import sample.model.Game;
import sample.model.Phase;
import sample.model.cards.Card;
import sample.model.cards.MonsterCard;
import sample.view.DuelMenu;

import java.io.IOException;
import java.util.ArrayList;

import static sample.controller.responses.DuelMenuResponses.*;

public class AttackController {
    private static int damage;

    private boolean isAttacking = false;
    private GamePlayController gamePlayController = GamePlayController.getInstance();
    private ArrayList<Card> cardsShouldBeUsedOnce = new ArrayList();
    private ArrayList<MonsterCard> cantBeAttacked = new ArrayList<>();
    private ArrayList<MonsterCard> attackedCardsInTurn = new ArrayList<>();

    public static int getDamage() {
        return damage;
    }

    public static void setDamage(int damage) {
        AttackController.damage = damage;
    }


    public ArrayList<MonsterCard> getCantBeAttacked() {
        return cantBeAttacked;
    }

    public void setCantBeAttacked(ArrayList<MonsterCard> cantBeAttacked) {
        this.cantBeAttacked = cantBeAttacked;
    }

    public ArrayList<MonsterCard> getAttackedCardsInTurn() {
        return attackedCardsInTurn;
    }

    public void setAttackedCardsInTurn(ArrayList<MonsterCard> attackedCardsInTurn) {
        this.attackedCardsInTurn = attackedCardsInTurn;
    }

    public DuelMenuResponses normalAttack(int number) throws IOException //TODO RESET CARD IN NEEDED PLACES
    {
        damage = 0;
        if (gamePlayController.getSelectedCard() == null)
            return DuelMenuResponses.NO_CARD_SELECTED;
        else if (!((MonsterCard) gamePlayController.getSelectedCard()).toStringPosition().equals("OO")
                || !(gamePlayController.getSelectedCard() instanceof MonsterCard)
                || (gamePlayController.getSelectedCard().getCardPlacedZone() != gamePlayController.getCurrentPlayer().getMonsterCardZone()))
            return YOU_CANT_ATTACK_WITH_THIS_CARD;
        else if (Game.getPhases().get(gamePlayController.getCurrentPhaseNumber()) != Phase.PhaseLevel.BATTLE)
            return DuelMenuResponses.CANT_DO_THIS_ACTION_IN_THIS_PHASE;
        else if (alreadyAttackedThisTurn((MonsterCard) gamePlayController.getSelectedCard()))
            return DuelMenuResponses.ALREADY_ATTACKED;
        else if (gamePlayController.getOpponentPlayer().getMonsterCardZone().getCardByPlaceNumber(number) == null)
            return DuelMenuResponses.NO_CARD_TO_ATTACK;
        String position = gamePlayController.getOpponentPlayer().getMonsterCardZone().getCardByPlaceNumber(number).toStringPosition();
        MonsterCard target = gamePlayController.getOpponentPlayer().getMonsterCardZone().getCardByPlaceNumber(number);
        MonsterCard attacker = (MonsterCard) gamePlayController.getSelectedCard();
        if (target.getCardName().equals("Command Knight") && target.isActivated() && gamePlayController.getOpponentPlayer().getMonsterCardZone().getNumberOfCard() >= 2)
            return CANT_ATTACK_TO_THIS_CARD;
        if (target.getCardName().equals("Texchanger") && !haveBeenUsed(target)) {
            cardsShouldBeUsedOnce.add(target);
            GamePlayController.getMonsterEffectController().textChanger(target);
            return EFFECT_DONE_SUCCESSFULLY;
        }
        isAttacking = true;
        checkerForEffects();
        if (!isAttacking)
            return ATTACK_CANCELED;
        if (position.equals("OO"))
            return attackTOAttackPos(attacker, target, number);
        else if (position.equals("DO"))
            return attackToDefencePos(attacker, target, number, false);
        else { gamePlayController.getDuelMenu().flipMonster(number-1,target, MonsterCard.Mode.DEFENSE);
            return attackToDefencePos(attacker, target, number, true);
        }
    }

    public void checkerForEffects() throws IOException {
        if (gamePlayController.ifPlayerHasThisCardGiveIt(gamePlayController.getOpponentPlayer(), "Magic Cylinder") != null) {
            gamePlayController.changeTurn();
            GamePlayController.getInstance().getDuelMenu().getInstance().doYouWannaActivateSpecialCard("Magic Cylinder");
            if (getAnswer("Magic Cylinder")) {
                GamePlayController.getTrapEffectController().magicCylinder(gamePlayController.ifPlayerHasThisCardGiveIt(gamePlayController.getCurrentPlayer(), "Magic Cylinder"));
                isAttacking = false;
            }
            gamePlayController.changeTurn();
        }
        if (gamePlayController.ifPlayerHasThisCardGiveIt(gamePlayController.getOpponentPlayer(), "Mirror Force") != null) {
            gamePlayController.changeTurn();
            GamePlayController.getInstance().getDuelMenu().getInstance().doYouWannaActivateSpecialCard("Mirror Force");
            if (getAnswer("Mirror Force")) {
                GamePlayController.getTrapEffectController().mirrorForce(gamePlayController.ifPlayerHasThisCardGiveIt(gamePlayController.getCurrentPlayer(), "Mirror Force"));
                isAttacking = false;
            }
            gamePlayController.changeTurn();
        }
        if (gamePlayController.ifPlayerHasThisCardGiveIt(gamePlayController.getOpponentPlayer(), "Negate Attack") != null) {
            gamePlayController.changeTurn();
            if (getAnswer("Negate Attack")) {
                GamePlayController.getTrapEffectController().negateAttack(gamePlayController.ifPlayerHasThisCardGiveIt(gamePlayController.getCurrentPlayer(), "Negate Attack"));
                isAttacking = false;
            }
            gamePlayController.changeTurn();
        }
    }

    public Boolean getAnswer(String name) {
        String ans = gamePlayController.getDuelMenu().yesNoQuestionAlert("do you wanna activate your " + name + " ?");
        if (ans.equals("yes"))
            return true;
        else return false;
    }

    public DuelMenuResponses attackToDefencePos(MonsterCard attacker, MonsterCard target, int number, Boolean hidden) throws IOException {
        attackedCardsInTurn.add(attacker);
        target.setHidden(false);
        int difference = attacker.getGameATK() - target.getGameDEF();
        damage = difference;
        if (hidden) {
            if (target.getCardName().equals("Command Knight") && !target.isActivated())
                GamePlayController.getMonsterEffectController().commandKnight(true, target);
            if (target.getCardName().equals("Man-Eater Bug"))
                GamePlayController.getMonsterEffectController().manEaterBug(target);
            if (target.getCardName().equals("The Calculator") && !target.isActivated())
                GamePlayController.getMonsterEffectController().theCalculator(target);
        }
        if (difference > 0) {
            if (target.getCardName().equals("Yomi Ship"))
                GamePlayController.getMonsterEffectController().yomiShip(attacker, target);
            if (target.getCardName().equals("Marshmallon")) {
                if (hidden) {
                    GamePlayController.getInstance().getDuelMenu().getInstance().lifePointReduced(1000);
                    gamePlayController.getCurrentPlayer().reduceLifePoint(1000);
                }
                return THIS_CARD_CANT_BE_DESTROYED;
            }
            if (target.getCardName().equals("Exploder Dragon")) {
                gamePlayController.getCurrentPlayer().getMonsterCardZone().moveCardToGraveyardWithoutAddress(attacker, gamePlayController.getCurrentPlayer());
                GamePlayController.getInstance().getDuelMenu().getInstance().printString("attacker destroyed by Exploder Dragon effect!");
            }
            gamePlayController.getOpponentPlayer().getMonsterCardZone().moveCardToGraveyard(number, gamePlayController.getOpponentPlayer());
            if (!hidden)
                return DuelMenuResponses.DEFENCE_POSITION_MONSTER_DESTROYED;
            else GamePlayController.getInstance().getDuelMenu().getInstance().hiddenDefensePositionMonsterDestroyed(target.getCardName());
        } else if (difference == 0) {
            if (!hidden)
                return DuelMenuResponses.NO_CARD_DESTROYED;
            else GamePlayController.getInstance().getDuelMenu().getInstance().hiddenDefensePosNoCardDestroyed(target.getCardName());
        } else {
            gamePlayController.getCurrentPlayer().reduceLifePoint(-difference);
            if (!hidden)
                return DuelMenuResponses.NO_CARD_DESTROYED_CURRENT_DAMAGED;
            else GamePlayController.getInstance().getDuelMenu().getInstance().hiddenDefensePosNoCardDestroyedWithDamage(target.getCardName());
        }
        return MONSTER_CARD_POSITION_CHANGED_SUCCESSFULLY;
    }

    public DuelMenuResponses attackTOAttackPos(MonsterCard attacker, MonsterCard target, int number) throws IOException {
        target.setHidden(false);
        int difference = attacker.getGameATK() - target.getGameATK();
        damage = difference;
        if (difference > 0) {
            attackedCardsInTurn.add(attacker);
            if (target.getCardName().equals("Exploder Dragon")) {
                gamePlayController.getCurrentPlayer().getMonsterCardZone().moveCardToGraveyardWithoutAddress(attacker, gamePlayController.getCurrentPlayer());
                GamePlayController.getInstance().getDuelMenu().getInstance().printString("attacker destroyed by Exploder Dragon effect!");
                gamePlayController.getOpponentPlayer().getMonsterCardZone().moveCardToGraveyard(number, gamePlayController.getOpponentPlayer());
                return EFFECT_DONE_SUCCESSFULLY;
            }
            if (target.getCardName().equals("Yomi Ship"))
                GamePlayController.getMonsterEffectController().yomiShip(attacker, target);
            gamePlayController.getOpponentPlayer().reduceLifePoint(difference);
            if (target.getCardName().equals("Marshmallon")) {
                return THIS_CARD_CANT_BE_DESTROYED;
            }
            gamePlayController.getOpponentPlayer().getMonsterCardZone().moveCardToGraveyard(number, gamePlayController.getOpponentPlayer());
            return DuelMenuResponses.DESTROYED_OPPONENT_MONSTER_AND_OPPONENT_RECEIVED_DAMAGE;
        } else if (difference == 0) {
            if (target.getCardName().equals("Marshmallon")) {
                gamePlayController.getCurrentPlayer().getMonsterCardZone().moveCardToGraveyardWithoutAddress(attacker, gamePlayController.getCurrentPlayer());
                return THIS_CARD_CANT_BE_DESTROYED;
            }
            gamePlayController.getOpponentPlayer().getMonsterCardZone().moveCardToGraveyard(number, gamePlayController.getOpponentPlayer());
            gamePlayController.getCurrentPlayer().getMonsterCardZone().moveCardToGraveyardWithoutAddress(attacker, gamePlayController.getCurrentPlayer());
            return DuelMenuResponses.BOTH_MONSTERS_ARE_DESTROYED;
        } else {
            gamePlayController.getCurrentPlayer().reduceLifePoint(-difference);
            gamePlayController.getCurrentPlayer().getMonsterCardZone().moveCardToGraveyardWithoutAddress(attacker, gamePlayController.getCurrentPlayer());
            return DuelMenuResponses.DESTROYED_CURRENT_MONSTER_AFTER_ATTACK;
        }

    }

    public boolean alreadyAttackedThisTurn(MonsterCard card) {
        for (MonsterCard monsterCard : attackedCardsInTurn) {
            if (card == monsterCard) {
                return true;
            }
        }
        return false;
    }

    public DuelMenuResponses directAttack() throws IOException {
        if (gamePlayController.getSelectedCard() == null)
            return DuelMenuResponses.NO_CARD_SELECTED;
        if (!(gamePlayController.getSelectedCard() instanceof MonsterCard)) return YOU_CANT_ATTACK_WITH_THIS_CARD;
        MonsterCard attacker = (MonsterCard) gamePlayController.getSelectedCard();
        if (attacker.getCardPlacedZone() != gamePlayController.getCurrentPlayer().getMonsterCardZone())
            return YOU_CANT_ATTACK_WITH_THIS_CARD;
        if (Game.getPhases().get(gamePlayController.getCurrentPhaseNumber()) != Phase.PhaseLevel.BATTLE)
            return DuelMenuResponses.CANT_DO_THIS_ACTION_IN_THIS_PHASE;
        else if (attacker.getMode() != MonsterCard.Mode.ATTACK) return NOT_IN_ATTACK_POSITION;
        else if (alreadyAttackedThisTurn(attacker))
            return DuelMenuResponses.ALREADY_ATTACKED;
        else if (!gamePlayController.getOpponentPlayer().getMonsterCardZone().getZoneCards().isEmpty())
            return CANT_ATTACK_DIRECTLY;
        attackedCardsInTurn.add(attacker);
        isAttacking = true;
        checkerForEffects();
        if (!isAttacking)
            return ATTACK_CANCELED;
        gamePlayController.getOpponentPlayer().reduceLifePoint((attacker).getGameATK());
        return YOUR_OPPONENT_DAMAGED_DIRECT_ATTACK;
    }


    public GamePlayController getGamePlayController() {
        return gamePlayController;
    }

    public void setGamePlayController(GamePlayController gamePlayController) {
        this.gamePlayController = gamePlayController;
    }

    public Boolean checkCanBeDestroyed() {  //MASHAMLLON}
        return true;
    }

    public Boolean canBeaAttacked() {
        //COMMAND KNIGHT
        return true;
    }

    public Boolean haveBeenUsed(Card card) {
        for (Card card1 : cardsShouldBeUsedOnce) {
            if (card1 == card)
                return true;
        }
        return false;
    }

    public boolean isAttacking() {
        return isAttacking;
    }

    public void setAttacking(boolean attacking) {
        isAttacking = attacking;
    }

    public ArrayList<Card> getCardsShouldBeUsedOnce() {
        return cardsShouldBeUsedOnce;
    }

    public void setCardsShouldBeUsedOnce(ArrayList<Card> cardsShouldBeUsedOnce) {
        this.cardsShouldBeUsedOnce = cardsShouldBeUsedOnce;
    }
}
