package view;

import controller.LoginController;
import controller.RegexController;

import java.util.Scanner;
import java.util.regex.Matcher;

public class LoginMenu extends Menu{
    RegexController regexController = new RegexController();
    public LoginMenu(String menuName) {
        super(menuName);
    }
    LoginController loginController = new LoginController("Login Menu");
    @Override
    public void scanInput() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();
            if(input.startsWith("user create ")) {
                if(!regexController.createUserRegex(input))
                    System.out.println("invalid command");
            }
            if(input.startsWith("user login ")) {
                if (!regexController.loginUserRegex(input))
                    System.out.println("invalid command");
            }
            else if (input.equals("menu exit"))
                checkAndCallMenuExit();
            else if(regexController.showMenuRegex(input))
              checkAndCallShowCurrentMenu();
                else System.out.println("invalid command");
            if (super.isExit) {
                super.isExit = false;
                return;
            }
        }
    }
}
