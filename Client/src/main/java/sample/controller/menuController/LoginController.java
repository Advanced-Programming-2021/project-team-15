package sample.controller.menuController;

import org.json.JSONObject;
import sample.controller.ClientManager;
import sample.controller.responses.LoginMenuResponses;
import sample.model.User;

import java.util.HashMap;
import java.util.Map;

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

    public LoginMenuResponses loginUser(String username, String password) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("class", "LoginController");
        map.put("method", "loginUser");
        map.put("username", username);
        map.put("password", password);
        String output = (String) ClientManager.sendAndGetResponse(map);
        if (output.startsWith("USER_LOGIN_SUCCESSFUL")) {
            String[] logRes = output.split(" ");
            MainMenuController.setToken(logRes[1]);
            System.out.println(MainMenuController.getToken());
            return LoginMenuResponses.valueOf(logRes[0]);
        }
        return LoginMenuResponses.valueOf(output);
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
