package sample.controller.menuController;

import com.opencsv.exceptions.CsvValidationException;
import org.json.JSONObject;
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

    public String callMethods(JSONObject jsonObject) throws IOException, CsvValidationException {
        ShopMenuResponses shopMenuResponses;
        MainMenuController.setUser(MainMenuController.getActiveUsers().get(jsonObject.getString("token")));
        switch (jsonObject.getString("method")) {
            case "buyItem" :
                shopMenuResponses = buyItem(
                        jsonObject.getString("cardName"));
                break;
//            case "getUserMoney" :
//                return getUserMoney(jsonObject);
//            case "getUserSpecificCardCount" :
//                return getUserSpecificCardCount(jsonObject);
            default:
                return "Something Happened!";
        }
        return shopMenuResponses.toString();
    }

//    public String getUserMoney(JSONObject jsonObject) {
//        return String.valueOf(MainMenuController.getUserByToken(jsonObject.getString("token")).getMoney());
//    }
//
//    public String getUserSpecificCardCount(JSONObject jsonObject) {
//        User user = MainMenuController.getUserByToken(jsonObject.getString("token"));
//        return String.valueOf(user.getUserSpecificCardCount(jsonObject.getString("cardName")));
//    }

}
