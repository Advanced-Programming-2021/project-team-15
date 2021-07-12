package sample.model;

import sample.model.cards.Card;

import java.util.ArrayList;

public class User {
    private static ArrayList<User> allUsers;

    static {
        allUsers = new ArrayList<>();
    }

    private String activeDeckName;
    private Deck activeDeck;
    private ArrayList<Deck> allDecksOfUser;
    private ArrayList<Card> allCardsOfUser;
    private String userName;
    private String passWord;
    private String nickName;
    private int score;
    private int money=1000;
private int rank=1;
    public User(String userName, String nickName, String passWord) {
        this.userName = userName;
        this.nickName = nickName;
        this.passWord = passWord;
        allDecksOfUser = new ArrayList<>();
        allCardsOfUser = new ArrayList<>();
    }

    @Override
    public boolean equals(Object object) {
        if (object == null)
            return false;
        if (object == this)
            return true;
        if (!(object instanceof User)) return false;
        User user = (User) object;
        if (!user.userName.equals(userName) || !user.nickName.equals(nickName) || !user.passWord.equals(passWord)
        || user.money!=money || user.score!=score || !user.activeDeckName.equals(activeDeckName)) return false;
        return user.allDecksOfUser.equals(allDecksOfUser) && user.allCardsOfUser.equals(allCardsOfUser);
    }

    public static User getUserByUserName(String userName) {
        for (User user : allUsers) {
            if (user.userName.equals(userName))
                return user;
        }
        return null;
    }

    public static void removeUserByUsername(String username) {
        for (User user : allUsers) {
            if (user.userName.equals(username)) {
                User.getAllUsers().remove(user);
                return;
            }
        }
    }

    public static User getUserByNickname(String nickname) {
        for (User user : allUsers) {
            if (user.nickName.equals(nickname))
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

    public Card getCardByName(String cardName) {
        for (Card card : allCardsOfUser) {
            if (card.getCardName().equals(cardName))
                return card;
        }
        return null;
    }

    public void removeUserCardByName(String cardName) {
        if (getCardIndexByName(cardName)==-1)
            return;
        allCardsOfUser.remove(getCardIndexByName(cardName));
    }

    private int getCardIndexByName(String cardName) {
        int index = 0;
        for (Card card : allCardsOfUser) {
            if (card.getCardName().equals(cardName))
                return index;
            index++;
        }
        return -1;
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

    public void addCard(Card card) {
        allCardsOfUser.add(card);
    }

    public void removeDeckByName(String deckName) {
        allDecksOfUser.remove(getDeckByName(deckName));
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
        this.setMoney(this.getMoney() + amount);
    }

    public Deck getDeckByName(String name) {
        for (Deck deck : allDecksOfUser) {
            if (deck.getName().equals(name))
                return deck;
        }
        return null;
    }

    public void addDeck(Deck deck) {
        allDecksOfUser.add(deck);
    }

    public void removeDeck(Deck deck) {
        allDecksOfUser.remove(deck);
    }

    public String getActiveDeckName() {
        return activeDeckName;
    }

    public void setActiveDeckName(String activeDeckName) {
        this.activeDeckName = activeDeckName;
    }

    public Deck getActiveDeck() {
        return activeDeck;
    }

    public void setActiveDeck(Deck activeDeck) {
        this.activeDeck = activeDeck;
    }

    public int getUserSpecificCardCount(Card searchCard) {
        int count = 0;
        for (Card card : allCardsOfUser) {
            if (card.equals(searchCard)) count++;
        }
        return count;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}
