package controller;

import model.*;

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
        // yami,forest,closedForest,umiruka,swordOfDarkDestruction,blackPendant,unitedWeStand,magnumShield
        if (amount > 0) {
            ((MonsterCard) card).addAttackPoint(amount);
        } else {
            ((MonsterCard) card).reduceAttackPoint(amount);
        }

    }

    public void changeDefence(Card card, int amount) {
        // yami,forest,umiruka,swordOfDestruction,magnumShield,unitedWeStand
        if (amount > 0) {
            ((MonsterCard) card).addDefensePoint(amount);
        } else {
            ((MonsterCard) card).reduceDefensePoint(amount);
        }

    }

    public void DestroyMonster(Player player, Card card) {
        //darkHole,raigeki,mirrorForce,trapHole,torrentialTribute,solemnWarning
        player.getMonsterCardZone().removeCardFromMonsterCardZone((MonsterCard) card);
        player.getGraveyardZone().addCardToGraveyardZone(card);
    }

    public void DestroyMagicCard(Player player, Card card) {
        // twinTwisters,harpie'sFeatherDuster,mysticalSpaceTyphoon,magicJammer
        player.getMagicCardZone().removeCardFromMagicCardZone((MagicCard) card);
        player.getGraveyardZone().addCardToGraveyardZone(card);
    }

    public void addFieldSpellToHand(Player player) {
        //terraforming(میگرده تو دک دنبال کارت فیلد اسپل اولین چیزی  ک پیدا کرد رو ادد میکنه ب دست)
       Card card= player.getDeckZone().searchForMagicCardByIcon(MagicCard.CardIcon.FIELD);
        player.getHand().addCardToHand(card);

    }

    public void specialSummonFromGraveyard(Player player, Card card) {
        //monsterReborn,callOfTheHaunted
        player.getGraveyardZone().removeCardFromGraveyardZone(card);
        player.getMonsterCardZone().summonOrSetMonster();
    }

    public void drawCard(Player player, int amount, Card card) {
        //potOfGreed,supplySquad,TimeSeal(حریف تو نوبت بعد نتونه کارت برداره)
        for (int i = 0; i < amount; i++) {
            player.getDeckZone().removeCardFromDeckZone(card);
            player.getHand().addCardToHand(card);
        }
    }


    public void swordsOfRevealingLight() {
//برا 3 نوبت حریف نمیتونه حمله کنه و کارتای رو ب پشتش ب حالت رو در میان
    }

    public void changeLifePoint(Player player, int amount) {
        //messengerOfPeace
        //magicCylinder(اگ حریف بهمون با یه هیولا حمله کنه و اینو رو کنیم،به ازای اتک هیولا از لایپوینت حریف کم میشه)
        //solemnWarning(اینطوری ک حریف اگ خواست یجوری کارت احضار کنه حالا ب هر روشی،اینو رو کنیم 2000 تا لایف پوینت میدیم احضارو کنسل میکنیم و کارته رو نابود میکنیم)
        //spellAbsorbtion(اینطوریه ک هروقت ی اسپل کارت فعال بشه چ از طرف ما بوده باشه چ ا طرف حریف، به لایفپوینت ما اضافه میشه)
        if (amount > 0) {
            ((Player) player).increaseLifePoint(amount);
        } else {
            ((Player) player).reduceLifePoint(amount);
        }
    }

    public void messengerOfPeace() {

    }

    public void ringOfDefense() {
        //اینطوری ک اثر مخرب ک ب لایفپوینت وارد شده از طریق کارت تله رو ب صفر میرسونه
    }

    public void ritualSummon() {
// برا احضار آیننی استفاده میشه
    }

    public void magicCylinder() {
//
    }

    public void mindCrush() {
        //یه کارت اسم میبریم اگ اون کارت تو دست حریف بود هرچی ازش داره تو دستش رو کنار میذاره و اگه نبود ما باید ی کارتو بطور رندم از دستمون بیرون بندازیم
    }

    public void negateAttack() {
        // حریف اگ حمله کنه میاد اثر حمله رو خنثی میکنه و کل فاز بتل رو تموم میکنه
    }

}
