package controller;

import controller.responses.ShopMenuResponses;
import model.Card;
import model.User;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class ShopController extends MenuController {
    JSONController jsonController = new JSONController();

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
            user.getAllCardsOfUser().add(Card.getCardByName(cardName));
            user.changeMoney((-1) * Card.getCardByName(cardName).getPrice());
            return ShopMenuResponses.BUY_SUCCESSFUL;
        }
    }

    public ShopMenuResponses showAllCards(HashMap<String, String> enteredDetails) {
        jsonController.refreshCardsFromFileJson();
        System.out.println(Card.getAllCards().size());
        ArrayList<Card> sortedCards = sortCardsByName(Card.getAllCards());
        System.out.println(sortedCards.size());
        StringBuilder stringBuilder = new StringBuilder();
        for (Card card : sortedCards) {
            stringBuilder.append(card.getCardName()).append(" : ").append(card.getCardDescription()).append("\n");
        }
        enteredDetails.put("allCards", stringBuilder.toString());
        return ShopMenuResponses.SHOP_SHOW_ALL;
    }

    private ArrayList<Card> sortCardsByName(ArrayList<Card> cards) {
        ArrayList<Card> sortedCards = new ArrayList<>(cards);
        sortedCards.sort(Comparator.comparing(Card::getCardName));
        return sortedCards;
    }


}
