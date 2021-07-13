package sample.view;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javafx.util.Pair;
import sample.Main;
import sample.controller.gamePlayController.AttackController;
import sample.controller.gamePlayController.GamePlayController;
import sample.controller.menuController.MenuController;
import sample.controller.responses.DuelMenuResponses;
import sample.controller.utilizationController.AudioController;
import sample.controller.utilizationController.UtilityController;
import sample.model.Game;
import sample.model.Phase;
import sample.model.Player;
import sample.model.cards.Card;
import sample.model.cards.MagicCard;
import sample.model.cards.MonsterCard;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static sample.controller.responses.DuelMenuResponses.SHOW_CARD;

public class DuelMenu {
    private static final GamePlayController gamePlayController;
    public static boolean isPause;
    public static boolean isFinished;
    private static DuelMenu duelMenu;
    private static Image backOfCard = new Image(String.valueOf(DuelMenu.class.
            getResource("/Images/cardAnimeGirl.jpg")));

    static {
        gamePlayController = GamePlayController.getInstance();
    }

    DuelMenuResponses duelMenuResponses;
    private boolean weAreOnGame = false;
    private String secondUsername;
    private ImageView[][] playerCards;
    private ImageView[][] opponentCards;
    @FXML
    private StackPane opponentDeck;
    @FXML
    private StackPane playerDeck;
    private Boolean cantDoThisKindsOfMove = false;
    @FXML
    private Pane father;
    @FXML
    private AnchorPane grandFather;
    @FXML
    private GridPane firstPlayerBoardCards;
    @FXML
    private GridPane secondPlayerBoardCards;
    @FXML
    private GridPane firstPlayerHand;
    @FXML
    private GridPane secondPlayerHand;
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
    @FXML
    private StackPane currentGraveyard;
    @FXML
    private StackPane opponentGraveyard;
    @FXML
    private ScrollPane currentGraveyardShow;
    @FXML
    private ScrollPane opponentGraveyardShow;
    @FXML
    private Button setButton;
    @FXML
    private Button summonButton;
    @FXML
    private Button attackButton;
    @FXML
    private Button directAttackButton;
    @FXML
    private Button activateButton;
    @FXML
    private Button changePosition;
    @FXML
    private Button flipSummon;
    @FXML
    private ImageView viewImage;
    private Timer timer;

    public static DuelMenu getInstance() {
        if (duelMenu == null)
            duelMenu = new DuelMenu();
        return duelMenu;
    }

    private static PathTransition newPathTransitionTo(ImageView block, double toX, double toY) {
        double fromX = block.getLayoutBounds().getWidth() / 2;
        double fromY = block.getLayoutBounds().getHeight() / 2;
        toX -= block.getLayoutX() - block.getLayoutBounds().getWidth() / 2;
        toY -= block.getLayoutY() - block.getLayoutBounds().getHeight() / 2;
        Path path = new Path();
        path.getElements().add(new MoveTo(fromX, fromY));
        path.getElements().add(new LineTo(toX, toY));
        PathTransition transition = new PathTransition();
        transition.setPath(path);
        transition.setNode(block);
        transition.setDuration(Duration.millis(2000));
        return transition;
    }

    private void refreshGraveyard(StackPane graveyard) {
        Player player;
        if (graveyard == currentGraveyard) player = gamePlayController.getCurrentPlayer();
        else player = gamePlayController.getOpponentPlayer();
        graveyard.getChildren().clear();
        for (Card card : player.getGraveyardZone().getZoneCards()) {
            ImageView imageView = new ImageView(card.getCardImage());
            graveyard.getChildren().add(imageView);
        }
    }

    public void checkForActionButtonsActivity(Card selectedCard) {
        if (selectedCard == null || selectedCard.getOwner() == gamePlayController.getOpponentPlayer()) {
            inActiveButton(setButton);
            inActiveButton(summonButton);
            inActiveButton(attackButton);
            inActiveButton(directAttackButton);
            inActiveButton(activateButton);
            inActiveButton(flipSummon);
            inActiveButton(changePosition);
            return;
        }
        if (selectedCard.getCardPlacedZone() == gamePlayController.getCurrentPlayer().getHand())
            activeButton(setButton);
        else inActiveButton(setButton);
        if ((selectedCard.getCardPlacedZone() == gamePlayController.getCurrentPlayer().getHand()
                && selectedCard instanceof MonsterCard)) activeButton(summonButton);
        else inActiveButton(summonButton);
        if (Game.getPhases().get(gamePlayController.getCurrentPhaseNumber()) == Phase.PhaseLevel.BATTLE &&
                selectedCard.getCardPlacedZone() == gamePlayController.getCurrentPlayer().getMonsterCardZone() && ((MonsterCard) selectedCard).toStringPosition().equals("OO"))
            activeButton(attackButton);
        else inActiveButton(attackButton);
        if (selectedCard.getCardPlacedZone() == gamePlayController.getCurrentPlayer().getMonsterCardZone() && ((MonsterCard) selectedCard).toStringPosition().equals("OO"))
            activeButton(directAttackButton);
        else inActiveButton(directAttackButton);
        if ((selectedCard instanceof MagicCard) && (((MagicCard) selectedCard).getMagicType() == MagicCard.MagicType.SPELL
                || (((MagicCard) selectedCard).getMagicType() == MagicCard.MagicType.TRAP && selectedCard.getCardPlacedZone() == gamePlayController.getCurrentPlayer().getMagicCardZone())))
            activeButton(activateButton);
        else inActiveButton(activateButton);
        if (selectedCard.getCardPlacedZone() == gamePlayController.getCurrentPlayer().getMonsterCardZone() && ((MonsterCard) selectedCard).toStringPosition().equals("DH"))
            activeButton(flipSummon);
        else inActiveButton(flipSummon);
        if (selectedCard.getCardPlacedZone() == gamePlayController.getCurrentPlayer().getMonsterCardZone() && !((MonsterCard) selectedCard).toStringPosition().equals("DH"))
            activeButton(changePosition);
        else inActiveButton(changePosition);

    }

