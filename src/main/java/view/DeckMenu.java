package view;

import controller.DeckController;
import controller.responses.DeckMenuResponses;
import model.Deck;
import utility.Utility;

import java.util.HashMap;
import java.util.Scanner;

public class DeckMenu extends Menu {
    private static DeckMenu deckMenu;
    DeckMenuResponses responses;
    String deckName;
    String cardName;
    String deckType;
    String toPrint;
    Deck.DeckType deckTypeEnum;
    private DeckController deckController = new DeckController();

    private DeckMenu() {
        super("Deck Menu");
    }

    public static DeckMenu getInstance() {
        if (deckMenu == null)
            deckMenu = new DeckMenu();
        return deckMenu;
    }


    @Override
    public void scanInput() {
        while (true) {
            String input = Utility.getNextLine();
            if (input.equals("menu exit")) checkAndCallMenuExit();
            else if (input.startsWith("deck create")) checkAndCallCreateDeck(input);
            else if (input.startsWith("deck delete")) checkAndCallDeleteDeck(input);
            else if (input.startsWith("deck set-activate")) checkAndCallSetActiveDeck(input);
            else if (input.startsWith("deck add-card")) checkAndCallAddOrRemoveCard(input, "add");
            else if (input.startsWith("deck rm-card")) checkAndCallAddOrRemoveCard(input, "remove");
            else if (input.equals("deck show --all")) checkAndCallShowAllDecks();
            else if (input.equals("deck show --cards")) checkAndCallShowAllCardsOfUser();
            else if (input.startsWith("deck show")) checkAndCallShowThisDeck(input);
            else if (regexController.showMenuRegex(input)) checkAndCallShowCurrentMenu();
            else System.out.println("invalid command");
            if (super.isExit) {
                super.isExit = false;
                return;
            }

        }

    }

    public void checkAndCallAddOrRemoveCard(String input, String addOrRemove) {
        HashMap<String, String> enteredDetails = new HashMap<>();
        if (!regexController.AddOrRemoveCardRegex(input, enteredDetails))
            System.out.println("invalid command");
        else {
            deckName = enteredDetails.get("deck");
            cardName = enteredDetails.get("card");
            deckType = enteredDetails.get("type");
            if (deckType.equals("main")) deckTypeEnum = Deck.DeckType.MAIN;
            else deckTypeEnum = Deck.DeckType.SIDE;
            if (addOrRemove.equals("add"))
                responses = deckController.addCardToDeck(cardName, deckName, deckTypeEnum);
            else if (addOrRemove.equals("remove"))
                responses = deckController.removeCardFromDeck(cardName, deckName, deckTypeEnum);
            printResponse(responses);
        }


    }

    public void checkAndCallShowAllCardsOfUser() {
        StringBuilder allCardsList = new StringBuilder();
        responses = deckController.showAllCardsOfUser(allCardsList);
        if (responses == DeckMenuResponses.SHOW_ALL_CARDS)
            toPrint = allCardsList.toString();
        printResponse(responses);
    }

    public void checkAndCallShowThisDeck(String input) {
        HashMap<String, String> enteredDetails = new HashMap<>();
        if (!regexController.showDeckRegex(input, enteredDetails))
            System.out.println("invalid command");
        else {
            String deckName = enteredDetails.get("deck");
            String deckType = enteredDetails.get("type");
            if (deckType.equals("main")) deckTypeEnum = Deck.DeckType.MAIN;
            else deckTypeEnum = Deck.DeckType.SIDE;
            StringBuilder deckDetails = new StringBuilder();
            responses = deckController.showThisDeckOfTheUser(deckDetails, deckName, deckTypeEnum);
            toPrint = deckDetails.toString();
            printResponse(responses);
        }

    }

    public void checkAndCallShowAllDecks() {
        StringBuilder allDecksList = new StringBuilder();
        responses = deckController.showAllDecksOfTheUser(allDecksList);
        if (responses == DeckMenuResponses.SHOW_ALL_DECKS)
            toPrint = allDecksList.toString();
        printResponse(responses);
    }

    public void checkAndCallCreateDeck(String input) {
        HashMap<String, String> enteredDetails = new HashMap<>();
        if (!regexController.createDeckRegex(input, enteredDetails))
            System.out.println("invalid command");
        else {
            deckName = enteredDetails.get("name");
            responses = deckController.createDeck(deckName);
            printResponse(responses);
        }
    }

    public void checkAndCallDeleteDeck(String input) {
        HashMap<String, String> enteredDetails = new HashMap<>();
        if (!regexController.deleteDeckRegex(input, enteredDetails))
            System.out.println("invalid command");
        else {
            deckName = enteredDetails.get("name");
            responses = deckController.removeDeck(deckName);
            printResponse(responses);
        }
    }

    public void checkAndCallSetActiveDeck(String input) {
        HashMap<String, String> enteredDetails = new HashMap<>();
        if (!regexController.setActiveDeckRegex(input, enteredDetails))
            System.out.println("invalid command");
        else {
            deckName = enteredDetails.get("name");
            responses = deckController.setActiveDeck(deckName);
            printResponse(responses);
        }

    }

    private void printResponse(DeckMenuResponses deckMenuResponses) {
        String output = "";
        switch (deckMenuResponses) {
            case DECK_CREATE_SUCCESSFUL:
                output = "deck created successfully!";
                break;
            case DECK_NAME_ALREADY_EXISTS:
                output = "deck with name " + deckName + " already exists";
                break;
            case DECK_DELETE_SUCCESSFUL:
                output = "deck deleted successfully";
                break;
            case DECK_NAME_NOT_EXIST:
                output = "deck with name " + deckName + " does not exist";
                break;
            case DECK_ACTIVATE_SUCCESSFUL:
                output = "deck activated successfully";
                break;
            case CARD_REMOVE_SUCCESSFUL:
                output = "card removed from deck successfully";
                break;
            case CARD_NAME_NOT_EXIST_IN_DECK:
                output = "card with name " + cardName + " does not exist in " + deckType + " deck";
                break;
            case CARD_NAME_NOT_EXIST:
                output = "card with name " + cardName + " does not exist";
                break;
            case CARD_ADD_TO_DECK_SUCCESSFUL:
                output = "card added to deck successfully";
                break;
            case DECK_FULL:
                output = deckType + " deck is full";
                break;
            case MAX_SIZE_IDENTICAL_CARDS_ALREADY_IN_DECK:
                output = "there are already three cards with name " + cardName + " in deck " + deckName;
                break;
            case SHOW_ALL_DECKS:
            case SHOW_DECK:
            case SHOW_ALL_CARDS:
                output = toPrint;
                break;
            default:
                break;
        }
        System.out.println(output);
    }
}
