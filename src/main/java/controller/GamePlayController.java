package controller;

import controller.responses.DuelMenuResponses;
import model.*;
import view.DuelMenu;

public class GamePlayController extends MenuController{
    private static Game game;
    private int currentPhaseNumber =1;
    private static Card selectedCard;
    private  Player currentPlayer;
    private  Player opponentPlayer;

    public GamePlayController() {
        super("Duel Menu");
    }


    public static Game getGame() {
        return game;
    }

    public static void setGame(Game game) {
        GamePlayController.game = game;
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
         NumericZone zone =(NumericZone) player.getZoneByZoneType(zoneTypeEnum);
         if(zone.getCardByPlaceNumber(cardNumber)!=null)
         {   selectedCard = zone.getCardByPlaceNumber(cardNumber);
             selectedCard.setSelected(true);
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
