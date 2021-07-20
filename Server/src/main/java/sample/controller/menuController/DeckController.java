package sample.controller.menuController;

import org.json.JSONArray;
import org.json.JSONObject;
import sample.controller.responses.DeckMenuResponses;
import sample.controller.responses.LoginMenuResponses;
import sample.model.Deck;
import sample.model.cards.Card;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class DeckController extends MenuController {
    public DeckController() {
        super("Deck Menu");
    }

    public DeckMenuResponses createDeck(String deckName, String token) {
        if (MainMenuController.getActiveUsers().get(token).getDeckByName(deckName) != null)
            return DeckMenuResponses.DECK_NAME_ALREADY_EXISTS;
        else {
            new Deck(MainMenuController.getActiveUsers().get(token).getUserName(), deckName);
            return DeckMenuResponses.DECK_CREATE_SUCCESSFUL;
        }
    }
    public Object callMethods(HashMap<String ,Object> jsonObject) {
        DeckMenuResponses deckMenuResponses ;
        switch ((String)jsonObject.get("method")) {
            case "getUser" :
                return MainMenuController.getUserByToken((String) jsonObject.get("token"));
            case "createDeck" :
              deckMenuResponses= createDeck((String)jsonObject.get("deckName"),(String)jsonObject.get("token"));
                break;
            case "removeDeck" :
                deckMenuResponses = removeDeck((String)jsonObject.get("deckName"),(String)jsonObject.get("token"));
                break;
            case "setActiveDeck" :
                deckMenuResponses = setActiveDeck((String)jsonObject.get("deckName"),(String)jsonObject.get("token"));
                break;
            case "addCardToDeck" :
                deckMenuResponses = addCardToDeck((Card)jsonObject.get("card"),(String)jsonObject.get("deckName"), Deck.DeckType.valueOf((String)jsonObject.get("type")),(String)jsonObject.get("token"));
                break;
            case "removeCardFromDeck" :
                deckMenuResponses = removeCardFromDeck((Card)jsonObject.get("card"),(String)jsonObject.get("deckName"), Deck.DeckType.valueOf((String)jsonObject.get("type")),(String)jsonObject.get("token"));
                break;
            case "sortDecks" :
               return  sortDecks(jsonObject);
            default:
                return "Something Happened!";
        }
        return deckMenuResponses;
    }

    public DeckMenuResponses removeDeck(String deckName ,  String token) {
        if (MainMenuController.getActiveUsers().get(token).getDeckByName(deckName) == null)
            return DeckMenuResponses.DECK_NAME_NOT_EXIST;
        else {
            Deck deck = MainMenuController.getActiveUsers().get(token).getDeckByName(deckName);
            ArrayList<ArrayList<Card>> mainAndSideDeck = new ArrayList<>();
            mainAndSideDeck.add(deck.getMainDeck());
            mainAndSideDeck.add(deck.getSideDeck());
            for (ArrayList<Card> deckToCopyCards : mainAndSideDeck) {
                for (Card card : deckToCopyCards) {
                    MainMenuController.getActiveUsers().get(token).addCard(card);
                }
            }
            MainMenuController.getActiveUsers().get(token).removeDeckByName(deckName);
            return DeckMenuResponses.DECK_DELETE_SUCCESSFUL;
        }
    }

    public DeckMenuResponses setActiveDeck(String deckName ,  String token) {
        if (MainMenuController.getActiveUsers().get(token).getDeckByName(deckName) == null)
            return DeckMenuResponses.DECK_NAME_NOT_EXIST;
        else {
            if (MainMenuController.getActiveUsers().get(token).getActiveDeck() != null) user.getActiveDeck().setActive(false);
            MainMenuController.getActiveUsers().get(token).setActiveDeck(MainMenuController.getActiveUsers().get(token).getDeckByName(deckName));
            MainMenuController.getActiveUsers().get(token).getDeckByName(deckName).setActive(true);
            MainMenuController.getActiveUsers().get(token).setActiveDeckName(MainMenuController.getActiveUsers().get(token).getActiveDeck().getName());
            databaseController.refreshUsersToFileJson();
            return DeckMenuResponses.DECK_ACTIVATE_SUCCESSFUL;
        }
    }

    public DeckMenuResponses removeCardFromDeck(Card card, String deckName, Deck.DeckType deckType,  String token) {
        Deck addingDeck = MainMenuController.getActiveUsers().get(token).getDeckByName(deckName);
        MainMenuController.getActiveUsers().get(token).addCard(card);
            addingDeck.removeCardFromDeck(card, deckType);
            if (addingDeck.getMainDeck().size() < Deck.mainDeckMinCardCount)
                addingDeck.setValid(false);
            databaseController.refreshUsersToFileJson();
            return DeckMenuResponses.CARD_REMOVE_SUCCESSFUL;
        }


    public DeckMenuResponses addCardToDeck(Card card, String deckName, Deck.DeckType deckType , String token) {
        Deck addingDeck = MainMenuController.getActiveUsers().get(token).getDeckByName(deckName);
        if (deckType == Deck.DeckType.MAIN && addingDeck.getMainDeck().size() == Deck.mainDeckMaxCardCount)
            return DeckMenuResponses.DECK_FULL;
        if (deckType == Deck.DeckType.SIDE && addingDeck.getSideDeck().size() == Deck.sideDeckMaxCardCount)
            return DeckMenuResponses.DECK_FULL;
        else if (addingDeck.getSpecifiedCardCountInDeckByName(card.getCardName()) >= Deck.DeckMaxSpecifiedCardCount)
            return DeckMenuResponses.MAX_SIZE_IDENTICAL_CARDS_ALREADY_IN_DECK;
        else {
            addingDeck.addCardToDeck(cloner.deepClone(card), deckType);
            MainMenuController.getActiveUsers().get(token).getAllCardsOfUser().remove(card);
            if (addingDeck.getMainDeck().size() >= Deck.mainDeckMinCardCount)
                addingDeck.setValid(true);
            databaseController.refreshUsersToFileJson();
            return DeckMenuResponses.CARD_ADD_TO_DECK_SUCCESSFUL;
        }
    }

    public ArrayList<Deck> sortDecks(HashMap<String, Object> jsonObject) {
        ArrayList<Deck> jsonArray= (ArrayList<Deck>) jsonObject.get("deck array");
        ArrayList<Deck> decks = new ArrayList<>();
        for (int i=0;i<jsonArray.size();i++){
            decks.add((Deck)jsonArray.get(i));
        }
         ArrayList<Deck> sortedDecks = new ArrayList<>(decks);
         sortedDecks.sort(Comparator.comparing(Deck::getName));
         return sortedDecks;
    }

}
