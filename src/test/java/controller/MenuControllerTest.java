package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import controller.responses.DeckMenuResponses;
import controller.responses.LoginMenuResponses;
import model.Card;
import model.Deck;
import model.User;
import org.testng.annotations.Test;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import static org.junit.Assert.*;


public class MenuControllerTest {
    LoginController loginController = new LoginController();
    ShopController shopController = new ShopController();
    ScoreboardController scoreboardController = new ScoreboardController("Scoreboard Menu");
    DeckController deckController = new DeckController();
    JSONController jsonController = new JSONController();
    DeckMenuResponses deckMenuResponses;
    LoginMenuResponses loginMenuResponses;

    @Test
    public void registerUserTest() {
        loginMenuResponses = loginController.registerUser("pedaret", "bale", "babat");
        assertEquals(LoginMenuResponses.USER_CREATE_SUCCESSFUL, loginMenuResponses);
        assertNotNull(User.getUserByUserName("pedaret"));
        loginMenuResponses = loginController.registerUser("pedaret", "kheyr", "babash");
        assertEquals(LoginMenuResponses.USER_USERNAME_ALREADY_EXISTS, loginMenuResponses);
        loginMenuResponses = loginController.registerUser("pedaresh", "bale", "babat");
        assertEquals(LoginMenuResponses.USER_NICKNAME_ALREADY_EXISTS, loginMenuResponses);
        loginController.removeUser("pedaret","babat");
    }

    @Test
    public void loginUserTest() {
        loginController.registerUser("pedaret", "bale", "babat");
        loginMenuResponses = loginController.loginUser("pedaresh", "babat");
        assertEquals(LoginMenuResponses.USER_USERNAME_PASSWORD_NOT_MATCHED, loginMenuResponses);
        loginMenuResponses = loginController.loginUser("pedaret", "babash");
        assertEquals(LoginMenuResponses.USER_USERNAME_PASSWORD_NOT_MATCHED, loginMenuResponses);
        loginController.loginUser("pedaret", "babat");
        assertEquals(User.getUserByUserName("pedaret"), MenuController.user);
        loginController.removeUser("pedaret","babat");
    }

    @Test
    public void shopBuyItemTest() {
        loginMenuResponses = loginController.registerUser("pedaret", "bale", "babat");
        loginMenuResponses = loginController.loginUser("pedaret", "babat");
        assertNotNull(MenuController.user);
        User user = MenuController.user;
        user.setMoney(3000);
        shopController.buyItem("Battle Ox");
        assertEquals(3000, user.getMoney());
        shopController.buyItem("Battle OX");
        assertEquals(100, user.getMoney());
        shopController.buyItem("Battle OX");
        assertEquals(100, user.getMoney());
        user.setMoney(6000);
        shopController.buyItem("Battle OX");
        shopController.buyItem("Axe Raider");
        assertEquals(0, user.getMoney());
        String[] cardNames = {"Battle OX", "Battle OX", "Axe Raider"};
        ArrayList<String> userCardsName = new ArrayList<>();
        for (Card card : user.getAllCardsOfUser()) userCardsName.add(card.getCardName());
        assertEquals(Arrays.asList(cardNames), userCardsName);
        loginController.removeUser("pedaret","babat");
    }

    @Test
    public void sortUsersTest() throws FileNotFoundException {
        ArrayList<User> toSortUsers = readUsersFromFile("src/test/resources/toSortUsersTest.json");
        Map<Integer, ArrayList<User>> sortedUsersTreeMap = scoreboardController.sortUsersByScore(toSortUsers);
        ArrayList<User> sortingUsers = new ArrayList<>();
        for (Integer score : sortedUsersTreeMap.keySet()) {
            sortingUsers.addAll(sortedUsersTreeMap.get(score));
        }
        ArrayList<User> sortedUsersTested = readUsersFromFile("src/test/resources/sortedUsersTest.json");
        for (int i = 0; i < sortedUsersTested.size(); i++) {
            assertEquals(sortedUsersTested.get(i).getUserName(), sortingUsers.get(i).getUserName());
        }
    }

    @Test
    public void createDeckTest() throws FileNotFoundException {
        User user = readUsersFromFile("src/test/resources/toAddCardToDeckTest.json").get(0);
        User.getAllUsers().add(user);
        jsonController.refreshUsersToFileJson();
        MenuController.setUser(user);
        deckController.createDeck("Uh");
        assertNotNull(user.getDeckByName("Uh"));
        deckMenuResponses = deckController.createDeck("Uh");
        assertEquals(DeckMenuResponses.DECK_NAME_ALREADY_EXISTS,deckMenuResponses);
        deckController.removeDeck("Uh");
        assertNull(user.getDeckByName("Uh"));
        loginController.removeUser(user.getUserName(), user.getPassWord());
    }

    @Test
    public void addCardToDeckTest() throws FileNotFoundException {
        User user = readUsersFromFile("src/test/resources/toAddCardToDeckTest.json").get(0);
        User.getAllUsers().add(user);
        jsonController.refreshUsersToFileJson();
        MenuController.setUser(user);
        jsonController.MonsterCardParseJson();
        jsonController.MagicCardParseJson();
        user.setActiveDeck(user.getDeckByName(user.getActiveDeckName()));
        deckMenuResponses = deckController.addCardToDeck("Yami", "gav", Deck.DeckType.MAIN);
        assertEquals(DeckMenuResponses.MAX_SIZE_IDENTICAL_CARDS_ALREADY_IN_DECK, deckMenuResponses);
        deckMenuResponses = deckController.addCardToDeck("Yami", "ga", Deck.DeckType.MAIN);
        assertEquals(DeckMenuResponses.CARD_ADD_TO_DECK_SUCCESSFUL, deckMenuResponses);
        deckMenuResponses = deckController.addCardToDeck("Yomi Ship", "gaavv", Deck.DeckType.MAIN);
        assertEquals(DeckMenuResponses.CARD_ADD_TO_DECK_SUCCESSFUL, deckMenuResponses);
        deckMenuResponses = deckController.removeCardFromDeck("Wattkid", "ga", Deck.DeckType.SIDE);
        assertEquals(DeckMenuResponses.CARD_REMOVE_SUCCESSFUL, deckMenuResponses);
        deckMenuResponses = deckController.setActiveDeck("gaavv");
        assertEquals(DeckMenuResponses.DECK_ACTIVATE_SUCCESSFUL, deckMenuResponses);
        User testedUser = readUsersFromFile("src/test/resources/addedCardToDeckTest.json").get(0);
        assertEquals(user, testedUser);
        loginController.removeUser(user.getUserName(),user.getPassWord());
    }

    private ArrayList<User> readUsersFromFile(String path) throws FileNotFoundException {
        Gson gson = new GsonBuilder().create();
        Reader reader = new FileReader(path);
        Type usersListType = new TypeToken<ArrayList<User>>() {
        }.getType();
        return gson.fromJson(reader, usersListType);
    }

    private void writeUsersToFile(String path, ArrayList<User> users) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Writer writer = new FileWriter(path);
        gson.toJson(users, writer);
    }
}
