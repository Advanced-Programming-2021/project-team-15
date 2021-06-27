import com.opencsv.exceptions.CsvValidationException;
import controller.utilizationController.DatabaseController;
import view.LoginMenu;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, CsvValidationException {
        DatabaseController databaseController = new DatabaseController();
        databaseController.loadGameCards();
        LoginMenu loginMenu = LoginMenu.getInstance();
        loginMenu.scanInput();
    }
}
