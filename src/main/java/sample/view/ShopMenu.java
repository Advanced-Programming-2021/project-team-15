package sample.view;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import sample.controller.menuController.ShopController;
import sample.controller.responses.ShopMenuResponses;
import sample.model.cards.Card;

public class ShopMenu {
    private static ShopMenu shopMenu;
    ShopMenuResponses responses;
    String allCards;
    private ShopController shopController = new ShopController();
    private int maximumCardsInRow = 5;

//    private ShopMenu() {
//        super("Shop Menu");
//    }
    @FXML
    public ScrollPane cardsContainer;
    @FXML
    private Label dalam;

    public static ShopMenu getInstance() {
        if (shopMenu == null)
            shopMenu = new ShopMenu();
        return shopMenu;
    }

//    @Override
//    public void start(Stage stage) throws Exception {
//        initializeContainer();
//    }

    public void initializeContainer() {
        int rowsCount = Card.getAllCards().size() / maximumCardsInRow + 1;
        GridPane cardsGridPane = new GridPane();
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
        for (int i = 0; i < rowsCount-1; i++) {
            for (int j = 0; j < maximumCardsInRow; j++) {
                Image cardImage = Card.getAllCards().get(i * maximumCardsInRow + j).getCardImage();
//              Image cardImage = new Image(String.valueOf(getClass().getResource("/Images/mamal.jpg")));
                ImageView showingCardImage = new ImageView(cardImage);
                showingCardImage.setFitWidth(200);
                showingCardImage.setFitHeight(200);
                cardsGridPane.add(showingCardImage,j,i);
            }
        }
        cardsContainer.setContent(cardsGridPane);
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
                output = "bought item successfully!";
                break;
            case CARD_NAME_NOT_EXIST:
                output = "there is no card with this name";
                break;
            case USER_MONEY_NOT_ENOUGH:
                output = "not enough money";
                break;
            default:
                break;
        }
        System.out.println(output);
    }


}
