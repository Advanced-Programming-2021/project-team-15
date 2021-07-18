package sample.view;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import sample.controller.menuController.ImportExportController;
import sample.controller.responses.ImportExportResponses;
import sample.controller.utilizationController.AudioController;
import sample.controller.utilizationController.DatabaseController;
import sample.controller.utilizationController.UtilityController;
import sample.model.cards.Card;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ImportExportMenu {
    private static ImportExportMenu importExportMenu;
    private static ArrayList<Card> toImportCards = new ArrayList<>();
    private static Card toExportCard;
    private final ImportExportController importExportController = ImportExportController.getInstance();
    private final int maximumCardsInRow = 4;
    private ImageView toDragCard;
    @FXML
    private ScrollPane cardsList;
    @FXML
    private VBox importPlace;
    @FXML
    private ScrollPane cardsPreShow;
    @FXML
    private Label importLabel;
    @FXML
    private Label exportLabel;
    @FXML
    private ImageView exportPlace;
    @FXML
    private Label exportCardName;
    @FXML
    private ImageView exportCardImage;

    public static ImportExportMenu getInstance() {
        if (importExportMenu == null)
            importExportMenu = new ImportExportMenu();
        return importExportMenu;
    }

    public void initializeScene() {
        initializeExportContainer();
        initializeImportContainer();
        initializeExportDropBox();
    }

    private GridPane setCardsPreShow() {
        cardsPreShow.setVisible(true);
        GridPane showCards = new GridPane();
        cardsPreShow.setContent(showCards);
        return showCards;
    }

    public void importCards(MouseEvent mouseEvent) throws IOException {
        for (Card card : toImportCards)
            printResponse(importExportController.importCard(card.getCardName()));
        cardsPreShow.setContent(null);
        cardsPreShow.setVisible(false);
        toImportCards.clear();
        inactivateButton(importLabel);
    }

    public void exportCards(MouseEvent mouseEvent) throws IOException {
        printResponse(importExportController.exportCard(toExportCard.getCardName()));
        inactivateButton(exportLabel);
        exportCardImage.setImage(null);
        exportCardName.setText(null);
    }

    public void backButtonClicked(MouseEvent mouseEvent) throws IOException {
        AudioController.playClick();
        Scene mainMenuScene = new Scene(FXMLLoader.load(getClass().getResource("/FxmlFiles/MainMenu.fxml")));
        WelcomeMenu.stage.setScene(mainMenuScene);
    }

    private void addCardToGridPane(Card card, GridPane gridPane, int cardCounter) {
        Image cardImage = DatabaseController.getInstance().getImageByCard(card);
        Card.getAllCardsImages().put(card.getCardName(), cardImage);
        ImageView showingCardImage = new ImageView(cardImage);
        showingCardImage.setFitWidth(100);
        showingCardImage.setFitHeight(100);
        gridPane.add(showingCardImage, 0, cardCounter);
        Label cardNameLabel = new Label();
        cardNameLabel.setPrefWidth(150);
        cardNameLabel.setPrefHeight(50);
        cardNameLabel.setAlignment(Pos.CENTER);
        cardNameLabel.setText("Name : " + card.getCardName());
        gridPane.add(cardNameLabel, 1, cardCounter);
    }

    private void initializeExportDropBox() {
        exportPlace.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                exportPlace.setOpacity(1);
                exportPlace.setCursor(Cursor.HAND);
            }
        });
        exportPlace.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                exportPlace.setOpacity(0.2);
            }
        });
        exportPlace.setOnDragOver(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                Dragboard dragboard = event.getDragboard();
                if (dragboard.hasString()) {
                    event.acceptTransferModes(TransferMode.ANY);
                }
                event.consume();
            }
        });

        exportPlace.setOnDragDropped(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasString()) {
                    exportCardName.setText(toExportCard.getCardName());
                    exportCardImage.setImage(toExportCard.getCardImage());
                    selectCard(toExportCard);
                    success = true;
                }
                event.setDropCompleted(success);
                event.consume();
            }
        });
    }

    private void initializeImportContainer() {
        importPlace.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                importPlace.setOpacity(1);
                importPlace.setCursor(Cursor.HAND);
            }
        });
        importPlace.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                importPlace.setOpacity(0.2);
            }
        });
        importPlace.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                GridPane showCardsGridPane = setCardsPreShow();
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasFiles()) {
                    success = true;
                    String filePath;
                    int cardCounter = 0;
                    for (File file : db.getFiles()) {
                        filePath = file.getAbsolutePath();
                        Card toImportCard = DatabaseController.getInstance().deserializeCard(file);
                        addCardToGridPane(toImportCard, showCardsGridPane, cardCounter);
                        toImportCards.add(toImportCard);
                        cardCounter++;
                    }
                    activateButton(importLabel, true);
                } else System.out.println("are da");
                event.setDropCompleted(success);
                event.consume();
            }
        });
        importPlace.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                if (db.hasFiles()) {
                    event.acceptTransferModes(TransferMode.MOVE);
                } else {
                    event.consume();
                }
            }
        });
    }

    private void initializeExportContainer() {
        int rowsCount = Card.getAllCards().size() / maximumCardsInRow + 1;
        GridPane cardsGridPane = new GridPane();
        setScrollPaneProps(cardsList);
        for (int i = 0; i < rowsCount; i++) {
            for (int j = 0; j < maximumCardsInRow; j++) {
                if (i * maximumCardsInRow + j >= Card.getAllCards().size()) break;
                Image cardImage = Card.getAllCards().get(i * maximumCardsInRow + j).getCardImage();
                ImageView showingCardImage = new ImageView(cardImage);
                setShowingCardImageProps(showingCardImage);
                cardsGridPane.add(showingCardImage, j, i);
            }
        }
        cardsList.setBackground(Background.EMPTY);
        cardsList.setContent(cardsGridPane);
    }

    private void setShowingCardImageProps(ImageView showingCardImage) {
        showingCardImage.setOnMouseEntered(mouseEvent -> {
            showingCardImage.setFitHeight(130);
            showingCardImage.setFitWidth(130);
        });
        showingCardImage.setOnMouseExited(mouseEvent -> {
            showingCardImage.setFitWidth(120);
            showingCardImage.setFitHeight(120);
        });
        showingCardImage.setOnDragDetected(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
//                System.out.println("Event on Source: drag detected");
                toExportCard = Card.getCardByImage(showingCardImage.getImage());
                Dragboard db = showingCardImage.startDragAndDrop(TransferMode.ANY);
                ClipboardContent content = new ClipboardContent();
                content.putString("Hello!");
                db.setContent(content);
                event.consume();
            }
        });
        showingCardImage.setFitWidth(120);
        showingCardImage.setFitHeight(120);
    }

    private void setScrollPaneProps(ScrollPane scrollPane) {
        scrollPane.setOnMouseEntered(new EventHandler<>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                cardsList.setOpacity(1);
            }
        });
        scrollPane.setOnMouseExited(new EventHandler<>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                cardsList.setOpacity(0.3);
            }
        });
        scrollPane.setCursor(Cursor.HAND);
        scrollPane.setBackground(Background.EMPTY);
    }

    private void selectCard(Card selectedCard) {
        toExportCard = selectedCard;
        activateButton(exportLabel, false);
    }

    private void activateButton(Label button, boolean importing) {
        button.setCursor(Cursor.HAND);
        button.setOpacity(1);
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    AudioController.playClick();
                    if (importing) importCards(mouseEvent);
                    else exportCards(mouseEvent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void inactivateButton(Label button) {
        button.setCursor(Cursor.DEFAULT);
        button.setOpacity(0.5);
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                printResponse(ImportExportResponses.CARD_NOT_FOUND);
            }
        });
    }
