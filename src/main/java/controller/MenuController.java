package controller;

public class MenuController {

    private String menuName;

    public MenuController(String menuName) {
        this.menuName = menuName;
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

    public enum MenuResponse {
        SHOW_Current_Menu,
        EXIT_MENU
    }
}
