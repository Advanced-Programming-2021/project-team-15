package controller;

import model.Card;
import model.MagicCard;
import model.MonsterCard;
import model.Player;
import utility.Utility;
import view.DuelMenu;

import java.util.ArrayList;
import java.util.Map;

import static controller.responses.DuelMenuResponses.*;

public class MonsterEffectController {
    GamePlayController gamePlayController  =  GamePlayController.getInstance();
    EffectController effectController  = gamePlayController.getEffectController();
    AttackController attackController  = gamePlayController.getAttackController();
     DuelMenu duelMenu = DuelMenu.getInstance();

    public void CommandKnight(Boolean x,MonsterCard commandKnight)  //flip and change pos to up
    {   if(commandKnight.isActivated())  //?
        return;
        int amount = 400;
        if(!x)
            amount = -amount;
        Map<Integer, MonsterCard> map = gamePlayController.getCurrentPlayer().getMonsterCardZone().getZoneCards();
        for (Map.Entry<Integer, MonsterCard> entry : map.entrySet()) {
            if (entry.getValue() != null && entry.getValue()!=gamePlayController.getSelectedCard())
                entry.getValue().setGameATK(entry.getValue().getGameATK() + amount);
        }
    if(gamePlayController.getCurrentPlayer().getMonsterCardZone().getNumberOfCard()>=2)
        gamePlayController.getAttackController().getCantBeAttacked().add((MonsterCard) gamePlayController.getSelectedCard());
    }

    public void commandKnightForNewAddedCard(MonsterCard monsterCard)
    {  monsterCard.setGameATK(monsterCard.getGameATK()+400);
    }
    public void     YomiShip(int num)  //ATK
    {   MonsterCard  yomiShip = (MonsterCard) gamePlayController.getOpponentPlayer().getMonsterCardZone().getZoneCards().get(num);
        if(yomiShip.isActivated())
            return;
        yomiShip.setActivated(true);
        gamePlayController.getCurrentPlayer().getMonsterCardZone().moveCardToGraveyardWithoutAddress(gamePlayController.getSelectedCard(), gamePlayController.getCurrentPlayer());
        duelMenu.printResponse(EFFECT_DONE_SUCCESSFULLY);
    }
    public void  Suijin(int num)  //ATK
    { MonsterCard  suijin = (MonsterCard) gamePlayController.getOpponentPlayer().getMonsterCardZone().getZoneCards().get(num);
        if(suijin.isActivated())
            return;
        suijin.setActivated(true);
        gamePlayController.getSuijinVictims().put((MonsterCard) gamePlayController.getSelectedCard(),((MonsterCard) gamePlayController.getSelectedCard()).getGameATK());
        ((MonsterCard) gamePlayController.getSelectedCard()).setGameATK(0);
        duelMenu.printResponse(EFFECT_DONE_SUCCESSFULLY);
    }

    public void ManEaterBug(MonsterCard manEaterBug)  //flip or change pos from down to up
    {  if(manEaterBug.isActivated())
        return;
        manEaterBug.setActivated(true);
        Player player  = effectController.getOwnerOfMonster(manEaterBug);
        Player victim;
        if(player== gamePlayController.getCurrentPlayer())
           victim = gamePlayController.getOpponentPlayer();
        else victim = gamePlayController.getCurrentPlayer();
        duelMenu.printResponse(ENTER_ONE_NUMBER);
        while (true)
        {  int  num =  Integer.parseInt(Utility.getNextLine());
           if(victim.getMonsterCardZone().getZoneCards().get(num) != null)
           {  victim.getMonsterCardZone().moveCardToGraveyard(num, victim);
               duelMenu.printResponse(EFFECT_DONE_SUCCESSFULLY);
           }else {
               duelMenu.printResponse(INVALID_CELL_NUMBER);
               duelMenu.printResponse(ENTER_ONE_NUMBER);
           }
        }
    }
    public void GateGuardian()   //summon or set
    {  if(gamePlayController.getSelectedCard().isActivated())
        return;
        if(gamePlayController.getCurrentPlayer().getMonsterCardZone().getNumberOfCard()<3)
        {  duelMenu.printResponse(NO_WAY_TO_RITUAL_SUMMON);
            return;
        }
        gamePlayController.getSelectedCard().setActivated(true);
        chooseThreeMonsterAndTribute();
    }


