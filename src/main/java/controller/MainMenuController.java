package controller;

import view.LoginMenu;

public class MainMenuController extends MenuController{
    LoginMenu loginMenu = new LoginMenu("Login Menu");
    public MainMenuController(String menuName) {
        super(menuName);
    }
    public void menuEnter(String menuName)
    {

    }
    public void logout(){
        loginMenu.scanInput();
    }
}
