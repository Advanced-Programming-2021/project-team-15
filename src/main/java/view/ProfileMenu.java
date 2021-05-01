package view;

import controller.RegexController;

import java.util.Scanner;

public class ProfileMenu extends Menu {
    public ProfileMenu() {
        super("Profile Menu");
    }

    @Override
    public void scanInput() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();
            if (input.equals("menu exit"))
                checkAndCallMenuExit();
            else if (regexController.changeNicknameRegex(input))
                continue;
            else if (regexController.changePasswordRegex(input))
                continue;
            else if (regexController.showMenuRegex(input))
                checkAndCallShowCurrentMenu();
            else System.out.println("invalid command");
            if (super.isExit) {
                super.isExit = false;
                return;
            }
        }

    }
}

