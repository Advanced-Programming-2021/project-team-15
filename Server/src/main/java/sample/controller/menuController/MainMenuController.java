package sample.controller.menuController;

import sample.controller.responses.MainMenuResponses;
import sample.model.User;
import sample.view.Menu;

import java.util.HashMap;

public class MainMenuController extends MenuController {
    private static final HashMap<String, Menu> allMenus = new HashMap<>();
    private static HashMap<String, User> activeUsers = new HashMap<>();

    public MainMenuController() {
        super("sample.Main Menu");
    }

    public static HashMap<String, User> getActiveUsers() {
        return activeUsers;
    }

    public static User getUserByToken(String token) {
        System.out.println("we are sending "+activeUsers.get(token).getUserName()+" with trunk size : "+activeUsers.get(token).getAllCardsOfUser().size());
        return activeUsers.get(token);
    }

    public static HashMap<String, Menu> getAllMenus() {
        return allMenus;
    }

    public Object callMethods(HashMap<String, Object> jsonObject) {
        switch ((String) jsonObject.get("method")) {
            case "getUser":
                return MainMenuController.getUserByToken((String) jsonObject.get("token"));
            default: {
                System.out.println("Something Happened!");
                return "Something Happened!";
            }
        }
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
