package controller;

import controller.responses.DuelMenuResponses;
import model.*;
import view.DuelMenu;

import java.util.ArrayList;

import static controller.responses.DuelMenuResponses.*;

public class GamePlayController extends MenuController{
    private static Game game;

    private int currentPhaseNumber =1;
    private static Card selectedCard;
    public static Card summonedCard;
    public static Card setCard;
    private  Player currentPlayer;
    private  Player opponentPlayer;
    protected ArrayList<Card> changedPositionCardsInTurn = new ArrayList<>();

    public GamePlayController() {
        super("Duel Menu");
    }


    public static Game getGame() {
        return game;
    }

    public static void setGame(Game game) {
        GamePlayController.game = game;
    }

    public static Card getSummonedCard() {
        return summonedCard;
    }

    public static void setSummonedCard(Card summonedCard) {
        GamePlayController.summonedCard = summonedCard;
    }

    public static Card getSetCard() {
        return setCard;
    }

    public static void setSetCard(Card setCard) {
        GamePlayController.setCard = setCard;
    }

    public int getCurrentPhaseNumber() {
        return currentPhaseNumber;
    }

    public void setCurrentPhaseNumber(int currentPhaseNumber) {
        this.currentPhaseNumber = currentPhaseNumber;
    }

    public static Card getSelectedCard() {
        return selectedCard;
    }

    public static void setSelectedCard(Card selectedCard) {
        GamePlayController.selectedCard = selectedCard;
    }



