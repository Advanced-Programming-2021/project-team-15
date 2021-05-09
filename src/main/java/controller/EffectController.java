package controller;

import model.Card;
import model.MonsterCard;
import model.Player;

public class EffectController  extends GamePlayController {
//Magic















  ///  public void changeAttack(Card card, int amount) {
        // yami,forest,closedForest,umiruka,swordOfDarkDestruction,blackPendant,unitedWeStand,magnumShield
//        if (amount > 0) {
//            ((MonsterCard) card).addAttackPoint(amount);
//        } else {
//            ((MonsterCard) card).reduceAttackPoint(amount);
//        }

   // }

    public void changeDefence(Card card, int amount) {
//        // yami,forest,umiruka,swordOfDestruction,magnumShield,unitedWeStand
//        if (amount > 0) {
//            ((MonsterCard) card).addDefensePoint(amount);
//        } else {
//            ((MonsterCard) card).reduceDefensePoint(amount);
//        }

    }

    public void DestroyMonster(Player player, Card card) {
        //darkHole,raigeki,mirrorForce,trapHole,torrentialTribute,solemnWarning
//        player.getMonsterCardZone().removeCardFromMonsterCardZone((MonsterCard) card);
//        player.getGraveyardZone().addCardToGraveyardZone(card);
    }

    public void DestroyMagicCard(Player player, Card card) {
//        // twinTwisters,harpie'sFeatherDuster,mysticalSpaceTyphoon,magicJammer
//        player.getMagicCardZone().removeCardFromMagicCardZone((MagicCard) card);
//        player.getGraveyardZone().addCardToGraveyardZone(card);
    }

    public void addFieldSpellToHand(Player player) {
        //terraforming(میگرده تو دک دنبال کارت فیلد اسپل اولین چیزی  ک پیدا کرد رو ادد میکنه ب دست)
//        Card card= player.getDeckZone().searchForMagicCardByIcon(MagicCard.CardIcon.FIELD);
//        player.getHand().addCardToHand(card);

    }

    public void specialSummonFromGraveyard(Player player, Card card) {
//        //monsterReborn,callOfTheHaunted
//        player.getGraveyardZone().removeCardFromGraveyardZone(card);
//        player.getMonsterCardZone().summonOrSetMonster();
//    }

    public void drawCard(Player player, int amount, Card card) {
//        //potOfGreed,supplySquad,TimeSeal
//        for (int i = 0; i < amount; i++) {
//            player.getDeckZone().removeCardFromDeckZone(card);
//            player.getHand().addCardToHand(card);
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
//        if (amount > 0) {
//            ((Player) player).increaseLifePoint(amount);
//        } else {
//            ((Player) player).reduceLifePoint(amount);
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


    //monster effect

    public void changeAttack(Card card,int amount) {
// commandKnight(ب کارتای توی زمین خودمون ب حملشون 400 تا اضافه میکنه),
// suijin(وقتی ی کارت بهش حمله میکنه فقط یبار میشه از اثرش استفاده کرد و اینه ک حمله کارته رو برای همون نوبت صفر میکنه),
// scanner(یبار در هر نوبت یکی از هیولاهای حریف ک تو قبرستونه رو انتخاب میکنه و حمله و دفاعش رو کپی اون میکنه)
        //theCalculator(ب ازای کارتای رو ب بالا ضرب در 300 جمعشون میه حمله خودش)
    }
    public void changeDefense(Card card,int amount){
        //scanner,
    }

    public void destroyMonster() {
// yomiShip(کارتی ک بش حمله کنه رو نابود میکنه)
// ,manEaterBug,(اگ فلیپ شه ب هر صورتی،ی کارتی رو انتخاب میکنیم و نابودش میکنه)
    }

    public void beastKingBarbados(){
//میشه بدون قربانی کردن دو تا کارت احضرش کرد ولی حملش میشه 1900،میشه با قربانی 3 هیولا ب حالت رو اخضارش کرد اینطوری همه هیولاهای حریف ک تو زمینشن نابود میشن
    }
    public void marshmallon(){
        //تو حمله عادی نابود نمیشه،اگ یکی بش حمله کرد و رو ب پشت بود،از لایفپوینت حمله کننده 1000 تا کم میشه
    }
    public void specialSummonFromHandOrDeckOrGraveyard(String cardName){
// cyberse(ی بار تو هر نوبت ک بش حمله میشه حمله رو خنثی میکنه و ی کارت از نوع سایبر(leotron) از تو د یا دست یا قبرستون میاد اسپشال سامن میکنه)
    }
    public void mirageDragon(){
        //وقتی کارتش ب رو تو زمینه حریف هیچ کارت تله ای نمیتونه فعال کنه
//
    }
    public void heraldOfCreation(){
//یبار تو هرنوبت ی کارت از دست رو میندازیم بیرون و ی کارت سطح 7 یا بالاتر از قربستون ب دستمون اضافه میکنیم
    }
    public void exploderDragon(){
//اگ کارت تو حمله نابود شه،کارتی ک بش حمله کرده رو هم نابود میکنه و از لایفپوینت کسی چیزی کم نمیشه
    }
    public void TerraTiger(){
//اگ کارت ب صورت عادی احضار شده باشد صاحب کارت میتونه از دستش ی هیولای ی هیولای عادی سطح 4 یا پایین تر ب حالت دفاعی بذاره تو زمین
    }

}
