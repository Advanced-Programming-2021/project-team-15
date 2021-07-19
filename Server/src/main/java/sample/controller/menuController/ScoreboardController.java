package sample.controller.menuController;

import sample.controller.responses.ScoreboardMenuResponses;
import sample.model.User;

import java.util.*;

public class ScoreboardController extends MenuController {
    public ScoreboardController() {
        super("Scoreboard Menu");
    }

    public ScoreboardMenuResponses showScoreboard(StringBuilder usersListStringBuilder) {
        databaseController.refreshUsersFromFileJson();
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
}
