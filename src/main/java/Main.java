import controller.JSONController;
import model.Card;
import model.MonsterCard;
import view.LoginMenu;

public class Main {
    public static void main(String[] args) {
        JSONController jsonController = new JSONController();
      //  jsonController.MonsterCardParseJson();
        jsonController.MagicCardParseJson();
        for (Card card : Card.getAllCards()) {
            MonsterCard monsterCard = (MonsterCard)card;
            System.out.println("Card Name : "+ monsterCard.getCardName());
            System.out.println("Card Type : "+monsterCard.getCardType());
            System.out.println("Level : "+ monsterCard.getLevel());
            System.out.println("Monster Type : "+ monsterCard.getMonsterAttribute());
            System.out.println("Card Type : "+ monsterCard.getMonsterEffectType());
            System.out.println("Attribute : "+ monsterCard.getMonsterAttribute());
            System.out.println("-----------------------------------------------");
        }
        LoginMenu loginMenu = new LoginMenu("Login Menu");
        loginMenu.scanInput();
        System.out.println("return shod");
    }
}
