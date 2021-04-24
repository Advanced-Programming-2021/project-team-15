import controller.JSONController;
import controller.LoginController;
import view.LoginMenu;

public class Main {
    public static void main(String[] args) {
        JSONController jsonController = new JSONController();
        jsonController.cardParseJson();
        LoginMenu loginMenu = new LoginMenu("Login Menu");
        loginMenu.scanInput();
        System.out.println("return shod");
    }
}
