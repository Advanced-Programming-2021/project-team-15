package sample.controller.menuController;

import org.json.JSONObject;
import sample.controller.responses.LoginMenuResponses;
import sample.model.User;

import java.util.UUID;

public class LoginController extends MenuController {
    public LoginController() {
        super("Login Menu");
    }

    public LoginMenuResponses registerUser(String userName, String nickName, String passWord) {
        databaseController.refreshUsersFromFileJson();
        if (User.getUserByUserName(userName) != null)
            return LoginMenuResponses.USER_USERNAME_ALREADY_EXISTS;
        else if (User.getUserByNickname(nickName) != null)
            return LoginMenuResponses.USER_NICKNAME_ALREADY_EXISTS;
        else {
            User.getAllUsers().add(new User(userName, nickName, passWord));
            databaseController.refreshUsersToFileJson();
            return LoginMenuResponses.USER_CREATE_SUCCESSFUL;
        }
    }

    public synchronized String loginUser(String username, String password) {
        databaseController.refreshUsersFromFileJson();
        if (User.getUserByUserName(username) == null || !User.getUserByUserName(username).getPassWord().equals(password))
            return LoginMenuResponses.USER_USERNAME_PASSWORD_NOT_MATCHED.toString();
        else {
            User user = User.getUserByUserName(username);
            MenuController.setUser(user);
            String token = UUID.randomUUID().toString();
            MainMenuController.getActiveUsers().put(token,user);
            return LoginMenuResponses.USER_LOGIN_SUCCESSFUL+" "+token;
        }
    }

    public String callMethods(JSONObject jsonObject) {
        LoginMenuResponses loginMenuResponses;
        switch (jsonObject.getString("method")) {
            case "loginUser" :
                return loginUser(
                       jsonObject.getString("username"), jsonObject.getString("password"));
            case "registerUser" :
                loginMenuResponses = registerUser(
                        jsonObject.getString("username"), jsonObject.getString("nickname"), jsonObject.getString("password"));
                break;
            default:
                return "Something Happened!";
        }
        return loginMenuResponses.toString();
    }

    public LoginMenuResponses removeUser(String username, String password) {
        databaseController.refreshUsersFromFileJson();
        if (User.getUserByUserName(username) == null) return LoginMenuResponses.USER_USERNAME_PASSWORD_NOT_MATCHED;
        else if (!User.getUserByUserName(username).getPassWord().equals(password))
            return LoginMenuResponses.USER_USERNAME_PASSWORD_NOT_MATCHED;
        else {
            User.removeUserByUsername(username);
            databaseController.refreshUsersToFileJson();
            return LoginMenuResponses.USER_REMOVE_SUCCESSFUL;
        }
    }
}
