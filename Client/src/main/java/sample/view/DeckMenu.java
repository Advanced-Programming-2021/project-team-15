package sample.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Callback;
import sample.controller.menuController.DeckController;
import sample.controller.menuController.MenuController;
import sample.controller.responses.DeckMenuResponses;
import sample.model.Deck;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.function.UnaryOperator;

public class DeckMenu {
    private boolean first = true;
    private Deck selectedDeck;
    @FXML
    private ScrollPane mainCards;
    @FXML
    private ScrollPane sideCards;
    private static DeckMenu deckMenu;
    @FXML
    private Label activatedDeck;
    @FXML
    private Label mainNumber;
    @FXML
    private Label sideNumber;
    @FXML
    private Label validation;
    private DeckController deckController = new DeckController();

    public static DeckMenu getInstance() {
        if (deckMenu == null)
            deckMenu = new DeckMenu();
        return deckMenu;
    }
    private static ArrayList<Deck> sorted ;
   public void update()
   {   MenuController.getUserByToken();
       sorted = deckController.sortDecks(MenuController.getUser().getAllDecksOfUser());
   }
    private ListView<Deck> listView = null;
    @FXML
    private VBox leftSide;

    public void start() throws Exception {
        setSideCards();
        setMainCards();
        sorted =new ArrayList<>();
        sorted = deckController.sortDecks(MenuController.getUser().getAllDecksOfUser());
        activatedDeck.setText(MenuController.getUser().getActiveDeck().getName());
        ObservableList<Deck> decksList = FXCollections.observableArrayList();
        decksList.addAll(sorted);
        listView = new ListView<>();
        listView.setCellFactory((Callback<ListView<Deck>, ListCell<Deck>>) param -> {
            return new ListCell<Deck>() {
                @Override
                protected void updateItem(Deck deck, boolean empty) {
                    super.updateItem(deck, empty);

                    if (deck == null || empty) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        HBox root = new HBox();
                        root.setAlignment(Pos.CENTER_LEFT);
                        root.setPadding(new Insets(5, 10, 5, 10));
                        Label label = new Label(deck.getName());
                        label.setFont(new Font("Arial", 20));
                        root.getChildren().add(label);
                        Region region = new Region();
                        HBox.setHgrow(region, Priority.ALWAYS);
                        root.getChildren().add(region);
                        Button rmv = new Button("Remove");
                        rmv.setFont(new Font("Arial", 20));
                        rmv.setStyle("-fx-background-radius: 30");
                        rmv.setStyle("-fx-background-insets: 0,1,1");
                        rmv.setStyle("-fx-background-color:\n" +
                                "            linear-gradient(#ff3535, #ff0084),\n" +
                                "            radial-gradient(center 50% -40%, radius 200%, #ee6136 45%, #c82800 50%)");
                        rmv.setOnAction(event -> {
                            if (selectedDeck == deck) {
                                if (selectedDeck.isActive())
                                    activatedDeck.setText("");
                                selectedDeck = null;
                                cleanEveryThing();
                            }
                            listView.getItems().remove(deck);
                            update();
                            deckController.removeDeck(deck.getName());
                        });
                        Button edit = new Button("Edit");
                        edit.setFont(new Font("Arial", 20));
                        edit.setStyle("-fx-background-radius: 30");
                        edit.setStyle("-fx-background-insets: 0,1,1");
                        edit.setStyle("-fx-text-fill: black");
                        edit.setStyle(" -fx-background-color:\n" +
                                "            #c3c4c4,\n" +
                                "            linear-gradient(#d6d6d6 50%, white 100%),\n" +
                                "            radial-gradient(center 50% -40%, radius 200%, #e6e6e6 45%, rgba(230,230,230,0) 50%);");
                        edit.setOnAction(event -> {
                            try {MenuController.getUserByToken();
                                goToEditDeckMenu(deck);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
                        root.getChildren().addAll(rmv, edit);
                        setText(null);
                        setGraphic(root);
                    }

                }
            };

        });
        listView.setItems(decksList);
        leftSide.getChildren().add(listView);


        listView.getSelectionModel().selectedItemProperty()
                .addListener(new ChangeListener<Deck>() {
                    public void changed(ObservableValue<? extends Deck> ov,
                                        Deck old_val, Deck new_val) {
                        updateDeckDetails(new_val);
                        selectedDeck = new_val;
                        setSideCards();
                        setMainCards();

                    }
                });
    }

    public void setSideCards() {
        GridPane pane = new GridPane();
        pane.setPadding(new Insets(10, 10, 10, 20));
        sideCards.setContent(pane);
        pane.setAlignment(Pos.CENTER);
        pane.setVgap(10);
        pane.setHgap(10);
        if (selectedDeck == null || selectedDeck.getSideDeck().isEmpty())
            return;
        for (int i = 0; i < selectedDeck.getSideDeck().size(); i++) {
            Rectangle rectangle = new Rectangle();
            rectangle.setHeight(153.5);
            rectangle.setWidth(105.25);
            rectangle.setArcHeight(5);
            rectangle.setArcWidth(5);
            rectangle.setFill(new ImagePattern(selectedDeck.getSideDeck().get(i).getCardImage()));
            pane.add(rectangle, i, 0);
        }
    }

    public void setMainCards() {
        GridPane pane = new GridPane();
        pane.setPadding(new Insets(10, 10, 10, 20));
        mainCards.setContent(pane);
        pane.setAlignment(Pos.CENTER);
        pane.setVgap(10);
        pane.setHgap(10);
        if (selectedDeck == null || selectedDeck.getMainDeck().isEmpty())
            return;
        for (int i = 0; i < selectedDeck.getMainDeck().size(); i++) {
            Rectangle rectangle = new Rectangle();
            rectangle.setHeight(153.5);
            rectangle.setWidth(105.25);
            rectangle.setArcHeight(5);
            rectangle.setArcWidth(5);
            rectangle.setFill(new ImagePattern(selectedDeck.getMainDeck().get(i).getCardImage()));
            pane.add(rectangle, i, 0);
        }
    }


    private void cleanEveryThing() {
        mainNumber.setText("");
        sideNumber.setText("");
        validation.setText("");
        setSideCards();
        setMainCards();

    }

    public void goToEditDeckMenu(Deck deck) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FxmlFiles/DeckEdit.fxml"));
        Scene scene = new Scene(loader.load());
        WelcomeMenu.stage.setScene(scene);
        DeckEdit deckEdit = loader.getController();
        deckEdit.setDeck(deck);
        deckEdit.start();
    }


    public void activateButtonPressed(MouseEvent mouseEvent) {
        if (selectedDeck == null)
            new Alert(Alert.AlertType.ERROR, "you haven't selected any deck").show();
        deckController.setActiveDeck(selectedDeck.getName());
        activatedDeck.setText(selectedDeck.getName());
        update();
    }

    public void createDeck(MouseEvent mouseEvent) throws Exception {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("create deck");
        dialog.setContentText("please enter name for the new deck :");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            DeckMenuResponses deckMenuResponses = deckController.createDeck(result.get());
            System.out.println(result.get());
            if (deckMenuResponses.equals(DeckMenuResponses.DECK_NAME_ALREADY_EXISTS)) {
                new Alert(Alert.AlertType.ERROR, "this deck name already exists").show();
            } else if (deckMenuResponses.equals(DeckMenuResponses.DECK_CREATE_SUCCESSFUL)) {
                new Alert(Alert.AlertType.INFORMATION, "new deck created successfully!").show();
                listView.getItems().add(MenuController.getUser().getDeckByName(result.get()));
                update();
                listView.refresh();
                return;
            }
        }

    }

    public void backButtonClicked(MouseEvent mouseEvent) throws IOException {
        Scene mainMenuScene = new Scene(FXMLLoader.load(getClass().getResource("/FxmlFiles/MainMenu.fxml")));
        WelcomeMenu.stage.setScene(mainMenuScene);
    }


    public void updateDeckDetails(Deck newVal) {
        if (!listView.getItems().contains(newVal))
            return;
        mainNumber.setText("Main Deck : " + newVal.getMainDeck().size() + "");
        sideNumber.setText("Side Deck : " + newVal.getSideDeck().size() + "");
        if (newVal.isValid()) validation.setText("Valid");
        else validation.setText("InValid");
    }

}
