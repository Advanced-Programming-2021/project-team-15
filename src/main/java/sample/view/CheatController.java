package sample.view;

import javafx.animation.*;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import sample.controller.gamePlayController.GamePlayController;
import sample.controller.utilizationController.AudioController;

public class CheatController {
    private Stage stage;
    @FXML
    private VBox cheatsVBox;
    @FXML
    private TextField increaseLPTF;
    @FXML
    private TextField increaseMoneyTF;
    @FXML
    private Label response;
    public void init() {
        AudioController.playSwamp();
        TranslateTransition trans = new TranslateTransition(Duration.millis(2000), cheatsVBox);
        trans.setFromY(400);
        trans.setToY(10);
        trans.play();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void increaseMoneyButtonClicked(MouseEvent mouseEvent) {
        if (increaseMoneyTF.getText().equals("")) setResponseText("Fill The Box!");
        else GamePlayController.getInstance().increaseMoney(Integer.parseInt(increaseMoneyTF.getText()));
        setResponseText("Cheat Activated!");
        increaseMoneyTF.setText("");
    }

    public void increaseLPButtonClicked(MouseEvent mouseEvent) {
        if (increaseLPTF.getText().equals("")) setResponseText("Fill The Box!");
        else GamePlayController.getInstance().increaseLp(Integer.parseInt(increaseLPTF.getText()));
        setResponseText("Cheat Activated!");
        increaseLPTF.setText("");
    }

    private void setResponseText(String text) {
        final IntegerProperty i = new SimpleIntegerProperty(0);
        Timeline timeline = new Timeline();
        KeyFrame keyFrame = new KeyFrame(
                Duration.millis(50),
                event -> {
                    if (i.get() > text.length()) {
                        timeline.stop();
                    } else {
                        response.setText(text.substring(0, i.get()));
                        i.set(i.get() + 1);
                    }
                });
        timeline.getKeyFrames().add(keyFrame);
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

}
