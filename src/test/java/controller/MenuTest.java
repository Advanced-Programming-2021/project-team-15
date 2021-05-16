package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import controller.responses.DeckMenuResponses;
import controller.responses.LoginMenuResponses;
import model.Card;
import model.User;
import org.testng.annotations.Test;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import static org.junit.Assert.*;


public class MenuTest {
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
        loginController.removeUser("pedaret");
    }

    @Test
    public void loginUserTest() {
        loginController.registerUser("pedaret", "bale", "babat");
        assertNull(MenuController.user);
        loginMenuResponses = loginController.loginUser("pedaresh", "babat");
        assertEquals(LoginMenuResponses.USER_USERNAME_PASSWORD_NOT_MATCHED, loginMenuResponses);
        loginMenuResponses = loginController.loginUser("pedaret", "babash");
        assertEquals(LoginMenuResponses.USER_USERNAME_PASSWORD_NOT_MATCHED, loginMenuResponses);
        loginController.loginUser("pedaret", "babat");
        assertEquals(User.getUserByUserName("pedaret"), MenuController.user);
        loginController.removeUser("pedaret");
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
        loginController.removeUser("pedaret");
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
