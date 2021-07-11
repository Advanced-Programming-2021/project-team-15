package sample.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import sample.Main;
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
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/FxmlFiles/CreateMonster.fxml")));
        Main.stage.setScene(scene);
    }

    public void createMagicButtonClicked(MouseEvent mouseEvent)throws IOException {
        AudioController.playClick();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/FxmlFiles/CreateMagic.fxml")));
        Main.stage.setScene(scene);
    }

    public void backButtonClicked(MouseEvent mouseEvent) throws IOException{
        AudioController.playClick();
        Scene mainMenuScene = new Scene(FXMLLoader.load(getClass().getResource("/FxmlFiles/MainMenu.fxml")));
        Main.stage.setScene(mainMenuScene);
    }
}
