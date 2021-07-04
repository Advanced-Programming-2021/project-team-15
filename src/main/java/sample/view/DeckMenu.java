package sample.view;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import sample.controller.menuController.DeckController;
import sample.controller.menuController.MenuController;
import sample.controller.responses.DeckMenuResponses;
import sample.controller.utilizationController.UtilityController;
import sample.model.Deck;
import sample.model.User;

import java.util.HashMap;

public class DeckMenu extends Application {
    private static DeckMenu deckMenu;
    DeckMenuResponses responses;
    String deckName;
    String cardName;
    String deckType;
    String toPrint;
    Deck.DeckType deckTypeEnum;
    private DeckController deckController = new DeckController();
    public static DeckMenu getInstance() {
        if (deckMenu == null)
            deckMenu = new DeckMenu();
        return deckMenu;
    }
    @FXML
    private VBox leftSide;

    @Override
    public void start(Stage stage) throws Exception {
        ObservableList<Deck> decksList = FXCollections.observableArrayList();
//        decksList.addAll(MenuController.getUser().getAllDecksOfUser());
        decksList.addAll(new Deck("ali","d1"),
                new Deck("sara","d2"),
                new Deck("mary","d3"),
                new Deck("ali","d1"),
                new Deck("sara","d2"),
                new Deck("ali","d1"),
                new Deck("sara","d2"),
                new Deck("ali","d1"),
                new Deck("sara","d2"),
                new Deck("ali","d1"),
                new Deck("sara","d2")
        );
        // Create the ListView
        ListView<Deck> listView = new ListView<>();

        listView.setCellFactory((Callback<ListView<Deck>, ListCell<Deck>>) param -> {
            return new ListCell<Deck>() {
                @Override
                protected void updateItem(Deck deck, boolean empty) {
                    super.updateItem(deck, empty);

                    if (deck == null || empty) {
                        setText(null);
                    } else {
                        // Here we can build the layout we want for each ListCell. Let's use a HBox as our root.
                        HBox root = new HBox();
                        root.setAlignment(Pos.CENTER_LEFT);
                        root.setPadding(new Insets(5, 10, 5, 10));

                        // Within the root, we'll show the username on the left and our two buttons to the right
                        root.getChildren().add(new Label(deck.getName()));

                        // I'll add another Region here to expand, pushing the buttons to the right
                        Region region = new Region();
                        HBox.setHgrow(region, Priority.ALWAYS);
                        root.getChildren().add(region);

                        // Now for our buttons
                        Button btnAddFriend = new Button("Remove");
                        btnAddFriend.setOnAction(event -> {
                            MenuController.getUser().removeDeck(deck);
                            System.out.println("removed "+deck.getName());
                        });
                        Button btnRemove = new Button("Edit");
                        btnRemove.setOnAction(event -> {
                            // Code to remove friend
                            System.out.println("edit!");
                        });
                        root.getChildren().addAll(btnAddFriend, btnRemove);

                        // Finally, set our cell to display the root HBox
                        setText(null);
                        setGraphic(root);
                    }

                }
            };

        });

        listView.setItems(decksList);
       leftSide.getChildren().add(listView);
    }


//    @Override
//    public void scanInput() {
//        while (true) {
//            String input = UtilityController.getNextLine();
//            if (input.equals("menu exit")) checkAndCallMenuExit();
//            else if (input.startsWith("deck create")) checkAndCallCreateDeck(input);
//            else if (input.startsWith("deck delete")) checkAndCallDeleteDeck(input);
//            else if (input.startsWith("deck set-activate")) checkAndCallSetActiveDeck(input);
//            else if (input.startsWith("deck add-card")) checkAndCallAddOrRemoveCard(input, "add");
//            else if (input.startsWith("deck rm-card")) checkAndCallAddOrRemoveCard(input, "remove");
//            else if (input.equals("deck show --all")) checkAndCallShowAllDecks();
//            else if (input.equals("deck show --cards")) checkAndCallShowAllCardsOfUser();
//            else if (input.startsWith("deck show")) checkAndCallShowThisDeck(input);
//            else if (input.startsWith("card show ")) UtilityController.showCardByName(input);
//            else if (regexController.showMenuRegex(input)) checkAndCallShowCurrentMenu();
//            else if (input.startsWith("menu enter ")) System.out.println("Navigation is not possible hear");
//            else System.out.println("invalid command");
//            if (super.isExit) {
//                super.isExit = false;
//                return;
//            }
//        }
//    }
//
//    public void checkAndCallAddOrRemoveCard(String input, String addOrRemove) {
//        HashMap<String, String> enteredDetails = new HashMap<>();
//        if (!regexController.AddOrRemoveCardRegex(input, enteredDetails))
//            System.out.println("invalid command");
//        else {
//            deckName = enteredDetails.get("deck");
//            cardName = enteredDetails.get("card");
//            deckType = enteredDetails.get("type");
//            if (deckType.equals("main")) deckTypeEnum = Deck.DeckType.MAIN;
//            else deckTypeEnum = Deck.DeckType.SIDE;
//            if (addOrRemove.equals("add"))
//                responses = deckController.addCardToDeck(cardName, deckName, deckTypeEnum);
//            else if (addOrRemove.equals("remove"))
//                responses = deckController.removeCardFromDeck(cardName, deckName, deckTypeEnum);
//            printResponse(responses);
//        }
//    }
//
//    public void checkAndCallShowAllCardsOfUser() {
//        StringBuilder allCardsList = new StringBuilder();
//        responses = deckController.showAllCardsOfUser(allCardsList);
//        if (responses == DeckMenuResponses.SHOW_ALL_CARDS)
//            toPrint = allCardsList.toString();
//        printResponse(responses);
//    }
//
//    public void checkAndCallShowThisDeck(String input) {
//        HashMap<String, String> enteredDetails = new HashMap<>();
//        if (!regexController.showDeckRegex(input, enteredDetails))
//            System.out.println("invalid command");
//        else {
//            String deckName = enteredDetails.get("deck");
//            String deckType = enteredDetails.get("type");
//            if (deckType.equals("main")) deckTypeEnum = Deck.DeckType.MAIN;
//            else deckTypeEnum = Deck.DeckType.SIDE;
//            StringBuilder deckDetails = new StringBuilder();
//            responses = deckController.showThisDeckOfTheUser(deckDetails, deckName, deckTypeEnum);
//            toPrint = deckDetails.toString();
//            printResponse(responses);
//        }
//
//    }
//
//    public void checkAndCallShowAllDecks() {
//        StringBuilder allDecksList = new StringBuilder();
//        responses = deckController.showAllDecksOfTheUser(allDecksList);
//        if (responses == DeckMenuResponses.SHOW_ALL_DECKS)
//            toPrint = allDecksList.toString();
//        printResponse(responses);
//    }
//
//    public void checkAndCallCreateDeck(String input) {
//        HashMap<String, String> enteredDetails = new HashMap<>();
//        if (!regexController.createDeckRegex(input, enteredDetails))
//            System.out.println("invalid command");
//        else {
//            deckName = enteredDetails.get("name");
//            responses = deckController.createDeck(deckName);
//            printResponse(responses);
//        }
//    }
//
//    public void checkAndCallDeleteDeck(String input) {
//        HashMap<String, String> enteredDetails = new HashMap<>();
//        if (!regexController.deleteDeckRegex(input, enteredDetails))
//            System.out.println("invalid command");
//        else {
//            deckName = enteredDetails.get("name");
//            responses = deckController.removeDeck(deckName);
//            printResponse(responses);
//        }
//    }
//
//    public void checkAndCallSetActiveDeck(String input) {
//        HashMap<String, String> enteredDetails = new HashMap<>();
//        if (!regexController.setActiveDeckRegex(input, enteredDetails))
//            System.out.println("invalid command");
//        else {
//            deckName = enteredDetails.get("name");
//            responses = deckController.setActiveDeck(deckName);
//            printResponse(responses);
//        }
//
//    }
//
//    private void printResponse(DeckMenuResponses deckMenuResponses) {
//        String output = "";
//        switch (deckMenuResponses) {
//            case DECK_CREATE_SUCCESSFUL:
//                output = "deck created successfully!";
//                break;
//            case DECK_NAME_ALREADY_EXISTS:
//                output = "deck with name " + deckName + " already exists";
//                break;
//            case DECK_DELETE_SUCCESSFUL:
//                output = "deck deleted successfully";
//                break;
//            case DECK_NAME_NOT_EXIST:
//                output = "deck with name " + deckName + " does not exist";
//                break;
//            case DECK_ACTIVATE_SUCCESSFUL:
//                output = "deck activated successfully";
//                break;
//            case CARD_REMOVE_SUCCESSFUL:
//                output = "card removed from deck successfully";
//                break;
//            case CARD_NAME_NOT_EXIST_IN_DECK:
//                output = "card with name " + cardName + " does not exist in " + deckType + " deck";
//                break;
//            case CARD_NAME_NOT_EXIST:
//                output = "card with name " + cardName + " does not exist";
//                break;
//            case CARD_ADD_TO_DECK_SUCCESSFUL:
//                output = "card added to deck successfully";
//                break;
//            case DECK_FULL:
//                output = deckType + " deck is full";
//                break;
//            case MAX_SIZE_IDENTICAL_CARDS_ALREADY_IN_DECK:
//                output = "there are already three cards with name " + cardName + " in deck " + deckName;
//                break;
//            case SHOW_ALL_DECKS:
//            case SHOW_DECK:
//            case SHOW_ALL_CARDS:
//                output = toPrint;
//                break;
//            default:
//                break;
//        }
//        System.out.println(output);
//    }
}
