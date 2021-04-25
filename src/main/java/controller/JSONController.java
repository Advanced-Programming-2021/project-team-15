package controller;

import com.google.gson.Gson;
import model.Card;
import model.MonsterCard;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class JSONController {
    private Card card ;
    public  void MonsterCardParseJson() {
        Gson gson = new Gson();
        try (Reader reader = new FileReader("Monster.json")) {
            MonsterCard[] monsterCardArray = gson.fromJson(reader, MonsterCard[].class);
            for(MonsterCard monsterCard : monsterCardArray) {
                card.addCard(monsterCard);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
