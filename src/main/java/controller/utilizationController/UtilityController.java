package controller.utilizationController;

import java.util.Scanner;

public class UtilityController {
    private static Scanner scanner;
    public static void initializeScanner() {
        scanner = new Scanner(System.in);
    }
    public static String getNextLine() {
        return scanner.nextLine();
    }
}
