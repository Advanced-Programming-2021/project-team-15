package controller.gamePlayController;

import controller.menuController.MenuController;
import controller.responses.DuelMenuResponses;
import model.*;
import model.cards.Card;
import model.cards.MagicCard;
import model.cards.MonsterCard;
import model.zones.Hand;
import model.zones.NumericZone;
import model.zones.Zone;
import view.DuelMenu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static controller.responses.DuelMenuResponses.*;
import static model.cards.MagicCard.CardIcon.COUNTER;
import static model.cards.MagicCard.CardIcon.QUICK_PLAY;

public class GamePlayController extends MenuController {
    private static GamePlayController gamePlayController = null;
    private static Card setCard;
    private static TrapEffectController trapEffectController = new TrapEffectController();
    private static SpellEffectController spellEffectController = new SpellEffectController();
    private static MonsterEffectController monsterEffectController = new MonsterEffectController();
    public Player mainCurrentPlayer;
    private ArrayList<MagicCard> chainCards = new ArrayList<>();
    private ArrayList<Player> chainPlayers = new ArrayList<>();
    private DuelMenu duelMenu = DuelMenu.getInstance();
    private HashMap<MonsterCard, Integer> suijinVictims = new HashMap<>();
    private EffectController effectController;
    private Game game;
    private int currentPhaseNumber = 0;
    private Card selectedCard;
    private Player currentPlayer;
    private Player opponentPlayer;
    private boolean preparationNotDoneYet = false;
    private AttackController attackController;
    private ArrayList<MonsterCard> summonedOrSetMonstersInTurn = new ArrayList<>();
    private ArrayList<MagicCard> setSpellCardsInTurn = new ArrayList<>();
    private ArrayList<MagicCard> setTrapCardsInTurn = new ArrayList<>();
    private HashMap<Player, Card> activatedCards = new HashMap<>();
    private ArrayList<MagicCard> setTrapAndSpellCardsInTurn = new ArrayList<>();
    private ArrayList<Card> changedPositionCardsInTurn = new ArrayList<>();

    public GamePlayController() {
        super("Duel Menu");
    }

    public static TrapEffectController getTrapEffectController() {
        return trapEffectController;
    }

    public static void setTrapEffectController(TrapEffectController trapEffectController) {
        GamePlayController.trapEffectController = trapEffectController;
    }

    public static GamePlayController getInstance() {
        if (gamePlayController == null)
            gamePlayController = new GamePlayController();
        return gamePlayController;
    }

    public static Card getSetCard() {
        return setCard;
    }

    public static void setSetCard(Card setCard) {
        GamePlayController.setCard = setCard;
    }

    public static MonsterEffectController getMonsterEffectController() {
        return monsterEffectController;
    }

    public static void setMonsterEffectController(MonsterEffectController monsterEffectController) {
        GamePlayController.monsterEffectController = monsterEffectController;
    }

    public ArrayList<MagicCard> getChainCards() {
        return chainCards;
    }

    public void setChainCards(ArrayList<MagicCard> chainCards) {
        this.chainCards = chainCards;
    }

    public ArrayList<Player> getChainPlayers() {
        return chainPlayers;
    }

    public void setChainPlayers(ArrayList<Player> chainPlayers) {
        this.chainPlayers = chainPlayers;
    }

    public HashMap<MonsterCard, Integer> getSuijinVictims() {
        return suijinVictims;
    }

