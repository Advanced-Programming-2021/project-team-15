package sample.view;

import sample.controller.menuController.ProfileController;
import sample.controller.responses.ProfileMenuResponses;
import sample.controller.utilizationController.UtilityController;

import java.util.HashMap;

public class ProfileMenu extends Menu {
    private static ProfileMenu profileMenu;
    ProfileController profileController = new ProfileController();
    String newNickname;
    ProfileMenuResponses responses;
    private ProfileMenu() {
        super("Profile Menu");
    }

    public static ProfileMenu getInstance() {
        if (profileMenu == null)
            profileMenu = new ProfileMenu();
        return profileMenu;
    }

    @Override
    public void scanInput() {
        while (true) {
            String input = UtilityController.getNextLine();
            if (input.startsWith("profile change --nickname")) checkAndCallChangeNickname(input);
            else if (input.startsWith("profile change")) checkAndCallChangePassword(input);
            else if (input.equals("menu exit")) checkAndCallMenuExit();
            else if (regexController.showMenuRegex(input)) checkAndCallShowCurrentMenu();
            else if (input.startsWith("menu enter ")) System.out.println("Navigation is not possible hear");
            else System.out.println("invalid command");
            if (super.isExit) {
                super.isExit = false;
                return;
            }
        }

    }

    public void checkAndCallChangeNickname(String input) {
        HashMap<String, String> enteredDetails = new HashMap<>();
        if (!regexController.changeNicknameRegex(input, enteredDetails))
            System.out.println("invalid command");
        else {
            newNickname = enteredDetails.get("nickname");
            responses = profileController.changeNickname(newNickname);
            printResponse(responses);
        }
    }

    public void checkAndCallChangePassword(String input) {
        HashMap<String, String> enteredDetails = new HashMap<>();
        if (!regexController.changePasswordRegex(input, enteredDetails))
            System.out.println("invalid command");
        else {
            String currentPassword = enteredDetails.get("current");
            String newPassword = enteredDetails.get("new");
            responses = profileController.changePassword(currentPassword , newPassword);
            printResponse(responses);
        }

    }
    private void printResponse(ProfileMenuResponses profileMenuResponses)
    {
        String output = "";
        switch (profileMenuResponses) {
            case PASSWORD_CHANGE_SUCCESSFUL:
                output = "password changed successfully!";
                break;
            case IDENTICAL_PASSWORDS:
                output = "please enter a new password";
                break;
            case CURRENT_PASSWORD_INVALID:
                output = "current password is invalid";
                break;
            case NICKNAME_CHANGE_SUCCESSFUL:
                output = "nickname changed successfully!";
                break;
            case USER_NICKNAME_ALREADY_EXISTS:
                output = "user with nickname "+newNickname+" already exists";
                break;
            default:
                break;
        }
        System.out.println(output);
    }
}

