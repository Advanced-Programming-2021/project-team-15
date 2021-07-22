package sample.controller.menuController;

import sample.controller.ClientManager;
import sample.controller.responses.ChatMenuResponses;
import sample.controller.responses.ShopMenuResponses;
import sample.model.Message;
import sample.view.ChatMenu;

import java.util.ArrayList;
import java.util.HashMap;

public class ChatController extends MenuController{
    public ChatController() {
        super("Chat Menu");
    }

    public ChatMenuResponses refreshChatBox() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("class", "ChatController");
        map.put("method", "refresh");
        map.put("token",MainMenuController.getToken());
        Message.setAllMessages((ArrayList<Message>) ClientManager.sendAndGetResponse(map));
        return ChatMenuResponses.REFRESH_SUCCESSFUL;
    }

    public ChatMenuResponses sendText(String text) {
        text = text.replaceAll("nigga","***");
        HashMap<String, Object> map = new HashMap<>();
        map.put("class", "ChatController");
        map.put("method", "send");
        map.put("text",text);
        map.put("token",MainMenuController.getToken());
        String output = (String) ClientManager.sendAndGetResponse(map);
//        ChatMenu.getInstance().refreshChatBox();
        return ChatMenuResponses.valueOf(output);
    }
}
