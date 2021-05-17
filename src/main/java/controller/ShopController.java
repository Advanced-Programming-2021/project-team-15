package controller;

import controller.responses.ShopMenuResponses;
import model.Card;
import model.User;

import java.util.ArrayList;
import java.util.HashMap;

public class ShopController extends MenuController {

    public ShopController() {
        super("Shop Menu");
    }

    public ShopMenuResponses buyItem(String cardName) {
        User user = MenuController.getUser();
        jsonController.refreshCardsFromFileJson();
        if (Card.getCardByName(cardName) == null)
            return ShopMenuResponses.CARD_NAME_NOT_EXIST;
        else if (user.getMoney() < Card.getCardByName(cardName).getPrice())
            return ShopMenuResponses.USER_MONEY_NOT_ENOUGH;
        else {
            user.getAllCardsOfUser().add(cloner.deepClone(Card.getCardByName(cardName)));
            user.changeMoney((-1) * Card.getCardByName(cardName).getPrice());
            jsonController.refreshUsersToFileJson();
            return ShopMenuResponses.BUY_SUCCESSFUL;
        }
    }

    public ShopMenuResponses showAllCards(HashMap<String, String> enteredDetails) {
        jsonController.refreshCardsFromFileJson();
        ArrayList<Card> sortedCards = sortCardsByName(Card.getAllCards());
        StringBuilder stringBuilder = new StringBuilder();
        for (Card card : sortedCards) {
            stringBuilder.append(card.getCardName()).append(" : ").append(card.getCardDescription()).append("\n");
        }
        enteredDetails.put("allCards", stringBuilder.toString());
        return ShopMenuResponses.SHOP_SHOW_ALL;
    }

}
