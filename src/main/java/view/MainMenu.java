package view;

import controller.MainMenuController;
import controller.RegexController;

import java.util.Scanner;

public class MainMenu extends Menu {
    private static MainMenu mainMenu;

    private MainMenu() {
        super("Main Menu");
    }

    public static MainMenu getInstance() {
        if (mainMenu == null)
            mainMenu = new MainMenu();
        return mainMenu;
    }

    MainMenuController mainMenuController = new MainMenuController();

    @Override
    public void scanInput() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();
            if (input.equals("menu exit"))
                checkAndCallMenuExit();
            else if (input.equals("user logout"))
                mainMenuController.logout();
            else if (input.startsWith("menu enter")) {
                if (regexController.enterMenuRegex(input)) ;
            } else if (regexController.showMenuRegex(input))
                checkAndCallShowCurrentMenu();
            else System.out.println("invalid command");
        }
    }
}



