package sample.view;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import sample.controller.menuController.ImportExportController;
import sample.controller.menuController.MenuController;
import sample.controller.responses.ImportExportResponses;
import sample.controller.utilizationController.DatabaseController;
import sample.controller.utilizationController.UtilityController;
import sample.model.cards.Card;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class ImportExportMenu{
    private static ImportExportMenu importExportMenu;
    private final ImportExportController importExportController = ImportExportController.getInstance();
    private static ArrayList<Card> toImportCards = new ArrayList<>();
    private final int maximumCardsInRow = 4;
    @FXML
    private ScrollPane cardsList;
    @FXML
    private Pane importPane;
    @FXML
    private VBox importPlace;
    @FXML
    private ScrollPane cardsPreShow;

//    private ImportExportMenu() {
//        super("ImportExport Menu");
//    }

    public static ImportExportMenu getInstance() {
        if (importExportMenu == null)
            importExportMenu = new ImportExportMenu();
        return importExportMenu;
    }

    public void initializeScene() {
        initializeExportContainer();
        initializeImportContainer();
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
    }

    public void exportCards(MouseEvent mouseEvent) {

    }

    private void addCardToGridPane(Card card, GridPane gridPane, int cardCounter) {
        Image cardImage = DatabaseController.getInstance().getImageByCard(card);
        Card.getAllCardsImages().put(card.getCardName(),cardImage);
        ImageView showingCardImage = new ImageView(cardImage);
        showingCardImage.setFitWidth(100);
        showingCardImage.setFitHeight(100);
        gridPane.add(showingCardImage,0,cardCounter);
        Label cardNameLabel = new Label();
        cardNameLabel.setPrefWidth(150);
        cardNameLabel.setPrefHeight(50);
        cardNameLabel.setAlignment(Pos.CENTER);
        cardNameLabel.setText("Name : "+card.getCardName());
        gridPane.add(cardNameLabel,1,cardCounter);
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
                    int cardCounter=0;
                    for (File file:db.getFiles()) {
                        filePath = file.getAbsolutePath();
                        Card toImportCard = DatabaseController.getInstance().deserializeCard(file);
                        addCardToGridPane(toImportCard,showCardsGridPane,cardCounter);
                        toImportCards.add(toImportCard);
                        cardCounter++;
                    }
                }
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
        showingCardImage.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                selectCard(Objects.requireNonNull(Card.getCardByImage(showingCardImage.getImage())));
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
//        cardsGridPane.setHgap(5);
//        cardsGridPane.setVgap(5);
    }

    private void selectCard(Card selectedCard) {
//        toBuyCard = selectedCard;
//        cardName.setText(selectedCard.getCardName());
//        cardImage.setImage(selectedCard.getCardImage());
//        cardCount.setText(String.valueOf(MenuController.getUser().getUserSpecificCardCount(selectedCard)));
//        int money = MenuController.getUser().getMoney();
//        moneyLabel.setText(String.valueOf(money));
//        priceLabel.setText(String.valueOf(selectedCard.getPrice()));
//        if (money>=selectedCard.getPrice()) activateBuyButton(buyCardButton);
//        else inactivateBuyButton(buyCardButton);
        System.out.println(selectedCard.getCardName());
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
                output = "card with this name doesn't exist";
                break;
            case CARD_EXPORT_SUCCESSFUL:
                output = "card exported successfully";
                UtilityController.makeAlert("Happy!!","Your doing great!",output, new Image(String.valueOf(getClass().
                        getResource("/Images/okAnimeGirl.png" ))));
                break;
            case CARD_IMPORT_SUCCESSFUL:
                output = "card imported successfully";
                UtilityController.makeAlert("Happy!!","Your doing great!",output, new Image(String.valueOf(getClass().
                        getResource("/Images/okAnimeGirl.png" ))));
                break;
            case CARD_ALREADY_EXISTS:
                output = "card with this name already exists";
                UtilityController.makeAlert("WTF!!","What are you doing?!",output, new Image(String.valueOf(getClass().
                        getResource("/Images/confusedAnimeGirl.jpg" ))));
                break;
        }
        System.out.println(output);
    }
}
