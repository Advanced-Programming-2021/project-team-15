package controller;

import model.Card;

import java.util.ArrayList;
import java.util.List;

public class MagicEffectController {
    private List<Card> cardsAffected;
    private Card effectCard;
    private int remainsTurn;

    public MagicEffectController(int gameControllerID, Card effectCard) {
        cardsAffected = new ArrayList<>();
    }

    public void changeAttack(Card card, int amount) {

    }

    public void changeDefence(Card card, int amount) {

    }

    public void changeAttackAndDefense(int amountAtk, int amountDef) {

    }

    public void DestroyMonster(String cardName) {

    }

    public void DestroyMagicCard(String cardName) {

    }

    public void addFieldSpellToHand() {

    }

    public void specialSummonFromGraveyard() {

    }

    public void drawCard(int amount) {

    }

    public void destroyOpponentMonsters() {
    }

    public void destroyBothSideMonsters() {

    }

    public void destroyOpponentMagicCards() {

    }

    public void swordsOfRevealingLight() {

    }

    public void changeLifePoint(int amount) {

    }

    public void messengerOfPeace() {

    }

    public void ringOfDefense() {
    }

    public void ritualSummon() {

    }

    public void magicCylinder() {

    }

    public void mindCrush() {
    }

    public void negateAttack() {
    }

}
