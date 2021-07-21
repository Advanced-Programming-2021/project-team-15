package sample.controller.menuController;

import com.opencsv.exceptions.CsvValidationException;
import sample.controller.ClientManager;
import sample.controller.responses.LoginMenuResponses;
import sample.controller.responses.ShopMenuResponses;
import sample.model.cards.Card;
import sample.model.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ShopController extends MenuController {
    public static HashMap<String, Integer> getAllCardsCountByName() {
        return allCardsCountByName;
    }

    public static ArrayList<String> getUnmarketableCards() {
        return unmarketableCards;
    }

    private static HashMap<String, Integer> allCardsCountByName;
    private static ArrayList<String> unmarketableCards;

    public ShopController() {
        super("Shop Menu");
        updateAdminPanel();
    }

    public static void updateAdminPanel() {
        setAllCardsCountByName();
        setUnmarketableCards();
    }

    public static void setAllCardsCountByName() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("class", "ShopController");
        map.put("method", "getAllCardsCountByName");
        map.put("token",MainMenuController.getToken());
        allCardsCountByName = (HashMap<String, Integer>) ClientManager.sendAndGetResponse(map);
    }

    public static void setUnmarketableCards() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("class", "ShopController");
        map.put("method", "getUnmarketableCards");
        map.put("token",MainMenuController.getToken());
        unmarketableCards = (ArrayList<String>) ClientManager.sendAndGetResponse(map);
    }

    public ShopMenuResponses buyItem(String cardName) throws IOException, CsvValidationException {
        HashMap<String, Object> map = new HashMap<>();
        map.put("class", "ShopController");
        map.put("method", "buyItem");
        map.put("token",MainMenuController.getToken());
        map.put("cardName", cardName);
        String output = (String) ClientManager.sendAndGetResponse(map);
        MenuController.getUserByToken();
        return ShopMenuResponses.valueOf(output);
    }

    public ShopMenuResponses initAdminPanel() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("class", "ShopController");
        map.put("method", "adminPanel");
        map.put("token",MainMenuController.getToken());
        map.put("username",MainMenuController.getUser().getUserName());
        String output = (String) ClientManager.sendAndGetResponse(map);
        return ShopMenuResponses.valueOf(output);
    }

    public ShopMenuResponses unmarketableCard(String cardName) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("class", "ShopController");
        map.put("method", "unmarketableCard");
        map.put("token",MainMenuController.getToken());
        map.put("cardName", cardName);
        String output = (String) ClientManager.sendAndGetResponse(map);
        return ShopMenuResponses.valueOf(output);
    }

    public ShopMenuResponses changeCardCount(String cardName, int count) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("class", "ShopController");
        map.put("method", "changeCardCount");
        map.put("token",MainMenuController.getToken());
        map.put("count",count);
        map.put("cardName", cardName);
        String output = (String) ClientManager.sendAndGetResponse(map);
        return ShopMenuResponses.valueOf(output);
    }

//    public ShopMenuResponses showAllCards(HashMap<String, String> enteredDetails) throws IOException, CsvValidationException {
//        databaseController.loadGameCards();
//        ArrayList<Card> sortedCards = sortCardsByName(Card.getAllCards());
//        StringBuilder stringBuilder = new StringBuilder();
//        for (Card card : sortedCards) {
//            stringBuilder.append(card.getCardName()).append(" : ").append(card.getCardDescription()).append("\n");
//        }
//        enteredDetails.put("allCards", stringBuilder.toString());
//        return ShopMenuResponses.SHOP_SHOW_ALL;
//    }

}