    public void Scanner(){

    }

    public void Marshmallon(Boolean hidden){
        if(hidden)
            gamePlayController.getCurrentPlayer().reduceLifePoint(1000);

    }
    public void BeastKingBarbaros(Boolean isSummon)
    {   if(isSummon) {
        duelMenu.printResponse(DO_YOU_WANNA_TRIBUTE);
        String string = Utility.getNextLine();;
        if(string.equals("yes")) {
            chooseThreeMonsterAndTribute();
            for(int i =  1; i<=5 ; i++)
            {   gamePlayController.getOpponentPlayer().getMagicCardZone().moveCardToGraveyard(i , gamePlayController.getOpponentPlayer());
                gamePlayController.getOpponentPlayer().getMonsterCardZone().moveCardToGraveyard(i , gamePlayController.getOpponentPlayer());
            }
        }
        else {
            ((MonsterCard)gamePlayController.getSelectedCard()).setGameATK(1900);
            gamePlayController.doSummon();
            gamePlayController.setSelectedCard(null);
            duelMenu.printResponse(EFFECT_DONE_SUCCESSFULLY);
        }
    }else {
        ((MonsterCard)gamePlayController.getSelectedCard()).setGameATK(1900);
        gamePlayController.doSummon();
        gamePlayController.setSelectedCard(null);
        duelMenu.printResponse(EFFECT_DONE_SUCCESSFULLY);
    } }





