package sample.controller.menuController;

import com.opencsv.exceptions.CsvValidationException;
import org.json.JSONObject;
import sample.controller.ClientManager;
import sample.controller.responses.LoginMenuResponses;
import sample.controller.responses.ShopMenuResponses;
import sample.model.cards.Card;
import sample.model.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShopController extends MenuController {

    public ShopController() {
        super("Shop Menu");
    }

    public ShopMenuResponses buyItem(String cardName) throws IOException, CsvValidationException {
//        User user = MenuController.getUser();
//        databaseController.loadGameCards();
//        if (Card.getCardByName(cardName) == null)
//            return ShopMenuResponses.CARD_NAME_NOT_EXIST;
//        else if (user.getMoney() < Card.getCardByName(cardName).getPrice())
//            return ShopMenuResponses.USER_MONEY_NOT_ENOUGH;
//        else {
//            user.getAllCardsOfUser().add(cloner.deepClone(Card.getCardByName(cardName)));
//            user.changeMoney((-1) * Card.getCardByName(cardName).getPrice());
//            databaseController.refreshUsersToFileJson();
//            return ShopMenuResponses.BUY_SUCCESSFUL;
//        }
        Map<String, String> map = getThisClassMap("buyItem");
        map.put("cardName",cardName);
        JSONObject jsonObject = new JSONObject(map);
        String output = ClientManager.sendAndGetResponse(jsonObject.toString());
        return ShopMenuResponses.valueOf(output);
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

    public Map<String,String> getThisClassMap(String methodName) {
        Map<String, String> map = new HashMap<>();
        map.put("class", "ShopController");
        map.put("method", methodName);
        map.put("token",MainMenuController.getToken());
        return map;
    }
//
//    public String getUserMoney() {
//        Map<String, String> map = getThisClassMap("getUserMoney");
//        JSONObject jsonObject = new JSONObject(map);
//        return ClientManager.sendAndGetResponse(jsonObject.toString());
//    }
//
//    public String getUserSpecificCardCount(Card card) {
//        Map<String, String> map = getThisClassMap("getUserSpecificCardCount");
//        map.put("cardName",card.getCardName());
//        JSONObject jsonObject = new JSONObject(map);
//        return ClientManager.sendAndGetResponse(jsonObject.toString());
//    }

}
