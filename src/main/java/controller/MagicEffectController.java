package controller;

import model.Card;
import model.MonsterCard;

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
        if(amount>0){
            ((MonsterCard)card).addAttackPoint(amount);
        }
        else{
            ((MonsterCard)card).reduceAttackPoint(amount);
        }

    }

    public void changeDefence(Card card, int amount) {
        if(amount>0){
            ((MonsterCard)card).addDefensePoint(amount);
        }
        else{
            ((MonsterCard)card).reduceDefensePoint(amount);
        }

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
