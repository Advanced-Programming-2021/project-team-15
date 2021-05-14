package controller;

import controller.responses.DuelMenuResponses;
import model.*;
import view.DuelMenu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static controller.responses.DuelMenuResponses.*;

public class GamePlayController extends MenuController {
    private DuelMenu duelMenu = DuelMenu.getInstance();
    private static GamePlayController gamePlayController = null;
    public static GamePlayController getInstance() {
        if( gamePlayController==null)
           gamePlayController= new GamePlayController();
        return gamePlayController;
    }
   private EffectController effectController;
    private  Game game;
    private int currentPhaseNumber = 1;
    private Card selectedCard;
    private static Card setCard;
    private   Player currentPlayer;
    private Player opponentPlayer;
    private AttackController attackController;
    private ArrayList<MonsterCard> summonedOrSetMonstersInTurn = new ArrayList<>();
    private ArrayList<MagicCard> setSpellCardsInTurn = new ArrayList<>();
    private ArrayList<MagicCard> setTrapCardsInTurn = new ArrayList<>();
    private HashMap<Player , Card> activatedCards = new HashMap<>();
    private ArrayList<MagicCard> setTrapAndSpellCardsInTurn = new ArrayList<>();
    private ArrayList<Card> changedPositionCardsInTurn = new ArrayList<>();

    public GamePlayController() {
        super("Duel Menu");
    }


    public static Card getSetCard() {
        return setCard;
    }

    public static void setSetCard(Card setCard) {
        GamePlayController.setCard = setCard;
    }

    public static GamePlayController getGamePlayController() {
        return gamePlayController;
    }

    public static void setGamePlayController(GamePlayController gamePlayController) {
        GamePlayController.gamePlayController = gamePlayController;
    }

    public EffectController getEffectController() {
        return effectController;
    }

    public void setEffectController(EffectController effectController) {
        this.effectController = effectController;
    }

    public int getCurrentPhaseNumber() {
        return currentPhaseNumber;
    }

    public void setCurrentPhaseNumber(int currentPhaseNumber) {
        this.currentPhaseNumber = currentPhaseNumber;
    }



