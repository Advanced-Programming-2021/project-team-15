package sample.controller.menuController;

import sample.controller.responses.ChatMenuResponses;
import sample.model.Message;

import java.util.HashMap;

public class ChatController extends MenuController {
    public ChatController() {
        super("Chat Menu");
    }
    private static HashMap<String,Boolean> onlineUsersSeen = new HashMap<>();

    public ChatMenuResponses sendMessage(String text, String token) {
        Message message = new Message(MainMenuController.getUserByToken(token).getUserName(), text);
        Message.getAllMessages().add(message);
        onlineUsersSeen.replaceAll((u, v) -> false);
        return ChatMenuResponses.MESSAGE_SENT;
    }

    public Object callMethods(HashMap<String, Object> jsonObject) {
        ChatMenuResponses chatMenuResponses;
        MenuController.setUser(MainMenuController.getUserByToken((String) jsonObject.get("token")));
        switch ((String) jsonObject.get("method")) {
            case "enter" : {
                onlineUsersSeen.put((String) jsonObject.get("token"),false);
                return "Welcome";
            }
            case "exit" : {
                onlineUsersSeen.remove((String) jsonObject.get("token"));
                return "Bye";
            }
            case "refresh": {
                if (onlineUsersSeen.get((String) jsonObject.get("token"))) return null;
                else {
                    onlineUsersSeen.put((String) jsonObject.get("token"), true);
                    return Message.getAllMessages();
                }
            }
            case "online" : return onlineUsersSeen.size();
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
