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

public class LoginMenu  {
    private static LoginMenu loginMenu;
    LoginController loginController = new LoginController();
    @FXML
    private Label passwordError;
    @FXML
    private Label usernameError;
    @FXML
    private Label result;
    @FXML
    private TextField passwordTextField;
    @FXML
    private   TextField  usernameTextField;
    public static LoginMenu getInstance() {
        if (loginMenu == null)
            loginMenu = new LoginMenu();
        return loginMenu;
    }
    public void backButtonClicked(MouseEvent mouseEvent) throws IOException {
        Scene welcomeMenu= new Scene(FXMLLoader.load(getClass().getResource("/FxmlFiles/Welcome.fxml")));
        Main.stage.setScene(welcomeMenu);

    }
    public void submitButtonClicked(MouseEvent mouseEvent){
        if(usernameTextField.getText().equals("")) {
            usernameError.setText("username field is empty");
            if (passwordTextField.getText().equals(""))
                passwordError.setText("password field is empty");
            return;
        }

        passwordError.setText("");
        usernameError.setText("");
        showResponse(loginController.loginUser(usernameTextField.getText(),passwordTextField.getText()));
        passwordTextField.setText("");
        usernameTextField.setText("");
    }



    private void showResponse(LoginMenuResponses loginMenuResponses) {
        String output = "";
        switch (loginMenuResponses) {
            case USER_USERNAME_PASSWORD_NOT_MATCHED:
                result.setText("Username and password didn't match");
                break;
            default:
                break;
        }
        System.out.println(output);
    }
}