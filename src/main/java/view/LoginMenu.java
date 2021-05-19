package view;

import controller.LoginController;
import controller.responses.LoginMenuResponses;
import utility.Utility;

import java.util.HashMap;
import java.util.Scanner;

public class LoginMenu extends Menu {
    private static LoginMenu loginMenu;
    LoginController loginController = new LoginController();
    LoginMenuResponses responses;
    private String username;
    private String nickname;
    private String password;

    {
        nextMenu = MainMenu.getInstance();
    }

    private LoginMenu() {
        super("Login Menu");
    }

    public static LoginMenu getInstance() {
        if (loginMenu == null)
            loginMenu = new LoginMenu();
        return loginMenu;
    }

    @Override
    public void scanInput() {
        while (true) {
            String input = Utility.getNextLine();
            if (input.startsWith("user create ")) checkAndCallRegister(input);
            else if (input.startsWith("user login ")) this.isEnter = checkAndCallLogin(input);
            else if (input.startsWith("user remove ")) checkAndCallRemove(input);
            else if (input.equals("menu exit")) checkAndCallMenuExit();
            else if (regexController.showMenuRegex(input)) checkAndCallShowCurrentMenu();
            else System.out.println("invalid command");
            if (isEnter) {
                isEnter = false;
                nextMenu.scanInput();
            }
            if (super.isExit) {
                super.isExit = false;
                return;
            }
        }
    }

    private void checkAndCallRegister(String input) {
        HashMap<String, String> enteredDetails = new HashMap<>();
        if (!regexController.createUserRegex(input, enteredDetails))
            System.out.println("invalid command");
        else {
            username = enteredDetails.get("username");
            nickname = enteredDetails.get("nickname");
            password = enteredDetails.get("password");
            responses = loginController.registerUser(username, nickname, password);
            printResponse(responses);
        }
    }

    private boolean checkAndCallLogin(String input) {
        HashMap<String, String> enteredDetails = new HashMap<>();
        if (!regexController.loginUserRegex(input, enteredDetails))
            System.out.println("invalid command");
        else {
            responses = loginController.loginUser(enteredDetails.get("username"), enteredDetails.get("password"));
            printResponse(responses);
            return responses == LoginMenuResponses.USER_LOGIN_SUCCESSFUL;
        }
        return false;
    }

    private void checkAndCallRemove(String input) {
        HashMap<String, String> enteredDetails = new HashMap<>();
        if (!regexController.removeUserRegex(input, enteredDetails))
            System.out.println("invalid command");
        else {
            responses = loginController.removeUser(enteredDetails.get("username"), enteredDetails.get("password"));
            printResponse(responses);
        }
    }

    private void printResponse(LoginMenuResponses loginMenuResponses) {
        String output = "";
        switch (loginMenuResponses) {
            case USER_CREATE_SUCCESSFUL:
                output = "user created successfully!";
                break;
            case USER_NICKNAME_ALREADY_EXISTS:
                output = "user with nickname " + nickname + " already exists";
                break;
            case USER_USERNAME_ALREADY_EXISTS:
                output = "user with username " + username + " already exists";
                break;
            case USER_LOGIN_SUCCESSFUL:
                output = "user logged in successfully!";
                break;
            case USER_USERNAME_PASSWORD_NOT_MATCHED:
                output = "Username and password didn't match";
                break;
            case USER_LOGOUT_SUCCESSFUL:
                output = "user logged out successfully!";
                break;
            case USER_REMOVE_SUCCESSFUL:
                output = "user removed successfully!";
                break;
            default:
                break;
        }
        System.out.println(output);
    }
}