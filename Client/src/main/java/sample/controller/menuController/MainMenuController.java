package sample.controller.menuController;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;
import org.json.JSONObject;
import sample.controller.ClientManager;
import sample.controller.responses.MainMenuResponses;
import sample.model.User;
import sample.model.cards.Card;
import sample.model.cards.MagicCard;
import sample.model.cards.MonsterCard;
import sample.view.*;

import java.lang.reflect.Type;
import java.util.*;

public class MainMenuController extends MenuController {
    private static final HashMap<String, Menu> allMenus = new HashMap<>();

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        MainMenuController.user = user;
    }

    private static User user;

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        MainMenuController.token = token;
    }

    private static String token;

    public static void getUserByToken(String token) {
        Map<String, String> map = new HashMap<>();
        map.put("class", "MainMenuController");
        map.put("method", "getUserByToken");
        map.put("token",MainMenuController.getToken());
        JSONObject jsonObject = new JSONObject(map);
        String userString = ClientManager.sendAndGetResponse(jsonObject.toString());
        GsonBuilder gsonBuilder = new GsonBuilder();
        RuntimeTypeAdapterFactory<Card> runtimeTypeAdapterFactory = RuntimeTypeAdapterFactory
                .of(Card.class, "type").
                        registerSubtype(MonsterCard.class, "MONSTER").
                        registerSubtype(MagicCard.class, "MAGIC");
        Type usersListType = new TypeToken<User>() {
        }.getType();
        setUser(gsonBuilder.registerTypeAdapterFactory(runtimeTypeAdapterFactory).create().fromJson(userString, usersListType));
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
