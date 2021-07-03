package sample.view;

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

import java.io.IOException;

public class SignUpMenu {
    private static SignUpMenu signUpMenu;
    LoginController loginController = new LoginController();
    LoginMenuResponses responses;
    @FXML
    private Label passwordError;
    @FXML
    private Label usernameError;
    @FXML
    private Label result;
    @FXML
    private TextField passwordTextField;
    @FXML
    private   TextField UsernameTextField;
    public static SignUpMenu getInstance() {
        if (signUpMenu == null)
            signUpMenu= new SignUpMenu();
        return signUpMenu;
    }
    public void backButtonClicked(MouseEvent mouseEvent) throws IOException {
        Scene welcomeMenu= new Scene(FXMLLoader.load(getClass().getResource("/FxmlFiles/Welcome.fxml")));
        Stage window= (Stage) ((Node)mouseEvent.getSource()).getScene().getWindow();
        window.setScene(welcomeMenu);
        window.show();
    }

    private void printResponse(LoginMenuResponses loginMenuResponses) {
        String output = "";
        switch (loginMenuResponses) {
            case USER_CREATE_SUCCESSFUL:
                output = "user created successfully!";
                break;
            case USER_NICKNAME_ALREADY_EXISTS:
                output = "user with this nickname already exists";
                break;
            case USER_USERNAME_ALREADY_EXISTS:
                output = "user with this username  already exists";
                break;
            case USER_LOGIN_SUCCESSFUL:
                output = "user logged in successfully!";
                break;
            case USER_USERNAME_PASSWORD_NOT_MATCHED:
                output = "Username and password didn't match";
                break;
            case USER_LOGOUT_SUCCESSFUL:
                output = "user logged out successfully!";
                break;
            case USER_REMOVE_SUCCESSFUL:
                output = "user removed successfully!";
                break;
            default:
                break;
        }
        System.out.println(output);
    }
}