    public DuelMenuResponses startNewGame(String secondPlayer, int roundNum) {
        User currentUser = MenuController.getUser();
        User secondUser = User.getUserByUserName(secondPlayer);
        if (User.getUserByUserName(secondPlayer) == null)
            return DuelMenuResponses.NO_PLAYER_WITH_THIS_USERNAME_EXISTS;
        else if (secondUser.getActiveDeck() == null)
            return DuelMenuResponses.OPPONENT_PLAYER_HAS_NO_ACTIVE_DECK;
        else if (currentUser.getActiveDeck() == null)
            return DuelMenuResponses.CURRENT_PLAYER_HAS_NO_ACTIVE_DECK;
        else if (!currentUser.getActiveDeck().isValid())
            return DuelMenuResponses.INVALID_CURRENT_PLAYER_DECK;
        else if (!secondUser.getActiveDeck().isValid())
            return DuelMenuResponses.INVALID_OPPONENT_PLAYER_DECK;
        else if (roundNum != 1 && roundNum != 3)
            return DuelMenuResponses.UNSUPPORTED_ROUND_NUMBER;
        else {
            Player first  = new Player(currentUser);
            Player second = new Player(secondUser);
            game = new Game(first,second, roundNum);
            attackController = new AttackController();
            effectController = new EffectController();
            return DuelMenuResponses.GAME_STARTED_SUCCESSFULLY; //TODO : agar dar view in return shod game sang kaghaz ..
        }


    }
    public void start()
    {   refresh();
        game.setRoundCount(game.getRoundCount()+1);
        currentPlayer = game.getFirstPlayer();
        opponentPlayer= game.getSecondPlayer();
        currentPlayer.setLifePoint(8000);
        opponentPlayer.setLifePoint(8000);
        currentPlayer.startNewGame();
        opponentPlayer.startNewGame();
        currentPlayer.getDeckZone().setZoneCards( cloner.deepClone(currentPlayer.getUser().getActiveDeck().getMainDeck()));
        opponentPlayer.getDeckZone().setZoneCards(cloner.deepClone(opponentPlayer.getUser().getActiveDeck().getMainDeck()));
        shuffle();
        for(int i =0  ; i <=5 ; i++)
        {
            currentPlayer.getHand().addCardToHand(currentPlayer.getDeckZone().getZoneCards().get(0));
             currentPlayer.getDeckZone().getZoneCards().remove(0);
          if(i<5) {
              opponentPlayer.getHand().addCardToHand(opponentPlayer.getDeckZone().getZoneCards().get(0));
              opponentPlayer.getDeckZone().getZoneCards().remove(0);
          }
        }
        currentPlayer.setFirstTurn(true);
    }
    public void drawPhase(){
        changeTurn();
        if(currentPlayer.getCanDraw()){
        currentPlayer.getHand().addCardToHand(currentPlayer.getDeckZone().getZoneCards().get(0));
        currentPlayer.getDeckZone().getZoneCards().remove(0);}
         else {
            currentPlayer.setCanDraw(false);
            goNextPhase();
        }
    }
    public Boolean RPS(String firstPlayerMove ,String secondPlayerMove)
    {
        Player winner;
        Player loser ;
        if(firstPlayerMove.equals(secondPlayerMove))
            return false;
        else if((firstPlayerMove.equals("rock") && (secondPlayerMove.equals("scissors"))) ||
                ( firstPlayerMove.equals("paper") && (secondPlayerMove.equals("rock"))) ||
                ( firstPlayerMove.equals("scissors") && secondPlayerMove.equals("paper")))
        {    winner = game.getFirstPlayer();
            loser=game.getSecondPlayer();}
        else {  loser = game.getFirstPlayer();
           winner=game.getSecondPlayer();}
        game.setFirstPlayer(winner);
        game.setSecondPlayer(loser);
        start();
        return true;

    }



    public void shuffle()
    {
        Collections.shuffle(currentPlayer.getDeckZone().getZoneCards());
        Collections.shuffle(opponentPlayer.getDeckZone().getZoneCards());
    }

    public DuelMenuResponses selectNumericZone(int cardNumber, String zoneType, String opponentOrPlayer) {
        Player player;
        if (opponentOrPlayer.equals("player")) player = currentPlayer;
        else player = opponentPlayer;
        Zone.ZoneType zoneTypeEnum;
        if (zoneType.equals("monster"))
            zoneTypeEnum = Zone.ZoneType.MONSTER_CARD;
        else if (zoneType.equals("spell"))
            zoneTypeEnum = Zone.ZoneType.MAGIC_CARD;
        else if (zoneType.equals("hand"))
            zoneTypeEnum = Zone.ZoneType.HAND;
        else return DuelMenuResponses.INVALID_SELECTION;
        if (cardNumber == 0 || cardNumber > 5)
            return DuelMenuResponses.INVALID_SELECTION;
        if (zoneTypeEnum.equals(Zone.ZoneType.HAND) && cardNumber > currentPlayer.getHand().getNumberOfCardsInHand())
            return DuelMenuResponses.INVALID_SELECTION;
        NumericZone zone = (NumericZone) player.getZoneByZoneType(zoneTypeEnum);
        if (zone.getCardByPlaceNumber(cardNumber) != null) {
            selectedCard = zone.getCardByPlaceNumber(cardNumber);
            selectedCard.setSelected(true);
            return DuelMenuResponses.CARD_SELECTED;
        } else return DuelMenuResponses.SELECTION_NO_CARD_FOUND;
    }

