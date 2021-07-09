package sample.view;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import sample.Main;
import sample.controller.menuController.DeckController;
import sample.controller.menuController.MenuController;
import sample.controller.responses.DeckMenuResponses;
import sample.model.Deck;
import sample.model.cards.Card;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Objects;

import static sample.controller.responses.DeckMenuResponses.*;

public class DeckEdit {

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }
    private DeckController deckController = new DeckController();
    private Deck deck;
    private ArrayList<Card> userCards;
    private Card selectedCard;
    @FXML
    private ImageView chosenCard ;
    private Card[][]  trunkCards ;
    private Card[][]   mainCards ;
    private Card[]     sideCards;
    private int trunkRow ;
    private int mainRow ;
    @FXML
    private ScrollPane mainDeck;
    @FXML
    private ScrollPane sideDeck;
    @FXML
    private ScrollPane trunk;

    private static DeckEdit deckEdit;
    public static DeckEdit getInstance() {
        if (deckEdit == null)
          deckEdit = new DeckEdit();
        return deckEdit;
    }
    public void start(){
        selectedCard = null;
        userCards =  MenuController.getUser().getAllCardsOfUser();
        trunkRow = (int) Math.ceil( userCards.size()/ 6.0);
        mainRow  =  15;
        trunkCards =new Card[trunkRow][6];
        mainCards= new Card[mainRow][4];
        sideCards = new Card[15];
        chosenCard.setFitHeight(307);
        chosenCard.setFitWidth(210.5);
        readyUpTrunk();
        readyUpMainDeck();
        readyUpSideDeck();
    }

    public void readyUpTrunk(){
        GridPane pane = new GridPane();
        pane.setPadding(new Insets(10,10,10,20));
        trunk.setContent(pane);
        pane.setAlignment(Pos.CENTER);
        pane.setVgap(10);
        pane.setHgap(10);
        if(userCards.isEmpty())
            return;
        for (int i= 0 ; i< trunkRow;i++){
            for (int j =0 ; j<6 ; j++) {
                Rectangle rectangle = new Rectangle();
                rectangle.setHeight(153.5);
                rectangle.setWidth(105.25);
                rectangle.setArcHeight(5);
                rectangle.setArcWidth(5);
                if ((6 * i + j) < userCards.size()) {
                    trunkCards[i][j] = userCards.get((6 * i + j));
                    rectangle.setFill(new ImagePattern(userCards.get((6 * i + j)).getCardImage()));
                    setShowingCardImageProps(rectangle);
                } else {
                    trunkCards[i][j] = null;
                    rectangle.setFill(Color.valueOf("#0d253b7c"));
                }
                rectangle.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        int row = GridPane.getRowIndex(rectangle);
                        int column = GridPane.getColumnIndex(rectangle);
                        if (trunkCards[row][column] != null )
                        {
                            selectedCard = trunkCards[row][column];
                            selectedCard.setDeckViewLocation(Card.DeckViewLocation.TRUNK);
                            chosenCard.setImage(trunkCards[row][column].getCardImage());
                        }
                    }});
                pane.add(rectangle,j,i);
        }
      }
    }



    public void readyUpSideDeck(){
        GridPane pane = new GridPane();
        pane.setPadding(new Insets(10,10,10,20));
        sideDeck.setContent(pane);
        pane.setAlignment(Pos.CENTER);
        pane.setVgap(10);
        pane.setHgap(10);
        for (int i = 0  ; i < 15 ; i++)
        { Rectangle rectangle = new Rectangle();
            rectangle.setHeight(153.5);
            rectangle.setWidth(105.25);
            rectangle.setArcHeight(5);
            rectangle.setArcWidth(5);
            if ( i <deck.getSideDeck().size()) {
               sideCards[i] = deck.getSideDeck().get(i)  ;
                rectangle.setFill(new ImagePattern(sideCards[i].getCardImage()));
                setShowingCardImageProps(rectangle);
            } else {
                sideCards[i] = null;
                rectangle.setFill(Color.valueOf("#0d253b7c"));
            }
            rectangle.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    int column = GridPane.getColumnIndex(rectangle);
                    if (sideCards[column] != null )
                    {
                        selectedCard = sideCards[column];
                        selectedCard.setDeckViewLocation(Card.DeckViewLocation.SIDE_DECK);
                        chosenCard.setImage(sideCards[column].getCardImage());
                    }
                }});
            pane.add(rectangle,i , 0);
        }
        }






    public void readyUpMainDeck(){
        GridPane pane = new GridPane();
        pane.setPadding(new Insets(10,10,10,20));
        mainDeck.setContent(pane);
        pane.setAlignment(Pos.CENTER);
        pane.setVgap(10);
        pane.setHgap(10);
        for (int i= 0 ; i< mainRow ;i++){
            for (int j =0 ; j<4 ; j++) {
                Rectangle rectangle = new Rectangle();
                rectangle.setHeight(153.5);
                rectangle.setWidth(105.25);
                rectangle.setArcHeight(5);
                rectangle.setArcWidth(5);
                if ((4 * i + j) < deck.getMainDeck().size()) {
                   mainCards[i][j] =  deck.getMainDeck().get((4 * i + j));
                    rectangle.setFill(new ImagePattern( deck.getMainDeck().get((4 * i + j)).getCardImage()));
                    setShowingCardImageProps(rectangle);
                } else {
                   mainCards[i][j] = null;
                    rectangle.setFill(Color.valueOf("#0d253b7c"));
                }
                rectangle.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        int row = GridPane.getRowIndex(rectangle);
                        int column = GridPane.getColumnIndex(rectangle);
                        if (mainCards[row][column] != null )
                        {
                            selectedCard = mainCards[row][column];
                            selectedCard.setDeckViewLocation(Card.DeckViewLocation.MAIN_DECK);
                            chosenCard.setImage(mainCards[row][column].getCardImage());
                        }
                    }});
                pane.add(rectangle,j,i);
            }
        }
    }

    public void removeCard(MouseEvent mouseEvent){
        if(selectedCard==null)
            new Alert(Alert.AlertType.ERROR,"you haven't selected any card").show();
        else if(selectedCard.getDeckViewLocation()== Card.DeckViewLocation.TRUNK)
            new Alert(Alert.AlertType.ERROR,"you can't remove this card").show();
        else {
            Deck.DeckType type ;
            if(selectedCard.getDeckViewLocation()== Card.DeckViewLocation.MAIN_DECK)
                type = Deck.DeckType.MAIN;
             else type = Deck.DeckType.SIDE;
            deckController.removeCardFromDeck(selectedCard,deck.getName(),type);
            selectedCard.setDeckViewLocation(Card.DeckViewLocation.TRUNK);
            new Alert(Alert.AlertType.INFORMATION,"card removed successfully").show();
            if(type== Deck.DeckType.MAIN)  readyUpMainDeck();
            else readyUpSideDeck();
            readyUpTrunk();
        }
    }
    public void addCardToMain(){
        if(selectedCard==null)
            new Alert(Alert.AlertType.ERROR,"you haven't selected any card").show();
        else if(selectedCard.getDeckViewLocation()!= Card.DeckViewLocation.TRUNK)
            new Alert(Alert.AlertType.ERROR,"you must choose your card from trunk").show();
        else {
            DeckMenuResponses deckMenuResponses = deckController.addCardToDeck(selectedCard,deck.getName(), Deck.DeckType.MAIN);
            if(deckMenuResponses.equals(DECK_FULL))
                new Alert(Alert.AlertType.ERROR,"deck is full! you cant add card to this").show();
            else if(deckMenuResponses.equals(MAX_SIZE_IDENTICAL_CARDS_ALREADY_IN_DECK))
            new Alert(Alert.AlertType.ERROR,"there are already three cards of this type").show();
            else if(deckMenuResponses.equals(CARD_ADD_TO_DECK_SUCCESSFUL)) {
                selectedCard.setDeckViewLocation(Card.DeckViewLocation.MAIN_DECK);
                readyUpMainDeck();
                readyUpTrunk();
                new Alert(Alert.AlertType.INFORMATION, "card added to main deck successfully").show();
            }
        }
    }
    public void addCardToSide(){
        if(selectedCard==null)
            new Alert(Alert.AlertType.ERROR,"you haven't selected any card").show();
        else if(selectedCard.getDeckViewLocation()!= Card.DeckViewLocation.TRUNK)
            new Alert(Alert.AlertType.ERROR,"you must choose your card from trunk").show();
        else {
            DeckMenuResponses deckMenuResponses = deckController.addCardToDeck(selectedCard,deck.getName(), Deck.DeckType.SIDE);
            if(deckMenuResponses.equals(DECK_FULL))
                new Alert(Alert.AlertType.ERROR,"deck is full! you cant add card to this").show();
            else if(deckMenuResponses.equals(MAX_SIZE_IDENTICAL_CARDS_ALREADY_IN_DECK))
                new Alert(Alert.AlertType.ERROR,"there are already three cards of this type").show();
            else if(deckMenuResponses.equals(CARD_ADD_TO_DECK_SUCCESSFUL)) {
                selectedCard.setDeckViewLocation(Card.DeckViewLocation.SIDE_DECK);
                readyUpSideDeck();
                readyUpTrunk();
                new Alert(Alert.AlertType.INFORMATION, "card added to side deck successfully").show();
            }
        }
    }
    private void setShowingCardImageProps(Rectangle rectangle) {
       rectangle.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(rectangle.getFill()!=Color.valueOf("#0d253b7c"))
                {rectangle.setHeight(163);
                 rectangle.setWidth(110);}
            }
        });
       rectangle.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                rectangle.setHeight(153.5);
                rectangle.setWidth(105.25);
            }
        });
        rectangle.setCursor(Cursor.HAND);
        rectangle.setHeight(153.5);
        rectangle.setWidth(105.25);
    }


    public void backButtonClicked() throws Exception {     FXMLLoader loader = new FXMLLoader(getClass().getResource("/FxmlFiles/DeckMenu.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Main.stage.setScene(scene);
        DeckMenu deckMenu = loader.getController();
        deckMenu.start();

    }




    public ArrayList<Card> getUserCards() {
        return userCards;
    }

    public void setUserCards(ArrayList<Card> userCards) {
        this.userCards = userCards;
    }

    public Card getSelectedCard() {
        return selectedCard;
    }

    public void setSelectedCard(Card selectedCard) {
        this.selectedCard = selectedCard;
    }

    public ImageView getChosenCard() {
        return chosenCard;
    }

    public void setChosenCard(ImageView chosenCard) {
        this.chosenCard = chosenCard;
    }

    public Card[][] getTrunkCards() {
        return trunkCards;
    }

    public void setTrunkCards(Card[][] trunkCards) {
        this.trunkCards = trunkCards;
    }

    public ScrollPane getMainDeck() {
        return mainDeck;
    }

    public void setMainDeck(ScrollPane mainDeck) {
        this.mainDeck = mainDeck;
    }

    public ScrollPane getSideDeck() {
        return sideDeck;
    }

    public void setSideDeck(ScrollPane sideDeck) {
        this.sideDeck = sideDeck;
    }

    public ScrollPane getTrunk() {
        return trunk;
    }

    public void setTrunk(ScrollPane trunk) {
        this.trunk = trunk;
    }
}
