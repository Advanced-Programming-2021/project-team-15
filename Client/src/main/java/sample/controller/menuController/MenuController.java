package sample.controller.menuController;

import com.rits.cloning.Cloner;
import org.json.JSONObject;
import sample.controller.ClientManager;
import sample.controller.utilizationController.DatabaseController;
import sample.model.cards.Card;
import sample.model.User;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

import static sample.controller.menuController.MenuController.MenuResponse.SHOW_Current_Menu;

public class MenuController {
    private static MenuController menuController = null;
    public static MenuController getInstance() {
        if (menuController==null)
            menuController = new MenuController("Menu");
        return menuController;
    }
    public static User user;
    protected DatabaseController databaseController = new DatabaseController();
    protected Cloner cloner = new Cloner();
    private String menuName;
    public MenuController(String menuName) {
        this.menuName = menuName;
        menuController = this;
    }

    public MenuResponse showCurrentMenu() {
        return SHOW_Current_Menu;
    }

    public MenuResponse menuExit() {
        return MenuResponse.EXIT_MENU;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public static User getUser() {
        HashMap<String, Object> jsonObject=  new HashMap<>();
        jsonObject.put("method","getUser");
        jsonObject.put("class","MainMenuController");
        jsonObject.put("token",MainMenuController.getToken());
         User  user = (User) ClientManager.sendAndGetResponse(jsonObject);
         return user;
    }

    public static void setUser(User user) {
        MenuController.user = user;
    }

    protected ArrayList<Card> sortCardsByName(ArrayList<Card> cards) {
        ArrayList<Card> sortedCards = new ArrayList<>(cards);
        sortedCards.sort(Comparator.comparing(Card::getCardName));
        return sortedCards;
    }

    public enum MenuResponse {
        SHOW_Current_Menu,
        EXIT_MENU
    }
}