    public DuelMenuResponses selectNotNumericZone(String zoneType, String opponentOrPlayer) {
        Player player;
        if (opponentOrPlayer.equals("player")) player = currentPlayer;
        else player = opponentPlayer;
        Zone.ZoneType zoneTypeEnum;
        if (zoneType.equals("field"))
            zoneTypeEnum = Zone.ZoneType.FIELD;
        else return DuelMenuResponses.INVALID_SELECTION;
        if (player.getFieldZone().getZoneCards().isEmpty())
            return DuelMenuResponses.SELECTION_NO_CARD_FOUND;
        else {
            selectedCard = player.getFieldZone().getZoneCards().get(0);
            selectedCard.setSelected(true);
            return DuelMenuResponses.CARD_SELECTED;
        }

    }

    public DuelMenuResponses deSelect() {
        if (selectedCard != null) {
            selectedCard.setSelected(false);
            selectedCard = null;
            return DuelMenuResponses.CARD_DESELECTED;
        } else return DuelMenuResponses.NO_CARD_SELECTED;
    }
    public DuelMenuResponses summonCommand()
    {     if (selectedCard == null)
        return DuelMenuResponses.NO_CARD_SELECTED;
        else {
            DuelMenuResponses duelMenuResponses = summon();
            selectedCard = null;
            return duelMenuResponses;
    }
    }

    public void showGameBoard()
    {     StringBuilder board = new StringBuilder();
        board.append(opponentPlayer.getUser().getNickName()).append("\n");
        board.append("\tc ".repeat(opponentPlayer.getHand().getNumberOfCardsInHand()));
        board.append("\n").append(opponentPlayer.getDeckZone().getZoneCards().size()).append("\n\t");
        board.append(opponentPlayer.getMagicCardZone().toStringPos(4)).append("\t").append(opponentPlayer.getMagicCardZone().toStringPos(2)).append("\t").append(opponentPlayer.getMagicCardZone().toStringPos(1)).append("\t").append(opponentPlayer.getMagicCardZone().toStringPos(3)).append("\t").append(opponentPlayer.getMagicCardZone().toStringPos(5)).append("\n\t");
        board.append(opponentPlayer.getMonsterCardZone().toStringPos(4)).append("\t").append(opponentPlayer.getMonsterCardZone().toStringPos(2)).append("\t").append(opponentPlayer.getMonsterCardZone().toStringPos(1)).append("\t").append(opponentPlayer.getMonsterCardZone().toStringPos(3)).append("\t").append(opponentPlayer.getMonsterCardZone().toStringPos(5)).append("\n");
        board.append(opponentPlayer.getGraveyardZone().getZoneCards().size()).append("\t\t\t\t\t\t").append(opponentPlayer.getFieldZone().toStringPos()).append("\n");
        board.append("\n----------------------------------------------------------\n\n");
        board.append(currentPlayer.getFieldZone().toStringPos()).append("\t\t\t\t\t\t").append(currentPlayer.getGraveyardZone().getZoneCards().size()).append("\n\t");
        board.append(currentPlayer.getMonsterCardZone().toStringPos(5)).append("\t").append(currentPlayer.getMonsterCardZone().toStringPos(3)).append("\t").append(currentPlayer.getMonsterCardZone().toStringPos(1)).append("\t").append(currentPlayer.getMonsterCardZone().toStringPos(2)).append("\t").append(currentPlayer.getMonsterCardZone().toStringPos(4)).append("\n\t");
        board.append(currentPlayer.getMagicCardZone().toStringPos(5)).append("\t").append(currentPlayer.getMagicCardZone().toStringPos(3)).append("\t").append(currentPlayer.getMagicCardZone().toStringPos(1)).append("\t").append(currentPlayer.getMagicCardZone().toStringPos(2)).append("\t").append(currentPlayer.getMagicCardZone().toStringPos(4)).append("\n");
        board.append("  \t\t\t\t\t\t").append(currentPlayer.getDeckZone().getZoneCards().size()).append("\n");
        board.append("c \t".repeat(currentPlayer.getHand().getNumberOfCardsInHand()));
        board.append("\n").append(currentPlayer.getUser().getNickName());
        duelMenu.getInstance().printString(board.toString());
    }

