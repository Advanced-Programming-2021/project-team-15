package sample.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.model.User;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;

public class ScoreboardMenu {
    private static ScoreboardMenu scoreboardMenu;
    //  StringBuilder usersListStringBuilder;
//    ScoreboardController scoreboardController = new ScoreboardController();
    //  ScoreboardMenuResponses responses;
    //  private ScoreboardMenu() {
    //    super("Scoreboard Menu");
    //  }
    @FXML
    TableView<User> scoreBoardTable;
    public static ScoreboardMenu getInstance() {
        if (scoreboardMenu == null)
            scoreboardMenu = new ScoreboardMenu();
        return scoreboardMenu;
    }
    public void showScoreBoard(Stage stage) throws IOException {
        URL url = new File("src/main/resources/FxmlFiles/Scoreboard.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(Objects.requireNonNull(url));
        stage.setTitle("ScoreBoard");
        Scene scene = new Scene(root, 1920, 1000);

        stage.setScene(scene);
        stage.show();
        for(User user: User.getAllUsers()){
            int counter=0;
            for(User user1 : User.getAllUsers()){
                if(user.getScore()>= user1.getScore()){
                    counter++;
                }
            }
            user.setRank(User.getAllUsers().size()-counter+1);
        }

        scoreBoardTable = (TableView<User>) scene.lookup("#scoreBoardTable1");
        scoreBoardTable.getColumns().clear();
        TableColumn<User,String> rankColumn = new TableColumn<>("RANK");
        TableColumn<User, String> nameColumn = new TableColumn<>("USERNAME");
        TableColumn<User, Integer> scoreColumn = new TableColumn<>("SCORE");
        rankColumn.setCellValueFactory(new PropertyValueFactory<>("rank"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));
        scoreBoardTable.getColumns().addAll(rankColumn,nameColumn, scoreColumn);

        ArrayList<User> users = User.getAllUsers();

        Comparator<User> comparator = Comparator.comparing(User::getScore).reversed();

        users.sort(comparator);

        scoreBoardTable.getItems().addAll(users);
    }


    public void scoreBoardBackButtonClicked(MouseEvent mouseEvent) throws IOException {
        Scene mainMenuScene = new Scene(FXMLLoader.load(getClass().getResource("/FxmlFiles/MainMenu.fxml")));
        WelcomeMenu.stage.setScene(mainMenuScene);
    }
    //    @Override
//    public void scanInput() {
//        while (true) {
//            String input = UtilityController.getNextLine();
//            if (input.equals("scoreboard show")) checkAndCallShowScoreboard();
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
//    private void checkAndCallShowScoreboard() {
//        usersListStringBuilder = new StringBuilder();
//        responses = scoreboardController.showScoreboard(usersListStringBuilder);
//        if (responses == ScoreboardMenuResponses.SHOW_SCOREBOARD)
//            printResponse(responses);
//    }
//
//    private void printResponse(ScoreboardMenuResponses scoreboardMenuResponses) {
//        String output = "";
//        switch (scoreboardMenuResponses) {
//            case SHOW_SCOREBOARD:
//                output = usersListStringBuilder.toString();
//                break;
//            default:
//                break;
//        }
//        System.out.println(output);
//    }


}