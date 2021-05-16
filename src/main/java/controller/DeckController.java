package controller;

import controller.responses.DeckMenuResponses;
import model.Card;
import model.Deck;

import java.util.ArrayList;
import java.util.Comparator;

public class DeckController extends MenuController {
    public DeckController() {
        super("Deck Menu");
    }

    public DeckMenuResponses createDeck(String deckName) {
        if (user.getDeckByName(deckName) != null)
            return DeckMenuResponses.DECK_NAME_ALREADY_EXISTS;
        else {
            new Deck(user, deckName);
            return DeckMenuResponses.DECK_CREATE_SUCCESSFUL;
        }
    }

    public DeckMenuResponses removeDeck(String deckName) {
        if (user.getDeckByName(deckName) == null)
            return DeckMenuResponses.DECK_NAME_NOT_EXIST;
        else {
            Deck deck = user.getDeckByName(deckName);
            ArrayList<ArrayList<Card>> mainAndSideDeck = new ArrayList<>();
            mainAndSideDeck.add(deck.getMainDeck());
            mainAndSideDeck.add(deck.getSideDeck());
            for (ArrayList<Card> deckToCopyCards : mainAndSideDeck) {
                for (Card card : deckToCopyCards) {
                    user.addCard(card);
                }
            }
            user.removeDeckByName(deckName);
            return DeckMenuResponses.DECK_DELETE_SUCCESSFUL;
        }
    }

    public DeckMenuResponses setActiveDeck(String deckName) {
        if (user.getDeckByName(deckName) == null)
            return DeckMenuResponses.DECK_NAME_NOT_EXIST;
        else {
            if (user.getActiveDeck() != null) user.getActiveDeck().setActive(false);
            user.setActiveDeck(user.getDeckByName(deckName));
            user.getDeckByName(deckName).setActive(true);
            return DeckMenuResponses.DECK_ACTIVATE_SUCCESSFUL;
        }
    }

    public DeckMenuResponses removeCardFromDeck(String cardName, String deckName, Deck.DeckType deckType) {
        if (user.getDeckByName(deckName) == null)
            return DeckMenuResponses.DECK_NAME_NOT_EXIST;
        Deck addingDeck = user.getDeckByName(deckName);
        if (addingDeck.getCardByName(cardName, deckType) == null)
            return DeckMenuResponses.CARD_NAME_NOT_EXIST_IN_DECK;
        else {
            user.addCard(addingDeck.getCardByName(cardName, deckType));
            addingDeck.removeCardFromDeckByName(cardName, deckType);
            if (addingDeck.getMainDeck().size() < addingDeck.mainDeckMinCardCount)
                addingDeck.setValid(false);
            jsonController.refreshUsersToFileJson();
            return DeckMenuResponses.CARD_REMOVE_SUCCESSFUL;
        }

    }

    public DeckMenuResponses addCardToDeck(String cardName, String deckName, Deck.DeckType deckType) {
        if (user.getCardByName(cardName) == null)
            return DeckMenuResponses.CARD_NAME_NOT_EXIST;
        else if (user.getDeckByName(deckName) == null)
            return DeckMenuResponses.DECK_NAME_NOT_EXIST;
        Deck addingDeck = user.getDeckByName(deckName);
        if (deckType == Deck.DeckType.MAIN && addingDeck.getMainDeck().size() == addingDeck.mainDeckMaxCardCount)
            return DeckMenuResponses.DECK_FULL;
        if (deckType == Deck.DeckType.SIDE && addingDeck.getSideDeck().size() == addingDeck.sideDeckMaxCardCount)
            return DeckMenuResponses.DECK_FULL;
        else if (addingDeck.getSpecifiedCardCountInDeckByName(cardName) >= addingDeck.DeckMaxSpecifiedCardCount)
            return DeckMenuResponses.MAX_SIZE_IDENTICAL_CARDS_ALREADY_IN_DECK;
        else {
//            System.out.println("adding");
            addingDeck.addCardToDeck(cloner.deepClone(Card.getCardByName(cardName)), deckType);
            user.removeUserCardByName(cardName);
            if (addingDeck.getMainDeck().size() >= addingDeck.mainDeckMinCardCount)
                addingDeck.setValid(true);
            jsonController.refreshUsersToFileJson();
            return DeckMenuResponses.CARD_ADD_TO_DECK_SUCCESSFUL;
        }
    }

