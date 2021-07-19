package sample.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import sample.controller.menuController.ProfileController;
import sample.controller.responses.ProfileMenuResponses;
import sample.controller.utilizationController.AudioController;

import java.io.IOException;

public class  ProfileMenu {
    @FXML
    private Label newNicknameError;
    @FXML
    private Label newPasswordError;

    @FXML
    private Label oldPasswordError;
    @FXML
    private Label result;

    @FXML
    private TextField newNicknameTextField;
    @FXML
    private TextField newPasswordTextField;
    @FXML
    private TextField oldPasswordTextField;
    private static ProfileMenu profileMenu;
    ProfileController profileController=new ProfileController();
    public static ProfileMenu getInstance() {
        if (profileMenu == null)
            profileMenu = new ProfileMenu();
        return profileMenu;
    }
    public void changePasswordButtonClicked(MouseEvent mouseEvent) throws IOException{
        AudioController.playClick();
        Scene profileScene = new Scene(FXMLLoader.load(getClass().getResource("/FxmlFiles/ProfileChangePassword.fxml")));
        WelcomeMenu.stage.setScene(profileScene);

    }
    public void changeNicknameButtonClicked(MouseEvent mouseEvent) throws IOException{
        AudioController.playClick();
        Scene profileScene = new Scene(FXMLLoader.load(getClass().getResource("/FxmlFiles/ProfileChangeNickname.fxml")));
        WelcomeMenu.stage.setScene(profileScene);
    }
    public void backButtonClicked(MouseEvent mouseEvent) throws IOException{
        AudioController.playClick();
        Scene mainMenuScene = new Scene(FXMLLoader.load(getClass().getResource("/FxmlFiles/MainMenu.fxml")));
        WelcomeMenu.stage.setScene(mainMenuScene);
    }

    public void submitNicknameButtonClicked(MouseEvent mouseEvent) throws IOException{
        AudioController.playClick();
        if ( newNicknameTextField.getText().equals("")) {
                newNicknameError.setText("new-nickname field is empty");
            return;
        }
        newNicknameError.setText("");
        ProfileMenuResponses responses = profileController.changeNickname(newNicknameTextField.getText());
        showResponse(responses);
        newNicknameTextField.setText("");
        if (responses == ProfileMenuResponses. NICKNAME_CHANGE_SUCCESSFUL) {
            Scene profileScene = new Scene(FXMLLoader.load(getClass().getResource("/FxmlFiles/Profile.fxml")));
            WelcomeMenu.stage.setScene(profileScene);
        }

    }

    private void showResponse(ProfileMenuResponses responses) {
        String output = "";
        switch (responses) {
            case USER_NICKNAME_ALREADY_EXISTS:
                result.setText("User nickname already exists");
                break;
            case  CURRENT_PASSWORD_INVALID:
                result.setText("Current password is invalid");
                break;
            case IDENTICAL_PASSWORDS:
                result.setText("Passwords are identical");
            default:
                break;
        }
        System.out.println(output);
    }


    public void backFromNicknameButtonClicked(MouseEvent mouseEvent) throws IOException {
        AudioController.playClick();
        Scene profileScene = new Scene(FXMLLoader.load(getClass().getResource("/FxmlFiles/Profile.fxml")));
        WelcomeMenu.stage.setScene(profileScene);
    }

    public void backFromPasswordButtonClicked(MouseEvent mouseEvent) throws IOException {
        AudioController.playClick();
        Scene profileScene = new Scene(FXMLLoader.load(getClass().getResource("/FxmlFiles/Profile.fxml")));
        WelcomeMenu.stage.setScene(profileScene);
    }

    public void submitPasswordButtonClicked(MouseEvent mouseEvent) throws IOException {
        AudioController.playClick();
        if ( newPasswordTextField.getText().equals("") || oldPasswordTextField.getText().equals("")) {
            if (newPasswordTextField.getText().equals(""))
                newPasswordError.setText("new-password field is empty");
            if(oldPasswordTextField.getText().equals(""))
                oldPasswordError.setText("old-password field is empty");
            return;
        }
        newPasswordError.setText("");
        oldPasswordError.setText("");
        ProfileMenuResponses responses = profileController.changePassword(oldPasswordTextField.getText(),newPasswordTextField.getText());
        showResponse(responses);
        newPasswordTextField.setText("");
        oldPasswordTextField.setText("");
        if (responses == ProfileMenuResponses. PASSWORD_CHANGE_SUCCESSFUL) {
            Scene profileScene = new Scene(FXMLLoader.load(getClass().getResource("/FxmlFiles/Profile.fxml")));
            WelcomeMenu.stage.setScene(profileScene);
        }
    }
}
