package sample.view;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import sample.controller.menuController.MenuController;
import sample.model.cards.Card;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DeckEdit {
    ArrayList<Card> userCards;
    @FXML
    private ScrollPane mainDeck;
    @FXML
    private ScrollPane sideDeck;
    @FXML
    private ScrollPane trunk;

    public void start(){
        userCards =  MenuController.getUser().getAllCardsOfUser();
    }

    public void readyUpTrunk(){
        GridPane pane = new GridPane();
        pane.setPadding(new Insets(10,10,10,20));
        trunk.setContent(pane);
        pane.setAlignment(Pos.CENTER);
        pane.setVgap(10);
        pane.setHgap(10);
        for (int i= 0 ; i< (int) Math.ceil( userCards.size()/ 6.0);i++){
            for (int j =0 ; j<6 ; j++)
            {   Rectangle rectangle = new Rectangle();
                rectangle.setHeight(153.5);
                rectangle.setWidth(105.25);
                rectangle.setArcHeight(5);
                rectangle.setArcWidth(5);
                if((6 * i + j)< userCards.size())
                { rectangle.setFill(new ImagePattern(userCards.get((6 * i + j)).getCardImage()));
                } else rectangle.setFill(Color.BLACK);



            }
        }


//        rectangle.setOnMouseClicked(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                selectedCard = rectangle;
//                selectedCard.setDeckViewLocation(DeckViewLocation.MAIN_DECK);
//                for (Node child : pane.getChildren()) {
//                    child.getStyleClass().remove("butoo");
//                }
//                rectangle.getStyleClass().add("butoo");
//                Rectangle rectangle1 = new Rectangle(250, 450);
//                rectangle1.setFill(selectedCard.getCard().getCardImage());
//                imageBar.getChildren().clear();
//                imageBar.getChildren().add(rectangle1);
//            }
//        });
//        pane.add(rectangle, j, i);
    }
    public void readyUpSideDeck(){

    }
    public void readyUpMainDeck(){

    }













}