    public void setButtonClicked(MouseEvent mouseEvent) {
        try {
            printResponse(gamePlayController.setCommand());
            refreshPlayersBox();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void summonButtonClicked(MouseEvent mouseEvent) {
        try {
            printResponse(gamePlayController.summonCommand());
            refreshPlayersBox();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void flipSummonButtonClicked(MouseEvent mouseEvent) {
        try {
            printResponse(gamePlayController.flipSummonCommand());
            refreshPlayersBox();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void changePositionButtonClicked(MouseEvent mouseEvent) {
        try {
            printResponse(gamePlayController.setPosCommand());
            refreshPlayersBox();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void flipSummon(Player player, int i, Card card) {
        RotateTransition rotateTransition = new RotateTransition();
        rotateTransition.setNode(playerCards[1][i]);
        rotateTransition.setAxis(Rotate.Z_AXIS);
        rotateTransition.setDuration(Duration.millis(1000));
        rotateTransition.setByAngle(-90);
        rotateTransition.setOnFinished(event -> {
            flipMonster(player, i, card, MonsterCard.Mode.ATTACK);
        });
        rotateTransition.play();
    }

    public void flipMonster(Player player, int i, Card card, MonsterCard.Mode mode) {
        Flip flip = new Flip();
        if (player == gamePlayController.getCurrentPlayer()) {
            flip.setNode(playerCards[1][i]);
            flip.setFrontImage(card.getCardImage());
            flip.setBackOfCard(backOfCard);
            if (mode == MonsterCard.Mode.DEFENSE)
                flip.setRightToLeft(true);
            else flip.setRightToLeft(false);
            flip.setFrontToBack(false);
            flip.play();
        } else {
            flip.setNode(opponentCards[1][i]);
            flip.setFrontImage(card.getCardImage());
            flip.setBackOfCard(backOfCard);
            if (mode == MonsterCard.Mode.DEFENSE)
                flip.setRightToLeft(true);
            else flip.setRightToLeft(false);
            flip.setFrontToBack(false);
            flip.play();
        }
    }

    public void flipMagic(Player player, int i, Card card) {
        Flip flip = new Flip();
        if (player == gamePlayController.getCurrentPlayer()) {
            flip.setNode(playerCards[0][i]);
            flip.setFrontImage(card.getCardImage());
            flip.setBackOfCard(backOfCard);
            flip.setRightToLeft(false);
            flip.setFrontToBack(false);
            flip.play();
        } else {
            flip.setNode(opponentCards[0][i]);
            flip.setFrontImage(card.getCardImage());
            flip.setBackOfCard(backOfCard);
            flip.setRightToLeft(false);
            flip.setFrontToBack(false);
            flip.play();
        }
        AudioController.playFlipSound();
    }

    public void attackButtonClicked(MouseEvent mouseEvent) {
        try {
            attack();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void directAttackButtonClicked(MouseEvent mouseEvent) {
        try {
            printResponse(gamePlayController.directAttack());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Timeline attackFire() {
        Image image = new Image(String.valueOf(DuelMenu.class.
                getResource("/Images/fire.gif")));
        viewImage.setImage(image);
        viewImage.setFitWidth(500);
        viewImage.setFitHeight(700);
        KeyFrame keyFrame = new KeyFrame(Duration.millis(2000), event -> {
            viewImage.setImage(null);
        });
        Timeline timeline = new Timeline(keyFrame);
        return timeline;
    }

    public void activateButtonClicked(MouseEvent mouseEvent) {
        try {
            gamePlayController.activateSpellCard();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void activeButton(Button button) {
        button.setCursor(Cursor.HAND);
        button.setOpacity(1);
        switch (button.getText()) {
            case "Change Position":
                button.setOnMouseClicked(this::changePositionButtonClicked);
                break;
            case "Flip Summon":
                button.setOnMouseClicked(this::flipSummonButtonClicked);
                break;
            case "Set":
                button.setOnMouseClicked(this::setButtonClicked);
                break;
            case "Summon":
                button.setOnMouseClicked(this::summonButtonClicked);
                break;
            case "Attack":
                button.setOnMouseClicked(this::attackButtonClicked);
                break;
            case "Direct Attack":
                button.setOnMouseClicked(this::directAttackButtonClicked);
                break;
            case "Activate":
                button.setOnMouseClicked(this::activateButtonClicked);
                break;
        }
    }

    public void inActiveButton(Button button) {
        button.setCursor(Cursor.DEFAULT);
        button.setOpacity(0.5);
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                UtilityController.makeAlert("No!!", "Can't be done!",
                        "This action is not active for this card", new Image(String.valueOf(getClass().
                                getResource("/Images/confusedAnimeGirl.jpg"))));
            }
        });
    }

    public void setShowGraveyard(StackPane graveyard) {
        graveyard.setCursor(Cursor.HAND);
        graveyard.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                ScrollPane scrollPane;
                Player player;
                if (graveyard == currentGraveyard) {
                    player = gamePlayController.getCurrentPlayer();
                    scrollPane = currentGraveyardShow;
                } else {
                    player = gamePlayController.getOpponentPlayer();
                    scrollPane = opponentGraveyardShow;
                }
                if (scrollPane.isVisible()) {
                    scrollPane.setVisible(false);
                    return;
                }
                scrollPane.setContent(null);
                GridPane gridPane = new GridPane();
                int counter = 0;
                for (Card card : player.getGraveyardZone().getZoneCards()) {
                    ImageView imageView = new ImageView(card.getCardImage());
                    gridPane.add(imageView, 0, counter);
                    Label cardNameLabel = new Label();
                    cardNameLabel.setPrefWidth(150);
                    cardNameLabel.setPrefHeight(50);
                    cardNameLabel.setAlignment(Pos.CENTER);
                    cardNameLabel.setText("Name : " +
                            Objects.requireNonNull(Card.getCardByImage(imageView.getImage())).getCardName());
                    gridPane.add(cardNameLabel, 1, counter);
                }
                scrollPane.setVisible(true);
            }
        });
    }

    public void initialGame() {
        isPause = false;
        refreshPlayersBox();
        initializeBoard();
        runAndUpdate();
        startTimerAndRun();
        initialCheatBox();
        setShowGraveyard(currentGraveyard);
        setShowGraveyard(opponentGraveyard);
    }

    private void initialCheatBox() {
        father.getScene().getAccelerators().put(new KeyCodeCombination(
                KeyCode.C, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN), new Runnable() {
            @Override
            public void run() {
                //Insert conditions here
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/FxmlFiles/CheatBox.fxml"));
                Scene scene = null;
                try {
                    scene = new Scene(loader.load());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                CheatController cheatController = loader.getController();
                Stage popupStage = new Stage();
                cheatController.setStage(popupStage);
                popupStage.initModality(Modality.WINDOW_MODAL);
                popupStage.setScene(scene);
                cheatController.init();
                popupStage.showAndWait();
            }
        });
    }


    public void changePos(int i, Player player, MonsterCard.Mode mode) {
        if (player == gamePlayController.getCurrentPlayer()) {
            RotateTransition rotateTransition = new RotateTransition();
            rotateTransition.setNode(playerCards[1][i]);
            rotateTransition.setAxis(Rotate.Z_AXIS);
            rotateTransition.setDuration(Duration.millis(1000));
            if (mode == MonsterCard.Mode.ATTACK)
                rotateTransition.setByAngle(-90);
            else rotateTransition.setByAngle(90);
            rotateTransition.play();
        } else {
            RotateTransition rotateTransition = new RotateTransition();
            rotateTransition.setNode(opponentCards[1][i]);
            rotateTransition.setAxis(Rotate.Z_AXIS);
            rotateTransition.setDuration(Duration.millis(1000));
            if (mode == MonsterCard.Mode.ATTACK)
                rotateTransition.setByAngle(-90);
            else rotateTransition.setByAngle(90);
            rotateTransition.play();
        }
    }

    public void initializeBoard() {
        playerCards = new ImageView[2][5];
        opponentCards = new ImageView[2][5];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 5; j++) {
                ImageView imageView = new ImageView();
                imageView.setFitHeight(120);
                imageView.setFitWidth(80);
                imageView.setImage(null);
                playerCards[i][j] = imageView;
                setOnMouseClickedForCardImage(playerCards[i][j], "player");
                firstPlayerBoardCards.add(imageView, j, i);
                firstPlayerBoardCards.setHgap(27);
                ImageView imageView1 = new ImageView();
                imageView1.setFitHeight(120);
                imageView1.setFitWidth(80);
                imageView1.setImage(null);
                opponentCards[i][j] = imageView1;
                setOnMouseClickedForCardImage(opponentCards[i][j], "opponent");
                secondPlayerBoardCards.add(imageView1, j, i);
                secondPlayerBoardCards.setHgap(27);
            }
        }
        runAndUpdate();
        updateDeck(playerDeck, gamePlayController.getCurrentPlayer());
        updateDeck(opponentDeck, gamePlayController.getOpponentPlayer());
    }

    public void startTimerAndRun() {
        timer = new java.util.Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
//                        if(!isPause)
//                            runAndUpdate();
                    }
                });
            }
        };
        long frameTimeInMilliseconds = (long) (200.0);
        timer.schedule(timerTask, 0, frameTimeInMilliseconds);

    }

    public void updateDeck(StackPane pane, Player player) {
        pane.getChildren().clear();
        for (int i = player.getDeckZone().getZoneCards().size() - 1; i >= 0; i--) {
            ImageView imageView = new ImageView();
            imageView.setFitHeight(120);
            imageView.setFitWidth(80);
            imageView.setImage(player.getDeckZone().getZoneCards().get(i).getCardImage());
            pane.getChildren().add(imageView);
        }
        Text text = new Text((player.getDeckZone().getZoneCards().size() + 1) + "");
        text.setFont(new Font("Verdana Bold", 20));
        text.setFill(Color.WHITE);
        text.setEffect(new Reflection());
        pane.getChildren().add(text);

    }

    public void runAndUpdate() {
//        checkForActionButtonsActivity(gamePlayController.getSelectedCard());
//        setPlayersCards(gamePlayController.getCurrentPlayer(),playerCards);
//       setPlayersCards(gamePlayController.getOpponentPlayer(),opponentCards);
    }

    public void removeFromHand(int i, Player player) {
        ImageView imageView;
        if (player == gamePlayController.getCurrentPlayer()) {
            imageView = (ImageView) getNodeByCoordinate(0, i, firstPlayerHand);
        } else imageView = (ImageView) getNodeByCoordinate(0, i, secondPlayerHand);
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setNode(imageView);
        fadeTransition.setDuration(Duration.millis(1000));
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.play();
        fadeTransition.setOnFinished(event -> {
            if (player == gamePlayController.getCurrentPlayer()) {
                firstPlayerHand.getChildren().remove(imageView);
                updateHandGrid(player);
            } else {
                secondPlayerHand.getChildren().remove(imageView);
                updateHandGrid(player);
            }
        });
    }

    public void updateHandGrid(Player player) {
        if (player == gamePlayController.getCurrentPlayer()) {
            firstPlayerHand.getChildren().clear();
        } else {
            secondPlayerHand.getChildren().clear();
        }
        for (Card card : player.getHand().getZoneCards()) {
            ImageView imageView = new ImageView();
            imageView.setFitHeight(120);
            imageView.setFitWidth(80);
            if (card.getOwner() == gamePlayController.getCurrentPlayer()) {
                imageView.setImage(card.getCardImage());
                selectHandCard(imageView, "player", player.getHand().getZoneCards().indexOf(card));
                firstPlayerHand.add(imageView, player.getHand().getZoneCards().indexOf(card), 0);
                firstPlayerHand.setHgap(30);
            } else {
                imageView.setImage(backOfCard);
                selectHandCard(imageView, "opponent", player.getHand().getZoneCards().indexOf(card));
                secondPlayerHand.add(imageView, player.getHand().getZoneCards().indexOf(card), 0);
                secondPlayerHand.setHgap(30);
            }
        }

    }

    public void addToHand(int i, Card card) {
        ImageView imageView = new ImageView();
        imageView.setFitHeight(120);
        imageView.setFitWidth(80);
        if (card.getOwner() == gamePlayController.getCurrentPlayer()) {
            imageView.setImage(card.getCardImage());
            selectHandCard(imageView, "player", i);
            firstPlayerHand.add(imageView, i, 0);
            firstPlayerHand.setHgap(30);
        } else {
            imageView.setImage(backOfCard);
            selectHandCard(imageView, "opponent", i);
            secondPlayerHand.add(imageView, i, 0);
            secondPlayerHand.setHgap(30);
        }
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setNode(imageView);
        fadeTransition.setDuration(Duration.millis(1000));
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
        fadeTransition.setOnFinished(event -> {
            updateDecks();
        });
    }

    public void moveToPlayerHandFromDeck(int i, Card card) {
        ImageView imageView = new ImageView(card.getCardImage());
        playerDeck.getChildren().remove(playerDeck.getChildren().get(playerDeck.getChildren().size() - 2));
        imageView.setFitHeight(120);
        imageView.setFitWidth(80);
        imageView.setLayoutX(playerDeck.getLayoutX());
        imageView.setLayoutY(playerDeck.getLayoutY());
        double toX, toY;
        toX = getNodeByCoordinate(0, i, firstPlayerHand).getLayoutX();
        toY = getNodeByCoordinate(0, i, firstPlayerHand).getLayoutY();
        PathTransition transition = newPathTransitionTo(imageView, toX, toY);
        System.out.println("from :" + imageView.getLayoutX() + " " + imageView.getLayoutY() + " to :" + toX + " " + toY);
        transition.play();
        transition.setOnFinished(event -> {
            System.out.println("now :" + imageView.getLayoutX() + " " + imageView.getLayoutY());
            gamePlayController.moveFirstCardFromHandToDeck(card.getOwner());
            imageView.setImage(null);
            updateDecks();
        });
    }

    public void updateDecks() {
        updateDeck(playerDeck, gamePlayController.getCurrentPlayer());
        updateDeck(opponentDeck, gamePlayController.getOpponentPlayer());
    }

    public void removeCard(Card card, int i) {
        if (card.getOwner() == gamePlayController.getCurrentPlayer()) {
            if (card instanceof MagicCard)
                disAppearTransition(playerCards[0][i - 1]);
            else disAppearTransition(playerCards[1][i - 1]);
        } else {
            if (card instanceof MagicCard)
                disAppearTransition(opponentCards[0][i - 1]);
            else disAppearTransition(opponentCards[1][i - 1]);
        }
    }

    public void addCard(Card card, int i) {
        if (card.getOwner() == gamePlayController.getCurrentPlayer()) {
            if (card instanceof MagicCard) {
                playerCards[0][i - 1].setRotate(0);
                if (card.getHidden())
                    playerCards[0][i - 1].setImage(backOfCard);
                else playerCards[0][i - 1].setImage(card.getCardImage());
                appearTransition(playerCards[0][i - 1]);
            } else {
                if (((MonsterCard) card).toStringPosition().equals("DO") ||
                        ((MonsterCard) card).toStringPosition().equals("DH"))
                    playerCards[1][i - 1].setRotate(90.0);
                else playerCards[1][i - 1].setRotate(0);
                if (card.getHidden())
                    playerCards[1][i - 1].setImage(backOfCard);
                else playerCards[1][i - 1].setImage(card.getCardImage());
                appearTransition(playerCards[1][i - 1]);
            }
        } else {
            if (card instanceof MagicCard) {
                opponentCards[0][i - 1].setRotate(0);
                if (card.getHidden())
                    opponentCards[0][i - 1].setImage(backOfCard);
                else opponentCards[0][i - 1].setImage(card.getCardImage());
                appearTransition(opponentCards[0][i - 1]);
            } else {
                if (((MonsterCard) card).toStringPosition().equals("DO") ||
                        ((MonsterCard) card).toStringPosition().equals("DH"))
                    opponentCards[1][i - 1].setRotate(90.0);
                else opponentCards[1][i - 1].setRotate(0);
                if (card.getHidden())
                    opponentCards[1][i - 1].setImage(backOfCard);
                else opponentCards[1][i - 1].setImage(card.getCardImage());
                appearTransition(opponentCards[1][i - 1]);
            }
        }

    }


    public void disAppearTransition(ImageView imageView) {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setNode(imageView);
        fadeTransition.setDuration(Duration.millis(1000));
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.play();
        fadeTransition.setOnFinished(event -> {
            imageView.setImage(null);
            imageView.setRotate(0);
        });
    }

    public void appearTransition(ImageView imageView) {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setNode(imageView);
        fadeTransition.setDuration(Duration.millis(1000));
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
        fadeTransition.setOnFinished(event -> {
            updateDecks();
        });
    }

    public void settingButtonClicked(MouseEvent mouseEvent) {

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

    public void nextPhase() throws IOException {
        printResponse(gamePlayController.goNextPhase());
        refreshPhaseBox();
    }

    public void refreshPhaseBox() {

        phaseNameShow.setText(Game.getPhases().get(gamePlayController.getCurrentPhaseNumber()).getName());
    }

    public void refreshPlayersBox() {
        checkForActionButtonsActivity(gamePlayController.getSelectedCard());
        currentPlayerName.setText(gamePlayController.getCurrentPlayer().getUser().getUserName() + " :   " +
                gamePlayController.getCurrentPlayer().getUser().getNickName());
        currentPlayerLifePoint.setText(String.valueOf(gamePlayController.getCurrentPlayer().getLifePoint()));
        currentPlayerLifePointProgressBar.setProgress((double) gamePlayController.getCurrentPlayer().getLifePoint() /
                GamePlayController.lP);
        opponentPlayerName.setText(gamePlayController.getOpponentPlayer().getUser().getUserName() + " :   " +
                gamePlayController.getOpponentPlayer().getUser().getNickName());
        opponentPlayerLifePoint.setText(String.valueOf(gamePlayController.getOpponentPlayer().getLifePoint()));
        opponentPlayerLifePoint.setText(String.valueOf(gamePlayController.getOpponentPlayer().getLifePoint()));
        opponentPlayerLifePointProgressBar.setProgress((double) gamePlayController.getOpponentPlayer().getLifePoint() /
                GamePlayController.lP);
        if (gamePlayController.getSelectedCard() == null) {
            selectedCardPic.setImage(backOfCard);
            selectedCardDescription.setText("Select a card...");

        } else {
            selectedCardPic.setImage(gamePlayController.getSelectedCard().getCardImage());
            selectedCardDescription.setText(gamePlayController.getSelectedCard().getCardDescription());
        }
        if (gamePlayController.getCurrentPlayer().getLifePoint() <= 2000) AudioController.playHeartbeat();
    }

    public Node getNodeByCoordinate(Integer row, Integer column, GridPane gridPane) {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getColumnIndex(node).equals(column) && GridPane.getRowIndex(node).equals(row)) {
                return node;
            }
        }
        return null;
    }

    private void setDeckZone() {

    }

    private void setCardsInGrid(GridPane playerCardsInBoard, ImageView[][] imageView) {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 5; j++) {
                playerCardsInBoard.add(imageView[i][j], j, i);
                playerCardsInBoard.setHgap(27);
            }
        }
    }

    public void selectHandCard(ImageView imageView, String opponentOrPlayer, int i) {
        imageView.setCursor(Cursor.HAND);
        imageView.setOnMouseClicked(mouseEvent -> {
            DuelMenuResponses duelMenuResponses;
            duelMenuResponses = gamePlayController.selectNumericZone(i + 1, "hand", opponentOrPlayer);
            if (duelMenuResponses.equals(DuelMenuResponses.CARD_SELECTED)) ;
            if (gamePlayController.showCard().equals(SHOW_CARD))
                refreshPlayersBox();
        });
    }

    public void setOnMouseClickedForCardImage(ImageView imageView, String opponentOrPlayer) {
        if (imageView.getImage() != null)
            imageView.setCursor(Cursor.HAND);
        imageView.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY && imageView.getImage() != null) {
                DuelMenuResponses duelMenuResponses;
                int row = GridPane.getRowIndex(imageView);
                int column = GridPane.getColumnIndex(imageView);
                if (row == 0)
                    duelMenuResponses = gamePlayController.selectNumericZone(column + 1, "spell", opponentOrPlayer);
                else duelMenuResponses = gamePlayController.selectNumericZone(column + 1, "monster", opponentOrPlayer);
                if (duelMenuResponses.equals(DuelMenuResponses.CARD_SELECTED)) {
                    if (gamePlayController.showCard().equals(SHOW_CARD))
                        refreshPlayersBox();
                }
            }
        });
    }

    public void pauseButtonClicked(MouseEvent mouseEvent) {
        AudioController.playClick();
        isPause = true;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FxmlFiles/GameSetting.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        GameSettingController gameSettingController = loader.getController();
        Stage popupStage = new Stage();
        gameSettingController.setStage(popupStage);
        gameSettingController.init();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setScene(scene);
        popupStage.showAndWait();
    }

    public void surrender() throws IOException {
        gamePlayController.surrender();
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

    public void checkAndCallShowSelectedCard() throws IOException {
        duelMenuResponses = gamePlayController.showCard();
        printResponse(duelMenuResponses);
    }


    public void increaseLp(String input) {
        Matcher matcher = Pattern.compile("increase LP (\\d+)").matcher(input);
        if (matcher.find()) gamePlayController.increaseLp(Integer.parseInt(matcher.group(1)));
    }

    public void checkAndCallDeselect(String input) throws IOException {
        duelMenuResponses = gamePlayController.deSelect();
        printResponse(duelMenuResponses);
    }

    public void attack() throws IOException {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("fight!!");
        dialog.setContentText("please choose a monster");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            printResponse(gamePlayController.normalAttack(Integer.parseInt(result.get())));
        }
    }

    public void oneTribute() throws IOException {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("We need one Sacrifice");
        dialog.setContentText("please choose a monster");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            printResponse(gamePlayController.oneMonsterTribute(Integer.parseInt(result.get())));
        }
    }

    public void twoMonsterTribute() {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("We need 2 sacrifices!!");
        ButtonType loginButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 150, 10, 10));
        TextField one = new TextField();
        one.setPromptText("first");
        TextField to = new TextField();
        to.setPromptText("second");
        gridPane.add(one, 0, 0);
        gridPane.add(new Label(" and "), 1, 0);
        gridPane.add(to, 2, 0);
        dialog.getDialogPane().setContent(gridPane);
        Platform.runLater(() -> one.requestFocus());
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(one.getText(), to.getText());
            }
            return null;
        });
        Optional<Pair<String, String>> result = dialog.showAndWait();
        result.ifPresent(pair -> {
            try {
                printResponse(gamePlayController.twoMonsterTribute(Integer.parseInt(pair.getKey()), Integer.parseInt(pair.getValue())));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void printResponse(DuelMenuResponses duelMenuResponses) throws IOException {
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
                // TO DO :
                gamePlayController.drawPhase();
                gamePlayController.goNextPhase();
                System.out.println("it's " + gamePlayController.getCurrentPlayer().getUser().getNickName() + "'s turn");
                System.out.println("phase : draw phase");
            }
            break;
            case CANT_SUMMON_THIS_CARD:
                UtilityController.makeAlert("No!!", "Can't be done!",
                        "you can't summon this card", new Image(String.valueOf(getClass().
                                getResource("/Images/confusedAnimeGirl.jpg"))));
                break;
            case NOT_ALLOWED_IN_THIS_PHASE:
                UtilityController.makeAlert("No!!", "Can't be done!",
                        "action not allowed in this phase", new Image(String.valueOf(getClass().
                                getResource("/Images/confusedAnimeGirl.jpg"))));
                break;
            case MONSTER_ZONE_IS_FULL:
                UtilityController.makeAlert("No!!", "Can't be done!",
                        "monster card zone is full!", new Image(String.valueOf(getClass().
                                getResource("/Images/confusedAnimeGirl.jpg"))));
                break;
            case ALREADY_SUMMONED_SET:
                UtilityController.makeAlert("No!!", "Can't be done!",
                        "you already summoned/set on this turn", new Image(String.valueOf(getClass().
                                getResource("/Images/confusedAnimeGirl.jpg"))));
                break;
            case CARD_SUMMONED: {
                AudioController.playSummoned();
                gamePlayController.checkForEffectsAfterSummon();
            }
            break;
            case GET_ONE_NUMBER_TO_BE_TRIBUTE:
                oneTribute();
                break;
            case GET_TWO_NUMBERS_TO_BE_TRIBUTE:
                twoMonsterTribute();
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
                UtilityController.makeAlert("No!!", "Can't be done!",
                        "there are not enough cards for tribute", new Image(String.valueOf(getClass().
                                getResource("/Images/confusedAnimeGirl.jpg"))));
                break;
            case ONE_TRIBUTE_NO_MONSTER:
                UtilityController.makeAlert("No!!", "Can't be done!",
                        "there no monsters one this address", new Image(String.valueOf(getClass().
                                getResource("/Images/confusedAnimeGirl.jpg"))));
                break;
            case TWO_TRIBUTE_NO_MONSTER:
                UtilityController.makeAlert("No!!", "Can't be done!",
                        "there is no monster on one of these addresses", new Image(String.valueOf(getClass().
                                getResource("/Images/confusedAnimeGirl.jpg"))));
                break;
            case CANT_SET_THIS_CARD:
                UtilityController.makeAlert("No!!", "Can't be done!",
                        "you can't set this card", new Image(String.valueOf(getClass().
                                getResource("/Images/confusedAnimeGirl.jpg"))));
                break;
            case CANT_DO_THIS_ACTION_IN_THIS_PHASE:
                UtilityController.makeAlert("No!!", "Can't be done!",
                        "you can't do this action in this phase", new Image(String.valueOf(getClass().
                                getResource("/Images/confusedAnimeGirl.jpg"))));
                break;
            case CARD_SET_SUCCESSFULLY:
                AudioController.playSet();
                break;
            case CANT_CHANGE_THIS_CARD_POSITION:
                UtilityController.makeAlert("No!!", "Can't be done!",
                        "you can't change this card position", new Image(String.valueOf(getClass().
                                getResource("/Images/confusedAnimeGirl.jpg"))));
                break;
            case CANT_CHANGE_HIDDEN_CARD_POSITION:
                UtilityController.makeAlert("No!!", "Can't be done!",
                        "You can't change hidden cards position", new Image(String.valueOf(getClass().
                                getResource("/Images/confusedAnimeGirl.jpg"))));
                break;
            case ALREADY_CHANGED_POSITION:
                UtilityController.makeAlert("No!!", "Can't be done!",
                        "you already changed this card position in turn", new Image(String.valueOf(getClass().
                                getResource("/Images/confusedAnimeGirl.jpg"))));
                break;
            case ALREADY_WANTED_POSITION:
                UtilityController.makeAlert("No!!", "Can't be done!",
                        "selected card is already in wanted position", new Image(String.valueOf(getClass().
                                getResource("/Images/confusedAnimeGirl.jpg"))));
                break;
            case MONSTER_CARD_POSITION_CHANGED_SUCCESSFULLY:
                AudioController.playPosChanged();
                System.out.println("monster card position changed successfully");
                break;
            case CANT_FLIP_SUMMON:
                UtilityController.makeAlert("No!!", "Can't be done!",
                        "you can't flip summon this card", new Image(String.valueOf(getClass().
                                getResource("/Images/confusedAnimeGirl.jpg"))));
                break;
            case FLIP_SUMMONED_SUCCESSFULLY: {
                AudioController.playFlipSummoned();
                gamePlayController.checkForTrapHole();
            }
            break;
            case YOU_CANT_ATTACK_WITH_THIS_CARD:
                UtilityController.makeAlert("No!!", "Can't be done!",
                        "you can't attack with this card", new Image(String.valueOf(getClass().
                                getResource("/Images/confusedAnimeGirl.jpg"))));
                break;
            case NOT_IN_ATTACK_POSITION:
                System.out.println("Your card is not in attack position");
                break;
            case ALREADY_ATTACKED:
                UtilityController.makeAlert("No!!", "Can't be done!",
                        "this card already attacked", new Image(String.valueOf(getClass().
                                getResource("/Images/confusedAnimeGirl.jpg"))));
                break;
            case NO_CARD_TO_ATTACK:
                UtilityController.makeAlert("No!!", "Can't be done!",
                        "there is no card to attack here", new Image(String.valueOf(getClass().
                                getResource("/Images/confusedAnimeGirl.jpg"))));
                break;
            case DESTROYED_OPPONENT_MONSTER_AND_OPPONENT_RECEIVED_DAMAGE: {
                output = "your opponent's monster is destroyed and your opponent receives " + AttackController.getDamage() + " battle damage";
                Timeline timeline = attackFire();
                timeline.setOnFinished(vent -> {
                    UtilityController.makeAlert("Well done!!", "You are a true warrior...!", output, new Image(String.valueOf(getClass().
                            getResource("/Images/fightAnimeGirl.jpg"))));
                });
                timeline.play();
            }
            break;
            case BOTH_MONSTERS_ARE_DESTROYED: {
                Timeline timeline = attackFire();
                timeline.setOnFinished(vent -> {
                    UtilityController.makeAlert("hmmm...", "both bad news and good news!...", "both you and your opponent monster cards are destroyed and no one receives damage", new Image(String.valueOf(getClass().
                            getResource("/Images/fightAnimeGirl.jpg"))));
                });
                timeline.play();
            }
            break;
            case DESTROYED_CURRENT_MONSTER_AFTER_ATTACK: {
                Timeline timeline = attackFire();
                output = "your monster card is destroyed and you received " + AttackController.getDamage() + " battle damage";
                timeline.setOnFinished(vent -> {
                    UtilityController.makeAlert("bad news!!", "", output, new Image(String.valueOf(getClass().
                            getResource("/Images/sadAnimeGirl.jpg"))));
                });
                timeline.play();
            }
            break;
            case DEFENCE_POSITION_MONSTER_DESTROYED: {
                Timeline timeline = attackFire();
                timeline.setOnFinished(vent -> {
                    UtilityController.makeAlert("Well done!!", "You are a true warrior...!", "the defense position monster is destroyed", new Image(String.valueOf(getClass().
                            getResource("/Images/fightAnimeGirl.jpg"))));
                });
                timeline.play();
            }
            break;
            case NO_CARD_DESTROYED: {
                Timeline timeline = attackFire();
                timeline.setOnFinished(vent -> {
                    UtilityController.makeAlert("hmmm...", "nothing special!...", "no card is destroyed", new Image(String.valueOf(getClass().
                            getResource("/Images/fightAnimeGirl.jpg"))));
                });
                timeline.play();
            }
            break;
            case NO_CARD_DESTROYED_CURRENT_DAMAGED: {
                Timeline timeline = attackFire();
                output = "no card is destroyed and you received " + AttackController.getDamage() + " battle damage";
                timeline.setOnFinished(vent -> {
                    UtilityController.makeAlert("bad news!!", "", output, new Image(String.valueOf(getClass().
                            getResource("/Images/sadAnimeGirl.jpg"))));
                });
                timeline.play();
            }
            break;
            case CANT_ATTACK_DIRECTLY: {
                UtilityController.makeAlert("No!!", "Can't be done!",
                        "you can't attack the opponent directly", new Image(String.valueOf(getClass().
                                getResource("/Images/confusedAnimeGirl.jpg"))));

            }
            break;
            case YOUR_OPPONENT_DAMAGED_DIRECT_ATTACK: {
                output = "your opponent receives " + AttackController.getDamage() + " battle damage";
                Timeline timeline = attackFire();
                timeline.setOnFinished(vent -> {
                    UtilityController.makeAlert("Well done!!", "You are a true warrior...!", output, new Image(String.valueOf(getClass().
                            getResource("/Images/fightAnimeGirl.jpg"))));
                });
                timeline.play();
            }
            break;
            case ACTIVATE_EFFECT_ONLY_ON_SPELL:
                UtilityController.makeAlert("No!!", "Can't be done!",
                        "activate effect is only for spell cards", new Image(String.valueOf(getClass().
                                getResource("/Images/confusedAnimeGirl.jpg"))));
                break;
            case CANT_ACTIVATE_EFFECT_ON_THIS_PHASE:
                UtilityController.makeAlert("No!!", "Can't be done!",
                        "you can't activate an effect on this phase", new Image(String.valueOf(getClass().
                                getResource("/Images/confusedAnimeGirl.jpg"))));
                break;
            case YOU_ALREADY_ACTIVATED_THIS_CARD:
                UtilityController.makeAlert("No!!", "Can't be done!",
                        "you have already activated this card", new Image(String.valueOf(getClass().
                                getResource("/Images/confusedAnimeGirl.jpg"))));
                break;
            case SPELL_ZONE_CARD_IS_FULL:
                UtilityController.makeAlert("No!!", "Can't be done!",
                        "spell card zone is full", new Image(String.valueOf(getClass().
                                getResource("/Images/confusedAnimeGirl.jpg"))));
                break;
            case PREPARATIONS_OF_THIS_SPELL_ARE_NOT_DONE_YET:
                UtilityController.makeAlert("No!!", "Can't be done!",
                        "preparations of this spell are not done yet", new Image(String.valueOf(getClass().
                                getResource("/Images/confusedAnimeGirl.jpg"))));
                break;
            case SPELL_ACTIVATED:
            case TRAP_ACTIVATED:
                AudioController.playActivated();
                break;
            case CANT_BE_ADDED_TO_CHAIN:
                UtilityController.makeAlert("No!!", "can't be done",
                        "you can't add this card ro chain", new Image(String.valueOf(getClass().
                                getResource("/Images/confusedAnimeGirl.jpg"))));
                break;
            case EFFECT_DONE_SUCCESSFULLY:
                AudioController.playEffectDone();
                break;
            case SHOW_CARD:
                System.out.println(gamePlayController.getSelectedCard().cardShow());
                gamePlayController.setSelectedCard(null);
                break;
            case CANNOT_ACCESS_RIVAL_CARD:
                UtilityController.makeAlert("No!!", "can't be done",
                        "You can't access this card!", new Image(String.valueOf(getClass().
                                getResource("/Images/confusedAnimeGirl.jpg"))));
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
                UtilityController.makeAlert("No!!", "can't be done",
                        "invalid cell number!", new Image(String.valueOf(getClass().
                                getResource("/Images/confusedAnimeGirl.jpg"))));
                break;
            case WANNA_ADD_ANOTHER_CARD: //TODO
                System.out.println("wanna add another card to be destroyed?");
                break;
            case NO_WAY_TO_RITUAL_SUMMON:
                UtilityController.makeAlert("No!!", "can't be done",
                        "there is no way you could ritual summon a monster", new Image(String.valueOf(getClass().
                                getResource("/Images/confusedAnimeGirl.jpg"))));
                break;
            case SHOULD_RIVAL_SUMMON_RIGHT_NOW:
                UtilityController.makeAlert("No!!", "can't be done",
                        "you should ritual summon right now", new Image(String.valueOf(getClass().
                                getResource("/Images/confusedAnimeGirl.jpg"))));
                break;
            case ENTER_POS:
                System.out.println("choose position? (ATK/DEF)");
                break;
            case ENTER_SOME_NUMBERS:
                System.out.println("enter numbers of monsters to be destroyed. (e.g :1 2 3)");
                break;
            case LEVELS_DONT_MATCH:
                UtilityController.makeAlert("No!!", "can't be done",
                        "selected monsters levels don't match with ritual monster", new Image(String.valueOf(getClass().
                                getResource("/Images/confusedAnimeGirl.jpg"))));
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
            case TURN_CHANGED: {
                AudioController.playChangeTurn();
                swapBoards();
                updateDecks();
                updateHandGrid(gamePlayController.getCurrentPlayer());
                updateHandGrid(gamePlayController.getOpponentPlayer());
                refreshPlayersBox();
            }
            default:
                break;
        }
    }

    public void swapBoards() {
        firstPlayerBoardCards.getChildren().clear();
        secondPlayerBoardCards.getChildren().clear();
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 5; j++) {
                ImageView temp;
                temp = playerCards[i][j];
                playerCards[i][j] = opponentCards[i][j];
                opponentCards[i][j] = temp;
                setOnMouseClickedForCardImage(playerCards[i][j], "player");
                firstPlayerBoardCards.add(playerCards[i][j], j, i);
                setOnMouseClickedForCardImage(opponentCards[i][j], "opponent");
                secondPlayerBoardCards.add(opponentCards[i][j], j, i);
            }
        }
    }

    public void hiddenDefensePositionMonsterDestroyed(String name) {
        Timeline timeline = attackFire();
        timeline.setOnFinished(vent -> {
            UtilityController.makeAlert("Well done!!", "You are a true warrior...!", "opponent's monster card was " + name + " and the defense position monster is destroyed", new Image(String.valueOf(getClass().
                    getResource("/Images/fightAnimeGirl.jpg"))));
        });
        timeline.play();
    }

    public String yesNoQuestionAlert(String question) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(question);
        alert.setHeaderText("Yes Or No?");
        ButtonType buttonTypeOne = new ButtonType("Yes");
        ButtonType buttonTypeTwo = new ButtonType("No");
        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOne) {
            return "yes";
        } else if (result.get() == buttonTypeTwo) {
            return "no";
        } else {
            return "no";
        }
    }

    public void hiddenDefensePosNoCardDestroyed(String name) {
        Timeline timeline = attackFire();
        timeline.setOnFinished(vent -> {
            UtilityController.makeAlert("hmmm...", "nothing special!...", "opponent's monster card was " + name + " and no card is destroyed", new Image(String.valueOf(getClass().
                    getResource("/Images/fightAnimeGirl.jpg"))));
        });
        timeline.play();
    }

    public void hiddenDefensePosNoCardDestroyedWithDamage(String name) {
        Timeline timeline = attackFire();
        timeline.setOnFinished(vent -> {
            UtilityController.makeAlert("hmmm...", "nothing special!...", "opponent's monster card was " + name + " no card is destroyed and you received " + AttackController.getDamage() + " battle damage", new Image(String.valueOf(getClass().
                    getResource("/Images/fightAnimeGirl.jpg"))));
        });
        timeline.play();
    }


    public void roundFinished(String winner) {
        System.out.println(winner + " won the game!");
    }

    public void matchFinished(String winner, int score) {
        UtilityController.makeAlert("Finished!!", "Shake hands!", winner + " won the game with score : " + score,
                new Image(String.valueOf(getClass().
                        getResource("/Images/fightAnimeGirl.jpg"))));
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setNode(grandFather);
        fadeTransition.setDuration(Duration.millis(3000));
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.play();
        fadeTransition.setOnFinished(event -> {
            Scene mainMenuScene = null;
            try {
                mainMenuScene = new Scene(FXMLLoader.load(getClass().getResource("/FxmlFiles/MainMenu.fxml")));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Main.stage.setScene(mainMenuScene);
        });
    }

    public void lifePointReduced(int point) {
        System.out.println("current player received " + point + " damage!");

    }

    public void doYouWannaActivateSpecialCard(String name) {
        System.out.println("do you wanna activate your " + name + " ?");
    }


    public String defineStarterOfNextRound() {
//        return yesNoQuestionAlert("Dear " + name + " do you wanna be first player of this round?");
        UtilityController.makeAlert("Sad!!", "Oh no!...", "You lost 1 round! Try for Next Round", new Image(String.valueOf(getClass().
                getResource("/Images/fightAnimeGirl.jpg"))));
        return "yes";
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
