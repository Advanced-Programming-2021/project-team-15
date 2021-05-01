package controller;

import view.LoginMenu;

public class MainMenuController extends MenuController{
    LoginMenu loginMenu = new LoginMenu();
    public MainMenuController() {
        super("Main Menu");
    }
    public void menuEnter(String menuName)
    {

    }
    public void logout(){
        loginMenu.scanInput();
    }
}
