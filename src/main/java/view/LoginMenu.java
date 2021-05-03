package view;

import controller.LoginController;
import controller.responses.LoginMenuResponses;

import java.util.HashMap;
import java.util.Scanner;

public class LoginMenu extends Menu{
    private static LoginMenu loginMenu;
    private LoginMenu() {
        super("Login Menu");
    }
    public static LoginMenu getInstance() {  if(loginMenu==null)
        loginMenu = new LoginMenu();
        return loginMenu;
    }
    LoginController loginController = new LoginController();
    LoginMenuResponses responses;
    @Override
    public void scanInput() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();
            if(input.startsWith("user create ")) checkAndCallRegister(input);
            else if(input.startsWith("user login ")) checkAndCallLogin(input);
            else if (input.equals("menu exit")) checkAndCallMenuExit();
            else if(regexController.showMenuRegex(input)) checkAndCallShowCurrentMenu();
            else System.out.println("invalid command");
            if (super.isExit) {
                super.isExit = false;
                return;
            }
        }
    }
    private void checkAndCallRegister(String input) {
        HashMap<String,String> enteredDetails = new HashMap<>();
        if(!regexController.createUserRegex(input,enteredDetails))
            System.out.println("invalid command");
        else
            responses = loginController.registerUser
                    (enteredDetails.get("username"),
                    enteredDetails.get("nickname"),
                    enteredDetails.get("password"));
        switch (responses) {
            case USER_CREATE_SUCCESSFUL:
                System.out.println("user created successfully");
                break;
            case USER_NICKNAME_ALREADY_EXISTS:
                System.out.println("nickname");
                break;
            default:
        }
    }
    private void checkAndCallLogin(String input) {
        HashMap<String,String> enteredDetails = new HashMap<>();
        if (!regexController.loginUserRegex(input,enteredDetails))
            System.out.println("invalid command");
        else responses = loginController.loginUser(enteredDetails.get("username"),enteredDetails.get("password"));
    }
}