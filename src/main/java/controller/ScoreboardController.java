package controller;

import controller.responses.ScoreboardMenuResponses;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.User;

import java.net.URL;
import java.util.*;

import static javafx.scene.control.TableView.CONSTRAINED_RESIZE_POLICY;

public class ScoreboardController extends MenuController implements Initializable {
    @FXML
    private TableView<User> table;
    @FXML
    private TableColumn<User,String> Rank;
    @FXML
    private TableColumn<User,String> Player;
    @FXML
    private TableColumn<User,String> Score;
    public ScoreboardController(String menuName) {
        super(menuName);
    }

    public ScoreboardMenuResponses showScoreboard(StringBuilder usersListStringBuilder) {
        jsonController.refreshUsersFromFileJson();
        Map<Integer, ArrayList<User>> sortedUsers = sortUsersByScore(User.getAllUsers());
        int rank = 1;
        int fakeRank=0;
        for (Integer score : sortedUsers.keySet()) {
            for (User user : sortedUsers.get(score)) {
                usersListStringBuilder.append("\n").append(rank).append("- ")
                        .append(user.getNickName()).append(" : ").append(score);
                fakeRank++;
            }
            rank+=fakeRank;
            fakeRank=0;
        }
        return ScoreboardMenuResponses.SHOW_SCOREBOARD;
    }

    public Map<Integer, ArrayList<User>> sortUsersByScore(ArrayList<User> users) {
        ArrayList<Integer> scores = new ArrayList<>();
        for (User user : users) {
            if (!scores.contains(user.getScore()))
                scores.add(user.getScore());
        }
        users.sort(Comparator.comparing(User::getNickName));
        TreeMap<Integer, ArrayList<User>> sortingUsers = new TreeMap<>();
        for (Integer score : scores) {
            ArrayList<User> tempUsersWithScore = new ArrayList<>();
            for (User user : users)
                if (user.getScore() == score)
                    tempUsersWithScore.add(user);
            sortingUsers.put(score, tempUsersWithScore);
        }
        return sortingUsers.descendingMap();
    }
    public ArrayList<User> sortingScores(){
        ArrayList<User> players=new ArrayList<>();
        Comparator<User> sortedScores=Comparator.comparing(User::getScore);
        players.sort(sortedScores);
        return players;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ObservableList<User> players = FXCollections.observableArrayList(sortingScores());
        table.setItems(players);

        table.setColumnResizePolicy(CONSTRAINED_RESIZE_POLICY);
        Stage stage = new Stage();
        stage.setScene(new Scene(table));
        stage.setMaxHeight(100);
        stage.setMaxWidth(200);
        stage.show();
    }
}
