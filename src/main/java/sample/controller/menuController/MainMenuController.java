package sample.controller.menuController;

import sample.controller.responses.MainMenuResponses;
import sample.view.*;

import java.util.HashMap;

public class MainMenuController extends MenuController {
    private static final HashMap<String, Menu> allMenus = new HashMap<>();
    static {
        allMenus.put("Duel", DuelMenu.getInstance());
        allMenus.put("Deck", DeckMenu.getInstance());
        allMenus.put("Scoreboard", ScoreboardMenu.getInstance());
        allMenus.put("Profile", ProfileMenu.getInstance());
        allMenus.put("Shop",ShopMenu.getInstance());
        allMenus.put("ImportExport", ImportExportMenu.getInstance());
    }
    public MainMenuController() {
        super("sample.Main Menu");
    }

    public static HashMap<String, Menu> getAllMenus() {
        return allMenus;
    }

    public MainMenuResponses menuEnter(String menuName) {
        if (allMenus.containsKey(menuName))
            return MainMenuResponses.MENU_ENTER_SUCCESSFUL;
        else return MainMenuResponses.MENU_NAME_NOT_EXIST;
    }
    public MainMenuResponses logout() {
        return MainMenuResponses.USER_LOGOUT_SUCCESSFUL;
    }
    public MainMenuResponses increaseMoneyCheat(int amount) {
        user.changeMoney(amount);
        databaseController.refreshUsersToFileJson();
        return MainMenuResponses.INCREASED_MONEY_SUCCESSFULLY;
    }
}
