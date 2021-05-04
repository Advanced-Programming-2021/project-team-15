package view;

import controller.ShopController;

import java.util.HashMap;
import java.util.Scanner;

public class ShopMenu extends Menu{
    private static ShopMenu shopMenu;
    private ShopController shopController= new ShopController();
    // ShopMenuResponses responses;
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
            if (input.equals("menu exit")) checkAndCallMenuExit();
            else if(input.startsWith("shop buy")) checkAndCallBuyItem(input);
            else if(input.equals("shop show --all"))  shopController.showAllCards();
            else if(regexController.showMenuRegex(input))
                checkAndCallShowCurrentMenu();
            else System.out.println("invalid command");
            if (super.isExit) {
                super.isExit = false;
                return;
            }
        }
    }
    public void checkAndCallBuyItem(String input)
    {   HashMap<String, String> enteredDetails = new HashMap<>();
        if(!regexController.buyItemRegex(input , enteredDetails))
            System.out.println("invalid command");
        else {
            String cardName =  enteredDetails.get("name");
            //responses = shopController.buyItem(cardName);
            // printResponse(responses);
        }
        //protected void printResponse( DeckMenuResponses deckMenuResponses)
        //{

        //}

    }
}
