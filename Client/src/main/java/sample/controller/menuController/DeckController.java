package sample.controller.menuController;

import org.json.JSONArray;
import org.json.JSONObject;
import sample.controller.ClientManager;
import sample.controller.responses.DeckMenuResponses;
import sample.model.Deck;
import sample.model.cards.Card;

import java.util.ArrayList;
import java.util.Comparator;

public class DeckController extends MenuController {
    public DeckController() {
        super("Deck Menu");
    }

    public DeckMenuResponses createDeck(String deckName) {
        JSONObject jsonObject=  new JSONObject();
        jsonObject.put("method","createDeck");
        jsonObject.put("class","DeckController");
        jsonObject.put("deckName",deckName);
        jsonObject.put("token",MainMenuController.getToken());
        DeckMenuResponses deckMenuResponses =   DeckMenuResponses.valueOf(ClientManager.sendAndGetResponse(jsonObject.toString()));
       return deckMenuResponses;
    }

    public DeckMenuResponses removeDeck(String deckName) {
        JSONObject jsonObject=  new JSONObject();
        jsonObject.put("method","removeDeck");
        jsonObject.put("class","DeckController");
        jsonObject.put("deckName",deckName);
        jsonObject.put("token",MainMenuController.getToken());
        DeckMenuResponses deckMenuResponses =   DeckMenuResponses.valueOf(ClientManager.sendAndGetResponse(jsonObject.toString()));
        return deckMenuResponses;
        }


    public DeckMenuResponses setActiveDeck(String deckName) {
        JSONObject jsonObject=  new JSONObject();
        jsonObject.put("method","setActiveDeck");
        jsonObject.put("class","DeckController");
        jsonObject.put("deckName",deckName);
        jsonObject.put("token",MainMenuController.getToken());
        DeckMenuResponses deckMenuResponses =   DeckMenuResponses.valueOf(ClientManager.sendAndGetResponse(jsonObject.toString()));
        return deckMenuResponses;
    }

    public DeckMenuResponses removeCardFromDeck(Card card, String deckName, Deck.DeckType deckType) {
        JSONObject jsonObject=  new JSONObject();
        jsonObject.put("method","removeCardFromDeck");
        jsonObject.put("class","DeckController");
        jsonObject.put("deckName",deckName);
        jsonObject.put("card",card);
        jsonObject.put("type",deckType.toString());
        jsonObject.put("token",MainMenuController.getToken());
        DeckMenuResponses deckMenuResponses =   DeckMenuResponses.valueOf(ClientManager.sendAndGetResponse(jsonObject.toString()));
        return deckMenuResponses;
    }


    public DeckMenuResponses addCardToDeck(Card card, String deckName, Deck.DeckType deckType) {
        JSONObject jsonObject=  new JSONObject();
        jsonObject.put("method","removeCardFromDeck");
        jsonObject.put("class","DeckController");
        jsonObject.put("deckName",deckName);
        jsonObject.put("card",card);
        jsonObject.put("type",deckType.toString());
        jsonObject.put("token",MainMenuController.getToken());
        DeckMenuResponses deckMenuResponses =   DeckMenuResponses.valueOf(ClientManager.sendAndGetResponse(jsonObject.toString()));
        return deckMenuResponses;
    }

    public ArrayList<Deck> sortDecks(ArrayList<Deck> decks) {
        JSONObject jsonObject=  new JSONObject();
        jsonObject.put("method","removeCardFromDeck");
        jsonObject.put("class","DeckController");
        JSONArray deckArray  =new JSONArray();
        for(Deck deck :decks)
        {  deckArray.put(deck);
        }
        jsonObject.put("deck array",deckArray);
       String string = ClientManager.sendAndGetResponse(jsonObject.toString());
        JSONObject jsonObj = new JSONObject(string);
        JSONArray result = jsonObj.getJSONArray("sorted deck");
        ArrayList<Deck> sorted = new ArrayList<>();
        for (int i=0;i<result.length();i++){
            sorted.add((Deck) result.get(i));
        }
        return sorted;
    }

}
