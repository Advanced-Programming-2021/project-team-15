package sample.view;

import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import sample.controller.gamePlayController.GamePlayController;
import sample.controller.responses.DuelMenuResponses;
import sample.controller.utilizationController.UtilityController;
import sample.model.cards.MonsterCard;

import java.io.IOException;
import java.util.ArrayList;

import static sample.controller.responses.DuelMenuResponses.GAME_STARTED_SUCCESSFULLY;

public class PopupController {
    @FXML
    private AnchorPane anchor;
    @FXML
    private VBox vbox;
    @FXML
    private TextField usernameTF;
    @FXML
    private ChoiceBox<Object> choiceBox;
    @FXML
    private Button button;
    @FXML
    private ScrollPane cardsScrollPane;

    private String round = "";
    private Stage stage;

    public void initialize() {
        TranslateTransition trans = new TranslateTransition(Duration.millis(1000), vbox);
        trans.setFromY(200);
        trans.setToY(0);
        trans.play();
        String[] values = {"1", "3"};
        choiceBox.setItems(FXCollections.observableArrayList(values));
        choiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue ov, Number value, Number new_value) {
                round = values[new_value.intValue()];
            }
        });
        choiceBox.setCursor(Cursor.HAND);
        button.setText("start new game");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    startGameButtonClicked();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public void showList(ArrayList<MonsterCard> cards) {
        GridPane pane = new GridPane();
        pane.setPadding(new Insets(10, 10, 10, 20));
        cardsScrollPane.setContent(pane);
        pane.setAlignment(Pos.CENTER);
        pane.setVgap(10);
        pane.setHgap(10);
        for (int i = 0; i <cards.size(); i++) {
            StackPane stackPane= new StackPane();
            Rectangle rectangle = new Rectangle();
            rectangle.setHeight(153.5);
            rectangle.setWidth(105.25);
            rectangle.setArcHeight(5);
            rectangle.setArcWidth(5);
            stackPane.setPrefHeight(153.5);
            stackPane.setPrefWidth(105.25);
            rectangle.setFill(new ImagePattern(cards.get(i).getCardImage()));
            stackPane.getChildren().add(rectangle);
            Text text = new Text((i + ""));
            text.setFont(new Font("Verdana Bold", 20));
            text.setFill(Color.WHITE);
            text.setEffect(new Reflection());
            stackPane.getChildren().add(text);
            pane.add(stackPane,i,0);
        }
        anchor.getChildren().add(pane);
    }

    public void startGameButtonClicked() throws IOException {
        if (round.equals("") || usernameTF.getText().equals("")) {
            UtilityController.makeAlert("Confused!!", "What are you doing?!", "complete information!", new Image(String.valueOf(getClass().
                    getResource("/Images/confusedAnimeGirl.jpg"))));

        } else {
            DuelMenuResponses duelMenuResponses = GamePlayController.getInstance().startNewGame(usernameTF.getText(), Integer.parseInt(round));
            if (duelMenuResponses.equals(DuelMenuResponses.NO_PLAYER_WITH_THIS_USERNAME_EXISTS))
                UtilityController.makeAlert("Confused!!", "What are you doing?!", "this player doesn't exist!", new Image(String.valueOf(getClass().
                        getResource("/Images/confusedAnimeGirl.jpg"))));
            if (duelMenuResponses.equals(DuelMenuResponses.OPPONENT_PLAYER_HAS_NO_ACTIVE_DECK))
                UtilityController.makeAlert("Sad!!", "You are so bad!", "opponent player has no active deck!", new Image(String.valueOf(getClass().
                        getResource("/Images/sadAnimeGirl.jpg"))));
            if (duelMenuResponses.equals(DuelMenuResponses.CURRENT_PLAYER_HAS_NO_ACTIVE_DECK))
                UtilityController.makeAlert("Confused!!", "What are you doing?!", "you have no active deck!", new Image(String.valueOf(getClass().
                        getResource("/Images/confusedAnimeGirl.jpg"))));
            if (duelMenuResponses.equals(DuelMenuResponses.INVALID_CURRENT_PLAYER_DECK))
                UtilityController.makeAlert("Sad!!", "You are so bad!", "your active deck is not valid!", new Image(String.valueOf(getClass().
                        getResource("/Images/sadAnimeGirl.jpg"))));
            if (duelMenuResponses.equals(DuelMenuResponses.INVALID_OPPONENT_PLAYER_DECK))
                UtilityController.makeAlert("Sad!!", "You are so bad!", "opponent player active deck is not valid!", new Image(String.valueOf(getClass().
                        getResource("/Images/sadAnimeGirl.jpg"))));
            if (duelMenuResponses.equals(GAME_STARTED_SUCCESSFULLY)) {
                stage.close();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/FxmlFiles/RockPaperScissors.fxml"));
                Scene scene = new Scene(loader.load());
                RockPaperScissors rockPaperScissors = loader.getController();
                rockPaperScissors.start();
                WelcomeMenu.stage.setScene(scene);
            }
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

}
