package sample.controller.menuController;

import com.google.gson.GsonBuilder;
import org.json.JSONObject;
import sample.controller.responses.LoginMenuResponses;
import sample.controller.responses.MainMenuResponses;
import sample.model.User;
import sample.view.*;

import java.util.HashMap;

public class MainMenuController extends MenuController {
    private static final HashMap<String, Menu> allMenus = new HashMap<>();

    public static HashMap<String, User> getActiveUsers() {
        return activeUsers;
    }

    private static HashMap<String, User> activeUsers = new HashMap<>();

    public String getUserByToken(JSONObject jsonObject) {
        User user = activeUsers.get(jsonObject.getString("token"));
        GsonBuilder gsonBuilder = new GsonBuilder();
        return gsonBuilder.create().toJson(user);
    }

    public String callMethods(JSONObject jsonObject) {
        switch (jsonObject.getString("method")) {
            case "getUserByToken" :
                return getUserByToken(jsonObject);
            default:
                return "Something Happened!";
        }
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
