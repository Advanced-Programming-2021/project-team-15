package sample.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.controller.utilizationController.AudioController;

import java.io.IOException;

public class CreateCardMenu {
    private static CreateCardMenu createCardMenu;
    public static CreateCardMenu getInstance() {
        if (createCardMenu == null)
            createCardMenu = new CreateCardMenu();
        return createCardMenu;
    }

    public void createMonsterButtonClicked(MouseEvent mouseEvent)throws IOException {
        AudioController.playClick();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FxmlFiles/CreateMonster.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        CreateMonsterController createMonsterController = loader.getController();
        Stage popupStage = new Stage();
        createMonsterController.setStage(popupStage);
        popupStage.initModality(Modality.WINDOW_MODAL);
        popupStage.setScene(scene);
        popupStage.showAndWait();
    }

    public void createMagicButtonClicked(MouseEvent mouseEvent)throws IOException {
        AudioController.playClick();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FxmlFiles/CreateMagic.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        CreateMagicController createMagicController = loader.getController();
        Stage popupStage = new Stage();
        createMagicController.setStage(popupStage);
        popupStage.initModality(Modality.WINDOW_MODAL);
        popupStage.setScene(scene);
        popupStage.showAndWait();
    }

    public void backButtonClicked(MouseEvent mouseEvent) throws IOException{
        AudioController.playClick();
        Scene mainMenuScene = new Scene(FXMLLoader.load(getClass().getResource("/FxmlFiles/MainMenu.fxml")));
        WelcomeMenu.stage.setScene(mainMenuScene);
    }

}
