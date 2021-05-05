package controller;

import controller.responses.ScoreboardMenuResponses;
import model.User;

import java.util.*;

public class ScoreboardController extends MenuController {
    public ScoreboardController(String menuName) {
        super(menuName);
    }

    public ScoreboardMenuResponses showScoreboard(StringBuilder usersListStringBuilder) {
        jsonController.refreshUsersFromFileJson();
        TreeMap<Integer, ArrayList<User>> sortedUsers = sortUsersByScore();
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

    private TreeMap<Integer, ArrayList<User>> sortUsersByScore() {
        ArrayList<User> users = new ArrayList<>(User.getAllUsers());
        ArrayList<Integer> scores = new ArrayList<>();
        for (User user : users) {
            if (!scores.contains(user.getScore()))
                scores.add(user.getScore());
        }
        users.sort(Comparator.comparing(User::getNickName));
        TreeMap<Integer, ArrayList<User>> sortedUsers = new TreeMap<>();
        for (Integer score : scores) {
            ArrayList<User> tempUsersWithScore = new ArrayList<>();
            for (User user : users)
                if (user.getScore() == score)
                    tempUsersWithScore.add(user);
            sortedUsers.put(score, tempUsersWithScore);
        }
        return sortedUsers;
    }
}
