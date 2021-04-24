package controller;

import com.google.gson.Gson;
import model.Card;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class JSONController {
    public void cardParseJson() {
        Gson gson = new Gson();
        try (Reader reader = new FileReader("Monster.json")) {
            Card[] CardArray = gson.fromJson(reader, Card[].class);
            for(Card card : CardArray) {
                System.out.println(card.getCardName());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
