package sample.controller.menuController;

import sample.controller.responses.DeckMenuResponses;
import sample.model.Deck;
import sample.model.cards.Card;

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
            new Deck(user.getUserName(), deckName);
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
            user.setActiveDeckName(user.getActiveDeck().getName());
            databaseController.refreshUsersToFileJson();
            return DeckMenuResponses.DECK_ACTIVATE_SUCCESSFUL;
        }
    }

    public DeckMenuResponses removeCardFromDeck(Card card, String deckName, Deck.DeckType deckType) {
        Deck addingDeck = user.getDeckByName(deckName);
            user.addCard(card);
            addingDeck.removeCardFromDeck(card, deckType);
            if (addingDeck.getMainDeck().size() < Deck.mainDeckMinCardCount)
                addingDeck.setValid(false);
            databaseController.refreshUsersToFileJson();
            return DeckMenuResponses.CARD_REMOVE_SUCCESSFUL;
        }


    public DeckMenuResponses addCardToDeck(Card card, String deckName, Deck.DeckType deckType) {
        Deck addingDeck = user.getDeckByName(deckName);
        if (deckType == Deck.DeckType.MAIN && addingDeck.getMainDeck().size() == Deck.mainDeckMaxCardCount)
            return DeckMenuResponses.DECK_FULL;
        if (deckType == Deck.DeckType.SIDE && addingDeck.getSideDeck().size() == Deck.sideDeckMaxCardCount)
            return DeckMenuResponses.DECK_FULL;
        else if (addingDeck.getSpecifiedCardCountInDeckByName(card.getCardName()) >= Deck.DeckMaxSpecifiedCardCount)
            return DeckMenuResponses.MAX_SIZE_IDENTICAL_CARDS_ALREADY_IN_DECK;
        else {
            addingDeck.addCardToDeck(cloner.deepClone(card), deckType);
           user.getAllCardsOfUser().remove(card);
            if (addingDeck.getMainDeck().size() >= Deck.mainDeckMinCardCount)
                addingDeck.setValid(true);
            databaseController.refreshUsersToFileJson();
            return DeckMenuResponses.CARD_ADD_TO_DECK_SUCCESSFUL;
        }
    }

    public ArrayList<Deck> sortDecks(ArrayList<Deck> decks) {
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
            deckDetails.append("* ").append("Deck : ").append(toPrintDeck.getName()).append("\n");
            deckDetails.append("* ").append(deckType.toString()).append("deck :\n");
            deckDetails.append("* ").append("Monsters :\n");
            for (Card card : monsterCards) {
                deckDetails.append("+").append(card.getCardName()).append(" : ").append(card.getCardDescription()).append("\n");
            }
            deckDetails.append("* ").append("Magics :\n");
            for (Card card : magicCards) {
                deckDetails.append("+").append(card.getCardName()).append(" : ").append(card.getCardDescription()).append("\n");
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
            //else activeDeck = deck;
        }
        activeDeck = user.getActiveDeck();
        otherDecks = sortDecks(otherDecks);
        allDecks.append("* ").append("Decks :\n").append("* ").append("Active Deck :\n");
        allDecks.append(getDeckDetails(activeDeck)).append("* ").append("Other decks :\n");
        for (Deck deck : otherDecks) {
            allDecks.append(getDeckDetails(deck));
        }
        return DeckMenuResponses.SHOW_ALL_DECKS;
    }

    public DeckMenuResponses showAllCardsOfUser(StringBuilder allCards) {
        ArrayList<Card> sortedAllUserCards = sortCardsByName(user.getAllCardsOfUser());
        allCards.append("* All cards of user : \n");
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
