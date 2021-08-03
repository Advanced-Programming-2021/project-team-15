package sample.controller.utilizationController;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import sample.model.cards.Card;

import java.util.HashMap;
import java.util.Optional;
import java.util.Scanner;

public class UtilityController {
    private static Scanner scanner;

    public static void initializeScanner() {
        scanner = new Scanner(System.in);
    }

    public static String getNextLine() {
        return scanner.nextLine();
    }

    public static void printString(String input) {
        System.out.println(input);
    }

    public static void showCardByName(String input) {
        HashMap<String, String> enteredDetails = new HashMap<>();
        RegexController regexController = new RegexController();
        if (!regexController.cardShowRegex(input, enteredDetails))
            System.out.println("invalid command");
        else {
            String cardName = enteredDetails.get("name");
            if (Card.getCardByName(cardName) == null) printString("No card exists with this name");
            else printString(Card.getCardByName(cardName).cardShow());
        }
    }

    public static void makeAlert(String title,String header, String context, Image graphic) {
        AudioController.playAlert();
        ImageView imageView = new ImageView(graphic);
        imageView.setFitHeight(80);
        imageView.setFitWidth(80);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.getDialogPane().getStylesheets().add(
                UtilityController.class.getResource("/CSSFiles/DuelAlert.css").toExternalForm());
        alert.getDialogPane().getStyleClass().add("dialog-pane");
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(context);
        alert.setGraphic(imageView);
        alert.setWidth(550);
        alert.setHeight(250);
        alert.show();
    }

}
