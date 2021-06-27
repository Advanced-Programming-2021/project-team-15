import com.opencsv.exceptions.CsvValidationException;
import controller.utilizationController.DatabaseController;
import view.LoginMenu;

import java.io.IOException;

/*To Handle :
giving other menus instructions
card show (page 10)
*/
//TODO check:
//attack controller line 102 - attackToDefencePos return

public class Main {
    public static void main(String[] args) throws IOException, CsvValidationException {
        DatabaseController databaseController = new DatabaseController();
        databaseController.loadGameCards();
        LoginMenu loginMenu = LoginMenu.getInstance();
        loginMenu.scanInput();
    }
}
