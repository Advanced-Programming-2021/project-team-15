package sample.view;

import javafx.scene.image.Image;
import sample.controller.menuController.LoginController;
import sample.controller.responses.LoginMenuResponses;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import sample.controller.utilizationController.AudioController;
import sample.controller.utilizationController.UtilityController;

import java.io.IOException;

public class SignUpMenu {
    private static SignUpMenu signUpMenu;
    LoginController loginController = new LoginController();
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
        WelcomeMenu.stage.setScene(welcomeMenu);
    }

     public void signupButtonClicked(MouseEvent mouseEvent){
         AudioController.playClick();
        if(usernameField.getText().equals("") || passwordField.getText().equals("") || nicknameField.getText().equals("")) {
            if(usernameField.getText().equals(""))
                UtilityController.makeAlert("Error!!","Can't do action!","username field is empty",
                        new Image(String.valueOf(getClass().
                                getResource("/Images/sadAnimeGirl.jpg" ))));
            if (passwordField.getText().equals(""))
                UtilityController.makeAlert("Error!!","Can't do action!","password field is empty",
                        new Image(String.valueOf(getClass().
                                getResource("/Images/sadAnimeGirl.jpg" ))));
            if (nicknameField.getText().equals(""))
                UtilityController.makeAlert("Error!!","Can't do action!","nickname field is empty",
                        new Image(String.valueOf(getClass().
                                getResource("/Images/sadAnimeGirl.jpg" ))));
            return;
        }
        showResponse(loginController.registerUser(usernameField.getText(),nicknameField.getText(),passwordField.getText()));
        passwordField.setText("");
        usernameField.setText("");
        nicknameField.setText("");
        }

    private void showResponse(LoginMenuResponses loginMenuResponses) {
        String output = "";
        switch (loginMenuResponses) {
            case USER_CREATE_SUCCESSFUL:
                UtilityController.makeAlert("Happy!!","Good job!","User created successfully!",
                        new Image(String.valueOf(getClass().
                                getResource("/Images/okAnimeGirl.png" ))));
                break;
            case USER_NICKNAME_ALREADY_EXISTS:
                UtilityController.makeAlert("Error!!","Can't do action!","User with this nickname already exists",
                        new Image(String.valueOf(getClass().
                                getResource("/Images/sadAnimeGirl.jpg" ))));
                break;
            case USER_USERNAME_ALREADY_EXISTS:
                UtilityController.makeAlert("Error!!","Can't do action!","User with this username  already exists",
                        new Image(String.valueOf(getClass().
                                getResource("/Images/sadAnimeGirl.jpg" ))));
                break;
            case USER_USERNAME_PASSWORD_NOT_MATCHED:
                UtilityController.makeAlert("Error!!","Can't do action!","Username and password does not match",
                        new Image(String.valueOf(getClass().
                                getResource("/Images/sadAnimeGirl.jpg" ))));
                break;
            default:
                break;
        }
        System.out.println(output);
    }
}
