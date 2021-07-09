package sample;

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

public class Main extends Application {
    public static Stage stage;

    public static void main(String[] args) throws IOException, CsvValidationException {
        DatabaseController databaseController = new DatabaseController();
        databaseController.loadGameCards();
        AudioController.playMenu();
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Main.stage = stage;
        Parent root = FXMLLoader.load(getClass().getResource("/FxmlFiles/Welcome.fxml"));
        stage.setScene(new Scene(root));
        stage.setMaximized(true);
        stage.show();
    }


    public void loginButtonClicked(MouseEvent mouseEvent) throws IOException {
        Scene loginScene = new Scene(FXMLLoader.load(getClass().getResource("/FxmlFiles/Login.fxml")));
        stage.setScene(loginScene);

    }

    public void signUpButtonClicked(MouseEvent mouseEvent) throws IOException {
        Scene signUpScene = new Scene(FXMLLoader.load(getClass().getResource("/FxmlFiles/Signup.fxml")));
        stage.setScene(signUpScene);
    }

    public void exitButtonClicked(MouseEvent mouseEvent) throws IOException {
        System.exit(0);
    }

}