package controller;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import model.*;

import java.awt.*;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

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
    public void refreshUsersToFileJson() {
        Gson gson = new GsonBuilder().create();
        try(Writer writer = new FileWriter("Users.json")) {
            gson.toJson(User.getAllUsers(),writer);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void refreshUsersFromFileJson() {
        Gson gson = new GsonBuilder().create();
        try (Reader reader = new FileReader("Users.json")) {
//            Type founderListType = new TypeToken<ArrayList<Founder>>(){}.getType();
//            List<Founder> founderList = gson.fromJson(founderJson, founderListType);
            Type usersListType = new TypeToken<ArrayList<User>>(){}.getType();
            User.setAllUsers(gson.fromJson(reader, usersListType));
            //User.setAllUsers((ArrayList<User>)Arrays.asList(gson.fromJson(reader, ArrayList<User>.class)));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
//    static class MagicCardDeserializer implements JsonDeserializer<MagicCard> {
//        @Override
//        public MagicCard deserialize(JsonElement json, Type typeof, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
//            JsonObject jsonObject = json.getAsJsonObject();
//
//            JsonElement jsonType = jsonObject.get("Type");
//            String type = jsonType.getAsString();
//
//            MagicCard magicCard = null;
//
//            if("Trap".equals(type)) {
//                magicCard = new TrapCard();
//                magicCard.setMagicType(MagicCard.MagicType.TRAP);
//            } else if("Spell".equals(type)) {
//                magicCard = new SpellCard();
//                magicCard.setMagicType(MagicCard.MagicType.SPELL);
//            }
//            return magicCard;
//        }
//    }
    public void MagicCardParseJson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
//        gsonBuilder.registerTypeAdapter(MagicCard.class, new MagicCardDeserializer());
        Gson gson = gsonBuilder.create();
        try (Reader reader = new FileReader("Magic.json")) {
            MagicCard[] magicCardArray = gson.fromJson(reader, MagicCard[].class);
            for (MagicCard magicCard : magicCardArray) {
                magicCard.setCardType(Card.CardType.MAGIC);
                Card.addCard(magicCard);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
