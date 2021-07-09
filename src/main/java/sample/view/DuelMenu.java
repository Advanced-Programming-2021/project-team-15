package sample.view;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Main;
import sample.controller.gamePlayController.AttackController;
import sample.controller.gamePlayController.GamePlayController;
import sample.controller.menuController.MenuController;
import sample.controller.responses.DuelMenuResponses;
import sample.controller.utilizationController.AudioController;
import sample.controller.utilizationController.UtilityController;
import sample.model.Game;
import sample.model.Player;
import sample.model.cards.Card;
import sample.model.cards.MagicCard;
import sample.model.cards.MonsterCard;
import sample.model.zones.Zone;

import java.io.IOException;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static sample.controller.responses.DuelMenuResponses.SHOW_CARD;

public class DuelMenu {
    private static final GamePlayController gamePlayController;
    private static DuelMenu duelMenu;
    private static Image backOfCard = new Image(String.valueOf(DuelMenu.class.
            getResource("/Images/cardAnimeGirl.jpg" )));

    static {
        gamePlayController = GamePlayController.getInstance();
    }

    DuelMenuResponses duelMenuResponses;
    private boolean weAreOnGame = false;
    private String secondUsername;
    private ImageView[][] playerCards ;
    private ImageView[][] opponentCards;
    private Boolean cantDoThisKindsOfMove = false;
    @FXML
    private GridPane firstPlayerBoardCards;
    @FXML
    private GridPane secondPlayerBoardCards;
    @FXML
    private Label phaseNameShow;
    @FXML
    private Label currentPlayerLifePoint;
    @FXML
    private ProgressBar currentPlayerLifePointProgressBar;
    @FXML
    private Label currentPlayerName;
    @FXML
    private ImageView currentPlayerPic;
    @FXML
    private Label opponentPlayerLifePoint;
    @FXML
    private ProgressBar opponentPlayerLifePointProgressBar;
    @FXML
    private Label opponentPlayerName;
    @FXML
    private ImageView opponentPlayerPic;
    @FXML
    private Label selectedCardDescription;
    @FXML
    private ImageView selectedCardPic;

