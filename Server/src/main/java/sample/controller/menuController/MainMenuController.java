package sample.controller.menuController;

import org.json.JSONObject;
import sample.controller.responses.MainMenuResponses;
import sample.model.Deck;
import sample.model.User;
import sample.model.cards.Card;
import sample.view.*;

import java.util.HashMap;

public class MainMenuController extends MenuController {
    private static final HashMap<String, Menu> allMenus = new HashMap<>();

    public static HashMap<String, User> getActiveUsers() {
        return activeUsers;
    }

    private static HashMap<String, User> activeUsers = new HashMap<>();

    public String callMethods(JSONObject jsonObject){
        switch (jsonObject.getString("method")) {
            case "getUser" :
                return MainMenuController.getUserByToken(jsonObject.getString("token"));
            default:
                return "Something Happened!";
        }
    }
    public static String getUserByToken(String token)
    { JSONObject jsonObject = new JSONObject();
        jsonObject.put("user", activeUsers.get(token));
        return jsonObject.toString();
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

}
