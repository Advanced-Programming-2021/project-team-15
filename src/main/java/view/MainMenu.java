package view;

import controller.MainMenuController;
import controller.RegexController;

import java.util.Scanner;

public class MainMenu extends Menu {
    MainMenuController mainMenuController = new MainMenuController();
    public MainMenu() {
        super("Main Menu");
    }
    @Override
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