//
//    @Override
//    public void scanInput() throws IOException {
//        while (true) {
//            String input = UtilityController.getNextLine();
//            if (input.startsWith("import") ||
//                    input.startsWith("export")) checkAndCallImportExportCard(input);
//            else if (input.equals("menu exit")) checkAndCallMenuExit();
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
//    public void checkAndCallImportExportCard(String input) throws IOException {
//        HashMap<String, String> enteredDetails = new HashMap<>();
//        if (!regexController.importExportRegex(input, enteredDetails))
//            System.out.println("invalid command");
//        else {
//            String cardName = enteredDetails.get("name");
//            if (enteredDetails.get("action").equals("import"))
//                printResponse(importExportController.importCard(cardName));
//            else printResponse(importExportController.exportCard(cardName));
//        }
//    }

    private void printResponse(ImportExportResponses importExportResponses) {
        String output = "";
        switch (importExportResponses) {
            case CARD_NOT_FOUND:
                output = "Choose a card!";
                UtilityController.makeAlert("WTF!!", "What are you doing?!", output, new Image(String.valueOf(getClass().
                        getResource("/Images/confusedAnimeGirl.jpg"))));
                break;
            case CARD_EXPORT_SUCCESSFUL:
                output = "Card exported successfully!";
                UtilityController.makeAlert("Happy!!", "You're doing great!", output, new Image(String.valueOf(getClass().
                        getResource("/Images/AnimeGirl3.jpg"))));
                break;
            case CARD_IMPORT_SUCCESSFUL:
                output = "Card imported successfully!";
                UtilityController.makeAlert("Happy!!", "You're doing great!", output, new Image(String.valueOf(getClass().
                        getResource("/Images/AnimeGirl3.jpg"))));
                break;
            case CARD_ALREADY_EXISTS:
                output = "Card with this name already exists!";
                UtilityController.makeAlert("WTF!!", "What are you doing?!", output, new Image(String.valueOf(getClass().
                        getResource("/Images/confusedAnimeGirl.jpg"))));
                break;
        }
    }
}
