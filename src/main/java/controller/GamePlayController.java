package controller;

import controller.responses.DuelMenuResponses;
import model.*;
import view.DuelMenu;

import java.util.ArrayList;
import java.util.Collections;

import static controller.responses.DuelMenuResponses.*;

public class GamePlayController extends MenuController {
    EffectController effectController;
    protected static Game game;
    protected int currentPhaseNumber = 1;
    protected static Card selectedCard;
    public static Card setCard;
    protected Player currentPlayer;
    protected Player opponentPlayer;
    protected AttackController attackController;
    protected ArrayList<MonsterCard> summonedOrSetMonstersInTurn = new ArrayList<>();
    protected ArrayList<MagicCard> setSpellCardsInTurn = new ArrayList<>();
    protected ArrayList<MagicCard> setTrapCardsInTurn = new ArrayList<>();
    protected ArrayList<Card> activatedCardInTurn = new ArrayList<>();
    protected ArrayList<MagicCard> setTrapAndSpellCardsInTurn = new ArrayList<>();
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
            game = new Game((Player) currentUser, (Player) secondUser, roundNum);
            attackController = new AttackController();
            effectController = new EffectController();
            return DuelMenuResponses.GAME_STARTED_SUCCESSFULLY; //TODO : agar dar view in return shod game sang kaghaz ..
        }


    }
    public void start()
    {    game.setRoundCount(game.getRoundCount()+1);
        currentPlayer = game.getFirstPlayer();
        opponentPlayer= game.getSecondPlayer();
        currentPlayer.setLifePoint(8000);
        opponentPlayer.setLifePoint(8000);
        currentPlayer.getDeckZone().setZoneCards( cloner.deepClone(currentPlayer.getActiveDeck().getMainDeck()));
        opponentPlayer.getDeckZone().setZoneCards(cloner.deepClone(opponentPlayer.getActiveDeck().getMainDeck()));
        shuffle();
        for(int i =0  ; i <4 ; i++)
        {
            currentPlayer.getHand().addCardToHand(currentPlayer.getDeckZone().getZoneCards().get(i));
             currentPlayer.getDeckZone().getZoneCards().remove(i);
            opponentPlayer.getHand().addCardToHand(opponentPlayer.getDeckZone().getZoneCards().get(i));
            opponentPlayer.getDeckZone().getZoneCards().remove(i);

        }
    }
    public void RPC(String firstPlayerMove ,String secondPlayerMove)
    {
        int firstPlayerCount=0;
        int secondPlayerCount=0;
        Player first = game.getFirstPlayer();
        Player second=game.getSecondPlayer();
        Player winner = null;
        Player loser = null;
        // TODO : ROCK AND PAPER ...
        game.setFirstPlayer(winner);
        game.setSecondPlayer(loser);
        start();
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
        if (zoneTypeEnum.equals(Zone.ZoneType.HAND) && cardNumber > currentPlayer.getHand().getCardsInHand().size())
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

    public DuelMenuResponses summon() {
        if (selectedCard == null)
            return DuelMenuResponses.NO_CARD_SELECTED;
        else if (selectedCard.getCardPlacedZone() != currentPlayer.getHand() ||
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
            else return GET_ONE_NUMBER_TO_BE_TRIBUTE;
        } else {
            if (currentPlayer.getMonsterCardZone().getNumberOfCard() < 2)
                return DuelMenuResponses.NOT_ENOUGH_CARD_TO_BE_TRIBUTE;
            else return GET_TWO_NUMBERS_TO_BE_TRIBUTE;
        }
    }

    public DuelMenuResponses oneMonsterTribute(int address) {
        if (currentPlayer.getMonsterCardZone().getCardByPlaceNumber(address) == null)
            return DuelMenuResponses.ONE_TRIBUTE_NO_MONSTER;
        else currentPlayer.getMonsterCardZone().moveCardToGraveyard(address, currentPlayer);
        doSummon();
        selectedCard=null;
        return DuelMenuResponses.CARD_SUMMONED;
    }

    public DuelMenuResponses twoMonsterTribute(int firstAddress, int secondAddress) {
        if (currentPlayer.getMonsterCardZone().getCardByPlaceNumber(firstAddress) == null || currentPlayer.getMonsterCardZone().getCardByPlaceNumber(secondAddress) == null)
            return DuelMenuResponses.TWO_TRIBUTE_NO_MONSTER;
        currentPlayer.getMonsterCardZone().moveCardToGraveyard(firstAddress, currentPlayer);
        currentPlayer.getMonsterCardZone().moveCardToGraveyard(secondAddress, currentPlayer);
        doSummon();
        selectedCard = null;
        return DuelMenuResponses.CARD_SUMMONED;
    }

    public DuelMenuResponses set() {
        if (selectedCard == null)
            return DuelMenuResponses.NO_CARD_SELECTED;
        else if (selectedCard.getCardPlacedZone() != currentPlayer.getHand())
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

    public DuelMenuResponses setPosition(String wantedPosition) {
        if (selectedCard == null)
            return DuelMenuResponses.NO_CARD_SELECTED;
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

    public DuelMenuResponses flipSummon() {
        if (selectedCard == null)
            return NO_CARD_SELECTED;
        else if (selectedCard.getCardPlacedZone() != currentPlayer.getMonsterCardZone())
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

    public DuelMenuResponses activateSpellCard() {
        if (selectedCard == null)
            return NO_CARD_SELECTED;
        else if (!(selectedCard instanceof MagicCard) || ((MagicCard) selectedCard).getMagicType() != MagicCard.MagicType.SPELL)
            return ACTIVATE_EFFECT_ONLY_ON_SPELL;
        else if (Game.getPhases().get(currentPhaseNumber) != Phase.PhaseLevel.MAIN1 &&
                Game.getPhases().get(currentPhaseNumber) != Phase.PhaseLevel.MAIN2)
            return CANT_ACTIVATE_EFFECT_ON_THIS_TURN;
        else if (((MagicCard) selectedCard).isActivated())
            return YOU_ALREADY_ACTIVATED_THIS_CARD;
        else if (((MagicCard) selectedCard).getCardIcon() != MagicCard.CardIcon.FIELD &&
                (currentPlayer.getMagicCardZone()).getNumberOfCard() == 5)
            return SPELL_ZONE_CARD_IS_FULL;
        else if (((MagicCard) selectedCard).getCardIcon() == MagicCard.CardIcon.FIELD) {
            // special condition?
            currentPlayer.getFieldZone().moveCardToFieldZone((MagicCard) selectedCard, currentPlayer);
            ((MagicCard) selectedCard).setActivated(true);
        } else {
            // if()
            // special condition?
            currentPlayer.getMagicCardZone().moveToFirstEmptyPlaceFromHand((MagicCard) selectedCard, currentPlayer);
            ((MagicCard) selectedCard).setActivated(true);
        }
        selectedCard.setHidden(false);
        selectedCard= null;
        return SPELL_ACTIVATED;
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


    public DuelMenuResponses changePhaseLevel() {
        if (currentPhaseNumber == 6) {
            currentPhaseNumber = 1;
            changeTurn();
            return DuelMenuResponses.RIVALS_TURN_AND_SHOW_DRAW_PHASE;
        } else {
            currentPhaseNumber++;
            return DuelMenuResponses.SHOW_NEW_PHASE;
        }
    }

    public void changeTurn() {
        if (game.getFirstPlayer().getUserName().equals(currentPlayer.getUserName())) {
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
        activatedCardInTurn.clear();
        setTrapAndSpellCardsInTurn.clear();
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
        for (Card card : activatedCardInTurn) {
            if (selectedCard == card)
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
    {  return (currentPlayer.getLifePoint()<=0 || opponentPlayer.getLifePoint()<=0);
    }



    public DuelMenuResponses defineWinner() {
        Player winner;
        Player loser;
        if(currentPlayer.getLifePoint()<=0)
        {
            winner = currentPlayer;
            loser  = opponentPlayer;
        }else {
            winner = opponentPlayer;
            loser = currentPlayer;
        }
        game.getWinnerOfEachRound()[game.getRoundCount()]=winner;

        if(game.getRoundNumber()==1)
        {   game.giveAwardOneRound(winner, loser);
            return END_GAME;
        }
        else {
            if(game.getRoundCount()==1) //TODO ASK IF IT IS OK
                return END_GAME_AND_RPC;
            else if(game.getRoundCount()==2)
            {
                if(game.getWinnerOfEachRound()[0]==winner)
                { game.giveAwardThreeRounds(winner ,loser);
                    return END_MATCH;}
                else return END_GAME_AND_RPC;
            }
            else {
                game.giveAwardThreeRounds(winner, loser);
                 return END_MATCH;
            }
        }
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

    public ArrayList<Card> getActivatedCardInTurn() {
        return activatedCardInTurn;
    }

    public void setActivatedCardInTurn(ArrayList<Card> activatedCardInTurn) {
        this.activatedCardInTurn = activatedCardInTurn;
    }

    public ArrayList<MagicCard> getSetTrapAndSpellCardsInTurn() {
        return setTrapAndSpellCardsInTurn;
    }

    public void setSetTrapAndSpellCardsInTurn(ArrayList<MagicCard> setTrapAndSpellCardsInTurn) {
        this.setTrapAndSpellCardsInTurn = setTrapAndSpellCardsInTurn;
    }
}