    private ArrayList<Deck> sortDecks(ArrayList<Deck> decks) {
        ArrayList<Deck> sortedDecks = new ArrayList<>(decks);
        sortedDecks.sort(Comparator.comparing(Deck::getName));
        return sortedDecks;
    }

    public DeckMenuResponses showThisDeckOfTheUser(StringBuilder deckDetails, String deckName, Deck.DeckType deckType) {
        if (user.getDeckByName(deckName) == null)
            return DeckMenuResponses.DECK_NAME_NOT_EXIST;
        else {
            Deck toPrintDeck = user.getDeckByName(deckName);
            ArrayList<Card> mainOrSideDeck = new ArrayList<>();
            if (deckType == Deck.DeckType.MAIN)
                mainOrSideDeck = toPrintDeck.getMainDeck();
            else if (deckType == Deck.DeckType.SIDE)
                mainOrSideDeck = toPrintDeck.getSideDeck();
            ArrayList<Card> monsterCards = new ArrayList<>();
            ArrayList<Card> magicCards = new ArrayList<>();
            for (Card card : mainOrSideDeck) {
                if (card.getCardType() == Card.CardType.MONSTER)
                    monsterCards.add(card);
                else if (card.getCardType() == Card.CardType.MAGIC)
                    magicCards.add(card);
            }
            magicCards = sortCardsByName(magicCards);
            monsterCards = sortCardsByName(monsterCards);
            deckDetails.append("Deck : ").append(toPrintDeck.getName()).append("\n");
            deckDetails.append(deckType.toString()).append("deck :\n").append("Monsters :\n");
            for (Card card : monsterCards) {
                deckDetails.append(card.getCardName()).append(" : ").append(card.getCardDescription()).append("\n");
            }
            deckDetails.deleteCharAt(deckDetails.lastIndexOf("\n"));
            for (Card card : magicCards) {
                deckDetails.append(card.getCardName()).append(" : ").append(card.getCardDescription()).append("\n");
            }
            return DeckMenuResponses.SHOW_DECK;
        }
    }

    public DeckMenuResponses showAllDecksOfTheUser(StringBuilder allDecks) {
        Deck activeDeck = null;
        ArrayList<Deck> otherDecks = new ArrayList<>();
        for (Deck deck : user.getAllDecksOfUser()) {
            if (!deck.isActive())
                otherDecks.add(deck);
            else activeDeck = deck;
        }
        otherDecks = sortDecks(otherDecks);
        allDecks.append("Decks :\n").append("Active Deck :\n");
        allDecks.append(getDeckDetails(activeDeck)).append("Other decks :\n");
        for (Deck deck : otherDecks) {
            allDecks.append(getDeckDetails(deck));
        }
        return DeckMenuResponses.SHOW_ALL_DECKS;
    }

    public DeckMenuResponses showAllCardsOfUser(StringBuilder allCards) {
        ArrayList<Card> sortedAllUserCards = sortCardsByName(user.getAllCardsOfUser());
        for (Card card : sortedAllUserCards) {
            allCards.append(card.getCardName()).append(" : ").append(card.getCardDescription()).append("\n");
        }
        return DeckMenuResponses.SHOW_ALL_CARDS;
    }

    private String getDeckDetails(Deck deck) {
        if (deck == null)
            return "";
        String validation;
        if (deck.isValid()) validation = "valid";
        else validation = "invalid";
        return deck.getName() + " : main deck " + deck.getMainDeck().size() + " , side deck " +
                deck.getSideDeck().size() + " , " + validation + "\n";
    }

}
