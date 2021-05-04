package view;

public class ScoreboardMenu extends Menu {
    private static ScoreboardMenu scoreboardMenu;
    private ScoreboardMenu() {
        super("Scoreboard Menu");
    }
    public static ScoreboardMenu getInstance() {
        if(scoreboardMenu==null)
            scoreboardMenu = new ScoreboardMenu();
        return scoreboardMenu;
    }


    @Override
    public void scanInput() {

    }
}
