package model;

import java.util.ArrayList;

public class User {
    private static ArrayList<User> allUsers ;
    private ArrayList<Deck> allDecksOfUser;
    private ArrayList<Card> allCardsOfUser;
    private String userName;
    private String passWord;
    private String nickName;
    private int score;
    private int money;
    static {
        allUsers = new ArrayList<>();
    }
    public User(String userName, String nickName, String passWord) {
        this.userName = userName;
        this.nickName = nickName;
        this.passWord = passWord;
        allDecksOfUser = new ArrayList<>();
        allCardsOfUser = new ArrayList<>();
        allUsers.add(this);
    }

    public static User getUserByUserName(String userName) {
        for (User user : allUsers) {
            if(user.userName.equals(userName))
                return user;
        }
        return null;
    }
    public static User getUserByNickname(String nickname) {
        for (User user : allUsers) {
            if(user.nickName.equals(nickname))
                return user;
        }
        return null;
    }
    public static ArrayList<User> getAllUsers() {
        return allUsers;
    }

    public static void setAllUsers(ArrayList<User> allUsers) {
        User.allUsers = allUsers;
    }

    public ArrayList<Deck> getAllDecksOfUser() {
        return allDecksOfUser;
    }

    public void setAllDecksOfUser(ArrayList<Deck> allDecksOfUser) {
        this.allDecksOfUser = allDecksOfUser;
    }

    public ArrayList<Card> getAllCardsOfUser() {
        return allCardsOfUser;
    }

    public void setAllCardsOfUser(ArrayList<Card> allCardsOfUser) {
        this.allCardsOfUser = allCardsOfUser;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void changeMoney(int amount) {
        this.setMoney(this.getMoney()+amount);
    }

    public Deck getDeckByName(String name)
    {   for (Deck  deck : allDecksOfUser)
    {
        if(deck.getName().equals(name))
            return deck;
    }
    return null;
    }
    public void addDeck(Deck deck)
    {
        allDecksOfUser.add(deck);
    }
    public void removeDeck(Deck deck)
    {
        allDecksOfUser.remove(deck);
    }
}
