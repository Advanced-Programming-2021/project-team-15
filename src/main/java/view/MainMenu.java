package view;

import controller.MainMenuController;
import controller.RegexController;

import java.util.Scanner;

public class MainMenu extends Menu {
    RegexController regexController = new RegexController();
    MainMenuController mainMenuController = new MainMenuController("Main Menu");
    public MainMenu(String menuName) {
        super(menuName);
    }
    public void scanInput()
    {   Scanner scanner = new Scanner(System.in);
        while(true)
        {  String input = scanner.nextLine();
            if (input.equals("menu exit"))
            checkAndCallMenuExit();
            else if(input.equals("user logout"))
               mainMenuController.logout();
        else if (input.startsWith("menu enter"))
            { if(regexController.enterMenuRegex(input));
                continue;}
        else if(regexController.showMenuRegex(input))
            checkAndCallShowCurrentMenu();
        else System.out.println("invalid command");
        }
        }
    }



