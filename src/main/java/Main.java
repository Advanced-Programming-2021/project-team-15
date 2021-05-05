import controller.JSONController;
import model.Card;
import model.MagicCard;
import model.MonsterCard;
import view.LoginMenu;

/*To Handle :
menu entry from other menus
giving other menus instructions
shop buy item clone cards
Import/Export Menu???
*/





public class Main {
    public static void main(String[] args) {
        JSONController jsonController = new JSONController();
        jsonController.MonsterCardParseJson();
        jsonController.MagicCardParseJson();
        LoginMenu loginMenu = LoginMenu.getInstance();
        loginMenu.scanInput();

//        for (Card card : Card.getAllCards()) {
//
//            if (card.getCardType()== Card.CardType.MONSTER) {
//                MonsterCard monsterCard = (MonsterCard) card;
//                System.out.println("Card Name : " + monsterCard.getCardName());
//                System.out.println("Type : " + monsterCard.getCardType());
//                System.out.println("Level : " + monsterCard.getLevel());
//                System.out.println("Monster Type : " + monsterCard.getMonsterAttribute());
//                System.out.println("Card Type : " + monsterCard.getMonsterEffectType());
//                System.out.println("Attribute : " + monsterCard.getMonsterAttribute());
//            }
//            else {
//                MagicCard magicCard = (MagicCard) card;
//                System.out.println("Card Name : " + magicCard.getCardName());
//                System.out.println("Type : " + magicCard.getMagicType());
//                System.out.println("Icon : " + magicCard.getCardIcon());
//                System.out.println("Status : " + magicCard.getStatus());
//            }
//            System.out.println("-----------------------------------------------");
//        }
    }
}
