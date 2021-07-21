package sample.controller.menuController;

import org.json.JSONArray;
import org.json.JSONObject;
import sample.controller.ClientManager;
import sample.controller.responses.DeckMenuResponses;
import sample.model.Deck;
import sample.model.cards.Card;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class DeckController extends MenuController {
    public DeckController() {
        super("Deck Menu");
    }

    public DeckMenuResponses createDeck(String deckName) {
        HashMap<String,Object> jsonObject = new HashMap<>();
        jsonObject.put("method","createDeck");
        jsonObject.put("class","DeckController");
        jsonObject.put("deckName",deckName);
        jsonObject.put("token",MainMenuController.getToken());
        DeckMenuResponses deckMenuResponses = (DeckMenuResponses) ClientManager.sendAndGetResponse(jsonObject);
       return deckMenuResponses;
    }

    public DeckMenuResponses removeDeck(String deckName) {
        HashMap<String,Object> jsonObject = new HashMap<>();
        jsonObject.put("method","removeDeck");
        jsonObject.put("class","DeckController");
        jsonObject.put("deckName",deckName);
        jsonObject.put("token",MainMenuController.getToken());
        DeckMenuResponses deckMenuResponses = (DeckMenuResponses) ClientManager.sendAndGetResponse(jsonObject);
        return deckMenuResponses;
        }


    public DeckMenuResponses setActiveDeck(String deckName) {
        HashMap<String,Object> jsonObject = new HashMap<>();
        jsonObject.put("method","setActiveDeck");
        jsonObject.put("class","DeckController");
        jsonObject.put("deckName",deckName);
        jsonObject.put("token",MainMenuController.getToken());
        DeckMenuResponses deckMenuResponses = (DeckMenuResponses) ClientManager.sendAndGetResponse(jsonObject);
        return deckMenuResponses;
    }

    public DeckMenuResponses removeCardFromDeck(Card card, String deckName, Deck.DeckType deckType) {
        HashMap<String,Object> jsonObject = new HashMap<>();
        jsonObject.put("method","removeCardFromDeck");
        jsonObject.put("class","DeckController");
        jsonObject.put("deckName",deckName);
        jsonObject.put("card",card);
        jsonObject.put("type",deckType);
        jsonObject.put("token",MainMenuController.getToken());
        DeckMenuResponses deckMenuResponses = (DeckMenuResponses) ClientManager.sendAndGetResponse(jsonObject);
        return deckMenuResponses;
    }


    public DeckMenuResponses addCardToDeck(Card card, String deckName, Deck.DeckType deckType) {
        HashMap<String,Object> jsonObject = new HashMap<>();
        jsonObject.put("method","addCardToDeck");
        jsonObject.put("class","DeckController");
        jsonObject.put("deckName",deckName);
        jsonObject.put("card",card);
        jsonObject.put("type",deckType);
        jsonObject.put("token",MainMenuController.getToken());
        DeckMenuResponses deckMenuResponses = (DeckMenuResponses) ClientManager.sendAndGetResponse(jsonObject);
        return deckMenuResponses;
    }

    public ArrayList<Deck> sortDecks(ArrayList<Deck> decks) {
        HashMap<String,Object> jsonObject = new HashMap<>();
        jsonObject.put("method","sortDecks");
        jsonObject.put("class","DeckController");
        jsonObject.put("deck array",decks);
        jsonObject.put("token",MainMenuController.getToken());
       ArrayList<Deck> sorted = (ArrayList<Deck>) ClientManager.sendAndGetResponse(jsonObject);
        return sorted;
    }

}
