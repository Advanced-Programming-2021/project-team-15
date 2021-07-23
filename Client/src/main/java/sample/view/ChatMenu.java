package sample.view;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import sample.controller.menuController.ChatController;
import sample.controller.menuController.MenuController;
import sample.controller.responses.ChatMenuResponses;
import sample.controller.utilizationController.AudioController;
import sample.model.Message;

import java.io.IOException;


public class ChatMenu {
    private static ChatMenu chatMenu;
    @FXML
    private AnchorPane father;
    @FXML
    private ScrollPane chatSP;
    @FXML
    private TextField typeTF;
    @FXML
    private Label onlineLabel;
    private ChatController chatController = new ChatController();

    public static ChatMenu getInstance() {
        if (chatMenu == null)
            chatMenu = new ChatMenu();
        return chatMenu;
    }

    public void init() {
        chatController.enter();
        chatSP.setOnMouseEntered(mouseEvent -> chatSP.setOpacity(1));
        chatSP.setOnMouseExited(mouseEvent -> chatSP.setOpacity(0.3));
        father.getScene().getAccelerators().put(new KeyCodeCombination(KeyCode.ENTER), () -> {
            if (typeTF.getText().equals("")) return;
            addTextToChatBox(typeTF.getText());
        });
        KeyFrame keyFrame = new KeyFrame(new Duration(1000), actionEvent -> {
            refreshChatBox();
        });
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(keyFrame);
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public void refreshChatBox() {
        onlineLabel.setText("Online Users : " + chatController.online());
        if (chatController.refreshChatBox() == ChatMenuResponses.NO_CHANGE) return;
        GridPane gridPane = new GridPane();
        gridPane.setVgap(20);
        gridPane.setHgap(400);
        gridPane.getStylesheets().add(
                ChatMenu.class.getResource("/CSSFiles/Menus.css").toExternalForm());
        gridPane.getStyleClass().add("chatGrid");
        int i = 0;
        System.out.println(Message.getAllMessages().size());
        for (Message message : Message.getAllMessages()) {
            Label label = new Label();
            label.setText("[" + message.getSender() + "]" + ":\n  " + message.getText());
            label.getStylesheets().add(
                    ChatMenu.class.getResource("/CSSFiles/Menus.css").toExternalForm());
            label.getStyleClass().add("chatLabel");
            if (message.getSender().equals(MenuController.getUser().getUserName()))
                gridPane.add(label, 1, i);
            else gridPane.add(label, 0, i);
            i++;
        }
        chatSP.setContent(gridPane);
    }

    public void addTextToChatBox(String text) {
        chatController.sendText(text);
        typeTF.setText("");
    }

    public void backButtonClicked(MouseEvent mouseEvent) throws IOException {
        AudioController.playClick();
        chatController.exit();
        Scene mainMenuScene = new Scene(FXMLLoader.load(getClass().getResource("/FxmlFiles/GameMenu.fxml")));
        WelcomeMenu.stage.setScene(mainMenuScene);
    }
}