    public DuelMenuResponses summon() {
        if (selectedCard.getCardPlacedZone() != currentPlayer.getHand() ||
                !(selectedCard instanceof MonsterCard))
            return DuelMenuResponses.CANT_SUMMON_THIS_CARD;
        else if (((MonsterCard) selectedCard).getMonsterEffectType().equals(MonsterCard.MonsterEffectType.RITUAL))
            return DuelMenuResponses.CANT_SUMMON_THIS_CARD;
        else if (Game.getPhases().get(currentPhaseNumber) != Phase.PhaseLevel.MAIN1 &&
                Game.getPhases().get(currentPhaseNumber) != Phase.PhaseLevel.MAIN2)
            return DuelMenuResponses.NOT_ALLOWED_IN_THIS_PHASE;
        else if (currentPlayer.getMonsterCardZone().getNumberOfCard() == 5)
            return DuelMenuResponses.MONSTER_ZONE_IS_FULL;
        else if (!summonedOrSetMonstersInTurn.isEmpty())
            return DuelMenuResponses.ALREADY_SUMMONED_SET;
        else if (((MonsterCard) selectedCard).getLevel() <= 4) {
            doSummon();
            selectedCard =null;
            return DuelMenuResponses.CARD_SUMMONED;
        }
        if (((MonsterCard) selectedCard).getLevel() == 5 || ((MonsterCard) selectedCard).getLevel() == 6) {
            if (currentPlayer.getMonsterCardZone().getNumberOfCard() == 0)
                return DuelMenuResponses.NOT_ENOUGH_CARD_TO_BE_TRIBUTE;
            else {
               duelMenu.printResponse(GET_ONE_NUMBER_TO_BE_TRIBUTE);
               return oneMonsterTribute();
            }
        } else {
            if (currentPlayer.getMonsterCardZone().getNumberOfCard() < 2)
                return DuelMenuResponses.NOT_ENOUGH_CARD_TO_BE_TRIBUTE;
            else {
               duelMenu.printResponse( GET_TWO_NUMBERS_TO_BE_TRIBUTE);
               return twoMonsterTribute();
            }
        }
    }

    public DuelMenuResponses oneMonsterTribute() {
        int num = duelMenu.scannerNum();
        if (currentPlayer.getMonsterCardZone().getCardByPlaceNumber(num) == null)
            return DuelMenuResponses.ONE_TRIBUTE_NO_MONSTER;
        else currentPlayer.getMonsterCardZone().moveCardToGraveyard(num, currentPlayer);
        doSummon();
        selectedCard=null;
        return DuelMenuResponses.CARD_SUMMONED;
    }

    public DuelMenuResponses twoMonsterTribute() {
        int firstAddress = duelMenu.scannerNum();
        int secondAddress = duelMenu.scannerNum();
        if (currentPlayer.getMonsterCardZone().getCardByPlaceNumber(firstAddress) == null || currentPlayer.getMonsterCardZone().getCardByPlaceNumber(secondAddress) == null)
            return DuelMenuResponses.TWO_TRIBUTE_NO_MONSTER;
        currentPlayer.getMonsterCardZone().moveCardToGraveyard(firstAddress, currentPlayer);
        currentPlayer.getMonsterCardZone().moveCardToGraveyard(secondAddress, currentPlayer);
        doSummon();
        selectedCard = null;
        return DuelMenuResponses.CARD_SUMMONED;
    }
    public DuelMenuResponses setCommand()
    {   if (selectedCard == null)
        return DuelMenuResponses.NO_CARD_SELECTED;
        else {
            DuelMenuResponses duelMenuResponses = set();
            selectedCard = null;
            return duelMenuResponses;
    }
    }

