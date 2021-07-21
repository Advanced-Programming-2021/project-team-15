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

    public Object callMethods(HashMap<String ,Object> jsonObject){
        switch ((String) jsonObject.get("method")) {
            case "getUser" :
                return MainMenuController.getUserByToken((String) jsonObject.get("token"));
            default: {
                System.out.println("Something Happened!");
                return "Something Happened!";
            }
        }
    }
    public static Object getUserByToken(String token)
    {
        System.out.println(activeUsers.get(token).getUserName());
        return activeUsers.get(token);
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
