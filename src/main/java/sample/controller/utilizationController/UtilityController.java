package sample.controller.utilizationController;

import sample.model.cards.Card;

import java.util.HashMap;
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
}
