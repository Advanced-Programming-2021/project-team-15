package sample.view;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.layout.*;
import javafx.util.Duration;
import sample.controller.ClientManager;
import sample.controller.menuController.ChatController;
import sample.controller.menuController.MenuController;
import sample.model.Message;


public class ChatMenu {
    @FXML
    private AnchorPane father;
    @FXML
    private ScrollPane chatSP;
    @FXML
    private TextField typeTF;
    private static ChatMenu chatMenu;
    private ChatController chatController = new ChatController();
    public static ChatMenu getInstance() {
        if (chatMenu ==null)
            chatMenu = new ChatMenu();
        return chatMenu;
    }

    public void init() {
        father.getScene().getAccelerators().put(new KeyCodeCombination(KeyCode.ENTER), () -> {
            if (typeTF.getText().equals("")) return;
            addTextToChatBox(typeTF.getText());
        });
        KeyFrame keyFrame = new KeyFrame(new Duration(500) ,actionEvent -> {
            refreshChatBox();
        });
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(keyFrame);
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public void refreshChatBox() {
        System.out.println(chatController.refreshChatBox());
        GridPane gridPane = new GridPane();
        int i=0;
        System.out.println(Message.getAllMessages().size());
        for (Message message : Message.getAllMessages()) {
            Label label = new Label();
            label.setText(message.getSender()+":\n"+message.getText());
            gridPane.add(label,0,i);
            i++;
        }
        chatSP.setContent(gridPane);
    }
    public void addTextToChatBox(String text) {
        System.out.println(chatController.sendText(text));
    }
}