    public DuelMenuResponses set() {

        if (selectedCard.getCardPlacedZone() != currentPlayer.getHand())
            return DuelMenuResponses.CANT_SET_THIS_CARD;
        else if (Game.getPhases().get(currentPhaseNumber) != Phase.PhaseLevel.MAIN1 &&
                Game.getPhases().get(currentPhaseNumber) != Phase.PhaseLevel.MAIN2)
            return DuelMenuResponses.CANT_DO_THIS_ACTION_IN_THIS_PHASE;
        if (selectedCard instanceof MagicCard && ((MagicCard) selectedCard).getMagicType().equals(MagicCard.MagicType.SPELL))
            return setSpell();
        else if (selectedCard instanceof MagicCard && ((MagicCard) selectedCard).getMagicType().equals(MagicCard.MagicType.TRAP))
            return setTrap();
        else
            return setMonster();
    }

    public DuelMenuResponses setMonster() {
        if (opponentPlayer.getMonsterCardZone().getNumberOfCard() == 5)
            return DuelMenuResponses.MONSTER_ZONE_IS_FULL;
        else if (!summonedOrSetMonstersInTurn.isEmpty())
            return DuelMenuResponses.ALREADY_SUMMONED_SET;
        currentPlayer.getMonsterCardZone().summonOrSetMonster((MonsterCard) selectedCard, currentPlayer);
        selectedCard.setSet(true);
        setSetCard(selectedCard);
        summonedOrSetMonstersInTurn.add((MonsterCard) selectedCard);
        selectedCard.setHidden(true);
        ((MonsterCard) selectedCard).setMode(MonsterCard.Mode.DEFENSE);
        selectedCard= null;
        return DuelMenuResponses.CARD_SET_SUCCESSFULLY;
    }
    public DuelMenuResponses setPosCommand()
    {   if (selectedCard == null)
        return DuelMenuResponses.NO_CARD_SELECTED;
        else {
            DuelMenuResponses duelMenuResponses =setPosition();
            selectedCard=null;
            return   duelMenuResponses;
    }
    }

    public DuelMenuResponses setPosition() {
        String wantedPosition = duelMenu.scannerLine();
        if(isMonsterSummonedOrSetInThisTurn((MonsterCard) selectedCard) || attackController.alreadyAttackedThisTurn((MonsterCard)selectedCard ))
            return CANT_CHANGE_THIS_CARD_POSITION;
        else if (selectedCard.getCardPlacedZone() != currentPlayer.getMonsterCardZone() || !(selectedCard instanceof MonsterCard))
            return DuelMenuResponses.CANT_CHANGE_THIS_CARD_POSITION;
        else if (Game.getPhases().get(currentPhaseNumber) != Phase.PhaseLevel.MAIN1 &&
                Game.getPhases().get(currentPhaseNumber) != Phase.PhaseLevel.MAIN2)
            return DuelMenuResponses.CANT_DO_THIS_ACTION_IN_THIS_PHASE;
        else if (wantedPosition.equals("attack") && !((MonsterCard) selectedCard).toStringPosition().equals("DO"))
            return DuelMenuResponses.ALREADY_WANTED_POSITION;
        else if (wantedPosition.equals("defence") && !((MonsterCard) selectedCard).toStringPosition().equals("OO"))
            return ALREADY_WANTED_POSITION;
        else if (isSelectCardChangedBefore())
            return ALREADY_CHANGED_POSITION;
        if (wantedPosition.equals("defence"))
            ((MonsterCard) selectedCard).setMode(MonsterCard.Mode.DEFENSE);
        else if (wantedPosition.equals("attack")) {
            selectedCard.setHidden(false);
            ((MonsterCard) selectedCard).setMode(MonsterCard.Mode.ATTACK);
        }
        changedPositionCardsInTurn.add(selectedCard);
        selectedCard= null;
        return MONSTER_CARD_POSITION_CHANGED_SUCCESSFULLY;
    }

    public DuelMenuResponses setSpell() {
        if (currentPlayer.getMagicCardZone().getNumberOfCard() == 5)
            return SPELL_ZONE_CARD_IS_FULL;
        currentPlayer.getMagicCardZone().moveToFirstEmptyPlaceFromHand((MagicCard) selectedCard, currentPlayer);
        selectedCard.setSet(true);
        selectedCard.setHidden(true);
        setSpellCardsInTurn.add((MagicCard) selectedCard);
        setTrapAndSpellCardsInTurn.add((MagicCard) selectedCard);
        selectedCard= null;
        return CARD_SET_SUCCESSFULLY;
    }