    public static DuelMenu getInstance() {
        if (duelMenu == null)
            duelMenu = new DuelMenu();
        return duelMenu;
    }
    public void initialGame() {
        isPause = false;
        refreshPlayersBox();
        initializeBoard();
        runAndUpdate();
        startTimerAndRun();
    }
    public void initializeBoard() {
         playerCards = new ImageView[2][5];
         opponentCards = new ImageView[2][5] ;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 5; j++) {
                ImageView imageView = new ImageView();
                imageView.setFitHeight(100);
                imageView.setFitWidth(80);
                playerCards[i][j]=imageView;
                setOnMouseClickedForCardImage(imageView,"player");
                firstPlayerBoardCards.add(imageView,j,i);
                firstPlayerBoardCards.setHgap(27);
                ImageView imageView1 = new ImageView();
                imageView1.setFitHeight(100);
                imageView1.setFitWidth(80);
                opponentCards[i][j]=imageView1;
                setOnMouseClickedForCardImage(imageView1,"opponent");
                secondPlayerBoardCards.add(imageView1,j,i);
                secondPlayerBoardCards.setHgap(27);}
        }
    }
    private Timer timer;
    private boolean isPause;
    public void startTimerAndRun()
    { timer = new java.util.Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        if(!isPause)
                            runAndUpdate();

                    }
                });
            }
        };
        long frameTimeInMilliseconds = (long)(200.0);
        timer.schedule(timerTask, 0, frameTimeInMilliseconds);

    }
    public void runAndUpdate()
    {  setPlayersCards(gamePlayController.getCurrentPlayer(),playerCards);
       setPlayersCards(gamePlayController.getOpponentPlayer(),opponentCards);
    }

    public void setPlayersCards(Player player ,ImageView[][] imageViews)
    {   for (int i = 0; i < 2; i++) {
        for (int j = 0; j < 5; j++) {
            if( i==0 && !player.getMagicCardZone().toStringPos(i+1).equals("E"))
            { if(player.getMagicCardZone().toStringPos(i+1).equals("O"))
            { MagicCard magicCard= (MagicCard) player.getMagicCardZone().getZoneCards().get(i + 1);
                imageViews[i][j].setImage(magicCard.getCardImage());
            }
            else  imageViews[i][j].setImage(backOfCard);
            }
            else if( i==1 && !player.getMonsterCardZone().toStringPos(i+1).equals("E"))
            {
                MonsterCard monsterCard  = (MonsterCard) player.getMonsterCardZone().getZoneCards().get(i+1);
                if(monsterCard.getHidden())
                    imageViews[i][j].setImage(backOfCard);
                else   imageViews[i][j].setImage(monsterCard.getCardImage());
            } else  imageViews[i][j].setImage(null);
        }
    }
    }



    public void settingButtonClicked(MouseEvent mouseEvent)
    {

    }
    public void mainMenuButtonClicked() throws IOException {
        AudioController.playMenu();
        Scene mainMenuScene = new Scene(FXMLLoader.load(getClass().getResource("/FxmlFiles/MainMenu.fxml")));
        Main.stage.setScene(mainMenuScene);
    }

    public void newDuelButtonClicked(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FxmlFiles/PopUp.fxml"));
        Scene scene = new Scene(loader.load());
        PopupController popupController = loader.getController();
        Stage popupStage = new Stage();
        popupController.setStage(popupStage);
        popupStage.initModality(Modality.WINDOW_MODAL);
        popupStage.setScene(scene);
        popupController.initialize();
        popupStage.showAndWait();
    }

    public boolean isWeAreOnGame() {
        return weAreOnGame;
    }

    public void setWeAreOnGame(boolean weAreOnGame) {
        this.weAreOnGame = weAreOnGame;
    }

    public Boolean getCantDoThisKindsOfMove() {
        return cantDoThisKindsOfMove;
    }

    public void setCantDoThisKindsOfMove(Boolean cantDoThisKindsOfMove) {
        this.cantDoThisKindsOfMove = cantDoThisKindsOfMove;
    }

    public void nextPhase() {
        printResponse(gamePlayController.goNextPhase());
        refreshPhaseBox();
    }

    public void refreshPhaseBox() {
        phaseNameShow.setText(Game.getPhases().get(gamePlayController.getCurrentPhaseNumber()).getName());
    }

    public void refreshPlayersBox() {
        currentPlayerName.setText(gamePlayController.getCurrentPlayer().getUser().getNickName());
        currentPlayerLifePoint.setText(String.valueOf(gamePlayController.getCurrentPlayer().getLifePoint()));
        currentPlayerLifePointProgressBar.setProgress((double) gamePlayController.getCurrentPlayer().getLifePoint()/8000);
        opponentPlayerName.setText(gamePlayController.getOpponentPlayer().getUser().getNickName());
        opponentPlayerLifePoint.setText(String.valueOf(gamePlayController.getOpponentPlayer().getLifePoint()));
        opponentPlayerLifePointProgressBar.setProgress((double) gamePlayController.getOpponentPlayer().getLifePoint()/8000);
        if (gamePlayController.getSelectedCard()==null) {
            selectedCardPic.setImage(backOfCard);
            selectedCardDescription.setText("Select a card...");
        }
        else {
            selectedCardPic.setImage(gamePlayController.getSelectedCard().getCardImage());
            selectedCardDescription.setText(gamePlayController.getSelectedCard().getCardDescription());
        }
        if (gamePlayController.getCurrentPlayer().getLifePoint()<=2000) AudioController.playHeartbeat();
    }
    public Node getNodeByCoordinate(Integer row, Integer column, GridPane gridPane) {
        for (Node node :gridPane.getChildren()) {
            if(GridPane.getColumnIndex(node) == row && GridPane.getColumnIndex(node) == column){
                return node;
            }
        }
        return null;
    }
    private void setDeckZone()
    {

    }

    private void setCardsInGrid(GridPane playerCardsInBoard ,ImageView[][] imageView) {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 5; j++) {
                playerCardsInBoard.add(imageView[i][j], j, i);
                playerCardsInBoard.setHgap(27);
            }
        }
    }
    public void setOnMouseClickedForCardImage(ImageView imageView ,String  opponentOrPlayer)
    {   imageView.setOnMouseClicked(mouseEvent -> {
        DuelMenuResponses duelMenuResponses;
        int row = GridPane.getRowIndex(imageView);
        int column = GridPane.getColumnIndex(imageView);
        if (row == 0) duelMenuResponses = gamePlayController.selectNumericZone(column+1,"spell",opponentOrPlayer);
        else duelMenuResponses =  gamePlayController.selectNumericZone(column+1,"monster",opponentOrPlayer);
        if(duelMenuResponses.equals(DuelMenuResponses.CARD_SELECTED));
        if(gamePlayController.showCard().equals(SHOW_CARD))
            selectedCardPic.setImage(gamePlayController.getSelectedCard().getCardImage());
    });
    }
