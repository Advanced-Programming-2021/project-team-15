package sample.controller.utilizationController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.ICSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import javafx.scene.image.Image;
import javafx.scene.shape.Path;
import sample.model.Deck;
import sample.model.User;
import sample.model.cards.Card;
import sample.model.cards.MagicCard;
import sample.model.cards.MonsterCard;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

public class DatabaseController {
    private static DatabaseController databaseController;

    public static DatabaseController getInstance() {
        if (databaseController == null)
            databaseController = new DatabaseController();
        return databaseController;
    }

    public void loadGameCards() throws IOException, CsvValidationException {
        Card.getAllCards().clear();
        File file = new File("src/main/resources/Monster.csv");
        FileReader fileReader = new FileReader(file);
        CSVReader reader = new CSVReader(fileReader);
        readMonsterCardsFromCSV(reader);
        file = new File("src/main/resources/Magic.csv");
        fileReader = new FileReader(file);
        reader = new CSVReader(fileReader);
        readMagicCardsFromCSV(reader);
        fileReader.close();
        reader.close();
//        setAllCardsImages();
    }

    private void setAllCardsImages() {
        for (Card card : Card.getAllCards()) {
            Image cardImage = getImageByCard(card);
            Card.getAllCardsImages().put(card.getCardName(), cardImage);
        }
    }

    public String getAddressByCard(Card card) {
        StringBuilder address = new StringBuilder();
        String[] nameParts = card.getCardName().split(" ");
        for (String part : nameParts) {
            part = part.toLowerCase();
            StringBuilder temp = new StringBuilder(part);
            temp.setCharAt(0, Character.toUpperCase(part.charAt(0)));
            part = temp.toString();
            address.append(part);
        }
        address.append(".jpg");
        return address.toString();
    }

    public Image getImageByCard(Card card) {
        try {
            return new Image(String.valueOf(getClass().getResource("/Images/Cards/" + getAddressByCard(card))));
        }catch (IllegalArgumentException e) {
            return new Image(String.valueOf(getClass().getResource("/Images/cardAnimeGirl2.jpg")));
        }
    }

    public void writeMonsterCardToCSV(MonsterCard card) throws IOException {
        String path = "src/main/resources/Monster.csv";
        CSVWriter writer = new CSVWriter(new FileWriter(path, true), ',',
                CSVWriter.NO_QUOTE_CHARACTER, ICSVWriter.DEFAULT_ESCAPE_CHARACTER, ICSVWriter.DEFAULT_LINE_END);
        String[] monsterDetails = new String[9];
        monsterDetails[0] = card.getCardName();
        monsterDetails[1] = String.valueOf(card.getLevel());
        monsterDetails[2] = String.valueOf(card.getMonsterAttribute().getName());
        monsterDetails[3] = String.valueOf(card.getMonsterType().getName());
        monsterDetails[4] = String.valueOf(card.getMonsterEffectType().getName());
        monsterDetails[5] = String.valueOf(card.getAttackPoint());
        monsterDetails[6] = String.valueOf(card.getDefensePoint());
        monsterDetails[7] = card.getCardDescription();
        monsterDetails[8] = String.valueOf(card.getPrice());
        writer.writeNext(monsterDetails);
        writer.close();
    }

    public void writeMagicCardToCSV(MagicCard card) throws IOException {
        String path = "src/main/resources/Magic.csv";
        CSVWriter writer = new CSVWriter(new FileWriter(path, true), ',',
                CSVWriter.DEFAULT_QUOTE_CHARACTER, ICSVWriter.DEFAULT_ESCAPE_CHARACTER, ICSVWriter.DEFAULT_LINE_END);
        String[] magicDetails = new String[6];
        magicDetails[0] = card.getCardName();
        magicDetails[1] = String.valueOf(card.getMagicType().getName());
        magicDetails[2] = String.valueOf(card.getCardIcon().getName());
        magicDetails[3] = card.getCardDescription();
        magicDetails[4] = String.valueOf(card.getStatus().getName());
        magicDetails[5] = String.valueOf(card.getPrice());
        writer.writeNext(magicDetails);
        writer.close();
    }

    private void readMonsterCardsFromCSV(CSVReader reader) throws IOException, CsvValidationException {
        String[] monsterArray = reader.readNext();
        while ((monsterArray = reader.readNext()) != null) {
            MonsterCard monsterCard = new MonsterCard(monsterArray[7], monsterArray[0], "0", Card.CardType.MONSTER);
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
    }

    private void readMagicCardsFromCSV(CSVReader reader) throws IOException, CsvValidationException {
        String[] magicArray = reader.readNext();
        while ((magicArray = reader.readNext()) != null) {
            MagicCard magicCard = new MagicCard(magicArray[3], magicArray[0], "0", Card.CardType.MAGIC);
            magicCard.setMagicType(MagicCard.MagicType.getMagicType(magicArray[1]));
            magicCard.setCardIcon(MagicCard.CardIcon.getCardIcon(magicArray[2]));
            magicCard.setStatus(MagicCard.Status.getStatus(magicArray[4]));
            magicCard.setPrice(Integer.parseInt(magicArray[5]));
            Card.addCard(magicCard);
        }
    }

    public void serializeCard(Card card) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        File cardFile = new File("src/main/resources/Cards/" + card.getCardName() + ".json");
        try (Writer writer = new FileWriter(cardFile)) {
            gson.toJson(card, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Card deserializeCard(File cardFile) {
        Card card;
        GsonBuilder gsonBuilder = new GsonBuilder();
        try (Reader reader = new FileReader(cardFile.getPath())) {
            RuntimeTypeAdapterFactory<Card> runtimeTypeAdapterFactory = RuntimeTypeAdapterFactory
                    .of(Card.class, "type").
                            registerSubtype(MonsterCard.class, "MONSTER").
                            registerSubtype(MagicCard.class, "MAGIC");
            Type cardType = new TypeToken<Card>() {}.getType();
            card = gsonBuilder.registerTypeAdapterFactory(runtimeTypeAdapterFactory).create().fromJson(reader, cardType);
            return card;
        } catch (IOException e) {
            //e.printStackTrace();
        }
        return null;
    }

    public void refreshUsersToFileJson() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (Writer writer = new FileWriter("src/main/resources/Users.json")) {
            setAllCardsType();
            gson.toJson(User.getAllUsers(), writer);
        } catch (IOException e) {
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
            if (user.getActiveDeck() != null) {
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
            Type usersListType = new TypeToken<ArrayList<User>>() {
            }.getType();
            User.setAllUsers(gsonBuilder.registerTypeAdapterFactory(runtimeTypeAdapterFactory).create().fromJson(reader, usersListType));
        } catch (IOException e) {
            System.out.println("Welcome to this Game!\nFrom: Group15 AP- 2021 Spring");
            //e.printStackTrace();
        }
    }
}