    public DuelMenuResponses setTrap() {
        if (currentPlayer.getMagicCardZone().getNumberOfCard() == 5)
            return SPELL_ZONE_CARD_IS_FULL;
        currentPlayer.getMagicCardZone().moveToFirstEmptyPlaceFromHand((MagicCard) selectedCard, currentPlayer);
        selectedCard.setSet(true);
        selectedCard.setHidden(true);
        setTrapCardsInTurn.add((MagicCard) selectedCard);
        setTrapAndSpellCardsInTurn.add((MagicCard) selectedCard);
        selectedCard= null;
        return CARD_SET_SUCCESSFULLY;
    }
    public DuelMenuResponses flipSummonCommand(){
        if (selectedCard == null)
            return NO_CARD_SELECTED;
        else {
            DuelMenuResponses duelMenuResponses = flipSummon();
            selectedCard = null;
            return duelMenuResponses;
        }
    }

    public DuelMenuResponses flipSummon() {
         if (selectedCard.getCardPlacedZone() != currentPlayer.getMonsterCardZone())
            return CANT_CHANGE_THIS_CARD_POSITION;
        else if (Game.getPhases().get(currentPhaseNumber) != Phase.PhaseLevel.MAIN1 &&
                Game.getPhases().get(currentPhaseNumber) != Phase.PhaseLevel.MAIN2)
            return CANT_DO_THIS_ACTION_IN_THIS_PHASE;
        else if (!((MonsterCard) selectedCard).toStringPosition().equals("DH") || isMonsterSummonedOrSetInThisTurn((MonsterCard) selectedCard))
            return CANT_FLIP_SUMMON;
        ((MonsterCard) selectedCard).setMode(MonsterCard.Mode.ATTACK);
        selectedCard.setHidden(false);
        selectedCard= null;
        return FLIP_SUMMONED_SUCCESSFULLY;
    }

    public void activateSpellCard(Player player) { //TODO COMPLETE
        if (selectedCard == null)
            duelMenu.printResponse(NO_CARD_SELECTED);
        else if (!(selectedCard instanceof MagicCard) || ((MagicCard) selectedCard).getMagicType() != MagicCard.MagicType.SPELL)
            duelMenu.printResponse(ACTIVATE_EFFECT_ONLY_ON_SPELL);
        else if (Game.getPhases().get(currentPhaseNumber) != Phase.PhaseLevel.MAIN1 &&
                Game.getPhases().get(currentPhaseNumber) != Phase.PhaseLevel.MAIN2)
           duelMenu.printResponse( CANT_ACTIVATE_EFFECT_ON_THIS_TURN);
        else if (selectedCard.isActivated())
           duelMenu.printResponse(YOU_ALREADY_ACTIVATED_THIS_CARD);
        else if (player.getHand().isExist(selectedCard) && ((MagicCard) selectedCard).getCardIcon() != MagicCard.CardIcon.FIELD
                && player.getMagicCardZone().getNumberOfCard() == 5)
            duelMenu.printResponse(SPELL_ZONE_CARD_IS_FULL);
        else if(((MagicCard) selectedCard).getCardIcon()== MagicCard.CardIcon.FIELD)
            player.getFieldZone().moveCardToFieldZone((MagicCard) selectedCard, player);
            callMethodOfSpells(selectedCard);
    }
    public void setCardActive()
    {  selectedCard.setActivated(true);
    }


    public void callMethodOfSpells(Card card)
        {  //TODO

        }



        public void callCardWitchCanBeActivated()
        {



        }



















    public DuelMenuResponses normalAttack(int number) {
        DuelMenuResponses responses = attackController.normalAttack(number);
        selectedCard=null;
        return responses;
    }
    public DuelMenuResponses directAttack(){
        DuelMenuResponses responses = attackController.directAttack();
        selectedCard = null;
        return responses;
    }


