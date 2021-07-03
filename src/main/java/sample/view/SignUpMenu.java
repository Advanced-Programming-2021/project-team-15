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
    private TextField nicknameTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private   TextField usernameTextField;
    public static SignUpMenu getInstance() {
        if (signUpMenu == null)
            signUpMenu= new SignUpMenu();
        return signUpMenu;
    }
    public void backButtonClicked(MouseEvent mouseEvent) throws IOException {
        Scene welcomeMenu= new Scene(FXMLLoader.load(getClass().getResource("/FxmlFiles/Welcome.fxml")));
        Main.stage.setScene(welcomeMenu);
    }

    public void submitButtonClicked(MouseEvent mouseEvent){
        if(usernameTextField.getText().equals("") || passwordTextField.getText().equals("") || nicknameTextField.getText().equals("")) {
            if(usernameTextField.getText().equals(""))
                usernameError.setText("username field is empty");
            if (passwordTextField.getText().equals(""))
                passwordError.setText("password field is empty");
            if (nicknameTextField.getText().equals(""))
                nicknameError.setText("nickname field is empty");
            return;
        }
        nicknameError.setText("");
        passwordError.setText("");
        usernameError.setText("");
        showResponse(loginController.registerUser(usernameTextField.getText(),nicknameTextField.getText(),passwordTextField.getText()));
        passwordTextField.setText("");
        usernameTextField.setText("");
        nicknameTextField.setText("");
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
