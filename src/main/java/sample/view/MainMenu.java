package sample.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import sample.controller.menuController.MainMenuController;
import sample.controller.responses.MainMenuResponses;
import sample.controller.utilizationController.AudioController;

import java.io.IOException;

public class MainMenu {
    private static MainMenu mainMenu;
    MainMenuController mainMenuController = new MainMenuController();
    MainMenuResponses responses;

    public static MainMenu getInstance() {
        if (mainMenu == null)
            mainMenu = new MainMenu();
        return mainMenu;
    }

//    @Override
//    public void scanInput() throws IOException, CsvValidationException {
//        while (true) {
//            String input = UtilityController.getNextLine();
//            if (input.equals("menu exit")) checkAndCallMenuExit();
//            else if (input.equals("user logout")) checkAndCallLogout(input);
//            else if (input.startsWith("menu enter")) checkAndCallMenuEnter(input);
//            else if (input.startsWith("increase ")) checkAndCallIncreaseMoneyCheat(input);
//            else if (regexController.showMenuRegex(input)) checkAndCallShowCurrentMenu();
//            else System.out.println("invalid command");
//            if (isEnter) {
//                isEnter = false;
//                nextMenu.scanInput();
//            }
//            if (super.isExit) {
//                super.isExit = false;
//                return;
//            }
//        }
//    }

//    private void checkAndCallLogout(String input) {
//        responses = mainMenuController.logout();
//        printResponse(responses);
//        if (responses == MainMenuResponses.USER_LOGOUT_SUCCESSFUL) {
//            super.isExit = true;
//        }
//    }
//
//    private void checkAndCallIncreaseMoneyCheat(String input) {
//        HashMap<String, String> enteredDetails = new HashMap<>();
//        if (!regexController.increaseMoneyRegex(input, enteredDetails))
//            System.out.println("invalid command");
//        else {
//            responses = mainMenuController.increaseMoneyCheat(Integer.parseInt(enteredDetails.get("amount")));
//            printResponse(responses);
//        }
//    }
//
//    private void checkAndCallMenuEnter(String input) {
//        HashMap<String, String> enteredDetails = new HashMap<>();
//        if (!regexController.enterMenuRegex(input, enteredDetails))
//            System.out.println("invalid command");
//        else {
//            responses = mainMenuController.menuEnter(enteredDetails.get("menuName"));
//            if (responses == MainMenuResponses.MENU_ENTER_SUCCESSFUL) {
//                nextMenu = MainMenuController.getAllMenus().get(enteredDetails.get("menuName"));
//                isEnter = true;
//            }
//            printResponse(responses);
//        }
//    }

    public void profileButtonClicked(MouseEvent mouseEvent) throws IOException {
        AudioController.playClick();
        Scene profileScene = new Scene(FXMLLoader.load(getClass().getResource("/FxmlFiles/Profile.fxml")));
        WelcomeMenu.stage.setScene(profileScene);
    }

    public void scoreboardButtonClicked(MouseEvent mouseEvent) throws IOException {
//<<<<<<< HEAD
       // Scene scoreboardScene = new Scene(FXMLLoader.load(getClass().getResource("/FxmlFiles/Scoreboard.fxml")));
        //Main.stage.setScene(scoreboardScene);
        AudioController.playClick();
        ScoreboardMenu.getInstance().showScoreBoard(WelcomeMenu.stage);
//=======

      //  Scene scoreboardScene = new Scene(FXMLLoader.load(getClass().getResource("/FxmlFiles/Scoreboard.fxml")));
    //    Main.stage.setScene(scoreboardScene);
//>>>>>>> ghraphics-phase2
    }

    public void deckButtonClicked(MouseEvent mouseEvent) throws Exception {
        AudioController.playClick();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FxmlFiles/DeckMenu.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        WelcomeMenu.stage.setScene(scene);
        DeckMenu deckMenu = loader.getController();
        deckMenu.start();
    }

    public void duelButtonClicked(MouseEvent mouseEvent) throws IOException {
        AudioController.playClick();
        AudioController.playGame();
        Scene duelScene = new Scene(FXMLLoader.load(getClass().getResource("/FxmlFiles/GameMenu.fxml")));
        WelcomeMenu.stage.setScene(duelScene);
    }

    public void shopButtonClicked(MouseEvent mouseEvent) throws Exception {
        AudioController.playClick();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FxmlFiles/Shop.fxml"));
        Scene scene = new Scene(loader.load());
        ShopMenu shopMenu = loader.getController();
        shopMenu.initializeContainer();
        WelcomeMenu.stage.setScene(scene);
    }

    public void importExportButtonClicked(MouseEvent mouseEvent) throws IOException {
        AudioController.playClick();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FxmlFiles/ImportExport.fxml"));
        Scene scene = new Scene(loader.load());
        ImportExportMenu importExportMenu = loader.getController();
        importExportMenu.initializeScene();
        WelcomeMenu.stage.setScene(scene);
    }

    public void logoutButtonClicked(MouseEvent mouseEvent) throws IOException {
        AudioController.playClick();
        Scene loginScene = new Scene(FXMLLoader.load(getClass().getResource("/FxmlFiles/Login.fxml")));
        mainMenuController.logout();
        WelcomeMenu.stage.setScene(loginScene);
    }

    public void createCardButtonClicked(MouseEvent mouseEvent) throws IOException {
        AudioController.playClick();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/FxmlFiles/CreateCard.fxml")));
        WelcomeMenu.stage.setScene(scene);
    }

//    private void printResponse(MainMenuResponses mainMenuResponses) {
//        String output = "";
//        switch (mainMenuResponses) {
//            case USER_LOGOUT_SUCCESSFUL:
//                output = "user logged out successfully!";
//                break;
//            case MENU_NAME_NOT_EXIST:
//                output = "menu name you entered doesn't exist!";
//                break;
//            case MENU_ENTER_SUCCESSFUL:
//                output = "entered menu" + nextMenu.menuName + "...";
//                break;
//            case MENU_NAVIGATION_NOT_POSSIBLE:
//                output = "menu navigation is not possible";
//                break;
//            case INCREASED_MONEY_SUCCESSFULLY:
//                output = "CHEAT ACTIVATED!";
//                break;
//            default:
//                break;
//        }
//        System.out.println(output);
//    }
}



