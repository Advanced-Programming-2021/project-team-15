package controller;

import model.User;
import view.MainMenu;

public class LoginController extends MenuController{

    public LoginController() {
        super("Login Menu");
    }
    public void registerUser(String userName , String nickName , String passWord) {
        new User(userName,nickName,passWord);
    }
    public void loginUser(String username, String password) {
        //System.out.println(user.getUserName());
        super.setUser(User.getUserByUserName(username));
        //System.out.println(super.user.getUserName());
        MainMenu mainMenu = new MainMenu();
        mainMenu.scanInput();
    }
}