    public void chooseThreeMonsterAndTribute()
    {  int i = 0;
        duelMenu.printResponse(ENTER_ONE_NUMBER);
        while (true)
        {
            int num = Integer.parseInt(Utility.getNextLine());
            if(gamePlayController.getCurrentPlayer().getMonsterCardZone().getZoneCards().get(num)==null)
                duelMenu.printResponse(ENTER_ONE_NUMBER);
            else {
                gamePlayController.getCurrentPlayer().getMonsterCardZone().moveCardToGraveyard(num, gamePlayController.getCurrentPlayer());
                i++;
                if(i==3) {duelMenu.printResponse(EFFECT_DONE_SUCCESSFULLY);
                    return;}
                duelMenu.printResponse(ENTER_ONE_NUMBER);
            }
        }
    }
    public void TextChanger(MonsterCard textChanger)
    {   if (attackController.haveBeenAttackStopper(textChanger))
        return ;
        attackController.getAttackStoppersInTurn().add(textChanger);
        gamePlayController.changeTurn();
        duelMenu.printResponse(DO_YOU_WANT_SUMMON_NORMAL_CYBERSE_CARD);
        String string = Utility.getNextLine();
        if(string.equals("no"))
            return;                 // OR WE CAN DONT CHANGE TURN AND USER GET OWNER OF .. METHOD
        if(isNormalCyberseExists(gamePlayController.getCurrentPlayer().getHand().getZoneCards()))
            checkFindAskTextChanger(gamePlayController.getCurrentPlayer().getHand().getZoneCards());
        else if(isNormalCyberseExists(gamePlayController.getCurrentPlayer().getDeckZone().getZoneCards()))
            checkFindAskTextChanger(gamePlayController.getCurrentPlayer().getDeckZone().getZoneCards());
        else if(isNormalCyberseExists(gamePlayController.getCurrentPlayer().getGraveyardZone().getZoneCards()))
            checkFindAskTextChanger(gamePlayController.getCurrentPlayer().getGraveyardZone().getZoneCards());
}
public void theCalculator(MonsterCard calculator)      //after being added to hand ?
{  int sum= 0 ;
    for (int i = 1; i<=5; i++)
    {  if(gamePlayController.getCurrentPlayer().getMonsterCardZone().getZoneCards().get(i)!= null)
        sum += ((MonsterCard)gamePlayController.getCurrentPlayer().getMonsterCardZone().getZoneCards().get(i)).getLevel();
    }
calculator.setGameATK(sum*300);
}


//BE ROO DAR ZAMIN BASHAD MirageDragon()
public void MirageDragon(Card card)
{   if(card.getCardName().equals("Mirage Dragon"))
      gamePlayController.getOpponentPlayer().getLimits().add(Limit.CANT_ACTIVATE_TRAP);
}
















public Boolean isNormalCyberseExists(ArrayList<Card> zoneCards)
{
    for(Card card : zoneCards)
    {
        if( card != null && (card instanceof MonsterCard) && ((MonsterCard) card).getMonsterType()== MonsterCard.MonsterType.CYBER
                && ((MonsterCard) card).getMonsterEffectType()== MonsterCard.MonsterEffectType.NORMAL)
        {  return true;
        } }
   return false;
}



public void checkFindAskTextChanger(ArrayList<Card> zoneCards)
{ duelMenu.printResponse(ENTER_ONE_NUMBER);
    while (true) {
        int num= Integer.parseInt(Utility.getNextLine());
        Card card= zoneCards.get(num-1);
        if(card==null || !(card instanceof MonsterCard) || ((MonsterCard) card).getMonsterType()!= MonsterCard.MonsterType.CYBER
                || ((MonsterCard) card).getMonsterEffectType()!= MonsterCard.MonsterEffectType.NORMAL)
        {
            duelMenu.printResponse(INVALID_CELL_NUMBER);
            duelMenu.printResponse(ENTER_ONE_NUMBER);
        }else {
            gamePlayController.getCurrentPlayer().getMonsterCardZone().summonOrSetMonster((MonsterCard) card ,gamePlayController.getCurrentPlayer());
            duelMenu.printResponse(ENTER_POS);
            String ans = Utility.getNextLine();
            if (ans.equals("ATK"))
                ((MonsterCard)card).setMode(MonsterCard.Mode.ATTACK);
            else
                ((MonsterCard)card).setMode(MonsterCard.Mode.DEFENSE);
            duelMenu.printResponse(EFFECT_DONE_SUCCESSFULLY);
            gamePlayController.changeTurn();
        }
    }
}
public void doHeraldOfCreation()
{     duelMenu.printResponse(ENTER_ONE_NUMBER);
    while (true)
    {   int n = Integer.parseInt(Utility.getNextLine());
        Card card=gamePlayController.getCurrentPlayer().getHand().getZoneCards().get(n-1);
        if(card != null)
        {   gamePlayController.getCurrentPlayer().getHand().removeCardFromHand(card);
            gamePlayController.getCurrentPlayer().getGraveyardZone().addCardToGraveyardZone(card);
            duelMenu.printResponse(ENTER_ONE_NUMBER);
            while (true)
            {int num = Integer.parseInt(Utility.getNextLine());
                if(gamePlayController.getCurrentPlayer().getGraveyardZone().getZoneCards().get(num-1) != null &&
                        ( gamePlayController.getCurrentPlayer().getGraveyardZone().getZoneCards().get(num-1) instanceof MonsterCard) &&
                        ((MonsterCard) gamePlayController.getCurrentPlayer().getGraveyardZone().getZoneCards().get(num-1)).getLevel()>=7)
                {
                    MonsterCard monsterCard = ((MonsterCard) gamePlayController.getCurrentPlayer().getGraveyardZone().getZoneCards().get(num-1));
                    gamePlayController.getCurrentPlayer().getGraveyardZone().removeCardFromGraveyardZone(monsterCard);
                    gamePlayController.getCurrentPlayer().getHand().addCardToHand(monsterCard);
                    duelMenu.printResponse(EFFECT_DONE_SUCCESSFULLY);
                }
                else {  duelMenu.printResponse(INVALID_CELL_NUMBER);
                    duelMenu.printResponse(ENTER_ONE_NUMBER);
                }
            }

        }
        else {
            duelMenu.printResponse(INVALID_CELL_NUMBER);
            duelMenu.printResponse(ENTER_ONE_NUMBER);
        }
    }

}
public Boolean HeraldOfCreation(){

       for (Card card : gamePlayController.getCurrentPlayer().getGraveyardZone().getZoneCards())
       {
           if(card!=null && (card instanceof MonsterCard) && ((MonsterCard) card).getLevel()>=7  && gamePlayController.getCurrentPlayer().getHand().getNumberOfCardsInHand()!=0)
               return true;
       }
       return false;
}


public void ExploderDragon()
{
   gamePlayController.getCurrentPlayer().getMonsterCardZone().moveCardToGraveyardWithoutAddress(gamePlayController.getSelectedCard(), gamePlayController.getCurrentPlayer());
    gamePlayController.getCurrentPlayer().getLimits().add(Limit.DONT_DECREASE_LP);
    gamePlayController.getOpponentPlayer().getLimits().add(Limit.DONT_DECREASE_LP);
}

public Boolean TerratigertheEmpoweredWarrior()
{  for (Card card : gamePlayController.getCurrentPlayer().getHand().getZoneCards())
{  if(card!= null && (card instanceof MonsterCard) && ((MonsterCard) card).getLevel()<=4 && ((MonsterCard) card).getMonsterEffectType()== MonsterCard.MonsterEffectType.NORMAL)
     return true;
}
return false;
}

public Boolean doTerratigertheEmpoweredWarrior()
{   duelMenu.printResponse(ENTER_ONE_NUMBER);
    while (true)
    {
        int num = Integer.parseInt(Utility.getNextLine());
        Card card= gamePlayController.getCurrentPlayer().getHand().getZoneCards().get(num-1);
        if(card!= null&& (card instanceof MonsterCard ) && ((MonsterCard) card).getLevel()<=4 && ((MonsterCard) card).getMonsterEffectType()== MonsterCard.MonsterEffectType.NORMAL)
        { gamePlayController.getCurrentPlayer().getMonsterCardZone().summonOrSetMonster((MonsterCard) card, gamePlayController.getCurrentPlayer());
            ((MonsterCard) card).setMode(MonsterCard.Mode.DEFENSE);
            duelMenu.printResponse(EFFECT_DONE_SUCCESSFULLY); }
        else {   duelMenu.printResponse(INVALID_CELL_NUMBER);
                  duelMenu.printResponse(ENTER_ONE_NUMBER);
        }
    }
}

public void TheTricky(MonsterCard theTricky)    //WHEN CAN I CALL ?
{  duelMenu.printResponse(ENTER_ONE_NUMBER);
    if(gamePlayController.getCurrentPlayer().getMonsterCardZone().getNumberOfCard()==5)
    {
        duelMenu.printResponse(MONSTER_ZONE_IS_FULL);
        return;
    }
    while (true)
    {
        int num = Integer.parseInt(Utility.getNextLine());
        Card card = gamePlayController.getCurrentPlayer().getHand().getZoneCards().get(num-1);
        if(card!=null)
        {
            gamePlayController.getCurrentPlayer().getHand().removeCardFromHand(card);
            gamePlayController.getCurrentPlayer().getGraveyardZone().addCardToGraveyardZone(card);
            gamePlayController.getCurrentPlayer().getMonsterCardZone().summonOrSetMonster(theTricky, gamePlayController.getCurrentPlayer());
            theTricky.setHidden(false);
            duelMenu.printResponse(ENTER_POS);  //special summon?
            String ans = Utility.getNextLine();
            if (ans.equals("ATK"))
               theTricky.setMode(MonsterCard.Mode.ATTACK);
            else
              theTricky.setMode(MonsterCard.Mode.DEFENSE);
            duelMenu.printResponse(EFFECT_DONE_SUCCESSFULLY);
        } else {
            duelMenu.printResponse(INVALID_CELL_NUMBER);
            duelMenu.printResponse(ENTER_ONE_NUMBER);
        }
    }
}






}















