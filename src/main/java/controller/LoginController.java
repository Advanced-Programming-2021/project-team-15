package controller;

import controller.responses.LoginMenuResponses;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.User;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController extends MenuController implements Initializable {
    @FXML
    Button signUpButton;
    @FXML
    Button loginButton;
    @FXML
    Button exitButton;

    public LoginController() {
        super("Login Menu");
    }

    public LoginMenuResponses registerUser(String userName, String nickName, String passWord) {
        jsonController.refreshUsersFromFileJson();
        if (User.getUserByUserName(userName) != null)
            return LoginMenuResponses.USER_USERNAME_ALREADY_EXISTS;
        else if (User.getUserByNickname(nickName) != null)
            return LoginMenuResponses.USER_NICKNAME_ALREADY_EXISTS;
        else {
            User.getAllUsers().add(new User(userName, nickName, passWord));
            jsonController.refreshUsersToFileJson();
            return LoginMenuResponses.USER_CREATE_SUCCESSFUL;
        }
    }

    public LoginMenuResponses loginUser(String username, String password) {
        jsonController.refreshUsersFromFileJson();
        if (User.getUserByUserName(username) == null || !User.getUserByUserName(username).getPassWord().equals(password))
            return LoginMenuResponses.USER_USERNAME_PASSWORD_NOT_MATCHED;
        else {
            MenuController.setUser(User.getUserByUserName(username));
            return LoginMenuResponses.USER_LOGIN_SUCCESSFUL;
        }
    }

    public LoginMenuResponses removeUser(String username, String password) {
        jsonController.refreshUsersFromFileJson();
        if (User.getUserByUserName(username) == null) return LoginMenuResponses.USER_USERNAME_PASSWORD_NOT_MATCHED;
        else if (!User.getUserByUserName(username).getPassWord().equals(password))
            return LoginMenuResponses.USER_USERNAME_PASSWORD_NOT_MATCHED;
        else {
            User.removeUserByUsername(username);
            jsonController.refreshUsersToFileJson();
            return LoginMenuResponses.USER_REMOVE_SUCCESSFUL;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage stage = (Stage) loginButton.getScene().getWindow();
                stage.close();
                URL url = null;
                try {
                    url = new File("src/main/java/FxmlFiles/Login.fxml").toURI().toURL();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                Parent root = null;
                try {
                    root = FXMLLoader.load(url);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Stage primaryStage = new Stage();
                primaryStage.setTitle("LoginPage");
                primaryStage.setScene(new Scene(root, 1920, 1080));
                primaryStage.show();
            }

        });
        signUpButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage stage = (Stage) signUpButton.getScene().getWindow();
                stage.close();
                URL url = null;
                try {
                    url = new File("src/main/java/FxmlFiles/Signup.fxml").toURI().toURL();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                Parent root = null;
                try {
                    root = FXMLLoader.load(url);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Stage primaryStage = new Stage();
                primaryStage.setTitle("SignUpPage");
                primaryStage.setScene(new Scene(root, 1920, 1080));
                primaryStage.show();
            }
        });
        exitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage stage = (Stage) exitButton.getScene().getWindow();
                stage.close();
            }
        });
    }
}
