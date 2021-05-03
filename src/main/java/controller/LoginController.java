package controller;

import controller.responses.LoginMenuResponses;
import model.User;
import view.MainMenu;

public class LoginController extends MenuController{
    JSONController jsonController = new JSONController();
    public LoginController() {
        super("Login Menu");
    }
    public LoginMenuResponses registerUser(String userName , String nickName , String passWord) {
        if (User.getUserByUserName(userName)!=null)
            return LoginMenuResponses.USER_USERNAME_ALREADY_EXISTS;
        else if (User.getUserByNickname(nickName)!=null)
            return LoginMenuResponses.USER_NICKNAME_ALREADY_EXISTS;
        else {
            new User(userName, nickName, passWord);
            jsonController.refreshUsersFileJson();
            return LoginMenuResponses.USER_CREATE_SUCCESSFUL;
        }
    }
    public LoginMenuResponses loginUser(String username, String password) {
        if (User.getUserByUserName(username)==null || !User.getUserByUserName(username).getPassWord().equals(password))
            return LoginMenuResponses.USER_USERNAME_PASSWORD_NOT_MATCHED;
        else {
            super.setUser(User.getUserByUserName(username));
            return LoginMenuResponses.USER_LOGIN_SUCCESSFUL;
        }
    }
}
