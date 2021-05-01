package view;

import controller.MenuController;
import controller.RegexController;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Menu {
    RegexController regexController;
    private final MenuController menuController;
    protected String menuName;
    protected boolean isExit = false;
    private MenuController.MenuResponse MenuControllerResponse;

    public Menu(String menuName) {
        this.setMenuName(menuName);
        menuController = new MenuController("Menu");
        regexController = new RegexController();
    }

    public abstract void scanInput();

    public void checkAndCallShowCurrentMenu() {
        MenuControllerResponse = menuController.showCurrentMenu();
        if (MenuControllerResponse.equals(MenuController.MenuResponse.SHOW_Current_Menu))
            System.out.println(this.getMenuName());
    }

    protected Matcher getCommandMatcher(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(input);
    }

    public void checkAndCallMenuExit() {
        MenuControllerResponse = menuController.menuExit();
        if (MenuControllerResponse.equals(MenuController.MenuResponse.EXIT_MENU))
            isExit = true;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }
}
