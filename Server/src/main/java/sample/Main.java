package sample;

import com.opencsv.exceptions.CsvValidationException;
import sample.controller.ServerController;
import sample.controller.utilizationController.DatabaseController;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, CsvValidationException {
        DatabaseController databaseController = new DatabaseController();
        databaseController.loadGameCards();
        ServerController.run();
//        WelcomeMenu.run(args);
    }
}
