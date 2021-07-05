package sample.view;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import sample.controller.menuController.DeckController;
import sample.controller.menuController.MenuController;
import sample.model.cards.Card;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DeckEdit {


    private ArrayList<Card> userCards;
    private Card selectedCard;
    @FXML
    private ImageView chosenCard ;
    private Card[][]  trunkCards ;
    @FXML
    private ScrollPane mainDeck;
    @FXML
    private ScrollPane sideDeck;
    @FXML
    private ScrollPane trunk;
    private DeckController deckController = new DeckController();
    private static DeckEdit deckEdit;
    public static DeckEdit getInstance() {
        if (deckEdit == null)
          deckEdit = new DeckEdit();
        return deckEdit;
    }
    public void start(){
        userCards =  MenuController.getUser().getAllCardsOfUser();
        trunkCards =new Card[(int) Math.ceil( userCards.size()/ 6.0)][6];
        chosenCard = new ImageView();
        chosenCard.setFitHeight(307);
        chosenCard.setFitWidth(210.5);
        readyUpTrunk();
    }

    public void readyUpTrunk(){
        GridPane pane = new GridPane();
        pane.setPadding(new Insets(10,10,10,20));
        trunk.setContent(pane);
        pane.setAlignment(Pos.CENTER);
        pane.setVgap(10);
        pane.setHgap(10);
        for (int i= 0 ; i< (int) Math.ceil( userCards.size()/ 6.0);i++){
            for (int j =0 ; j<6 ; j++) {
                Rectangle rectangle = new Rectangle();
                rectangle.setHeight(153.5);
                rectangle.setWidth(105.25);
                rectangle.setArcHeight(5);
                rectangle.setArcWidth(5);
                if ((6 * i + j) < userCards.size()) {
                    trunkCards[i][j] = userCards.get((6 * i + j));
                    rectangle.setFill(new ImagePattern(userCards.get((6 * i + j)).getCardImage()));
                } else {
                    trunkCards[i][j] = null;
                    rectangle.setFill(Color.BLACK);
                }
                rectangle.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        int row = GridPane.getRowIndex(rectangle);
                        int column = GridPane.getColumnIndex(rectangle);
                        if (trunkCards[row][column] != null && rectangle.getFill() != Color.BLACK)
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

    }
    public void readyUpMainDeck(){

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
