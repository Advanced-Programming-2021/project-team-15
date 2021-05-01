package controller;

import view.LoginMenu;

public class MainMenuController extends MenuController {
    public MainMenuController() {
        super("Main Menu");
    }
    public void menuEnter(String menuName) {

    }
    public void logout() {
        System.out.println(super.getUser().getUserName());
    }
}