    public void setSuijinVictims(HashMap<MonsterCard, Integer> suijinVictims) {
        this.suijinVictims = suijinVictims;
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
            Player first = new Player(currentUser);
            Player second = new Player(secondUser);
            game = new Game(first, second, roundNum);
            attackController = new AttackController();
            effectController = new EffectController();
            spellEffectController = new SpellEffectController();
            return DuelMenuResponses.GAME_STARTED_SUCCESSFULLY;
        }
    }

    public void startRound() {
        refresh();
        currentPlayer.setCanDraw(true);
        game.setRoundCount(game.getRoundCount() + 1);
        game.getFirstPlayer().setLifePoint(8000);
        game.getSecondPlayer().setLifePoint(8000);
        game.getFirstPlayer().startNewGame();
        game.getSecondPlayer().startNewGame();
        currentPlayer.getDeckZone().setZoneCards(cloner.deepClone(currentPlayer.getUser().getActiveDeck()).getMainDeck());
        opponentPlayer.getDeckZone().setZoneCards(cloner.deepClone(opponentPlayer.getUser().getActiveDeck()).getMainDeck());
        for (Card card : currentPlayer.getDeckZone().getZoneCards()) {
            card.setOwner(currentPlayer);
            card.setSummoned(false);
        }
        for (Card card : opponentPlayer.getDeckZone().getZoneCards()) {
            card.setOwner(opponentPlayer);
            card.setSummoned(false);
        }
        shuffle();
        for (int i = 0; i < 5; i++) {
            currentPlayer.getHand().addCardToHand(currentPlayer.getDeckZone().getZoneCards().get(0));
            currentPlayer.getDeckZone().getZoneCards().remove(0);
            opponentPlayer.getHand().addCardToHand(opponentPlayer.getDeckZone().getZoneCards().get(0));
            opponentPlayer.getDeckZone().getZoneCards().remove(0);
        }
        currentPlayer.setCanDraw(true);
    }

    public void drawPhase() {
        refresh();
        changeTurn();
        if (currentPlayer.getCanDraw()) {
            //     shuffle();
            currentPlayer.getHand().addCardToHand(currentPlayer.getDeckZone().getZoneCards().get(0));
            //DuelMenu.getInstance().printString(currentPlayer.getDeckZone().getZoneCards().get(0).getCardName());
            currentPlayer.getDeckZone().getZoneCards().remove(0);
            if (checkIfGameIsFinished())
                defineWinner();
            if (duelMenu.isAi && currentPlayer.getUser().getNickName().equals("ai")) {
                aiSummonOrSetLevel6Monster();
                aiSetMagicCard();
                aiDirectAttack();
                aiAttackToAttackPos();
            }
        } else {
            currentPlayer.
                    setCanDraw(true);

        }
    }
    public void aiSummonOrSetLevel6Monster(){
        for(int i=0 ; i<currentPlayer.getHand().getNumberOfCardsInHand() ; i++){
            if(currentPlayer.getMonsterCardZone().getNumberOfCard()<5 && currentPlayer.getMonsterCardZone().getNumberOfCard()>=2){
                if(currentPlayer.getHand().getZoneCards().get(i) instanceof MonsterCard &&((MonsterCard) currentPlayer.getHand().getZoneCards().get(i)).getLevel() == 6 ){
                    for(int j=0 ; j<2 ; j++){
                        for(int k=1 ; k<=5 ; k++){
                            currentPlayer.getGraveyardZone().addCardToGraveyardZone((Card) currentPlayer.getMonsterCardZone().getZoneCards().get(k));
                            currentPlayer.getMonsterCardZone().getZoneCards().remove(currentPlayer.getMonsterCardZone().getZoneCards().get(k));
                        }
                    }
                    currentPlayer.getMonsterCardZone().summonOrSetMonster((MonsterCard) currentPlayer.getHand().getZoneCards().get(i), currentPlayer);
                    ((MonsterCard) currentPlayer.getMonsterCardZone().getZoneCards().get(i)).setMode(MonsterCard.Mode.ATTACK);
                    ((MonsterCard) currentPlayer.getMonsterCardZone().getZoneCards().get(i)).setHidden(false);
                    return;
                }
            }
        }
        aiSummonOrSetLevel5Monster();
    }

    public void aiSummonOrSetLevel5Monster() {

        for (int i = 0; i < currentPlayer.getHand().getNumberOfCardsInHand(); i++) {
            if (currentPlayer.getMonsterCardZone().getNumberOfCard() < 5 && gamePlayController.getCurrentPlayer().getMonsterCardZone().getNumberOfCard() >= 1) {
                if (currentPlayer.getHand().getZoneCards().get(i) instanceof MonsterCard &&
                        ((MonsterCard) currentPlayer.getHand().getZoneCards().get(i)).getLevel() == 5) {
                    if (((MonsterCard) currentPlayer.getHand().getZoneCards().get(i)).getGameATK() >
                            ((MonsterCard) currentPlayer.getHand().getZoneCards().get(i)).getGameDEF()) {
                        for (int j = 1; j <= 5; j++) {
                            if (currentPlayer.getMonsterCardZone().getZoneCards().get(j) != null) {
                                currentPlayer.getGraveyardZone().addCardToGraveyardZone((Card) currentPlayer.getMonsterCardZone().getZoneCards().get(j));
                                currentPlayer.getMonsterCardZone().getZoneCards().remove(currentPlayer.getMonsterCardZone().getZoneCards().get(j));
                                currentPlayer.getMonsterCardZone().summonOrSetMonster((MonsterCard) currentPlayer.getHand().getZoneCards().get(i), currentPlayer);
                                ((MonsterCard) currentPlayer.getMonsterCardZone().getZoneCards().get(i)).setMode(MonsterCard.Mode.ATTACK);
                                ((MonsterCard) currentPlayer.getMonsterCardZone().getZoneCards().get(i)).setHidden(false);
                                return;
                            }
                        }
                    }else{
                        for (int j = 1; j <= 5; j++) {
                            if (currentPlayer.getMonsterCardZone().getZoneCards().get(j) != null) {
                                currentPlayer.getGraveyardZone().addCardToGraveyardZone((Card) currentPlayer.getMonsterCardZone().getZoneCards().get(j));
                                currentPlayer.getMonsterCardZone().getZoneCards().remove(currentPlayer.getMonsterCardZone().getZoneCards().get(j));
                                currentPlayer.getMonsterCardZone().summonOrSetMonster((MonsterCard) currentPlayer.getHand().getZoneCards().get(i), currentPlayer);
                                ((MonsterCard) currentPlayer.getMonsterCardZone().getZoneCards().get(i)).setMode(MonsterCard.Mode.DEFENSE);
                                ((MonsterCard) currentPlayer.getMonsterCardZone().getZoneCards().get(i)).setHidden(true);
                                return;
                            }
                        }
                    }

                }
            }
        }
        aiSummonOrSetNormalMonster();
    }

    public void aiSummonOrSetNormalMonster() {
        for (int i = 0; i < currentPlayer.getHand().getNumberOfCardsInHand(); i++) {
            if (currentPlayer.getMonsterCardZone().getNumberOfCard() < 5) {
                if (currentPlayer.getHand().getZoneCards().get(i) instanceof MonsterCard &&
                        ((MonsterCard) currentPlayer.getHand().getZoneCards().get(i)).getGameATK() > 1500 &&
                        ((MonsterCard) currentPlayer.getHand().getZoneCards().get(i)).getLevel() <= 4) {
                    //  gamePlayController.getCurrentPlayer().getMonsterCardZone().moveToFirstEmptyPlace(gamePlayController.getCurrentPlayer().getHand().getZoneCards().get(i));
                    currentPlayer.getMonsterCardZone().summonOrSetMonster((MonsterCard) currentPlayer.getHand().getZoneCards().get(i), currentPlayer);
                    ((MonsterCard) currentPlayer.getMonsterCardZone().getZoneCards().get(i)).setMode(MonsterCard.Mode.ATTACK);
                    ((MonsterCard) currentPlayer.getMonsterCardZone().getZoneCards().get(i)).setHidden(false);
                    // currentPlayer.getHand().getZoneCards().remove(currentPlayer.getHand().getZoneCards().get(i));
                    break;

                } else if (currentPlayer.getHand().getZoneCards().get(i) instanceof MonsterCard &&
                        ((MonsterCard) currentPlayer.getHand().getZoneCards().get(i)).getGameDEF() > 1500 &&
                        ((MonsterCard) currentPlayer.getHand().getZoneCards().get(i)).getLevel() <= 4) {
                    //   currentPlayer.getMonsterCardZone().moveToFirstEmptyPlace(currentPlayer.getHand().getZoneCards().get(i));
                    currentPlayer.getMonsterCardZone().summonOrSetMonster((MonsterCard) currentPlayer.getHand().getZoneCards().get(i), currentPlayer);
                    ((MonsterCard) currentPlayer.getMonsterCardZone().getZoneCards().get(i)).setMode(MonsterCard.Mode.DEFENSE);
                    ((MonsterCard) currentPlayer.getMonsterCardZone().getZoneCards().get(i)).setHidden(true);
                    // currentPlayer.getHand().getZoneCards().remove(currentPlayer.getHand().getZoneCards().get(i));
                    break;
                } else if (currentPlayer.getHand().getZoneCards().get(i) instanceof MonsterCard &&
                        ((MonsterCard) currentPlayer.getHand().getZoneCards().get(i)).getLevel() <= 4) {
                    // currentPlayer.getMonsterCardZone().moveToFirstEmptyPlace(currentPlayer.getHand().getZoneCards().get(i));
                    currentPlayer.getMonsterCardZone().summonOrSetMonster((MonsterCard) currentPlayer.getHand().getZoneCards().get(i), currentPlayer);
                    ((MonsterCard) currentPlayer.getMonsterCardZone().getZoneCards().get(i)).setMode(MonsterCard.Mode.DEFENSE);
                    ((MonsterCard) currentPlayer.getMonsterCardZone().getZoneCards().get(i)).setHidden(true);
                    //    currentPlayer.getHand().getZoneCards().remove(currentPlayer.getHand().getZoneCards().get(i));
                    break;
                }
            }
        }
    }

    public void aiSetMagicCard() {
        for (int i = 1; i <= currentPlayer.getHand().getNumberOfCardsInHand(); i++) {
            if (currentPlayer.getMagicCardZone().getNumberOfCard() < 5) {
                if (currentPlayer.getHand().getZoneCards().get(i) instanceof MagicCard) {
                    currentPlayer.getMagicCardZone().moveToFirstEmptyPlaceFromHand((MagicCard) currentPlayer.getHand().getZoneCards().get(i), currentPlayer);
                    //     currentPlayer.getMonsterCardZone().moveToFirstEmptyPlace(currentPlayer.getHand().getZoneCards().get(i));
                    ((MagicCard) currentPlayer.getMagicCardZone().getZoneCards().get(i)).setHidden(true);
                    //   currentPlayer.getHand().getZoneCards().remove(currentPlayer.getHand().getZoneCards().get(i));

                }
            }
        }
    }

    public void aiDirectAttack() {
        if (opponentPlayer.getMonsterCardZone().getNumberOfCard() == 0 && currentPlayer.getMonsterCardZone().getNumberOfCard() > 0) {
            for (int i = 1; i <= 5; i++) {
                if (currentPlayer.getMonsterCardZone().getZoneCards().get(i) != null && ((MonsterCard) currentPlayer.getMonsterCardZone().getZoneCards().get(i)).getMode() == MonsterCard.Mode.ATTACK) {
                    opponentPlayer.reduceLifePoint(((MonsterCard) currentPlayer.getMonsterCardZone().getZoneCards().get(i)).getGameATK());
                }
            }
        }
    }


    public void aiAttackToAttackPos() {
        int attacksCount = 0;
        if (opponentPlayer.getMonsterCardZone().getNumberOfCard() > 0 && currentPlayer.getMonsterCardZone().getNumberOfCard() > 0) {
            for (int i = 1; i <= 5; i++) {
                if (currentPlayer.getMonsterCardZone().getZoneCards().get(i) != null && ((MonsterCard) currentPlayer.getMonsterCardZone().getZoneCards().get(i)).getMode() == MonsterCard.Mode.ATTACK) {
                    int currentPlayerAttack = ((MonsterCard) currentPlayer.getMonsterCardZone().getZoneCards().get(i)).getGameATK();
                    for (int j = 1; j <= 5; j++) {
                        if (opponentPlayer.getMonsterCardZone().getZoneCards().get(j) != null && ((MonsterCard) opponentPlayer.getMonsterCardZone().getZoneCards().get(j)).getMode() == MonsterCard.Mode.ATTACK) {
                            int opponentPlayerAttack = ((MonsterCard) opponentPlayer.getMonsterCardZone().getZoneCards().get(j)).getGameATK();
                            if (currentPlayerAttack > opponentPlayerAttack) {
                                opponentPlayer.getGraveyardZone().addCardToGraveyardZone((MonsterCard) opponentPlayer.getMonsterCardZone().getZoneCards().get(j));
                                opponentPlayer.getMonsterCardZone().getZoneCards().remove(opponentPlayer.getMonsterCardZone().getZoneCards().get(j));
                                attacksCount += 1;
                                opponentPlayer.reduceLifePoint(currentPlayerAttack - opponentPlayerAttack);
                                break;
                            }
                        }
                    }
                }
            }
            if (attacksCount == 0) {
                aiAttackToDefensePos();
            }
        }

    }

    public void aiAttackToDefensePos() {
        for (int i = 1; i <= 5; i++) {
            if (currentPlayer.getMonsterCardZone().getZoneCards().get(i) != null && ((MonsterCard) currentPlayer.getMonsterCardZone().getZoneCards().get(i)).getMode() == MonsterCard.Mode.ATTACK) {
                int currentPlayerAttack = ((MonsterCard) currentPlayer.getMonsterCardZone().getZoneCards().get(i)).getGameATK();
                for (int j = 1; j <= 5; j++) {
                    if (opponentPlayer.getMonsterCardZone().getZoneCards().get(j) != null && ((MonsterCard) opponentPlayer.getMonsterCardZone().getZoneCards().get(j)).getMode() == MonsterCard.Mode.DEFENSE) {
                        ((MonsterCard) opponentPlayer.getMonsterCardZone().getZoneCards().get(j)).setHidden(false);
                        int opponentPlayerDefense = ((MonsterCard) opponentPlayer.getMonsterCardZone().getZoneCards().get(j)).getGameDEF();
                        if (currentPlayerAttack > opponentPlayerDefense) {
                            opponentPlayer.getGraveyardZone().addCardToGraveyardZone((MonsterCard) opponentPlayer.getMonsterCardZone().getZoneCards().get(j));
                            opponentPlayer.getMonsterCardZone().getZoneCards().remove(opponentPlayer.getMonsterCardZone().getZoneCards().get(j));
                            break;
                        } else {
                            currentPlayer.reduceLifePoint(opponentPlayerDefense - currentPlayerAttack);
                            break;
                        }
                    }
                }
            }
        }
        changeTurn();

    }

    public Boolean RPS(String firstPlayerMove, String secondPlayerMove) {
        Player winner;
        Player loser;
        if (firstPlayerMove.equals(secondPlayerMove))
            return false;
        else if ((firstPlayerMove.equals("rock") && (secondPlayerMove.equals("scissors"))) ||
                (firstPlayerMove.equals("paper") && (secondPlayerMove.equals("rock"))) ||
                (firstPlayerMove.equals("scissors") && secondPlayerMove.equals("paper"))) {
            winner = game.getFirstPlayer();
            loser = game.getSecondPlayer();
        } else if ((firstPlayerMove.equals("scissors") && (secondPlayerMove.equals("rock"))) ||
                (firstPlayerMove.equals("rock") && (secondPlayerMove.equals("paper"))) ||
                (firstPlayerMove.equals("paper") && secondPlayerMove.equals("scissors"))) {
            loser = game.getFirstPlayer();
            winner = game.getSecondPlayer();
        } else {
            System.out.println("try putting valid word!");
            return false;
        }
        game.setFirstPlayer(winner);
        game.setSecondPlayer(loser);
        currentPlayer = game.getFirstPlayer();
        opponentPlayer = game.getSecondPlayer();
        startRound();
        return true;
    }

    public void shuffle() {
        Collections.shuffle(currentPlayer.getDeckZone().getZoneCards());
        Collections.shuffle(opponentPlayer.getDeckZone().getZoneCards());
    }

    public DuelMenuResponses selectNumericZone(int cardNumber, String zoneType, String opponentOrPlayer) {
        Player player;
        if (opponentOrPlayer.equals("player")) player = currentPlayer;
        else player = opponentPlayer;
        Zone.ZoneType zoneTypeEnum;
        switch (zoneType) {
            case "monster":
                zoneTypeEnum = Zone.ZoneType.MONSTER_CARD;
                break;
            case "spell":
                zoneTypeEnum = Zone.ZoneType.MAGIC_CARD;
                break;
            case "hand":
                zoneTypeEnum = Zone.ZoneType.HAND;
                break;
            default:
                return DuelMenuResponses.INVALID_SELECTION;
        }
        if (cardNumber == 0 || cardNumber > 5)
            return DuelMenuResponses.INVALID_SELECTION;
        if (zoneTypeEnum.equals(Zone.ZoneType.HAND) && cardNumber > currentPlayer.getHand().getNumberOfCardsInHand())
            return DuelMenuResponses.INVALID_SELECTION;
        Zone zone = player.getZoneByZoneType(zoneTypeEnum);
        if (zone instanceof Hand && ((Hand) zone).getZoneCards().get(cardNumber - 1) != null) {
            selectedCard = ((Hand) zone).getZoneCards().get(cardNumber - 1);
            return DuelMenuResponses.CARD_SELECTED;
        } else if (zone instanceof NumericZone && ((NumericZone) zone).getCardByPlaceNumber(cardNumber) != null) {
            selectedCard = ((NumericZone) zone).getCardByPlaceNumber(cardNumber);
            return DuelMenuResponses.CARD_SELECTED;
        }
        return DuelMenuResponses.SELECTION_NO_CARD_FOUND;
    }

    public DuelMenuResponses showCard() {
        if (selectedCard == null) return NO_CARD_SELECTED;
        else if (selectedCard.getOwner() == opponentPlayer
                && !selectedCard.getSummoned())
            return CANNOT_ACCESS_RIVAL_CARD;
        else
            return SHOW_CARD;
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
            return DuelMenuResponses.CARD_SELECTED;
        }
    }

    public DuelMenuResponses deSelect() {
        if (selectedCard != null) {
            selectedCard = null;
            return DuelMenuResponses.CARD_DESELECTED;
        } else return DuelMenuResponses.NO_CARD_SELECTED;
    }

    public DuelMenuResponses summonCommand() {
        if (selectedCard == null)
            return DuelMenuResponses.NO_CARD_SELECTED;
        else {
            DuelMenuResponses duelMenuResponses = summon();
            selectedCard.setSummoned(true);
//            checkForEffectsAfterSummon();
            selectedCard = null;
            DuelMenu.getInstance().printString(showGameBoard());
            return duelMenuResponses;
        }
    }

    public String showGameBoard() {
        StringBuilder board = new StringBuilder();
        board.append(opponentPlayer.getUser().getNickName()).append(" : ").
                append(opponentPlayer.getLifePoint()).append("\n");
        board.append("\tc ".repeat(opponentPlayer.getHand().getNumberOfCardsInHand()));
        board.append("\n").append(opponentPlayer.getDeckZone().getZoneCards().size()).append("\n\t");
        board.append(opponentPlayer.getMagicCardZone().toStringPos(4)).append("\t").
                append(opponentPlayer.getMagicCardZone().toStringPos(2)).append("\t").
                append(opponentPlayer.getMagicCardZone().toStringPos(1)).append("\t").
                append(opponentPlayer.getMagicCardZone().toStringPos(3)).append("\t").
                append(opponentPlayer.getMagicCardZone().toStringPos(5)).append("\n\t");
        board.append(opponentPlayer.getMonsterCardZone().toStringPos(4)).append("\t").
                append(opponentPlayer.getMonsterCardZone().toStringPos(2)).append("\t").
                append(opponentPlayer.getMonsterCardZone().toStringPos(1)).append("\t").
                append(opponentPlayer.getMonsterCardZone().toStringPos(3)).append("\t").
                append(opponentPlayer.getMonsterCardZone().toStringPos(5)).append("\n");
        board.append(opponentPlayer.getGraveyardZone().getZoneCards().size()).append("\t\t\t\t\t\t").
                append(opponentPlayer.getFieldZone().toStringPos()).append("\n");
        board.append("\n------------------------------------------\n\n");
        board.append(currentPlayer.getFieldZone().toStringPos()).append("\t\t\t\t\t\t").
                append(currentPlayer.getGraveyardZone().getZoneCards().size()).append("\n\t");
        board.append(currentPlayer.getMonsterCardZone().toStringPos(5)).append("\t").
                append(currentPlayer.getMonsterCardZone().toStringPos(3)).append("\t").
                append(currentPlayer.getMonsterCardZone().toStringPos(1)).append("\t").
                append(currentPlayer.getMonsterCardZone().toStringPos(2)).append("\t").
                append(currentPlayer.getMonsterCardZone().toStringPos(4)).append("\n\t");
        board.append(currentPlayer.getMagicCardZone().toStringPos(5)).append("\t").
                append(currentPlayer.getMagicCardZone().toStringPos(3)).append("\t").
                append(currentPlayer.getMagicCardZone().toStringPos(1)).append("\t").
                append(currentPlayer.getMagicCardZone().toStringPos(2)).append("\t").
                append(currentPlayer.getMagicCardZone().toStringPos(4)).append("\n");
        board.append("  \t\t\t\t\t\t").append(currentPlayer.getDeckZone().getZoneCards().size()).append("\n");
        board.append("c \t".repeat(currentPlayer.getHand().getNumberOfCardsInHand()));
        board.append("\n").append(currentPlayer.getUser().getNickName()).append(" : ").
                append(currentPlayer.getLifePoint());
        //DuelMenu.getInstance().printString(board.toString());
        return board.toString();
    }

    public DuelMenuResponses summon() {
//        if (selectedCard instanceof MonsterCard) System.out.println("monster");
//        else if (selectedCard instanceof  MagicCard) System.out.println("magic");
//        else System.out.println("what");
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
        if (selectedCard.getCardName().equals("Gate Guardian")) {
            if (monsterEffectController.gateGuardian())
                return CARD_SUMMONED;
        }
        if (((MonsterCard) selectedCard).getLevel() <= 4) {
            doSummon();
            //selectedCard = null;
            return DuelMenuResponses.CARD_SUMMONED;
        }
        if (((MonsterCard) selectedCard).getLevel() == 5 || ((MonsterCard) selectedCard).getLevel() == 6) {
            if (selectedCard.getCardName().equals("The Tricky")) {
                boolean ans = monsterEffectController.theTricky((MonsterCard) selectedCard);
                if (ans) return EFFECT_DONE_SUCCESSFULLY;
            }
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
                duelMenu.printResponse(GET_TWO_NUMBERS_TO_BE_TRIBUTE);
                if (selectedCard.getCardName().equals("Beast King Barbaros")) {
                    boolean ans = monsterEffectController.BeastKingBarbaros();
                    if (!ans) return EFFECT_DONE_SUCCESSFULLY;
                }
                return twoMonsterTribute();
            }
        }
    }


    public DuelMenuResponses oneMonsterTribute() {
        duelMenu.printResponse(ENTER_ONE_NUMBER);
        int num = duelMenu.getNum();
        if (currentPlayer.getMonsterCardZone().getCardByPlaceNumber(num) == null)
            return DuelMenuResponses.ONE_TRIBUTE_NO_MONSTER;
        else currentPlayer.getMonsterCardZone().moveCardToGraveyard(num, currentPlayer);
        doSummon();
        //selectedCard = null;
        return DuelMenuResponses.CARD_SUMMONED;
    }

    public DuelMenuResponses twoMonsterTribute() {
        duelMenu.printResponse(ENTER_FIRST_NUMBER);
        int firstAddress = duelMenu.getNum();
        duelMenu.printResponse(ENTER_SECOND_NUMBER);
        int secondAddress = duelMenu.getNum();
        if (currentPlayer.getMonsterCardZone().getCardByPlaceNumber(firstAddress) == null || currentPlayer.getMonsterCardZone().getCardByPlaceNumber(secondAddress) == null)
            return DuelMenuResponses.TWO_TRIBUTE_NO_MONSTER;
        currentPlayer.getMonsterCardZone().moveCardToGraveyard(firstAddress, currentPlayer);
        currentPlayer.getMonsterCardZone().moveCardToGraveyard(secondAddress, currentPlayer);
        doSummon();
        //selectedCard = null;
        return DuelMenuResponses.CARD_SUMMONED;
    }

    public DuelMenuResponses setCommand() {
        if (selectedCard == null)
            return DuelMenuResponses.NO_CARD_SELECTED;
        else {
            DuelMenuResponses duelMenuResponses = set();
            selectedCard = null;
            DuelMenu.getInstance().printString(showGameBoard());
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
        if (selectedCard.getCardName().equals("Gate Guardian"))
            return CANT_NORMAL_SET_THIS_MONSTER;
        currentPlayer.getMonsterCardZone().summonOrSetMonster((MonsterCard) selectedCard, currentPlayer);
        setSetCard(selectedCard);
        summonedOrSetMonstersInTurn.add((MonsterCard) selectedCard);
        selectedCard.setHidden(true);
        ((MonsterCard) selectedCard).setMode(MonsterCard.Mode.DEFENSE);
        selectedCard = null;
        return DuelMenuResponses.CARD_SET_SUCCESSFULLY;
    }

    public DuelMenuResponses setPosCommand(String wantedPosition) {
        if (selectedCard == null)
            return DuelMenuResponses.NO_CARD_SELECTED;
        else {
            DuelMenuResponses duelMenuResponses = setPosition(wantedPosition);
            selectedCard = null;
            return duelMenuResponses;
        }
    }

    public DuelMenuResponses setPosition(String wantedPosition) {
        // kiarash goft
        if (isMonsterSummonedOrSetInThisTurn((MonsterCard) selectedCard) ||
                attackController.alreadyAttackedThisTurn((MonsterCard) selectedCard))
            return CANT_CHANGE_THIS_CARD_POSITION;
        else if (selectedCard.getCardPlacedZone() != currentPlayer.getMonsterCardZone() ||
                !(selectedCard instanceof MonsterCard))
            return DuelMenuResponses.CANT_CHANGE_THIS_CARD_POSITION;
        else if (Game.getPhases().get(currentPhaseNumber) != Phase.PhaseLevel.MAIN1 &&
                Game.getPhases().get(currentPhaseNumber) != Phase.PhaseLevel.MAIN2)
            return DuelMenuResponses.CANT_DO_THIS_ACTION_IN_THIS_PHASE;
        else if ((wantedPosition.equals("attack") && ((MonsterCard) selectedCard).getMode() == MonsterCard.Mode.ATTACK)
                || (wantedPosition.equals("defense") && ((MonsterCard) selectedCard).getMode() == MonsterCard.Mode.DEFENSE))
            return ALREADY_WANTED_POSITION;
        else if (((MonsterCard) selectedCard).toStringPosition().equals("DH"))
            return CANT_CHANGE_HIDDEN_CARD_POSITION;
        else if (isSelectCardChangedBefore())
            return ALREADY_CHANGED_POSITION;
        if (wantedPosition.equals("defense"))
            ((MonsterCard) selectedCard).setMode(MonsterCard.Mode.DEFENSE);
        else if (wantedPosition.equals("attack")) {
            selectedCard.setHidden(false);
            ((MonsterCard) selectedCard).setMode(MonsterCard.Mode.ATTACK);
        }
        changedPositionCardsInTurn.add(selectedCard);
        selectedCard = null;
        return MONSTER_CARD_POSITION_CHANGED_SUCCESSFULLY;
    }

    public DuelMenuResponses setSpell() {
        if (currentPlayer.getMagicCardZone().getNumberOfCard() == 5)
            return SPELL_ZONE_CARD_IS_FULL;
        currentPlayer.getMagicCardZone().moveToFirstEmptyPlaceFromHand((MagicCard) selectedCard, currentPlayer);
        selectedCard.setHidden(true);
        setSpellCardsInTurn.add((MagicCard) selectedCard);
        setTrapAndSpellCardsInTurn.add((MagicCard) selectedCard);
        selectedCard = null;
        return CARD_SET_SUCCESSFULLY;
    }

    public DuelMenuResponses setTrap() {
        if (currentPlayer.getMagicCardZone().getNumberOfCard() == 5)
            return SPELL_ZONE_CARD_IS_FULL;
        currentPlayer.getMagicCardZone().moveToFirstEmptyPlaceFromHand((MagicCard) selectedCard, currentPlayer);
        selectedCard.setHidden(true);
        setTrapCardsInTurn.add((MagicCard) selectedCard);
        setTrapAndSpellCardsInTurn.add((MagicCard) selectedCard);
        selectedCard = null;
        return CARD_SET_SUCCESSFULLY;
    }

    public DuelMenuResponses flipSummonCommand() {
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
        else if (!((MonsterCard) selectedCard).toStringPosition().equals("DH") ||
                isMonsterSummonedOrSetInThisTurn((MonsterCard) selectedCard))
            return CANT_FLIP_SUMMON;
        ((MonsterCard) selectedCard).setMode(MonsterCard.Mode.ATTACK);
        selectedCard.setHidden(false);
        checkForMonsters((MonsterCard) selectedCard);
        if (selectedCard.getCardName().equals("Man-Eater Bug") && !selectedCard.isActivated())
            monsterEffectController.manEaterBug((MonsterCard) selectedCard);
        selectedCard = null;
        return FLIP_SUMMONED_SUCCESSFULLY;
    }

    public void checkForEffectsAfterSummon() {
        if (gamePlayController.ifPlayerHasThisCardGiveIt(gamePlayController.getCurrentPlayer(),
                "Torrential Tribute") != null) {
            DuelMenu.getInstance().doYouWannaActivateSpecialCard("Torrential Tribute");
            if (getAnswer()) {
                GamePlayController.getTrapEffectController().torrentialTribute(gamePlayController.ifPlayerHasThisCardGiveIt(gamePlayController.getCurrentPlayer(), "Torrential Tribute"));
            }
        }
        if (gamePlayController.ifPlayerHasThisCardGiveIt(gamePlayController.getOpponentPlayer(), "Trap Hole") != null &&
                ((MonsterCard) selectedCard).getGameATK() >= 1000) {
            gamePlayController.changeTurn();
            DuelMenu.getInstance().doYouWannaActivateSpecialCard("Trap Hole");
            if (getAnswer()) {
                GamePlayController.getTrapEffectController().trapHole(gamePlayController.ifPlayerHasThisCardGiveIt(gamePlayController.getCurrentPlayer(), "Trap Hole"));
            }
            gamePlayController.changeTurn();
        }
        if (gamePlayController.ifPlayerHasThisCardGiveIt(gamePlayController.getOpponentPlayer(), "Torrential Tribute") != null) {
            gamePlayController.changeTurn();
            DuelMenu.getInstance().doYouWannaActivateSpecialCard("Torrential Tribute");
            if (getAnswer()) {
                GamePlayController.getTrapEffectController().torrentialTribute(gamePlayController.ifPlayerHasThisCardGiveIt(gamePlayController.getCurrentPlayer(), "Torrential Tribute"));
            }
            gamePlayController.changeTurn();
        }
    }

    public boolean getAnswer() {
        String ans = duelMenu.getString();
        if (ans.equals("yes"))
            return true;
        else return false;

    }

    public void activateSpellCard() { //TODO COMPLETE
        if (selectedCard == null)
            duelMenu.printResponse(NO_CARD_SELECTED);
        else if (!(selectedCard instanceof MagicCard))
            duelMenu.printResponse(ACTIVATE_EFFECT_ONLY_ON_SPELL);
        else if (Game.getPhases().get(currentPhaseNumber) != Phase.PhaseLevel.MAIN1 &&
                Game.getPhases().get(currentPhaseNumber) != Phase.PhaseLevel.MAIN2)
            duelMenu.printResponse(CANT_ACTIVATE_EFFECT_ON_THIS_TURN);
        else if (selectedCard.isActivated())
            duelMenu.printResponse(YOU_ALREADY_ACTIVATED_THIS_CARD);
        else if (selectedCard.getCardPlacedZone() == currentPlayer.getHand() && ((MagicCard) selectedCard).getCardIcon() != MagicCard.CardIcon.FIELD
                && currentPlayer.getMagicCardZone().getNumberOfCard() == 5)
            duelMenu.printResponse(SPELL_ZONE_CARD_IS_FULL);
        else if (((MagicCard) selectedCard).getCardIcon() == MagicCard.CardIcon.FIELD)
            currentPlayer.getFieldZone().moveCardToFieldZone((MagicCard) selectedCard, currentPlayer);
        spellEffectController.setDoIt(false);
        trapEffectController.setDoIt(false);

        if (chainCards.isEmpty())
            mainCurrentPlayer = currentPlayer;

        if (chainCards.isEmpty() || canContinueTheChain(selectedCard)) {
            addSelectedCardToChain();
            callSpellOrTrap((MagicCard) selectedCard, currentPlayer);
            if (!selectedCard.isActivated()) {
                duelMenu.printResponse(PREPARATIONS_OF_THIS_SPELL_ARE_NOT_DONE_YET);
                return;
            }
        } else {
            duelMenu.printResponse(CANT_ADD_THIS_CARD_TO_CHAIN);
            selectedCard = null;
        }
        selectedCard = null;
        if (mainCurrentPlayer == currentPlayer) {
            if (canMakeChain(opponentPlayer)) {
                changeTurn();
                if (!askForActivatingInRivalsTurn()) {
                    changeTurn();
                    if (canMakeChain(currentPlayer)) {
                        if (!askForActivatingInRivalsTurn())
                            doChainActions();
                    } else doChainActions();
                }

            }
        } else if (opponentPlayer == mainCurrentPlayer) {
            changeTurn();
            if (canMakeChain(currentPlayer)) {
                if (!askForActivatingInRivalsTurn())
                    doChainActions();
            } else doChainActions();
        }
    }


    public void addSelectedCardToChain() {
        chainCards.add((MagicCard) selectedCard);
        chainPlayers.add(currentPlayer);
    }

    public boolean canContinueTheChain(Card card) {
        int lastSpeed = getMagicCardSpeed(chainCards.get(chainCards.size() - 1));
        int cardSpeed = getMagicCardSpeed((MagicCard) card);
        if (cardSpeed != 1 && cardSpeed >= lastSpeed)
            return true;
        else return false;
    }

    public void doChainActions() {
        for (int i = chainCards.size() - 1; i >= 0; i--) {
            spellEffectController.setDoIt(true);
            trapEffectController.setDoIt(true);
            callSpellOrTrap(chainCards.get(i), chainPlayers.get(i));
        }
        chainCards.clear();
        duelMenu.setCantDoThisKindsOfMove(true);
    }


    public Boolean canMakeChain(Player player) {
        Map<Integer, MagicCard> magics = player.getMagicCardZone().getZoneCards();
        for (int i = 1; i <= 5; i++) {
            if (canContinueTheChain(magics.get(i)) && !magics.get(i).isActivated())
                return true;
        }
        return false;
    }


    public int getMagicCardSpeed(MagicCard card) {
        if (card.getMagicType() == MagicCard.MagicType.TRAP && card.getCardIcon() == COUNTER)
            return 3;
        else if (card.getMagicType() == MagicCard.MagicType.TRAP)
            return 2;
        else if (card.getMagicType() == MagicCard.MagicType.SPELL && card.getCardIcon() == QUICK_PLAY)
            return 2;
        else return 1;
    }

    public void activateCard(Card card) {
        card.setHidden(false);
        card.setActivated(true);
        activatedCards.put(currentPlayer, card);
        duelMenu.printResponse(SPELL_ACTIVATED);

    }


    public void activateSelectedCard() {
        selectedCard.setHidden(false);
        selectedCard.setActivated(true);
        activatedCards.put(currentPlayer, selectedCard);
        if (selectedCard.getCardPlacedZone() == currentPlayer.getHand())
            currentPlayer.getMagicCardZone().moveToFirstEmptyPlaceFromHand((MagicCard) selectedCard, currentPlayer);
        duelMenu.printResponse(SPELL_ACTIVATED);
    }

    public void callSpellOrTrap(MagicCard card, Player player) {  //TODO
        String cardName = card.getCardName();
        switch (cardName) {
            case "Yami":
                spellEffectController.yami(true);
                break;
            case "Forset":
                spellEffectController.forest(true);
                break;
            case "Closed Forest":
                spellEffectController.closedForest(true);
                break;
            case "Umiiruka":
                spellEffectController.umiiruka(true);
                break;
            case "Sword of Dark Destruction":
                spellEffectController.swordOfDarkDestruction(card);
                break;
            case "Black Pendant":
                spellEffectController.blackPendant(card);
                break;
            case "United We Stand":
                spellEffectController.unitedWeStand(card);
                break;
            case "Magnum Shield":
                spellEffectController.magnumShield(card);
                break;
            case "Terraforming":
                spellEffectController.terraforming(card);
                break;
            case "Pot of Greed":
                spellEffectController.potOfGReed(card);
                break;
            case "Raigeki":
                spellEffectController.raigeki(card);
                break;
            case "Harpie's Feather Duster":
                spellEffectController.harpiesFeatherDuster(card);
                break;
            case "Dark Hole":
                spellEffectController.darkHole(card);
                break;
            case " Mystical Space Typhoon":
                spellEffectController.mysticalSpaceTyphoon(card, player);
                break;
            case "Ring of defense":
                spellEffectController.ringOfDefense(card);
                break;
            case "Twin Twisters":
                spellEffectController.twinTwisters(card, player);
                break;
            case "Change of Heart":
                spellEffectController.changeOfHeart(card);
                break;
            case "Monster Reborn":
                spellEffectController.monsterReborn(card);
                break;
            case "Advanced Ritual Art":
                spellEffectController.advancedRitualArt(card);
                break;
            case "Mind Crush":
                trapEffectController.mindCrush(card);
                break;
            case "Time Seal":
                trapEffectController.timeSeal(card);
                break;
            case "Magic Jammer":
                trapEffectController.magicJammer(card);
                break;
            default:
                break;
        }
    }

    public boolean askForActivatingInRivalsTurn() {
        duelMenu.setCantDoThisKindsOfMove(true);
        duelMenu.showRivalTurn(currentPlayer.getUser().getUserName(), showGameBoard());
        duelMenu.printResponse(DO_YOU_WANT_ACTIVATE_SPELL_AND_TRAP);
        String ans = duelMenu.getString();
        if (ans.equals("no")) {
            return false;
        } else return true;
    }


    public Boolean doPlayerHasThisCard(Player player, String name) {
        Map<Integer, MonsterCard> monsterZone = player.getMonsterCardZone().getZoneCards();
        Map<Integer, MagicCard> magicZone = player.getMagicCardZone().getZoneCards();
        for (int i = 1; i <= 5; i++) {
            if (magicZone.get(i).getCardName().equals(name))
                return true;
            else if (monsterZone.get(i).getCardName().equals(name))
                return true;
        }
        return false;
    }

    public Card ifPlayerHasThisCardGiveIt(Player player, String name) {
        Map<Integer, MonsterCard> monsterZone = player.getMonsterCardZone().getZoneCards();
        Map<Integer, MagicCard> magicZone = player.getMagicCardZone().getZoneCards();
        for (int i = 1; i <= 5; i++) {
            if (magicZone.get(i) != null && magicZone.get(i).getCardName().equals(name))
                return magicZone.get(i);
            else if (monsterZone.get(i) != null && monsterZone.get(i).getCardName().equals(name))
                return monsterZone.get(i);
        }
        return null;
    }

    public DuelMenuResponses normalAttack(int number) {
        DuelMenuResponses responses = attackController.normalAttack(number);
        selectedCard = null;
        DuelMenu.getInstance().printString(showGameBoard());
        return responses;
    }

    public DuelMenuResponses directAttack() {
        DuelMenuResponses responses = attackController.directAttack();
        selectedCard = null;
        DuelMenu.getInstance().printString(showGameBoard());
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
        DuelMenu.getInstance().printString(showGameBoard());
        if (currentPhaseNumber == 5) {
            currentPhaseNumber = 0;
            return DuelMenuResponses.RIVALS_TURN_AND_SHOW_DRAW_PHASE;
        } else {
            currentPhaseNumber++;
            if (Game.getPhases().get(currentPhaseNumber).equals(Phase.PhaseLevel.MAIN1))
                checkHeraldOfCreation();
            return DuelMenuResponses.SHOW_NEW_PHASE;
        }
    }

    public void checkHeraldOfCreation() {
        Map<Integer, MonsterCard> monsterZone = currentPlayer.getMonsterCardZone().getZoneCards();
        for (int i = 1; i <= 5; i++) {
            if (monsterZone.get(i).getCardName().equals("Herald of Creation") && !monsterZone.get(i).getHidden()) {
                if (monsterEffectController.checkHeraldOfCreation()) {
                    monsterZone.get(i).setActivated(true);
                    duelMenu.printString("Herald of Creation is activated!");
                    monsterEffectController.heraldOfCreation();
                }
            }

        }

    }


    public void changeTurn() {
        if (game.getFirstPlayer() == currentPlayer) {
            currentPlayer = game.getSecondPlayer();
            opponentPlayer = game.getFirstPlayer();
        } else {
            currentPlayer = game.getFirstPlayer();
            opponentPlayer = game.getSecondPlayer();
        }
    }

    public void doSummon() {
        selectedCard.setHidden(false);
        ((MonsterCard) selectedCard).setMode(MonsterCard.Mode.ATTACK);
        currentPlayer.getMonsterCardZone().summonOrSetMonster((MonsterCard) selectedCard, currentPlayer);
        summonedOrSetMonstersInTurn.add((MonsterCard) selectedCard);
        checkForMonsters((MonsterCard) selectedCard);
    }


    public void checkForMonsters(MonsterCard monsterCard) {
        if (monsterCard.getCardName().equals("The Calculator") && !monsterCard.isActivated())
            monsterEffectController.theCalculator(monsterCard);
        if (monsterCard.getCardName().equals("Command Knight") && !monsterCard.isActivated())
            monsterEffectController.commandKnight(true, monsterCard);
        if (monsterCard.getCardName().equals("Terratiger, the Empowered Warrior") && monsterEffectController.checkTerratigerTheEmpoweredWarrior())
            monsterEffectController.terratigertheEmpoweredWarrior();
    }

    public void refresh() {
        suijinVictimsReset();
        effectController.removeControl();
        chainCards.clear();
        attackController.getCardsShouldBeUsedOnce().clear();
        changedPositionCardsInTurn.clear();
        summonedOrSetMonstersInTurn.clear();
        setSpellCardsInTurn.clear();
        setTrapCardsInTurn.clear();
        setTrapAndSpellCardsInTurn.clear();
        attackController.getAttackedCardsInTurn().clear();
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
        for (Map.Entry<Player, Card> card : activatedCards.entrySet()) {
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


    public Boolean checkIfGameIsFinished() {
        return (currentPlayer.getLifePoint() <= 0 || opponentPlayer.getLifePoint() <= 0 || currentPlayer.getDeckZone().getZoneCards().size() == 0 ||
                opponentPlayer.getDeckZone().getZoneCards().size() == 0);
    }

    public void defineStarter(Player winner, Player loser) {
        String ans = duelMenu.defineStarterOfNextRound(winner.getUser().getUserName());
        if (ans.equals("yes")) {
            currentPlayer = loser;
            opponentPlayer = winner;
        } else {
            currentPlayer = winner;
            opponentPlayer = loser;
        }
        duelMenu.startNewRound(currentPlayer.getUser().getUserName());
        startRound();
    }


    public void defineWinner() {
        Player winner;
        Player loser;
        if (currentPlayer.getLifePoint() <= 0 || currentPlayer.getDeckZone().getZoneCards().size() == 0) {
            winner = currentPlayer;
            loser = opponentPlayer;
        } else {
            winner = opponentPlayer;
            loser = currentPlayer;
        }
        game.getWinnerOfEachRound()[game.getRoundCount() - 1] = winner;

        if (game.getRoundNumber() == 1) {
            game.getWinnerOfEachRound()[0] = winner;
            game.giveAwardOneRound(winner, loser);
            duelMenu.matchFinished(winner.getUser().getUserName(), 1000);
        } else {
            if (game.getRoundCount() == 1) //TODO ASK IF IT IS OK
            {
                game.getWinnerOfEachRound()[0] = winner;
                game.getFirstPlayerLifePointEachRound().add(game.getFirstPlayer().getLifePoint());
                game.getSecondPlayerLifePointEachRound().add(game.getSecondPlayer().getLifePoint());
                duelMenu.roundFinished(winner.getUser().getUserName());
                defineStarter(winner, loser);
            } else if (game.getRoundCount() == 2) {
                if (game.getWinnerOfEachRound()[0] == winner) {
                    game.getWinnerOfEachRound()[1] = winner;
                    game.getFirstPlayerLifePointEachRound().add(game.getFirstPlayer().getLifePoint());
                    game.getSecondPlayerLifePointEachRound().add(game.getSecondPlayer().getLifePoint());
                    game.giveAwardThreeRounds(winner, loser);
                    duelMenu.matchFinished(winner.getUser().getUserName(), 3000);
                } else {
                    game.getWinnerOfEachRound()[1] = winner;
                    game.getFirstPlayerLifePointEachRound().add(game.getFirstPlayer().getLifePoint());
                    game.getSecondPlayerLifePointEachRound().add(game.getSecondPlayer().getLifePoint());
                    duelMenu.roundFinished(winner.getUser().getUserName());
                    defineStarter(winner, loser);
                }
            } else {
                game.getWinnerOfEachRound()[2] = winner;
                game.getFirstPlayerLifePointEachRound().add(game.getFirstPlayer().getLifePoint());
                game.getSecondPlayerLifePointEachRound().add(game.getSecondPlayer().getLifePoint());
                game.giveAwardThreeRounds(winner, loser);
                duelMenu.matchFinished(winner.getUser().getUserName(), 3000);
            }
        }
    }

    public Boolean isSpellSSetInThisTurn(MagicCard magicCard) {
        for (MagicCard magicCard1 : setSpellCardsInTurn) {
            if (magicCard == magicCard1)
                return true;
        }
        return false;
    }

    public Boolean isTrapSetInThisTurn(MagicCard magicCard) {
        for (MagicCard magicCard1 : setTrapCardsInTurn) {
            if (magicCard1 == magicCard)
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

    public void suijinVictimsReset() {
        for (Map.Entry<MonsterCard, Integer> entry : suijinVictims.entrySet()) {
            entry.getKey().setGameATK(entry.getValue() + entry.getKey().getGameATK());
        }
        suijinVictims.clear();
    }

    public Player getTheOtherPlayer(Player player) {
        if (player == currentPlayer)
            return opponentPlayer;
        else return currentPlayer;

    }


}

