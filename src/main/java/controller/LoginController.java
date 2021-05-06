package controller;

import controller.responses.LoginMenuResponses;
import model.User;

public class LoginController extends MenuController {
    public LoginController() {
        super("Login Menu");
    }

    public LoginMenuResponses registerUser(String userName, String nickName, String passWord) {
        jsonController.refreshUsersFromFileJson();
        if (User.getUserByUserName(userName) != null)
            return LoginMenuResponses.USER_USERNAME_ALREADY_EXISTS;
        else if (User.getUserByNickname(nickName) != null)
            return LoginMenuResponses.USER_NICKNAME_ALREADY_EXISTS;
        else {
            new User(userName, nickName, passWord);
            jsonController.refreshUsersToFileJson();
            return LoginMenuResponses.USER_CREATE_SUCCESSFUL;
        }
    }

    public LoginMenuResponses loginUser(String username, String password) {
        jsonController.refreshUsersFromFileJson();
        if (User.getUserByUserName(username) == null || !User.getUserByUserName(username).getPassWord().equals(password))
            return LoginMenuResponses.USER_USERNAME_PASSWORD_NOT_MATCHED;
        else {
            MenuController.setUser(User.getUserByUserName(username));
            return LoginMenuResponses.USER_LOGIN_SUCCESSFUL;
        }
    }
}
