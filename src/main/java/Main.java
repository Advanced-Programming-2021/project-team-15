import com.opencsv.exceptions.CsvValidationException;
import controller.utilizationController.DatabaseController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.LoginMenu;

import java.io.IOException;

/*To Handle :
giving other menus instructions
card show (page 10)
*/
//TODO check:
//attack controller line 102 - attackToDefencePos return

public class Main extends Application {
    public static Stage stage;

    public static void main(String[] args) throws IOException, CsvValidationException {
        DatabaseController databaseController = new DatabaseController();
        databaseController.loadGameCards();
        LoginMenu loginMenu = LoginMenu.getInstance();

    }

    @Override
    public void start(Stage stage) throws Exception {
        this.stage= stage;
        Parent root = FXMLLoader.load(getClass().getResource("/FxmFiles/Welcome.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }
}