package sample.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import sample.controller.gamePlayController.GamePlayController;
import sample.controller.utilizationController.UtilityController;

public class RockPaperScissors {
    private boolean second = false;
    private String firstPlayerMove = "";
    private String secondPlayerMove  = "";
    @FXML
    private Label turn;

    public void start()
    {   firstPlayerMove = "";
        secondPlayerMove  = "";
        second = false;
        turn.setText(GamePlayController.getInstance().getGame().getFirstPlayer().getUser().getNickName());
        UtilityController.makeAlert("Hi", "lets play!", "please choose rock, paper or scissors", new Image(String.valueOf(getClass().
            getResource("/Images/okAnimeGirl.png"))));
    }
    public boolean playRPS() {
       return  GamePlayController.getInstance().RPS(firstPlayerMove, secondPlayerMove);

    }


    public void submit()
    {
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
    public void submittedBySecondPlayer()
    {   if (secondPlayerMove.equals(""))
            UtilityController.makeAlert("Confused!!", "What are you doing?!", "you haven't select anything!", new Image(String.valueOf(getClass().
                    getResource("/Images/confusedAnimeGirl.jpg"))));
        else {
            if(!playRPS())
                start();
            else {
                UtilityController.makeAlert("Happy!!", "", "game started and the first player is " + GamePlayController.getInstance().getGame().getFirstPlayer().getUser().getNickName(), new Image(String.valueOf(getClass().
                        getResource("/Images/okAnimeGirl.png"))));
            }
        }
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
