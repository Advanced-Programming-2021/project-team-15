package sample.view;

import com.opencsv.exceptions.CsvValidationException;
import sample.controller.menuController.ShopController;
import sample.controller.responses.ShopMenuResponses;
import sample.controller.utilizationController.UtilityController;

import java.io.IOException;
import java.util.HashMap;

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
    public void scanInput() throws IOException, CsvValidationException {
        while (true) {
            String input = UtilityController.getNextLine();
            if (input.equals("menu exit")) checkAndCallMenuExit();
            else if (input.startsWith("shop buy")) checkAndCallBuyItem(input);
            else if (input.equals("shop show --all")) checkAndCallShowAllCards();
            else if (input.startsWith("card show ")) UtilityController.showCardByName(input);
            else if (regexController.showMenuRegex(input))
                checkAndCallShowCurrentMenu();
            else if (input.startsWith("menu enter ")) System.out.println("Navigation is not possible hear");
            else System.out.println("invalid command");
            if (super.isExit) {
                super.isExit = false;
                return;
            }
        }
    }

    private void checkAndCallBuyItem(String input) throws IOException, CsvValidationException {
        HashMap<String, String> enteredDetails = new HashMap<>();
        if (!regexController.buyItemRegex(input, enteredDetails))
            System.out.println("invalid command");
        else {
            String cardName = enteredDetails.get("name");
            responses = shopController.buyItem(cardName);
            printResponse(responses);
        }
    }

    private void checkAndCallShowAllCards() throws IOException, CsvValidationException {
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
                output = allCards;
                break;
            case BUY_SUCCESSFUL:
                output = "bought item successfully!";
                break;
            case CARD_NAME_NOT_EXIST:
                output = "there is no card with this name";
                break;
            case USER_MONEY_NOT_ENOUGH:
                output = "not enough money";
                break;
            default:
                break;
        }
        System.out.println(output);
    }
}
