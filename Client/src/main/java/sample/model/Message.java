package sample.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Message  implements Serializable {
    public static ArrayList<Message> getAllMessages() {
        return allMessages;
    }
    public static void setAllMessages(ArrayList<Message> allMessages) {
        Message.allMessages = allMessages;
    }
    private static ArrayList<Message> allMessages = new ArrayList<>();
    private static int idCounter = 0;
    private int id;
    private String sender;
    private String text;

    public Message(String sender, String text) {
        this.id = idCounter++;
        this.sender = sender;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
