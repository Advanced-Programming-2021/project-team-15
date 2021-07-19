package sample;

import com.opencsv.exceptions.CsvValidationException;
import sample.controller.ServerController;
import sample.controller.utilizationController.AudioController;
import sample.controller.utilizationController.DatabaseController;
import sample.view.WelcomeMenu;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, CsvValidationException {
        WelcomeMenu.run(args);
        ServerController.run();
    }
}
