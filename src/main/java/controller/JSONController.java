package controller;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import model.*;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;


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
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
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
            Type usersListType = new TypeToken<ArrayList<User>>(){}.getType();
            User.setAllUsers(gson.fromJson(reader, usersListType));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void refreshCardsFromFileJson() {
        Gson gson = new GsonBuilder().create();
        String[] filenames = {"Monster.json","Magic.json"};
        ArrayList<Card> tempCardsList = new ArrayList<>();
        for (String filename : filenames) {
            try (Reader reader = new FileReader(filename)) {
                Type cardsListType = new TypeToken<ArrayList<Card>>() {}.getType();
                tempCardsList.addAll(gson.fromJson(reader, cardsListType));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Card.setAllCards(tempCardsList);
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
