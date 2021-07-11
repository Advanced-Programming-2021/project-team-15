package sample.view;

import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import sample.Main;
import sample.controller.gamePlayController.GamePlayController;
import sample.controller.responses.DuelMenuResponses;
import sample.controller.utilizationController.UtilityController;

import java.io.IOException;

import static sample.controller.responses.DuelMenuResponses.GAME_STARTED_SUCCESSFULLY;

public class PopupController {
    @FXML
    private VBox vbox;
    @FXML
    private TextField usernameTF;
    @FXML
    private ChoiceBox<Object> choiceBox;
    @FXML
    private Button button;

    private String round = "";
    private Stage stage;

    public void initialize() {
        TranslateTransition trans = new TranslateTransition(Duration.millis(1500), vbox);
        trans.setFromY(300);
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
                Main.stage.setScene(scene);
            }
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

}
