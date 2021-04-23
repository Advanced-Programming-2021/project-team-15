import controller.LoginController;
import view.LoginMenu;

public class Main {
    public static void main(String[] args) {
        LoginMenu loginMenu = new LoginMenu("Login Menu");
        loginMenu.scanInput();
        System.out.println("return shod");
    }
}