    public DuelMenuResponses startNewGame(String secondPlayer , int roundNum)
    {  User currentUser = MenuController.getUser();
        User secondUser = User.getUserByUserName(secondPlayer);
        if(User.getUserByUserName(secondPlayer)==null)
            return DuelMenuResponses.NO_PLAYER_WITH_THIS_USERNAME_EXISTS;
        else if(secondUser.getActiveDeck()==null)
            return DuelMenuResponses.OPPONENT_PLAYER_HAS_NO_ACTIVE_DECK;
        else if(currentUser.getActiveDeck()==null)
            return DuelMenuResponses.CURRENT_PLAYER_HAS_NO_ACTIVE_DECK;
        else if(!currentUser.getActiveDeck().isValid())
            return DuelMenuResponses.INVALID_CURRENT_PLAYER_DECK;
        else if(!secondUser.getActiveDeck().isValid())
            return DuelMenuResponses.INVALID_OPPONENT_PLAYER_DECK;
        else if(roundNum!=1 && roundNum!=3)
            return DuelMenuResponses.UNSUPPORTED_ROUND_NUMBER;
        else {
            game= new Game( (Player) currentUser , (Player) secondUser , roundNum);
            currentPlayer = game.getFirstPlayer();
            return DuelMenuResponses.GAME_STARTED_SUCCESSFULLY;
        }

    }
    public DuelMenuResponses selectNumericZone(int cardNumber , String zoneType, String opponentOrPlayer )
    {   Player player;
        if(opponentOrPlayer.equals("player")) player=currentPlayer;
        else player=opponentPlayer;
        Zone.ZoneType zoneTypeEnum ;
        if(zoneType.equals("monster"))
            zoneTypeEnum = Zone.ZoneType.MONSTER_CARD;
        else if(zoneType.equals("spell"))
            zoneTypeEnum = Zone.ZoneType.MAGIC_CARD;
        else if(zoneType.equals("hand"))
            zoneTypeEnum = Zone.ZoneType.HAND;
        else return DuelMenuResponses.INVALID_SELECTION;
        if(cardNumber==0 || cardNumber >5)
            return DuelMenuResponses.INVALID_SELECTION;
        if(zoneTypeEnum.equals(Zone.ZoneType.HAND) &&  cardNumber > currentPlayer.getHand().getNumberOfCardsInHand())
            return DuelMenuResponses.INVALID_SELECTION;
         NumericZone zone =(NumericZone) player.getZoneByZoneType(zoneTypeEnum);
         if(zone.getCardByPlaceNumber(cardNumber)!=null)
         {   selectedCard = zone.getCardByPlaceNumber(cardNumber);
             selectedCard.setSelected(true);
             selectedCard.setPlacedZone(zoneTypeEnum);
             return DuelMenuResponses.CARD_SELECTED;
         }
         else return DuelMenuResponses.SELECTION_NO_CARD_FOUND;
    }
    public DuelMenuResponses selectNotNumericZone( String zoneType, String opponentOrPlayer )
    {  Player player;
        if(opponentOrPlayer.equals("player")) player=currentPlayer;
        else player=opponentPlayer;
        Zone.ZoneType zoneTypeEnum;
        if(zoneType.equals("field"))
            zoneTypeEnum = Zone.ZoneType.FIELD;
        else return  DuelMenuResponses.INVALID_SELECTION;
        if(player.getFieldZone().getZoneCards().isEmpty())
             return DuelMenuResponses.SELECTION_NO_CARD_FOUND;
        else {
            selectedCard = player.getFieldZone().getZoneCards().get(0);
            selectedCard.setSelected(true);
            return DuelMenuResponses.CARD_SELECTED;
        }

    }
    public DuelMenuResponses deSelect()
    {  if(selectedCard!=null) {
        selectedCard.setSelected(false);
        selectedCard =null;
        return DuelMenuResponses.CARD_DESELECTED;
    }
        else return DuelMenuResponses.NO_CARD_SELECTED;
    }
    public DuelMenuResponses summon()
    {  if(selectedCard==null)
        return DuelMenuResponses.NO_CARD_SELECTED;
        else if((!selectedCard.getPlacedZone().equals(Zone.ZoneType.HAND)) ||
                !(selectedCard instanceof MonsterCard))
            return DuelMenuResponses.CANT_SUMMON_THIS_CARD;
//        else if(((MonsterCard) selectedCard).getMonsterEffectType().equals(MonsterCard.MonsterEffectType.RITUAL))
//             return DuelMenuResponses.CANT_SUMMON_THIS_CARD;
        else if(Game.getPhases().get(currentPhaseNumber)!= Phase.PhaseLevel.MAIN1 &&
                 Game.getPhases().get(currentPhaseNumber) != Phase.PhaseLevel.MAIN2)
             return DuelMenuResponses.NOT_ALLOWED_IN_THIS_PHASE;
        else if(currentPlayer.getMonsterCardZone().getNumberOfMonsterCard()==5)
             return DuelMenuResponses.MONSTER_ZONE_IS_FULL;
         else if(setCard != null || summonedCard != null)
             return DuelMenuResponses.ALREADY_SUMMONED_SET;
         else if(((MonsterCard) selectedCard).getLevel() <=4) {
             doSummon();
             return  DuelMenuResponses.CARD_SUMMONED;
         }
          if(((MonsterCard) selectedCard).getLevel()==5 ||((MonsterCard) selectedCard).getLevel()==6 )
         { if (currentPlayer.getMonsterCardZone().getNumberOfMonsterCard()==0)
                 return DuelMenuResponses.NOT_ENOUGH_CARD_TO_BE_TRIBUTE;
                else return  GET_ONE_NUMBER_TO_BE_TRIBUTE;
         }
        else
          { if (currentPlayer.getMonsterCardZone().getNumberOfMonsterCard()<2)
                 return DuelMenuResponses.NOT_ENOUGH_CARD_TO_BE_TRIBUTE;
             else return GET_TWO_NUMBERS_TO_BE_TRIBUTE;}
    }
    public DuelMenuResponses oneMonsterTribute(int address)
    { if(currentPlayer.getMonsterCardZone().getCardByPlaceNumber(address)== null)
        return DuelMenuResponses.ONE_TRIBUTE_NO_MONSTER;
      else  currentPlayer.getMonsterCardZone().moveMonsterCardToGraveyard(address,currentPlayer);
      doSummon();
      return DuelMenuResponses.CARD_SUMMONED;
    }
    public DuelMenuResponses twoMonsterTribute(int firstAddress , int secondAddress)
    {   if( currentPlayer.getMonsterCardZone().getCardByPlaceNumber(firstAddress)== null || currentPlayer.getMonsterCardZone().getCardByPlaceNumber(secondAddress)== null)
        return DuelMenuResponses.TWO_TRIBUTE_NO_MONSTER;
            currentPlayer.getMonsterCardZone().moveMonsterCardToGraveyard(firstAddress ,currentPlayer);
            currentPlayer.getMonsterCardZone().moveMonsterCardToGraveyard(secondAddress ,currentPlayer);
            doSummon();
            return DuelMenuResponses.CARD_SUMMONED;
    }
     public DuelMenuResponses set()
     {  if(selectedCard ==null)
         return DuelMenuResponses.NO_CARD_SELECTED;
         else if(!selectedCard.getPlacedZone().equals(Zone.ZoneType.HAND))
             return DuelMenuResponses.CANT_SET_THIS_CARD;
         else if(Game.getPhases().get(currentPhaseNumber)!= Phase.PhaseLevel.MAIN1 &&
             Game.getPhases().get(currentPhaseNumber) != Phase.PhaseLevel.MAIN2)
             return DuelMenuResponses.CANT_DO_THIS_ACTION_IN_THIS_PHASE;
//         if(selectedCard instanceof MagicCard && ((MagicCard) selectedCard).getMagicType().equals(MagicCard.MagicType.SPELL))
//           return setSpell();
//         else if(selectedCard instanceof MagicCard && ((MagicCard) selectedCard).getMagicType().equals(MagicCard.MagicType.TRAP))
//           return setTrap();
         else
           return   setMonster();
     }
     public DuelMenuResponses setMonster()
     {  if(opponentPlayer.getMonsterCardZone().getNumberOfMonsterCard()==5)
         return DuelMenuResponses.MONSTER_ZONE_IS_FULL;
         else if(setCard!=null || summonedCard!=null)
             return DuelMenuResponses.ALREADY_SUMMONED_SET;
         currentPlayer.getMonsterCardZone().summonOrSetMonster((MonsterCard) selectedCard, currentPlayer);
         selectedCard.setSet(true);
         setSetCard(selectedCard);
         selectedCard.setAppearance(Card.Appearance.HIDDEN);
         ((MonsterCard) selectedCard).setMode(MonsterCard.Mode.DEFENSE);
         return DuelMenuResponses.CART_SET_SUCCESSFULLY;
     }

