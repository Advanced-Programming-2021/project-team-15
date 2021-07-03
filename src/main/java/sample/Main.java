package sample;

import com.opencsv.exceptions.CsvValidationException;
import sample.controller.utilizationController.DatabaseController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

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
        launch(args);

    }

    @Override
    public void start(Stage stage) throws Exception {
        DatabaseController databaseController = new DatabaseController();
        databaseController.loadGameCards();
        this.stage= stage;
        Parent root = FXMLLoader.load(getClass().getResource("/FxmlFiles/Welcome.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }


    public void loginButtonClicked(MouseEvent mouseEvent)throws IOException{
        Scene loginScene= new Scene(FXMLLoader.load(getClass().getResource("/FxmlFiles/Login.fxml")));
        stage.setScene(loginScene);
        stage.show();
    }
    public void SignUpButtonClicked(MouseEvent mouseEvent) throws IOException{
        Scene signUpScene= new Scene(FXMLLoader.load(getClass().getResource("/FxmlFiles/Signup.fxml")));
        stage.setScene(signUpScene);
        stage.show();
    }

}