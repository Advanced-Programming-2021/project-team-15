package sample.view;

import com.opencsv.exceptions.CsvValidationException;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import sample.controller.menuController.MenuController;
import sample.controller.menuController.ShopController;
import sample.controller.responses.ShopMenuResponses;
import sample.controller.utilizationController.AudioController;
import sample.controller.utilizationController.UtilityController;
import sample.model.cards.Card;

import java.io.IOException;
import java.util.Objects;

public class ShopMenu {
    private static ShopMenu shopMenu;
    private final int maximumCardsInRow = 5;
    //    private ShopMenu() {
//        super("Shop Menu");
//    }
    @FXML
    public ScrollPane cardsContainer;
    @FXML
    public Label cardName;
    @FXML
    public Label moneyLabel;
    @FXML
    public Label cardCount;
    @FXML
    public Label priceLabel;
    @FXML
    public VBox buyCardVBox;
    @FXML
    public Button buyCardButton;

    ShopMenuResponses responses;
    String allCards;
    private ShopController shopController = new ShopController();
    private boolean isBuyPossible = false;
    private Card toBuyCard;
    @FXML
    private ImageView cardImage;

    public static ShopMenu getInstance() {
        if (shopMenu == null)
            shopMenu = new ShopMenu();
        return shopMenu;
    }

    public void initializeContainer() {
        int rowsCount = Card.getAllCards().size() / maximumCardsInRow + 1;
        GridPane cardsGridPane = new GridPane();
        setCardsGridPaneProps(cardsGridPane);
        for (int i = 0; i < rowsCount; i++) {
            for (int j = 0; j < maximumCardsInRow; j++) {
                if (i * maximumCardsInRow + j >= Card.getAllCards().size()) break;
                Image cardImage = Card.getAllCards().get(i * maximumCardsInRow + j).getCardImage();
                ImageView showingCardImage = new ImageView(cardImage);
                setShowingCardImageProps(showingCardImage);
                cardsGridPane.add(showingCardImage, j, i);
            }
        }
        cardsContainer.setBackground(Background.EMPTY);
        cardsContainer.setContent(cardsGridPane);
    }

    private void setShowingCardImageProps(ImageView showingCardImage) {
        showingCardImage.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                showingCardImage.setFitHeight(170);
                showingCardImage.setFitWidth(170);
            }
        });
        showingCardImage.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                showingCardImage.setFitWidth(160);
                showingCardImage.setFitHeight(160);
            }
        });
        showingCardImage.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                selectCard(Objects.requireNonNull(Card.getCardByImage(showingCardImage.getImage())));
            }
        });
        showingCardImage.setFitWidth(160);
        showingCardImage.setFitHeight(160);
    }

    private void selectCard(Card selectedCard) {
        toBuyCard = selectedCard;
        cardName.setText(selectedCard.getCardName());
        cardImage.setImage(selectedCard.getCardImage());
        cardCount.setText(String.valueOf(MenuController.getUser().getUserSpecificCardCount(selectedCard)));
        int money = MenuController.getUser().getMoney();
        moneyLabel.setText(String.valueOf(money));
        priceLabel.setText(String.valueOf(selectedCard.getPrice()));
        if (money>=selectedCard.getPrice()) activateBuyButton(buyCardButton);
        else inactivateBuyButton(buyCardButton);
    }

    private void activateBuyButton(Button buyButton) {
        buyButton.setCursor(Cursor.HAND);
        buyButton.setOpacity(1);
        buyButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    AudioController.playClick();
                    printResponse(shopController.buyItem(toBuyCard.getCardName()));
                    int money = MenuController.getUser().getMoney();
                    moneyLabel.setText(String.valueOf(money));
                    priceLabel.setText(String.valueOf(toBuyCard.getPrice()));
                } catch (IOException | CsvValidationException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void inactivateBuyButton(Button buyButton) {
        buyButton.setCursor(Cursor.DEFAULT);
        buyButton.setOpacity(0.5);
        buyButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                printResponse(ShopMenuResponses.USER_MONEY_NOT_ENOUGH);
            }
        });
    }

    private void setCardsGridPaneProps(GridPane cardsGridPane) {
        cardsGridPane.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                cardsContainer.setOpacity(1);
            }
        });
        cardsGridPane.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                cardsContainer.setOpacity(0.5);
            }
        });
        cardsGridPane.setCursor(Cursor.HAND);
        cardsGridPane.setBackground(Background.EMPTY);
        cardsGridPane.setHgap(5);
        cardsGridPane.setVgap(5);
    }

    public void backButtonClicked(MouseEvent mouseEvent) throws IOException {
        AudioController.playClick();
        Scene mainMenuScene = new Scene(FXMLLoader.load(getClass().getResource("/FxmlFiles/MainMenu.fxml")));
        WelcomeMenu.stage.setScene(mainMenuScene);
    }
