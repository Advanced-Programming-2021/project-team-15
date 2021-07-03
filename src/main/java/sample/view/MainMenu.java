package view;

import com.opencsv.exceptions.CsvValidationException;
import controller.menuController.MainMenuController;
import controller.responses.MainMenuResponses;
import controller.utilizationController.UtilityController;

import java.io.IOException;
import java.util.HashMap;

public class MainMenu extends Menu {
    private static MainMenu mainMenu;
    MainMenuController mainMenuController = new MainMenuController();
    MainMenuResponses responses;
    private MainMenu() {
        super("sample.Main Menu");
    }

    public static MainMenu getInstance() {
        if (mainMenu == null)
            mainMenu = new MainMenu();
        return mainMenu;
    }

    @Override
    public void scanInput() throws IOException, CsvValidationException {
        while (true) {
            String input = UtilityController.getNextLine();
            if (input.equals("menu exit")) checkAndCallMenuExit();
            else if (input.equals("user logout")) checkAndCallLogout(input);
            else if (input.startsWith("menu enter")) checkAndCallMenuEnter(input);
            else if (input.startsWith("increase ")) checkAndCallIncreaseMoneyCheat(input);
            else if (regexController.showMenuRegex(input)) checkAndCallShowCurrentMenu();
            else System.out.println("invalid command");
            if (isEnter) {
                isEnter = false;
                nextMenu.scanInput();
            }
            if (super.isExit) {
                super.isExit = false;
                return;
            }
        }
    }

    private void checkAndCallLogout(String input) {
        responses = mainMenuController.logout();
        printResponse(responses);
        if (responses == MainMenuResponses.USER_LOGOUT_SUCCESSFUL) {
            super.isExit = true;
        }
    }

    private void checkAndCallIncreaseMoneyCheat(String input) {
        HashMap<String, String> enteredDetails = new HashMap<>();
        if (!regexController.increaseMoneyRegex(input, enteredDetails))
            System.out.println("invalid command");
        else {
            responses = mainMenuController.increaseMoneyCheat(Integer.parseInt(enteredDetails.get("amount")));
            printResponse(responses);
        }
    }

    private void checkAndCallMenuEnter(String input) {
        HashMap<String, String> enteredDetails = new HashMap<>();
        if (!regexController.enterMenuRegex(input, enteredDetails))
            System.out.println("invalid command");
        else {
            responses = mainMenuController.menuEnter(enteredDetails.get("menuName"));
            if (responses == MainMenuResponses.MENU_ENTER_SUCCESSFUL) {
                nextMenu = MainMenuController.getAllMenus().get(enteredDetails.get("menuName"));
                isEnter = true;
            }
            printResponse(responses);
        }
    }

    private void printResponse(MainMenuResponses mainMenuResponses) {
        String output = "";
        switch (mainMenuResponses) {
            case USER_LOGOUT_SUCCESSFUL:
                output = "user logged out successfully!";
                break;
            case MENU_NAME_NOT_EXIST:
                output = "menu name you entered doesn't exist!";
                break;
            case MENU_ENTER_SUCCESSFUL:
                output = "entered menu"+nextMenu.menuName+"...";
                break;
            case MENU_NAVIGATION_NOT_POSSIBLE:
                output = "menu navigation is not possible";
                break;
            case INCREASED_MONEY_SUCCESSFULLY:
                output = "CHEAT ACTIVATED!";
                break;
            default:
                break;
        }
        System.out.println(output);
    }
}



