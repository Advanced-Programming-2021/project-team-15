package sample.model;

import java.io.Serializable;

public class Request  implements Serializable {
    private MessageEnum requestOrResponse;
    private String token;
    private int round;
    public Request(String token, MessageEnum requestOrResponse)
    {  this.token =token;
        this.requestOrResponse = requestOrResponse;
    }
    public MessageEnum getRequestOrResponse() {
        return requestOrResponse;
    }

    public void setRequestOrResponse(MessageEnum requestOrResponse) {
        this.requestOrResponse = requestOrResponse;
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }
    public enum MessageEnum implements Serializable{
        ACCEPT_FOR_GAME,
         REQUEST_FOR_GAME,
        NO_PLAYER_IS_ONLINE,
        REQUEST_ADDED_SUCCESSFULLY,
        REQUEST_REMOVED;
    }
}