//    @Override
//    public void scanInput() throws IOException, CsvValidationException {
//        while (true) {
//            String input = UtilityController.getNextLine();
//            if (input.equals("menu exit")) checkAndCallMenuExit();
//            else if (input.startsWith("shop buy")) checkAndCallBuyItem(input);
//            else if (input.equals("shop show --all")) checkAndCallShowAllCards();
//            else if (input.startsWith("card show ")) UtilityController.showCardByName(input);
//            else if (regexController.showMenuRegex(input))
//                checkAndCallShowCurrentMenu();
//            else if (input.startsWith("menu enter ")) System.out.println("Navigation is not possible hear");
//            else System.out.println("invalid command");
//            if (super.isExit) {
//                super.isExit = false;
//                return;
//            }
//        }
//    }

//    private void checkAndCallBuyItem(String input) throws IOException, CsvValidationException {
//        HashMap<String, String> enteredDetails = new HashMap<>();
//        if (!regexController.buyItemRegex(input, enteredDetails))
//            System.out.println("invalid command");
//        else {
//            String cardName = enteredDetails.get("name");
//            responses = shopController.buyItem(cardName);
//            printResponse(responses);
//        }
//    }
//
//    private void checkAndCallShowAllCards() throws IOException, CsvValidationException {
//        HashMap<String, String> enteredDetails = new HashMap<>();
//        responses = shopController.showAllCards(enteredDetails);
//        if (responses == ShopMenuResponses.SHOP_SHOW_ALL)
//            allCards = enteredDetails.get("allCards");
//        printResponse(responses);
//    }

    private void printResponse(ShopMenuResponses shopMenuResponses) {
        String output = "";
        switch (shopMenuResponses) {
            case SHOP_SHOW_ALL:
                output = allCards;
                break;
            case BUY_SUCCESSFUL:
                output = "Bought item successfully!";
                UtilityController.makeAlert("Happy!!","You're doing great!",output, new Image(String.valueOf(getClass().
                        getResource("/Images/okAnimeGirl.png" ))));
                break;
            case CARD_NAME_NOT_EXIST:
                output = "There is no card with this name";
                UtilityController.makeAlert("Confused!!","What are you doing?!",output, new Image(String.valueOf(getClass().
                        getResource("/Images/confusedAnimeGirl.jpg" ))));
                break;
            case USER_MONEY_NOT_ENOUGH:
                output = "Not enough money";
                UtilityController.makeAlert("Sad!!","You are so bad!",output, new Image(String.valueOf(getClass().
                        getResource("/Images/sadAnimeGirl.jpg" ))));
                break;
            default:
                break;
        }
    }

//    private void UtilityController.makeAlert(String title,String header, String context, Image graphic) {
//        ImageView imageView = new ImageView(graphic);
//        imageView.setFitHeight(80);
//        imageView.setFitWidth(80);
//        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//        alert.getDialogPane().getStylesheets().add(
//                getClass().getResource("/CSSFiles/Dialogs.css").toExternalForm());
//        alert.getDialogPane().getStyleClass().add("dialog-pane");
//        alert.setTitle(title);
//        alert.setHeaderText(header);
//        alert.setContentText(context);
//        alert.setGraphic(imageView);
//        alert.setWidth(550);
//        alert.setHeight(250);
//        alert.show();
//    }

}
