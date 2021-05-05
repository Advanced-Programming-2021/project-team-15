package controller;

import controller.responses.ProfileMenuResponses;
import model.User;

public class ProfileController extends MenuController {
    public ProfileController() {
        super("Profile Menu");
    }

    public ProfileMenuResponses changeNickname(String newNickname) {
        if (User.getUserByNickname(newNickname)!=null)
            return ProfileMenuResponses.USER_NICKNAME_ALREADY_EXISTS;
        else {
            user.setNickName(newNickname);
            jsonController.refreshUsersToFileJson();
            return ProfileMenuResponses.NICKNAME_CHANGE_SUCCESSFUL;
        }
    }

    public ProfileMenuResponses changePassword(String userPassword, String newPassword) {
        if (!user.getPassWord().equals(userPassword))
            return ProfileMenuResponses.CURRENT_PASSWORD_INVALID;
        else if (userPassword.equals(newPassword))
            return ProfileMenuResponses.IDENTICAL_PASSWORDS;
        else {
            user.setPassWord(newPassword);
            jsonController.refreshUsersToFileJson();
            return ProfileMenuResponses.PASSWORD_CHANGE_SUCCESSFUL;
        }
    }


}

