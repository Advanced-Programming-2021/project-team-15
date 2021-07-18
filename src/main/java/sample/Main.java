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
import sample.view.WelcomeMenu;

import java.io.IOException;

/*To Handle :
giving other menus instructions
card show (page 10)
*/
//TODO check:
//attack sample.controller line 102 - attackToDefencePos return

public class Main {

    public static void main(String[] args) throws IOException, CsvValidationException {
        DatabaseController databaseController = new DatabaseController();
        databaseController.loadGameCards();
        AudioController.playMenu();
        WelcomeMenu.run(args);
    }


}