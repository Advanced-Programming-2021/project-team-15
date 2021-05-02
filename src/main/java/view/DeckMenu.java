package view;

import java.util.Scanner;

public class DeckMenu extends Menu {
    private static DeckMenu deckMenu;
    private DeckMenu()
    {
        super("Deck Menu");
    }
    public static DeckMenu getInstance()
    {
        if(deckMenu==null)
            deckMenu = new DeckMenu();
        return deckMenu;
    }



    @Override
    public void scanInput() {
        Scanner scanner = new Scanner(System.in);
        while (true)
        {  String input = scanner.nextLine();
            if (input.equals("menu exit"))
                checkAndCallMenuExit();
            else if(input.startsWith("deck add-card"))
            {
                if(regexController.addCardToDeckRegex(input , "add"))
                    continue;
            }else if(input.startsWith("deck rm-card"))
            {
                if(regexController.addCardToDeckRegex(input , "remove"))
                    continue;
            }
            else if(regexController.showAllDecksRegex(input))
                continue;
            else if(regexController.showDeckRegex(input))
                continue;
            else if(regexController.showCardsOfUserRegex(input))
                continue;
            else if(regexController.showMenuRegex(input))
                checkAndCallShowCurrentMenu();
            else System.out.println("invalid command");

        }

    }
}