//    @Override
//    public void scanInput() {
//        while (true) {
//            String input = UtilityController.getNextLine();
//            if (input.equals("menu exit")) checkAndCallMenuExit();
//            else if (input.startsWith("duel") && input.contains(" --ai")) checkAndCallNewAiDuel(input);
//            else if (input.startsWith("duel")) {
//                checkAndCallNewDuel(input);
//                if (weAreOnGame) {
//                    printResponse(gamePlayController.goNextPhase());
//                    break;
//                }
//            } else if (regexController.showMenuRegex(input)) checkAndCallShowCurrentMenu();
//            else System.out.println("invalid command");
//            if (super.isExit) {
//                super.isExit = false;
//                return;
//            }
//        }
//        while (true) {
//            String input = UtilityController.getNextLine();
//            if (cantDoThisKindsOfMove && !input.equals("activate effect") && !input.startsWith("select") && !input.equals("card show")) {
//                System.out.println("you can't do this kind of moves");
//                continue;
//            }
//            if (input.equals("menu exit")) checkAndCallMenuExit();
//            else if (input.equals("show graveyard")) showGraveYard();
//            else if (input.equals("surrender")) gamePlayController.surrender();
//            else if (input.matches("duel set-winner (\\.+)")) {
//                Matcher matcher = Pattern.compile("duel set-winner (\\.+)").matcher(input);
//                if (matcher.find()) gamePlayController.cheatAndWin(matcher.group(1));
//            } else if (input.matches("select(.*)(\\d)(.*)")) checkAndCallSelectNumericZone(input);
//            else if (input.equals("select -d")) checkAndCallDeselect(input);
//            else if(input.matches("increase LP (\\d+)")) increaseLp(input);
//            else if (input.startsWith("select")) checkAndCallSelectNotNumericZone(input);
//            else if (input.equals("card show")) checkAndCallShowSelectedCard();
//            else if (input.startsWith("card show ")) UtilityController.showCardByName(input);
//            else if (input.equals("next phase")) printResponse(gamePlayController.goNextPhase());
//            else if (input.equals("summon")) printResponse(gamePlayController.summonCommand());
//            else if (input.equals("set")) printResponse(gamePlayController.setCommand());
//            else if (input.matches("set --position (attack|defense)")) {
//                Matcher matcher = Pattern.compile("set --position (attack|defense)").matcher(input);
//                if (matcher.find()) printResponse(gamePlayController.setPosCommand(matcher.group(1)));
//            } else if (input.equals("flip-summon")) printResponse(gamePlayController.flipSummonCommand());
//            else if (input.matches("attack (\\d+)")) {
//                Matcher matcher = Pattern.compile("attack (\\d+)").matcher(input);
//                if (matcher.find()) printResponse(gamePlayController.normalAttack(Integer.parseInt(matcher.group(1))));
//            } else if (input.equals("attack direct")) printResponse(gamePlayController.directAttack());
//            else if (input.equals("activate effect")) gamePlayController.activateSpellCard();
//            else System.out.println("invalid command");
//            if (super.isExit) {
//                super.isExit = false;
//                return;
//            }
//        }
//    }

