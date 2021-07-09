package sample.view;

import sample.Main;
import sample.controller.menuController.LoginController;
import sample.controller.responses.LoginMenuResponses;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.controller.utilizationController.AudioController;

import java.io.IOException;

public class SignUpMenu {
    private static SignUpMenu signUpMenu;
    LoginController loginController = new LoginController();
    @FXML
    private Label passwordError;
    @FXML
    private Label usernameError;
    @FXML
    private Label success;
    @FXML
    private Label nicknameError;
    @FXML
    private TextField nicknameField;
    @FXML
    private TextField passwordField;
    @FXML
    private   TextField usernameField;
    public static SignUpMenu getInstance() {
        if (signUpMenu == null)
            signUpMenu= new SignUpMenu();
        return signUpMenu;
    }
    public void backButtonClicked(MouseEvent mouseEvent) throws IOException {
        AudioController.playClick();
        Scene welcomeMenu= new Scene(FXMLLoader.load(getClass().getResource("/FxmlFiles/Welcome.fxml")));
        Main.stage.setScene(welcomeMenu);
    }

     public void signupButtonClicked(MouseEvent mouseEvent){
         AudioController.playClick();
        if(usernameField.getText().equals("") || passwordField.getText().equals("") || nicknameField.getText().equals("")) {
            if(usernameField.getText().equals(""))
                usernameError.setText("username field is empty");
            if (passwordField.getText().equals(""))
                passwordError.setText("password field is empty");
            if (nicknameField.getText().equals(""))
                nicknameError.setText("nickname field is empty");
            return;
        }
        success.setText("");
        nicknameError.setText("");
        passwordError.setText("");
        usernameError.setText("");
        showResponse(loginController.registerUser(usernameField.getText(),nicknameField.getText(),passwordField.getText()));
        passwordField.setText("");
        usernameField.setText("");
        nicknameField.setText("");
        }

    private void showResponse(LoginMenuResponses loginMenuResponses) {
        String output = "";
        switch (loginMenuResponses) {
            case USER_CREATE_SUCCESSFUL:
                success.setText("user created successfully!");
                break;
            case USER_NICKNAME_ALREADY_EXISTS:
                nicknameError.setText("user with this nickname already exists");
                break;
            case USER_USERNAME_ALREADY_EXISTS:
                usernameError.setText("user with this username  already exists");
                break;
            case USER_USERNAME_PASSWORD_NOT_MATCHED:
                passwordError.setText("Username and password didn't match");
                break;
            default:
                break;
        }
        System.out.println(output);
    }
}
