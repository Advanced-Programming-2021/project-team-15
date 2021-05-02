package view;

import java.util.Scanner;

public class ShopMenu extends Menu{
    private static ShopMenu shopMenu;
    private ShopMenu()
    { super("Shop Menu"); }
    public static ShopMenu getInstance()
    {
        if(shopMenu==null)
            shopMenu = new ShopMenu();
        return shopMenu;
    }

    @Override
    public void scanInput() {
        Scanner scanner = new Scanner(System.in);
        while (true)
        { String input = scanner.nextLine();
            if (input.equals("menu exit"))
                checkAndCallMenuExit();
            else if(regexController.showAllCards(input))
                continue;
            else if(regexController.buyItemRegex(input))
                continue;
            else if(regexController.showMenuRegex(input))
                checkAndCallShowCurrentMenu();
            else System.out.println("invalid command");

        }
    }
}
