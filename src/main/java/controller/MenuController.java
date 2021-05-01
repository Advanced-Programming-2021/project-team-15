package controller;

import model.User;

public class MenuController {
    private static MenuController menuController = null;
    public static MenuController getInstance() {
        if (menuController==null)
            menuController = new MenuController("Menu");
        return menuController;
    }
    public User user;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public enum MenuResponse {
        SHOW_Current_Menu,
        EXIT_MENU
    }
}
