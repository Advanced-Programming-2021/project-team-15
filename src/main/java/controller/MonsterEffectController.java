package controller;
import model.Card;

import java.util.ArrayList;
import java.util.List;


public class MonsterEffectController {
    private List<Card> cardsAffected;
    private Card effectCard;
    private int remainsTurn;

    public MonsterEffectController(Card effectCard) {
        cardsAffected = new ArrayList<>();
    }

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
