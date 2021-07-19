package sample.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import sample.controller.gamePlayController.GamePlayController;
import sample.controller.utilizationController.UtilityController;

import java.io.IOException;

public class RockPaperScissors {
    private boolean second = false;
    private String firstPlayerMove = "";
    private String secondPlayerMove  = "";
    @FXML
    private Label turn;
    private static  RockPaperScissors rockPaperScissors= null;
    public static RockPaperScissors getInstance(){
     if(rockPaperScissors==null)
     {  rockPaperScissors = new RockPaperScissors();
         return  rockPaperScissors;
     }
     else return  rockPaperScissors;
    }
    public void start()
    {   firstPlayerMove = "";
        secondPlayerMove  = "";
        second = false;
        turn.setText(GamePlayController.getInstance().getGame().getFirstPlayer().getUser().getNickName());
        UtilityController.makeAlert("Hi", "lets play!", "please choose rock, paper or scissors", new Image(String.valueOf(getClass().
            getResource("/Images/okAnimeGirl.png"))));
    }
    public boolean playRPS() throws IOException {
       return  GamePlayController.getInstance().RPS(firstPlayerMove, secondPlayerMove);

    }


    public void submit() throws IOException {
        if(!second)
            submittedByFirstPlayer();
        else submittedBySecondPlayer();
    }

    public void submittedByFirstPlayer() {
        if (firstPlayerMove.equals(""))
            UtilityController.makeAlert("Confused!!", "What are you doing?!", "you haven't select anything!", new Image(String.valueOf(getClass().
                    getResource("/Images/confusedAnimeGirl.jpg"))));
        else {
            second = true;
            turn.setText(GamePlayController.getInstance().getGame().getSecondPlayer().getUser().getNickName());
            UtilityController.makeAlert("Hi", "lets play!", "please choose rock, paper or scissors", new Image(String.valueOf(getClass().
                    getResource("/Images/okAnimeGirl.png"))));

        }
    }
    public void submittedBySecondPlayer() throws IOException {   if (secondPlayerMove.equals(""))
            UtilityController.makeAlert("Confused!!", "What are you doing?!", "you haven't select anything!", new Image(String.valueOf(getClass().
                    getResource("/Images/confusedAnimeGirl.jpg"))));
        else {
            if(!playRPS())
                start();
        }
    }
    public void startNewGame() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FxmlFiles/Duel.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        WelcomeMenu.stage.setScene(scene);
        DuelMenu duelMenu = loader.getController();
        GamePlayController.getInstance().setDuelMenu(duelMenu);
        duelMenu.initialGame();

    }
    public void scissorsButtonClicked(MouseEvent mouseEvent)
    {   if(second)
        secondPlayerMove = "scissors";
        else  firstPlayerMove = "scissors";
    }
    public void paperButtonClicked(MouseEvent  mouseEvent)
    { if(second)
        secondPlayerMove = "paper";
    else  firstPlayerMove = "paper";
    }
    public void rockButtonClicked(MouseEvent mouseEvent)
    { if(second)
        secondPlayerMove = "rock";
    else  firstPlayerMove = "rock";
    }

}
