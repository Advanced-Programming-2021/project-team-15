import com.opencsv.exceptions.CsvValidationException;
import controller.JSONController;
import model.Card;
import model.MagicCard;
import model.MonsterCard;
import view.LoginMenu;

import java.io.IOException;

/*To Handle :
menu entry from other menus
giving other menus instructions
shop buy item clone cards
Import/Export Menu???
card show (page 10)
*/
//TODO check:
//attack controller line 102 - attackToDefencePos return





public class Main {
    public static void main(String[] args) throws IOException, CsvValidationException {
        JSONController jsonController = new JSONController();
        jsonController.loadGameCards();
        LoginMenu loginMenu = LoginMenu.getInstance();
//        for (Card card : Card.getAllCards()) {
//            if (card instanceof MonsterCard) System.out.println("monster");
//            else if (card instanceof MagicCard) System.out.println("magic");
//            else System.out.println("card");
//        }
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