     public DuelMenuResponses setPosition(String wantedPosition)
     { if(selectedCard==null)
         return DuelMenuResponses.NO_CARD_SELECTED;
         else if(!selectedCard.getPlacedZone().equals(Zone.ZoneType.MONSTER_CARD) || !(selectedCard instanceof MonsterCard))
             return DuelMenuResponses.CANT_CHANGE_THIS_CARD_POSITION;
         else if(Game.getPhases().get(currentPhaseNumber)!= Phase.PhaseLevel.MAIN1 &&
             Game.getPhases().get(currentPhaseNumber) != Phase.PhaseLevel.MAIN2)
             return DuelMenuResponses.CANT_DO_THIS_ACTION_IN_THIS_PHASE;
         else if(wantedPosition.equals("attack") && !((MonsterCard)selectedCard).toStringPosition().equals("DO"))
             return DuelMenuResponses.ALREADY_WANTED_POSITION;
         else if(wantedPosition.equals("defence") && !((MonsterCard)selectedCard).toStringPosition().equals("OO"))
         return ALREADY_WANTED_POSITION;
         else if (isSelectCardChangedBefore())
             return ALREADY_CHANGED_POSITION;
           if (wantedPosition.equals("defence"))
         ((MonsterCard)selectedCard).setMode(MonsterCard.Mode.DEFENSE);
           else if (wantedPosition.equals("attack"))
           {  selectedCard.setAppearance(Card.Appearance.OCCUPIED);
               ((MonsterCard) selectedCard).setMode(MonsterCard.Mode.ATTACK);
           }
           changedPositionCardsInTurn.add(selectedCard);
           return MONSTER_CARD_POSITION_CHANGED_SUCCESSFULLY;
     }
//     public DuelMenuResponses setSpell()
//     {
//
//
//     }
//     public DuelMenuResponses setTrap()
//     {
//
//     }

    public DuelMenuResponses changePhaseLevel()
    {
        if(currentPhaseNumber==6)
        {   currentPhaseNumber=1;
            changeTurn();
            return DuelMenuResponses.RIVALS_TURN_AND_SHOW_DRAW_PHASE;
        }
        else {
            currentPhaseNumber++;
            return DuelMenuResponses. SHOW_NEW_PHASE;
        }
    }
    public void changeTurn()
    {   if(game.getFirstPlayer().getUserName().equals(currentPlayer.getUserName()))
    {  currentPlayer =game.getSecondPlayer();
       opponentPlayer = game.getFirstPlayer();
    }
    else
    {
        currentPlayer = game.getFirstPlayer();
        opponentPlayer = game.getSecondPlayer();
    }
    }
    public void doSummon()
    {
        currentPlayer.getMonsterCardZone().summonOrSetMonster((MonsterCard) selectedCard , currentPlayer);
                   ((MonsterCard) selectedCard).setSummoned(true);
                   setSummonedCard(selectedCard);
    }
    public void refresh(){
        changedPositionCardsInTurn.clear();
        selectedCard = null;
    }
    public Boolean isSelectCardChangedBefore()
    {
        for (Card card : changedPositionCardsInTurn) {
            if ( selectedCard == card)
                return true;
        }
        return false;
    }



    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public Player getOpponentPlayer() {
        return opponentPlayer;
    }

    public void setOpponentPlayer(Player opponentPlayer) {
        this.opponentPlayer = opponentPlayer;
    }
}
