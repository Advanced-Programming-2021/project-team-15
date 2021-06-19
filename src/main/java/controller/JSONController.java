package controller;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import model.*;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class JSONController {

    public void loadGameCards() throws IOException, CsvValidationException {
        Card.getAllCards().clear();
        File file = new File("src/main/resources/Monster.csv");
        FileReader fileReader = new FileReader(file);
        CSVReader reader = new CSVReader(fileReader);

        String[] monsterArray = reader.readNext();
        while ((monsterArray = reader.readNext()) != null) {
            MonsterCard monsterCard = new MonsterCard(monsterArray[7],monsterArray[0],"0", Card.CardType.MONSTER );
            monsterCard.setLevel(Integer.parseInt(monsterArray[1]));
            monsterCard.setMonsterAttribute(MonsterCard.MonsterAttribute.getAttribute(monsterArray[2]));
            monsterCard.setMonsterType(MonsterCard.MonsterType.getMonsterTypeByName(monsterArray[3]));
            monsterCard.setMonsterEffectType(MonsterCard.MonsterEffectType.getMonsterEffectType(monsterArray[4]));
            monsterCard.setAttackPoint(Integer.parseInt(monsterArray[5]));
            monsterCard.setDefensePoint(Integer.parseInt(monsterArray[6]));
            monsterCard.setPrice(Integer.parseInt(monsterArray[8]));
            monsterCard.setGameATK(monsterCard.getAttackPoint());
            monsterCard.setGameDEF(monsterCard.getDefensePoint());
            monsterCard.setMode(MonsterCard.Mode.DEFENSE);
            Card.addCard(monsterCard);
        }

        file = new File("src/main/resources/Magic.csv");
        fileReader = new FileReader(file);
        reader = new CSVReader(fileReader);

        String[] magicArray = reader.readNext();
        while ((magicArray = reader.readNext()) != null) {
            MagicCard magicCard = new MagicCard(magicArray[3], magicArray[0], "0", Card.CardType.MAGIC );
            magicCard.setMagicType(MagicCard.MagicType.getMagicType(magicArray[1]));
            magicCard.setCardIcon(MagicCard.CardIcon.getCardIcon(magicArray[2]));
            magicCard.setStatus(MagicCard.Status.getMagicType(magicArray[4]));
            magicCard.setPrice(Integer.parseInt(magicArray[5]));
            Card.addCard(magicCard);
        }

        fileReader.close();
        reader.close();
    }

//    public void monsterCardParseJson() {
//        Gson gson = new Gson();
//        try (Reader reader = new FileReader("src/main/resources/Monster.json")) {
//            MonsterCard[] monsterCardArray = gson.fromJson(reader, MonsterCard[].class);
//            for(MonsterCard monsterCard : monsterCardArray) {
//                monsterCard.setCardType(Card.CardType.MONSTER);
//                Card.addCard(monsterCard);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

//    public void magicCardParseJson() {
//        GsonBuilder gsonBuilder = new GsonBuilder();
//        Gson gson = gsonBuilder.create();
//        try (Reader reader = new FileReader("src/main/resources/Magic.json")) {
//            MagicCard[] magicCardArray = gson.fromJson(reader, MagicCard[].class);
//            for (MagicCard magicCard : magicCardArray) {
//                magicCard.setCardType(Card.CardType.MAGIC);
//                Card.addCard(magicCard);
//            }
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public void refreshUsersToFileJson() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try(Writer writer = new FileWriter("src/main/resources/Users.json")) {
            setAllCardsType();
            gson.toJson(User.getAllUsers(),writer);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setAllCardsType() {
        for (User user : User.getAllUsers()) {
            for (Card card : user.getAllCardsOfUser())
                setType(card);
            for (Deck deck : user.getAllDecksOfUser())
                for (Card card : deck.getMainDeck())
                    setType(card);
            for (Deck deck : user.getAllDecksOfUser())
                for (Card card : deck.getSideDeck())
                    setType(card);
            if (user.getActiveDeck()!=null) {
                for (Card card : user.getActiveDeck().getMainDeck())
                    setType(card);
                for (Card card : user.getActiveDeck().getSideDeck())
                    setType(card);
            }
        }
    }

    private void setType(Card card) {
        if (card instanceof MonsterCard) {
            card.setType("MONSTER");
        } else if (card instanceof MagicCard) {
            card.setType("MAGIC");
        } else card.setType("NULL");
    }

    public void refreshUsersFromFileJson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        try (Reader reader = new FileReader("src/main/resources/Users.json")) {
            RuntimeTypeAdapterFactory<Card> runtimeTypeAdapterFactory = RuntimeTypeAdapterFactory
                    .of(Card.class, "type").
                            registerSubtype(MonsterCard.class, "MONSTER").
                            registerSubtype(MagicCard.class, "MAGIC");
            Type usersListType = new TypeToken<ArrayList<User>>(){}.getType();
            User.setAllUsers(gsonBuilder.registerTypeAdapterFactory(runtimeTypeAdapterFactory).create().fromJson(reader, usersListType));
        }
        catch (IOException e) {
            System.out.println("Welcome to this Game!\nFrom: Group15 AP- 2021 Spring");
            //e.printStackTrace();
        }
    }
//    public void refreshCardsFromFileJson() {
//        Card.getAllCards().removeAll(Card.getAllCards());
////        monsterCardParseJson();
////        magicCardParseJson();
//        GsonBuilder gsonBuilder = new GsonBuilder();
//        String[] filenames = {"src/main/resources/Monster.json","src/main/resources/Magic.json"};
//        ArrayList<Card> tempCardsList = new ArrayList<>();
//        int fileCounter = 0;
//        for (String filename : filenames) {
//            try (Reader reader = new FileReader(filename)) {
//                Type cardsListType = new TypeToken<ArrayList<Card>>() {}.getType();
//                tempCardsList.addAll(gsonBuilder.create().fromJson(reader, cardsListType));
//                for (Card card : tempCardsList) {
//                    if (fileCounter==0)
//                        card.setCardType(Card.CardType.MONSTER);
//                    else if (fileCounter==1) card.setCardType(Card.CardType.MAGIC);
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            fileCounter++;
//        }
//        Card.setAllCards(tempCardsList);
//    }
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

}