//    @Override
//    public void scanInput() {
//        while (true) {
//            String input = UtilityController.getNextLine();
//            if (input.equals("menu exit")) checkAndCallMenuExit();
//            else if (input.startsWith("duel") && input.contains(" --ai")) checkAndCallNewAiDuel(input);
//            else if (input.startsWith("duel")) {
//                checkAndCallNewDuel(input);
//                if (weAreOnGame) {
//                    printResponse(gamePlayController.goNextPhase());
//                    break;
//                }
//            } else if (regexController.showMenuRegex(input)) checkAndCallShowCurrentMenu();
//            else System.out.println("invalid command");
//            if (super.isExit) {
//                super.isExit = false;
//                return;
//            }
////        }
//        while (true) {
//            String input = UtilityController.getNextLine();
//            if (cantDoThisKindsOfMove && !input.equals("activate effect") && !input.startsWith("select") && !input.equals("card show")) {
//                System.out.println("you can't do this kind of moves");
//                continue;
//            }
//            if (input.equals("menu exit")) checkAndCallMenuExit();
//            else if (input.equals("show graveyard")) showGraveYard();
//            else if (input.equals("surrender")) gamePlayController.surrender();
//            else if (input.matches("duel set-winner (\\.+)")) {
//                Matcher matcher = Pattern.compile("duel set-winner (\\.+)").matcher(input);
//                if (matcher.find()) gamePlayController.cheatAndWin(matcher.group(1));
//            } else if (input.matches("select(.*)(\\d)(.*)")) checkAndCallSelectNumericZone(input);
//            else if (input.equals("select -d")) checkAndCallDeselect(input);
//            else if(input.matches("increase LP (\\d+)")) increaseLp(input);
//            else if (input.startsWith("select")) checkAndCallSelectNotNumericZone(input);
//            else if (input.equals("card show")) checkAndCallShowSelectedCard();
//            else if (input.startsWith("card show ")) UtilityController.showCardByName(input);
//            else if (input.equals("next phase")) printResponse(gamePlayController.goNextPhase());
//            else if (input.equals("summon")) printResponse(gamePlayController.summonCommand());
//            else if (input.equals("set")) printResponse(gamePlayController.setCommand());
//            else if (input.matches("set --position (attack|defense)")) {
//                Matcher matcher = Pattern.compile("set --position (attack|defense)").matcher(input);
//                if (matcher.find()) printResponse(gamePlayController.setPosCommand(matcher.group(1)));
//            } else if (input.equals("flip-summon")) printResponse(gamePlayController.flipSummonCommand());
//            else if (input.matches("attack (\\d+)")) {
//                Matcher matcher = Pattern.compile("attack (\\d+)").matcher(input);
//                if (matcher.find()) printResponse(gamePlayController.normalAttack(Integer.parseInt(matcher.group(1))));
//            } else if (input.equals("attack direct")) printResponse(gamePlayController.directAttack());
//            else if (input.equals("activate effect")) gamePlayController.activateSpellCard();
//            else System.out.println("invalid command");
//            if (super.isExit) {
//                super.isExit = false;
//                return;
//            }
//        }
//    }

    public void showGraveYard() {
        System.out.println(gamePlayController.showGraveYard(gamePlayController.getCurrentPlayer()));
        while (true) {
            String input = UtilityController.getNextLine();
            if (input.equals("back"))
                break;
            else if (input.equals("show graveyard"))
                System.out.println(gamePlayController.showGraveYard(gamePlayController.getCurrentPlayer()));
            else System.out.println("invalid command! you should go back to game!");
        }
    }

//    public void showCardByName(String input) {
//        HashMap<String, String> enteredDetails = new HashMap<>();
//        if (!regexController.cardShowRegex(input, enteredDetails))
//            System.out.println("invalid command");
//        else {
//            String cardName = enteredDetails.get("name");
//            if (cardName.equals("--selected")) checkAndCallShowSelectedCard();
//            else if (Card.getCardByName(cardName)==null) printString("No card exists with this name");
//            else printString(Card.getCardByName(cardName).cardShow());
//        }
//    }

    public void checkAndCallShowSelectedCard() {
        duelMenuResponses = gamePlayController.showCard();
        printResponse(duelMenuResponses);
    }

