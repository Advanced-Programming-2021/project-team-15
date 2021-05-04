package view;

import controller.ScoreboardController;
import controller.responses.ScoreboardMenuResponses;

import java.util.Scanner;

public class ScoreboardMenu extends Menu {
    private static ScoreboardMenu scoreboardMenu;
    StringBuilder usersListStringBuilder;
    ScoreboardController scoreboardController = new ScoreboardController("Scoreboard Menu");
    ScoreboardMenuResponses responses;
    private ScoreboardMenu() {
        super("Scoreboard Menu");
    }

    public static ScoreboardMenu getInstance() {
        if (scoreboardMenu == null)
            scoreboardMenu = new ScoreboardMenu();
        return scoreboardMenu;
    }

    @Override
    public void scanInput() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();
            if (input.equals("scoreboard show")) checkAndCallShowScoreboard();
            else if (input.equals("menu exit")) checkAndCallMenuExit();
            else if (regexController.showMenuRegex(input)) checkAndCallShowCurrentMenu();
            else System.out.println("invalid command");
            if (super.isExit) {
                super.isExit = false;
                return;
            }
        }
    }

    private void checkAndCallShowScoreboard() {
        usersListStringBuilder = new StringBuilder();
        responses = scoreboardController.showScoreboard(usersListStringBuilder);
        if (responses == ScoreboardMenuResponses.SHOW_SCOREBOARD)
            printResponse(responses);
    }

    private void printResponse(ScoreboardMenuResponses scoreboardMenuResponses) {
        String output = "";
        switch (scoreboardMenuResponses) {
            case SHOW_SCOREBOARD:
                output = usersListStringBuilder.toString();
                break;
            default:
                break;
        }
        System.out.println(output);
    }
}
