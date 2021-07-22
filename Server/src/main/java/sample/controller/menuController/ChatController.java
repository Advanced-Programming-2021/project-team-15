package sample.controller.menuController;

import sample.controller.responses.ChatMenuResponses;
import sample.model.Message;

import java.util.HashMap;

public class ChatController extends MenuController {
    public ChatController() {
        super("Chat Menu");
    }

    public ChatMenuResponses sendMessage(String text, String token) {
        Message message = new Message(MainMenuController.getUserByToken(token).getUserName(), text);
        Message.getAllMessages().add(message);
        return ChatMenuResponses.MESSAGE_SENT;
    }

    public Object callMethods(HashMap<String, Object> jsonObject) {
        ChatMenuResponses chatMenuResponses;
        MenuController.setUser(MainMenuController.getUserByToken((String) jsonObject.get("token")));
        switch ((String) jsonObject.get("method")) {
            case "refresh": {
                System.out.println(Message.getAllMessages());
                return Message.getAllMessages();
            }
            case "send":
                chatMenuResponses = sendMessage(
                        (String) jsonObject.get("text"), (String) jsonObject.get("token"));
                break;
            default:
                return "Something Happened!";
        }
        return chatMenuResponses.toString();
    }
}
