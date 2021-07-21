package sample.controller.menuController;

import sample.controller.ClientManager;
import sample.controller.responses.DeckMenuResponses;
import sample.controller.responses.ScoreboardMenuResponses;
import sample.controller.responses.ShopMenuResponses;
import sample.model.User;

import java.io.IOException;
import java.util.*;

public class ScoreboardController extends MenuController {
    public ScoreboardController() {
        super("Scoreboard Menu");
    }

    public ScoreboardMenuResponses showScoreboard(StringBuilder usersListStringBuilder) {
        HashMap<String, Object> jsonObject = new HashMap<>();
        jsonObject.put("class", "ScoreboardController");
        jsonObject.put("method", "showScoreboard");
        jsonObject.put("users",usersListStringBuilder);
        jsonObject.put("token",MainMenuController.getToken());
        String output = (String) ClientManager.sendAndGetResponse(jsonObject);
        return ScoreboardMenuResponses.valueOf(output);
    }
//    public ScoreboardMenuResponses refreshScoreboard(StringBuilder usersListStringBuilder ){
//
//    }

}
