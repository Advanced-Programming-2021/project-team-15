package sample.controller.menuController;

import com.opencsv.exceptions.CsvValidationException;
import sample.controller.responses.ShopMenuResponses;
import sample.controller.utilizationController.DatabaseController;
import sample.model.User;
import sample.model.cards.Card;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ShopController extends MenuController {

    private static HashMap<String, Integer> allCardsCountByName;
    private static ArrayList<String> unmarketableCards;

    public ShopController() {
        super("Shop Menu");
    }

    public static HashMap<String, Integer> getAllCardsCountByName() {
        return allCardsCountByName;
    }

    public static void setAllCardsCountByName(HashMap<String, Integer> allCardsCountByName) {
        ShopController.allCardsCountByName = allCardsCountByName;
    }

    public static ArrayList<String> getUnmarketableCards() {
        return unmarketableCards;
    }

    public static void setUnmarketableCards(ArrayList<String> unmarketableCards) {
        ShopController.unmarketableCards = unmarketableCards;
    }

    public static void init() {
        allCardsCountByName = new HashMap<>();
        unmarketableCards = new ArrayList<>();
        DatabaseController.getInstance().readCardsCount();
        DatabaseController.getInstance().readUnmarketableCards();
    }

    public ShopMenuResponses buyItem(String cardName) throws IOException, CsvValidationException {
        User user = MenuController.getUser();
        databaseController.loadGameCards();
        if (Card.getCardByName(cardName) == null)
            return ShopMenuResponses.CARD_NAME_NOT_EXIST;
        else if (user.getMoney() < Card.getCardByName(cardName).getPrice())
            return ShopMenuResponses.USER_MONEY_NOT_ENOUGH;
        else {
            user.getAllCardsOfUser().add(cloner.deepClone(Card.getCardByName(cardName)));
            user.changeMoney((-1) * Card.getCardByName(cardName).getPrice());
            databaseController.refreshUsersToFileJson();
            return ShopMenuResponses.BUY_SUCCESSFUL;
        }
    }

    public ShopMenuResponses showAllCards(HashMap<String, String> enteredDetails) throws IOException, CsvValidationException {
        databaseController.loadGameCards();
        ArrayList<Card> sortedCards = sortCardsByName(Card.getAllCards());
        StringBuilder stringBuilder = new StringBuilder();
        for (Card card : sortedCards) {
            stringBuilder.append(card.getCardName()).append(" : ").append(card.getCardDescription()).append("\n");
        }
        enteredDetails.put("allCards", stringBuilder.toString());
        return ShopMenuResponses.SHOP_SHOW_ALL;
    }

    public ShopMenuResponses unmarketableCard(String cardName) {
        if (unmarketableCards.contains(cardName)) unmarketableCards.remove(cardName);
        else {
            unmarketableCards.add(cardName);
            DatabaseController.getInstance().writeUnmarketableCards(unmarketableCards);
        }
        return ShopMenuResponses.DISABLED_SUCCESSFULLY;
    }

    public ShopMenuResponses adminPanel(String username) {
        if (MainMenuController.getAdmins().contains(username)) return ShopMenuResponses.ADMIN_ENTER_SUCCESSFUL;
        else return ShopMenuResponses.NOT_ADMIN;
    }

    public ShopMenuResponses changeCardCount(String cardName, int count) {
        int numberOfCard = allCardsCountByName.get(cardName);
        allCardsCountByName.put(cardName,numberOfCard+count);
        DatabaseController.getInstance().writeCardsCount(allCardsCountByName);
        return ShopMenuResponses.COUNT_CHANGED_SUCCESSFULLY;
    }

    public Object callMethods(HashMap<String, Object> jsonObject) {
        ShopMenuResponses shopMenuResponses;
        MenuController.setUser(MainMenuController.getUserByToken((String) jsonObject.get("token")));
        switch ((String) jsonObject.get("method")) {
            case "buyItem":
                try {
                    shopMenuResponses = buyItem(
                            (String) jsonObject.get("cardName"));
                } catch (IOException | CsvValidationException e) {
                    e.printStackTrace();
                    return "Something Happened!";
                }
                break;
            case "getAllCardsCountByName" :
                return allCardsCountByName;
            case "getUnmarketableCards" :
                return unmarketableCards;
            case "changeCardCount" :
                shopMenuResponses = changeCardCount(
                        (String) jsonObject.get("cardName"), Integer.parseInt((String)jsonObject.get("count")));
                break;
            case "adminPanel" :
                shopMenuResponses = adminPanel(
                        (String) jsonObject.get("username"));
                break;
            case "unmarketableCard" :
                shopMenuResponses = unmarketableCard(
                        (String) jsonObject.get("cardName"));
                break;
            default:
                return "Something Happened!";
        }
        return shopMenuResponses.toString();
    }
}
