package controller;

import com.google.gson.*;
import model.*;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;

public class JSONController {
    public void MonsterCardParseJson() {
        Gson gson = new Gson();
        try (Reader reader = new FileReader("Monster.json")) {
            MonsterCard[] monsterCardArray = gson.fromJson(reader, MonsterCard[].class);
            for(MonsterCard monsterCard : monsterCardArray) {
                monsterCard.setCardType(Card.CardType.MONSTER);
                Card.addCard(monsterCard);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class MagicCardDeserializer implements JsonDeserializer<MagicCard> {
        @Override
        public MagicCard deserialize(JsonElement json, Type typeof, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();

            JsonElement jsonType = jsonObject.get("Type");
            String type = jsonType.getAsString();

            MagicCard magicCard = null;

            if("Trap".equals(type)) {
                System.out.println("trap");;
            } else if("Spell".equals(type)) {
                System.out.println("spell");
            }
            // TODO : set properties of type model

            return magicCard;
        }
    }
    public void MagicCardParseJson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(MagicCard.class, new MagicCardDeserializer());
        Gson gson = gsonBuilder.create();
        try (Reader reader = new FileReader("Magic.json")) {
            MagicCard[] magicCardArray = gson.fromJson(reader, MagicCard[].class);
            for (MagicCard magicCard : magicCardArray) {
                System.out.println(magicCard.getCardName());
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
