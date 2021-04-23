package view;

import controller.LoginController;

import java.util.Scanner;
import java.util.regex.Matcher;

public class LoginMenu extends Menu{
    public LoginMenu(String menuName) {
        super(menuName);
    }
    LoginController loginController = new LoginController("LoginController");
    @Override
    public void scanInput() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();
            if (input.equals("Login"))
                checkAndCallLogin(getCommandMatcher(input, "Login"));
            if (input.equals("Show"))
                checkAndCallShowCurrentMenu();
            if (input.equals("Exit"))
                checkAndCallMenuExit();
            if (super.isExit) {
                super.isExit = false;
                return;
            }
        }
    }
    public void checkAndCallLogin(Matcher matcher) {
        loginController.loginUser("pari","15");
    }
}
