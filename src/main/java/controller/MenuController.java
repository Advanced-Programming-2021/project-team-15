package controller;

import com.rits.cloning.Cloner;
import model.Card;
import model.User;

import java.util.ArrayList;
import java.util.Comparator;

public class MenuController {
    private static MenuController menuController = null;
    public static MenuController getInstance() {
        if (menuController==null)
            menuController = new MenuController("Menu");
        return menuController;
    }
    public static User user;
    protected JSONController jsonController = new JSONController();
    protected Cloner cloner = new Cloner();
    private String menuName;
    public MenuController(String menuName) {
        this.menuName = menuName;
        menuController = this;
    }

    public MenuResponse showCurrentMenu() {
        return MenuResponse.SHOW_Current_Menu;
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