    public Boolean isMonsterSummonedOrSetInThisTurn(MonsterCard monsterCard) {
        for (MonsterCard card : summonedOrSetMonstersInTurn) {
            if (card == monsterCard)
                return true;
        }
        return false;
    }


    public DuelMenuResponses goNextPhase() {
        if(currentPhaseNumber ==5)
        {    currentPhaseNumber=1 ;
            return DuelMenuResponses.RIVALS_TURN_AND_SHOW_DRAW_PHASE;
        } else {
            currentPhaseNumber++;
            return DuelMenuResponses.SHOW_NEW_PHASE;
        }
    }

    public void changeTurn() {
        if (game.getFirstPlayer() ==currentPlayer) {
            currentPlayer = game.getSecondPlayer();
            opponentPlayer = game.getFirstPlayer();
        } else {
            currentPlayer = game.getFirstPlayer();
            opponentPlayer = game.getSecondPlayer();
        }
    }

    public void doSummon() {
        currentPlayer.getMonsterCardZone().summonOrSetMonster((MonsterCard) selectedCard, currentPlayer);
        ((MonsterCard) selectedCard).setSummoned(true);
        summonedOrSetMonstersInTurn.add((MonsterCard) selectedCard);
    }

    public void refresh() {
        changedPositionCardsInTurn.clear();
        summonedOrSetMonstersInTurn.clear();
        setSpellCardsInTurn.clear();
        setTrapCardsInTurn.clear();
        setTrapAndSpellCardsInTurn.clear();
        attackController.getAttackedCardsInTurn().clear();
        currentPlayer.setFirstTurn(false);
        selectedCard = null;
    }

    public Boolean isSelectCardChangedBefore() {
        for (Card card : changedPositionCardsInTurn) {
            if (selectedCard == card)
                return true;
        }
        return false;
    }

