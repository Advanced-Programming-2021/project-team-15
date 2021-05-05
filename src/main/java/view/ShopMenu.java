package view;

import controller.ShopController;
import controller.responses.ShopMenuResponses;

import java.util.HashMap;
import java.util.Scanner;

public class ShopMenu extends Menu {
    private static ShopMenu shopMenu;
    ShopMenuResponses responses;
    String allCards;
    private ShopController shopController = new ShopController();

    private ShopMenu() {
        super("Shop Menu");
    }

    public static ShopMenu getInstance() {
        if (shopMenu == null)
            shopMenu = new ShopMenu();
        return shopMenu;
    }

    @Override
    public void scanInput() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();
            if (input.equals("menu exit")) checkAndCallMenuExit();
            else if (input.startsWith("shop buy")) checkAndCallBuyItem(input);
            else if (input.equals("shop show --all")) checkAndCallShowAllCards();
            else if (regexController.showMenuRegex(input))
                checkAndCallShowCurrentMenu();
            else System.out.println("invalid command");
            if (super.isExit) {
                super.isExit = false;
                return;
            }
        }
    }

    private void checkAndCallBuyItem(String input) {
        HashMap<String, String> enteredDetails = new HashMap<>();
        if (!regexController.buyItemRegex(input, enteredDetails))
            System.out.println("invalid command");
        else {
            String cardName = enteredDetails.get("name");
            responses = shopController.buyItem(cardName);
            printResponse(responses);
        }
    }

    private void checkAndCallShowAllCards() {
        HashMap<String, String> enteredDetails = new HashMap<>();
        responses = shopController.showAllCards(enteredDetails);
        if (responses == ShopMenuResponses.SHOP_SHOW_ALL)
            allCards = enteredDetails.get("allCards");
        printResponse(responses);
    }

    private void printResponse(ShopMenuResponses shopMenuResponses) {
        String output = "";
        switch (shopMenuResponses) {
            case SHOP_SHOW_ALL:
                System.out.println(allCards);
                break;
            case BUY_SUCCESSFUL:
                System.out.println("bought item successfully!");
                break;
            case CARD_NAME_NOT_EXIST:
                System.out.println("there is no card with this name");
                break;
            case USER_MONEY_NOT_ENOUGH:
                System.out.println("not enough money");
                break;
            default:
                break;
        }
        System.out.println(output);
    }
}
