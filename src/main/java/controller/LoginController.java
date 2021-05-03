package controller;

import controller.responses.LoginMenuResponses;
import model.User;
import view.MainMenu;

public class LoginController extends MenuController{
    public LoginController() {
        super("Login Menu");
    }
    public LoginMenuResponses registerUser(String userName , String nickName , String passWord) {
        new User(userName,nickName,passWord);
        return LoginMenuResponses.USER_CREATE_SUCCESSFUL;
    }
    public LoginMenuResponses loginUser(String username, String password) {
        //System.out.println(user.getUserName());
        super.setUser(User.getUserByUserName(username));
        //System.out.println(super.user.getUserName());
        MainMenu mainMenu = MainMenu.getInstance();
        mainMenu.scanInput();
        return LoginMenuResponses.USER_LOGIN_SUCCESSFUL;
    }
}
