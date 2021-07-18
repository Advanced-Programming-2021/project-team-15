package sample.view;

import com.opencsv.exceptions.CsvValidationException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.controller.utilizationController.AudioController;
import sample.controller.utilizationController.DatabaseController;

import java.io.IOException;

/*To Handle :
giving other menus instructions
card show (page 10)
*/
//TODO check:
//attack sample.controller line 102 - attackToDefencePos return

public class WelcomeMenu extends Application {
    public static Stage stage;

    public static void run(String[] args) throws CsvValidationException, IOException {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        AudioController.playMenu();
        WelcomeMenu.stage = stage;
        Parent root = FXMLLoader.load(getClass().getResource("/FxmlFiles/Welcome.fxml"));
        stage.setScene(new Scene(root));
        stage.setMaximized(true);
        stage.show();
    }


    public void loginButtonClicked(MouseEvent mouseEvent) throws IOException {
        AudioController.playClick();
        Scene loginScene = new Scene(FXMLLoader.load(getClass().getResource("/FxmlFiles/Login.fxml")));
        stage.setScene(loginScene);
    }

    public void signUpButtonClicked(MouseEvent mouseEvent) throws IOException {
        AudioController.playClick();
        Scene signUpScene = new Scene(FXMLLoader.load(getClass().getResource("/FxmlFiles/Signup.fxml")));
        stage.setScene(signUpScene);
    }

    public void exitButtonClicked(MouseEvent mouseEvent) throws IOException {
        AudioController.playClick();
        System.exit(0);
    }

}