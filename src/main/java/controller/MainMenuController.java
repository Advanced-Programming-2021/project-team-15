package controller;

import controller.responses.MainMenuResponses;
import view.*;

import java.util.HashMap;

public class MainMenuController extends MenuController {
    private static HashMap<String, Menu> allMenus = new HashMap<>();
    static {
        allMenus.put("Duel", DuelMenu.getInstance());
        allMenus.put("Deck", DeckMenu.getInstance());
        allMenus.put("Scoreboard", ScoreboardMenu.getInstance());
        allMenus.put("Profile",ProfileMenu.getInstance());
        allMenus.put("Shop",ShopMenu.getInstance());
        allMenus.put("ImportExport",ImportExportMenu.getInstance());
    }
    public MainMenuController() {
        super("Main Menu");
    }
    public MainMenuResponses menuEnter(String menuName) {
        if (allMenus.containsKey(menuName))
            return MainMenuResponses.MENU_ENTER_SUCCESSFUL;
        else return MainMenuResponses.MENU_NAME_NOT_EXIST;
    }
    public MainMenuResponses logout() {
        return MainMenuResponses.USER_LOGOUT_SUCCESSFUL;
    }
}
