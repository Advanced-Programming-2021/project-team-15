package sample.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import sample.controller.menuController.LoginController;
import sample.controller.responses.LoginMenuResponses;
import sample.controller.utilizationController.AudioController;
import sample.controller.utilizationController.UtilityController;

import java.io.IOException;

public class LoginMenu {
    private static LoginMenu loginMenu;
    LoginController loginController = new LoginController();
    @FXML
    private TextField passwordTextField;
    @FXML
    private TextField usernameTextField;

    public static LoginMenu getInstance() {
        if (loginMenu == null)
            loginMenu = new LoginMenu();
        return loginMenu;
    }

    public void backButtonClicked(MouseEvent mouseEvent) throws IOException {
        AudioController.playClick();
        Scene welcomeMenu = new Scene(FXMLLoader.load(getClass().getResource("/FxmlFiles/Welcome.fxml")));
        WelcomeMenu.stage.setScene(welcomeMenu);

    }

    public void submitButtonClicked(MouseEvent mouseEvent) throws IOException {
        AudioController.playClick();
        if (usernameTextField.getText().equals("") || passwordTextField.getText().equals("")) {
            if (usernameTextField.getText().equals(""))
                UtilityController.makeAlert("Error!!","Can't do action!","username field is empty",
                        new Image(String.valueOf(getClass().
                    getResource("/Images/sadAnimeGirl.jpg" ))));
            if (passwordTextField.getText().equals(""))
                UtilityController.makeAlert("Error!!","Can't do action!","password field is empty",
                        new Image(String.valueOf(getClass().
                                getResource("/Images/sadAnimeGirl.jpg" ))));
            return;
        }
        LoginMenuResponses responses = loginController.loginUser(usernameTextField.getText(), passwordTextField.getText());
        showResponse(responses);
        passwordTextField.setText("");
        usernameTextField.setText("");
        if (responses == LoginMenuResponses.USER_LOGIN_SUCCESSFUL) {
            Scene mainMenuScene = new Scene(FXMLLoader.load(getClass().getResource("/FxmlFiles/MainMenu.fxml")));
            WelcomeMenu.stage.setScene(mainMenuScene);
        }
    }


    private void showResponse(LoginMenuResponses loginMenuResponses) {
        String output = "";
        switch (loginMenuResponses) {
            case USER_USERNAME_PASSWORD_NOT_MATCHED:
                UtilityController.makeAlert("Error!!","Can't do action!","Username and password didn't match",
                        new Image(String.valueOf(getClass().
                                getResource("/Images/sadAnimeGirl.jpg" ))));
                break;
            default:
                break;
        }
        System.out.println(output);
    }
}