package sample.controller.menuController;

import sample.model.Deck;
import sample.model.Request;
import sample.model.cards.Card;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import static sample.model.Request.MessageEnum.*;

public class LobbyMenu {
    private static LinkedBlockingQueue<Request> requests = new LinkedBlockingQueue<>();
public Object callMethods(HashMap<String, Object> commands) throws InterruptedException { switch ((String)commands.get("method")) {
    case "startGameRequest" :
              return startGameRequest((Request) commands.get("request"));
    case "addRequest" :
        return addRequest((Request) commands.get("request"));
    case "removeRequest":
        return removeRequest((Request) commands.get("request"));
    case "removeRequestsByToken":
       return removeRequestsByToken((String) commands.get("token"));
    default:
        return "Something Happened!";
}
}
public Request.MessageEnum addRequest(Request request) throws InterruptedException {
    requests.add(request);
    return REQUEST_ADDED_SUCCESSFULLY;
}
    public Request.MessageEnum removeRequest(Request request)
    {   requests.remove(request);
        return REQUEST_REMOVED;
    }
    public Request.MessageEnum removeRequestsByToken(String token)
    {   for(Request req : requests)
    { if(req.getToken().equals(token));
        requests.remove(req);
    }
        return  REQUEST_REMOVED;
    }


    public  Object startGameRequest(Request request)
{  HashMap<String, Object> commands = new HashMap<>();
    commands.put("message",NO_PLAYER_IS_ONLINE);
    Thread search = new Thread(() -> {
            for(Request req : requests) {
                if (request.getRound() == req.getRound() && !request.getToken().equals(req.getToken()) ) {
                    System.out.println(req.getRound()+" == "+request.getRound()+" with "+req.getToken()+" , "+request.getToken());
                    commands.put("first", MainMenuController.getActiveUsers().get(request.getToken()));
                    commands.put("second",MainMenuController.getActiveUsers().get(req.getToken()));
                    commands.put("round",req.getRound());
                    commands.put("message",ACCEPT_FOR_GAME);
                    requests.remove(req);
                    requests.remove(request);
                    break;
                }
            }
});
    search.start();

        return commands;
}









}
