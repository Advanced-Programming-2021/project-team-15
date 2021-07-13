package sample.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.control.skin.SliderSkin;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.controller.utilizationController.AudioController;

public class GameSettingController {
    @FXML
    private Slider volumeSlider;
    private Stage stage;
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void backButtonClicked(MouseEvent mouseEvent) {
        AudioController.playClick();
        stage.close();
    }

    public void init() {
        volumeSlider.setValue(AudioController.mainMusic.getVolume()*100);
        volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                AudioController.mainMusic.setVolume(t1.doubleValue()/100);
            }
        });
    }
}