//    public void checkAndCallNewDuel(String input) {
//        HashMap<String, String> enteredDetails = new HashMap<>();
//        if (!regexController.newDuelRegex(input, enteredDetails))
//            System.out.println("invalid command");
//        else {
//            String secondPlayer = enteredDetails.get("second player");
//            secondUsername = secondPlayer;
//            int rounds = Integer.parseInt(enteredDetails.get("rounds"));
//            duelMenuResponses = gamePlayController.startNewGame(secondPlayer, rounds);
//            printResponse(duelMenuResponses);
//            if (duelMenuResponses == DuelMenuResponses.GAME_STARTED_SUCCESSFULLY) {
//                weAreOnGame = true;
//                playRPS();
//            }
//        }
//    }

    public void checkAndCallNewAiDuel(String input) {


    }

    //    public void checkAndCallSelectNumericZone(String input) {
//        HashMap<String, String> enteredDetails = new HashMap<>();
//        if (!regexController.selectFromNumericZone(input, enteredDetails))
//            System.out.println("invalid command");
//        else {
//            String opponentOrPlayer = enteredDetails.get("opponentOrPlayer");
//            String zoneType = enteredDetails.get("zone type");
//            int cardNumber = Integer.parseInt(enteredDetails.get("cardNumber"));
//            duelMenuResponses = gamePlayController.selectNumericZone(cardNumber, zoneType, opponentOrPlayer);
//            printResponse(duelMenuResponses);
//        }
//    }
//
//    public void checkAndCallSelectNotNumericZone(String input) {
//        HashMap<String, String> enteredDetails = new HashMap<>();
//        if (!regexController.selectFromNotNumericZone(input, enteredDetails))
//            System.out.println("invalid command");
//        else {
//            String opponentOrPlayer = enteredDetails.get("opponentOrPlayer");
//            String zoneType = enteredDetails.get("zone type");
//            duelMenuResponses = gamePlayController.selectNotNumericZone(zoneType, opponentOrPlayer);
//            printResponse(duelMenuResponses);
//        }
//
//    }
    public void increaseLp(String input) {
        Matcher matcher = Pattern.compile("increase LP (\\d+)").matcher(input);
        if (matcher.find()) gamePlayController.increaseLp(Integer.parseInt(matcher.group(1)));
    }

    public void checkAndCallDeselect(String input) {
        duelMenuResponses = gamePlayController.deSelect();
        printResponse(duelMenuResponses);
    }

    public void printResponse(DuelMenuResponses duelMenuResponses) {
        String output;
        switch (duelMenuResponses) {
            case NO_PLAYER_WITH_THIS_USERNAME_EXISTS:
                System.out.println("there is no player with this username");
                break;
            case OPPONENT_PLAYER_HAS_NO_ACTIVE_DECK:
                System.out.println(secondUsername + " has no active deck");
                break;
            case CURRENT_PLAYER_HAS_NO_ACTIVE_DECK:
                System.out.println(MenuController.getUser().getUserName() + " has no active deck");
                break;
            case INVALID_OPPONENT_PLAYER_DECK:
                System.out.println(secondUsername + "'s deck is invalid");
                break;
            case INVALID_CURRENT_PLAYER_DECK:
                System.out.println(MenuController.getUser().getUserName() + "'s deck is invalid");
                break;
            case GAME_STARTED_SUCCESSFULLY:
                System.out.println("let's play rock paper scissors!");
                break;
            case INVALID_SELECTION:
                System.out.println("invalid selection");
                break;
            case CARD_SELECTED:
                System.out.println("card selected");
                break;
            case SELECTION_NO_CARD_FOUND:
                System.out.println("no card found in the given position");
                break;
            case NO_CARD_SELECTED:
                System.out.println("no card is selected yet");
                break;
            case CARD_DESELECTED:
                System.out.println("card deselected");
                break;
            case RIVALS_TURN_AND_SHOW_DRAW_PHASE: {
                System.out.println("phase : end phase");
                gamePlayController.drawPhase();
                gamePlayController.goNextPhase();
                System.out.println("it's " + gamePlayController.getCurrentPlayer().getUser().getNickName() + "'s turn");
                System.out.println("phase : draw phase");
            }
            break;
            case CANT_SUMMON_THIS_CARD:
                System.out.println("you can't summon this card");
                break;
            case NOT_ALLOWED_IN_THIS_PHASE:
                System.out.println("action not allowed in this phase");
                break;
            case MONSTER_ZONE_IS_FULL:
                System.out.println("monster card zone is full");
                break;
            case ALREADY_SUMMONED_SET:
                System.out.println("you already summoned/set on this turn");
                break;
            case CARD_SUMMONED: {
                System.out.println("summoned successfully");
                gamePlayController.checkForEffectsAfterSummon();
            }
            break;
            case GET_ONE_NUMBER_TO_BE_TRIBUTE:
                System.out.println("this card needs one tribute");
                break;
            case GET_TWO_NUMBERS_TO_BE_TRIBUTE:
                System.out.println("this card needs two tributes");
                break;
            case ENTER_ONE_NUMBER:
                System.out.println("enter number");
                break;
            case ENTER_FIRST_NUMBER:
                System.out.println("enter first number : ");
                break;
            case ENTER_SECOND_NUMBER:
                System.out.println("enter second number : ");
                break;
            case NOT_ENOUGH_CARD_TO_BE_TRIBUTE:
                System.out.println("there are not enough cards for tribute");
                break;
            case ONE_TRIBUTE_NO_MONSTER:
                System.out.println("there no monsters one this address");
                break;
            case TWO_TRIBUTE_NO_MONSTER:
                System.out.println("there is no monster on one of these addresses");
                break;
            case CANT_SET_THIS_CARD:
                System.out.println("you can't set this card");
                break;
            case CANT_DO_THIS_ACTION_IN_THIS_PHASE:
                System.out.println("you can't do this action in this phase");
                break;
            case CARD_SET_SUCCESSFULLY:
                System.out.println("set successfully");
                break;
            case CANT_CHANGE_THIS_CARD_POSITION:
                System.out.println("you can't change this card position");
                break;
            case CANT_CHANGE_HIDDEN_CARD_POSITION:
                System.out.println("You can't change hidden cards position");
                break;
            case ALREADY_CHANGED_POSITION:
                System.out.println("you already changed this card position in turn");
                break;
            case ALREADY_WANTED_POSITION:
                System.out.println("selected card is already in wanted position");
                break;
            case MONSTER_CARD_POSITION_CHANGED_SUCCESSFULLY:
                System.out.println("monster card position changed successfully");
                break;
            case CANT_FLIP_SUMMON:
                System.out.println("you can't flip summon this card");
                break;
            case FLIP_SUMMONED_SUCCESSFULLY: {
                System.out.println("flip summoned successfully");
                gamePlayController.checkForTrapHole();
            }
            break;
            case YOU_CANT_ATTACK_WITH_THIS_CARD:
                System.out.println("you can't attack with this card");
                break;
            case NOT_IN_ATTACK_POSITION:
                System.out.println("Your card is not in attack position");
                break;
            case ALREADY_ATTACKED:
                System.out.println("this card already attacked");
                break;
            case NO_CARD_TO_ATTACK:
                System.out.println("there is no card to attack here");
                break;
            case DESTROYED_OPPONENT_MONSTER_AND_OPPONENT_RECEIVED_DAMAGE:
                System.out.println("your opponent's monster is destroyed and your opponent receives " + AttackController.getDamage() + " battle damage");
                break;
            case BOTH_MONSTERS_ARE_DESTROYED:
                System.out.println("both you and your opponent monster cards are destroyed and no one receives damage");
                break;
            case DESTROYED_CURRENT_MONSTER_AFTER_ATTACK:
                System.out.println("your monster card is destroyed and you received " + AttackController.getDamage() + " battle damage");
                break;
            case DEFENCE_POSITION_MONSTER_DESTROYED:
                System.out.println("the defense position monster is destroyed");
                break;
            case NO_CARD_DESTROYED:
                System.out.println("no card is destroyed ");
                break;
            case NO_CARD_DESTROYED_CURRENT_DAMAGED:
                System.out.println("no card is destroyed and you received " + AttackController.getDamage() + " battle damage");
                break;
            case CANT_ATTACK_DIRECTLY:
                System.out.println("you can't attack the opponent directly");
                break;
            case YOUR_OPPONENT_DAMAGED_DIRECT_ATTACK:
                System.out.println("your opponent receives " + AttackController.getDamage() + " battle damage");
                break;
            case ACTIVATE_EFFECT_ONLY_ON_SPELL:
                System.out.println("activate effect is only for spell cards");
                break;
            case CANT_ACTIVATE_EFFECT_ON_THIS_PHASE:
                System.out.println("you can't activate an effect on this phase");
                break;
            case YOU_ALREADY_ACTIVATED_THIS_CARD:
                System.out.println("you have already activated this card");
                break;
            case SPELL_ZONE_CARD_IS_FULL:
                System.out.println("spell card zone is full");
                break;
            case PREPARATIONS_OF_THIS_SPELL_ARE_NOT_DONE_YET:
                System.out.println("preparations of this spell are not done yet");
                break;
            case SPELL_ACTIVATED:
                System.out.println("spell activated");
                break;
            case CANT_BE_ADDED_TO_CHAIN:
                System.out.println("you can't add this card ro chain");
                break;
            case EFFECT_DONE_SUCCESSFULLY:
                System.out.println("effect done successfully");
                break;
            case SHOW_CARD:
                System.out.println(gamePlayController.getSelectedCard().cardShow());
                gamePlayController.setSelectedCard(null);
                break;
            case CANNOT_ACCESS_RIVAL_CARD:
                System.out.println("You can't access this card!");
                break;
            case SHOW_NEW_PHASE:
                output = "Phase :" + " " + Game.getPhases().get(gamePlayController.getCurrentPhaseNumber()).getName();
                UtilityController.makeAlert("Nice!!", "You're playing good!", output, new Image(String.valueOf(getClass().
                        getResource("/Images/fightAnimeGirl.jpg"))));
                break;
            case CARD_EQUIPPED:
                System.out.println("card equipped!");
                break;
            case INVALID_CELL_NUMBER:
                System.out.println("invalid cell number!");
                break;
            case WANNA_ADD_ANOTHER_CARD:
                System.out.println("wanna add another card to be destroyed?");
                break;
            case NO_WAY_TO_RITUAL_SUMMON:
                System.out.println("there is no way you could ritual summon a monster");
                break;
            case SHOULD_RIVAL_SUMMON_RIGHT_NOW:
                System.out.println("you should ritual summon right now");
                break;
            case ENTER_POS:
                System.out.println("choose position? (ATK/DEF)");
                break;
            case ENTER_SOME_NUMBERS:
                System.out.println("enter numbers of monsters to be destroyed. (e.g :1 2 3)");
                break;
            case LEVELS_DONT_MATCH:
                System.out.println("selected monsters levels don't match with ritual monster");
                break;
            case ENTER_PLAYER:
                System.out.println("enter player (rival/current player)");
                break;
            case ATTACK_CANCELED:
                System.out.println("attack canceled!");
                break;
            case ACTIVATION_CANCELED:
                System.out.println("activation cancelled!");
                break;
            case CANT_ADD_THIS_CARD_TO_CHAIN:
                System.out.println("can't be added to chain");
                break;
            case CANT_ATTACK_TO_THIS_CARD:
                System.out.println("you can't attack to this card");
                break;
            case THIS_CARD_CANT_BE_DESTROYED:
                System.out.println("this card can't be destroyed");
                break;
            case DO_YOU_WANNA_TRIBUTE:
                System.out.println("do you wanna tribute to summon?");
                break;
            case DO_YOU_WANNA_TRIBUTE_2_MONSTERS:
                System.out.println("do you wanna tribute 2 monsters?");
                break;
            case DO_YOU_WANNA_TRIBUTE_TREE_MONSTERS:
                System.out.println("do you wanna tribute 3 monsters?");
                break;
            case DO_YOU_WANNA_NORMAL_SUMMON:
                System.out.println("do you wanna normal summon?");
                break;
            case WANNA_CHOOSE_FROM_DECK:
                System.out.println("wanna choose from deck?");
                break;
            case WANNA_CHOOSE_FROM_GRAVEYARD:
                System.out.println("wanna choose from graveyard?");
                break;
            case WANNA_CHOOSE_FROM_HAND:
                System.out.println("wanna choose from hand?");
                break;
            case NO_WAY_TO_SPECIAL_SUMMON:
                System.out.println("");
                break;
            case CANT_SPECIAL_SUMMON:
                System.out.println("you can't special summon this monster");
                break;
            case CANT_NORMAL_SET_THIS_MONSTER:
                System.out.println("you cant normal set this monster, it should be special summoned!");
                break;
            case DO_YOU_WANNA_SPECIAL_SUMMON:
                System.out.println("do you wanna special summon this monster?");
                break;
            case DO_YOU_WANT_ACTIVATE_SPELL_AND_TRAP:
                System.out.println("do you wanna activate spell or trap?");
                break;
            case YOU_SHOULD_SET_TRAP:
                System.out.println("you should set the trap");
                break;
            case CANT_ACTIVATE_TRAP_IN_THIS_TURN:
                System.out.println("you can't activate the trap in first turn you set");
                break;
            case DO_YOU_WANT_SUMMON_NORMAL_CYBERSE_CARD:
                System.out.println("do you want to summon a normal Cyberse card? (yes/no)");
                break;
            case CARD_DESTROYED_BY_TRAP_HOLE:
                System.out.println("card destroyed by trap hole!");
                break;
            case GIVE_A_NAME:
                System.out.println("give a name :");
                break;
            case CANCELED:
                System.out.println("canceled");
                break;
            case TRAP_ACTIVATED:
                System.out.println("trap activated");
                break;

            default:
                break;
        }
    }

    public void hiddenDefensePositionMonsterDestroyed(String name) {
        System.out.println("opponent's monster card was " + name + " and the defense position monster is destroyed");
    }

    public void hiddenDefensePosNoCardDestroyed(String name) {
        System.out.println("opponent's monster card was " + name + " and no card is destroyed");
    }

    public void hiddenDefensePosNoCardDestroyedWithDamage(String name) {
        System.out.println("opponent's monster card was " + name + " no card is destroyed and you received " + AttackController.getDamage() + " battle damage");
    }



    public void roundFinished(String winner) {
        System.out.println(winner + " won the game!");
    }

    public void matchFinished(String winner, int score) {
        System.out.println(winner + " won the game with score : " + score);
    }

    public void lifePointReduced(int point) {
        System.out.println("current player received " + point + " damage!");

    }

    public void doYouWannaActivateSpecialCard(String name) {
        System.out.println("do you wanna activate your " + name + " ?");
    }


    public String defineStarterOfNextRound(String name) {
        System.out.println("Dear " + name + " do you wanna be first player of this round?");
        return UtilityController.getNextLine();
    }

    public void startNewRound(String name) {
        System.out.println("GAME STARTED!");
        System.out.println("now it will be " + name + "'s turn");
    }


    public void newCardAddInDrawPhase(String cardName) {
        System.out.println("new card added to hand : " + cardName);
    }

    public void showRivalTurn(String userName, String board) {
        System.out.println("now it will be " + userName + "'s turn");
        System.out.println(board);

    }

    public void printString(String string) {
        System.out.println(string);
    }

    public int getNum() {
        return Integer.parseInt(UtilityController.getNextLine());
    }

    public String getString() {
        return UtilityController.getNextLine();
    }
}