    public Boolean isSelectedCardActivated() {
        for (Map.Entry<Player, Card> card :activatedCards.entrySet()) {
            if (selectedCard == card.getValue())
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

    public ArrayList<MonsterCard> getSummonedMonstersInTurn() {
        return summonedOrSetMonstersInTurn;
    }

    public void setSummonedMonstersInTurn(ArrayList<MonsterCard> summonedMonstersInTurn) {
        this.summonedOrSetMonstersInTurn = summonedMonstersInTurn;
    }

    public ArrayList<MagicCard> getSetSpellCardsInTurn() {
        return setSpellCardsInTurn;
    }

    public void setSetSpellCardsInTurn(ArrayList<MagicCard> setSpellCardsInTurn) {
        this.setSpellCardsInTurn = setSpellCardsInTurn;
    }

    public ArrayList<MagicCard> getSetTrapCardsInTurn() {
        return setTrapCardsInTurn;
    }

    public void setSetTrapCardsInTurn(ArrayList<MagicCard> setTrapCardsInTurn) {
        this.setTrapCardsInTurn = setTrapCardsInTurn;
    }

    public ArrayList<Card> getChangedPositionCardsInTurn() {
        return changedPositionCardsInTurn;
    }

    public void setChangedPositionCardsInTurn(ArrayList<Card> changedPositionCardsInTurn) {
        this.changedPositionCardsInTurn = changedPositionCardsInTurn;
    }


    public Boolean checkIfGameIsFinished()
    {  return (currentPlayer.getLifePoint()<=0 || opponentPlayer.getLifePoint()<=0 || currentPlayer.getDeckZone().getZoneCards().size()==0 ||
            opponentPlayer.getDeckZone().getZoneCards().size()==0);
    }




    public void defineWinner() {
        Player winner;
        Player loser;
        if(currentPlayer.getLifePoint()<=0 || currentPlayer.getDeckZone().getZoneCards().size()==0)
        {
            winner = currentPlayer;
            loser  = opponentPlayer;
        }else {
            winner = opponentPlayer;
            loser = currentPlayer;
        }
        game.getWinnerOfEachRound()[game.getRoundCount()]=winner;

        if(game.getRoundNumber()==1)
        {   game.getWinnerOfEachRound()[0]=winner;
            game.giveAwardOneRound(winner, loser);
            duelMenu.printResponse(END_GAME);
        }
        else {
            if(game.getRoundCount()==1) //TODO ASK IF IT IS OK
            {    game.getWinnerOfEachRound()[0]=winner;
                game.getFirstPlayerLifePointEachRound().add(game.getFirstPlayer().getLifePoint());
                game.getSecondPlayerLifePointEachRound().add(game.getSecondPlayer().getLifePoint());
                duelMenu.printResponse(END_GAME_AND_RPC);
            }
            else if(game.getRoundCount()==2)
            {
                if(game.getWinnerOfEachRound()[0]==winner)
                {   game.getWinnerOfEachRound()[1]=winner;
                    game.getFirstPlayerLifePointEachRound().add(game.getFirstPlayer().getLifePoint());
                    game.getSecondPlayerLifePointEachRound().add(game.getSecondPlayer().getLifePoint());
                    game.giveAwardThreeRounds(winner ,loser);
                    duelMenu.printResponse(END_MATCH);
                }
                else
                {   game.getWinnerOfEachRound()[1]=winner;
                    game.getFirstPlayerLifePointEachRound().add(game.getFirstPlayer().getLifePoint());
                    game.getSecondPlayerLifePointEachRound().add(game.getSecondPlayer().getLifePoint());
                    duelMenu.printResponse(END_GAME_AND_RPC);
                }
            }
            else {
                game.getWinnerOfEachRound()[2]=winner;
                game.getFirstPlayerLifePointEachRound().add(game.getFirstPlayer().getLifePoint());
                game.getSecondPlayerLifePointEachRound().add(game.getSecondPlayer().getLifePoint());
                game.giveAwardThreeRounds(winner, loser);
                duelMenu.printResponse(END_MATCH);
            }
        }
    }
    public Boolean isSpellSSetInThisTurn( MagicCard magicCard)
    {   for(MagicCard magicCard1 : setSpellCardsInTurn)
    {
        if(magicCard==magicCard1)
            return true;
    }
    return false;
    }
    public Boolean isTrapSetInThisTurn( MagicCard magicCard)
    {
        for(MagicCard magicCard1 : setTrapCardsInTurn)
        {
            if(magicCard1 ==magicCard)
                return true;
        }
        return false;
    }


    public AttackController getAttackController() {
        return attackController;
    }

    public void setAttackController(AttackController attackController) {
        this.attackController = attackController;
    }

    public ArrayList<MonsterCard> getSummonedOrSetMonstersInTurn() {
        return summonedOrSetMonstersInTurn;
    }

    public void setSummonedOrSetMonstersInTurn(ArrayList<MonsterCard> summonedOrSetMonstersInTurn) {
        this.summonedOrSetMonstersInTurn = summonedOrSetMonstersInTurn;
    }

    public ArrayList<MagicCard> getSetTrapAndSpellCardsInTurn() {
        return setTrapAndSpellCardsInTurn;
    }

    public void setSetTrapAndSpellCardsInTurn(ArrayList<MagicCard> setTrapAndSpellCardsInTurn) {
        this.setTrapAndSpellCardsInTurn = setTrapAndSpellCardsInTurn;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Card getSelectedCard() {
        return selectedCard;
    }

    public void setSelectedCard(Card selectedCard) {
        this.selectedCard = selectedCard;
    }

    public DuelMenu getDuelMenu() {
        return duelMenu;
    }

    public void setDuelMenu(DuelMenu duelMenu) {
        this.duelMenu = duelMenu;
    }

    public HashMap<Player, Card> getActivatedCards() {
        return activatedCards;
    }

    public void setActivatedCards(HashMap<Player, Card> activatedCards) {
        this.activatedCards = activatedCards;
    }

}
