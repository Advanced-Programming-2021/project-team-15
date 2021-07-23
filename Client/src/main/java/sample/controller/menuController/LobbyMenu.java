package sample.controller.menuController;

import sample.controller.ClientManager;
import sample.model.Request;

import java.util.ArrayList;
import java.util.HashMap;

public class LobbyMenu {
    private static Request request;
public static Object sendStartGameRequest(String token,int num){
    request =  new Request(token, Request.MessageEnum.REQUEST_FOR_GAME);
    request.setRound(num);
    HashMap<String,Object> jsonObject = new HashMap<>();
    jsonObject.put("method","addRequest");
    jsonObject.put("class","LobbyMenu");
    jsonObject.put("request",request);
    jsonObject.put("token",token);
    Object object  = ClientManager.sendAndGetResponse(jsonObject);
     return object;
}
    public static Object startGameAndWait(){
        HashMap<String,Object> jsonObject = new HashMap<>();
        jsonObject.put("method","startGameRequest");
        jsonObject.put("class","LobbyMenu");
        jsonObject.put("request",request);
        jsonObject.put("token",MainMenuController.getToken());
        Object object  = ClientManager.sendAndGetResponse(jsonObject);
        return object;
    }
    public static Object removeRequestsByToken(){
        HashMap<String,Object> jsonObject = new HashMap<>();
        jsonObject.put("method","removeRequestsByToken");
        jsonObject.put("class","LobbyMenu");
        jsonObject.put("token",MainMenuController.getToken());
        Object object  = ClientManager.sendAndGetResponse(jsonObject);
        return object;
    }


    public static Request getRequest() {
        return request;
    }

    public static void setRequest(Request request) {
        LobbyMenu.request = request;
    }
}
