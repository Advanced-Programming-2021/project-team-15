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
        HashMap<String, Object> map = new HashMap<>();
        map.put("class", "ShopController");
        map.put("method", "buyItem");
        map.put("token",MainMenuController.getToken());
        map.put("cardName", cardName);
        String output = (String) ClientManager.sendAndGetResponse(map);
        MainMenuController.getUserByToken();
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

}
