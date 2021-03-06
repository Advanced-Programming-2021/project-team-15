package sample.view;

import com.opencsv.exceptions.CsvValidationException;
import sample.controller.menuController.MenuController;
import sample.controller.utilizationController.RegexController;
import sample.controller.utilizationController.UtilityController;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Menu {
    static {
        UtilityController.initializeScanner();
    }

    private final MenuController menuController;
    protected String menuName;
    protected Menu nextMenu;
    protected boolean isExit = false;
    protected boolean isEnter = false;
    RegexController regexController = new RegexController();
    private MenuController.MenuResponse MenuControllerResponse;

    public Menu(String menuName) {
        this.setMenuName(menuName);
        menuController = new MenuController("Menu");
    }

    public abstract void scanInput() throws IOException, CsvValidationException;

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
