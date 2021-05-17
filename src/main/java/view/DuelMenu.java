package view;

import controller.GamePlayController;
import controller.MenuController;
import controller.responses.DuelMenuResponses;
import model.Zone;

import javax.security.sasl.SaslServer;
import java.util.HashMap;
import java.util.Scanner;

public class DuelMenu extends Menu{
    private static DuelMenu duelMenu;
    DuelMenuResponses duelMenuResponses;
    GamePlayController gamePlayController = GamePlayController.getInstance();
    private DuelMenu() {
        super("Duel Menu");
    }
    public static DuelMenu getInstance() {
        if(duelMenu==null)
            duelMenu = new DuelMenu();
        return duelMenu;
    }
    private String secondUsername;

    public Boolean getCantDoThisKindsOfMove() {
        return cantDoThisKindsOfMove;
    }

    public void setCantDoThisKindsOfMove(Boolean cantDoThisKindsOfMove) {
        this.cantDoThisKindsOfMove = cantDoThisKindsOfMove;
    }

    private Boolean cantDoThisKindsOfMove= false;


    @Override
    public void scanInput() {
        while (true){
            String input  = scannerLine();
            if(cantDoThisKindsOfMove)
                printString("it's not your turn to play this kind of moves");
            else if(input.startsWith("duel") && input.contains(" --ai"))
                 checkAndCallNewAiDuel(input);
             else if(input.startsWith("duel"))
                 checkAndCallNewDuel(input);
             if(input.matches("select(.*)(\\d)(.*)"))
                 checkAndCallSelectNumericZone(input);
             else if(input.startsWith("select"))
                 checkAndCallSelectNotNumericZone(input);
             else if(input.equals("select -d")) checkAndCallDeselect(input);
             //continue
        }
    }

    public void checkAndCallNewDuel(String input) {
        HashMap<String, String> enteredDetails = new HashMap<>();
        if(!regexController.newDuelRegex(input , enteredDetails))
            System.out.println("invalid command");
        else {
            String secondPlayer = enteredDetails.get("second player");
            secondUsername =secondPlayer;
            int rounds = Integer.parseInt(enteredDetails.get("rounds"));
           duelMenuResponses = gamePlayController.startNewGame(secondPlayer,rounds);
           printResponse(duelMenuResponses);
        }
    }
    public void checkAndCallNewAiDuel(String input){


    }

    public void checkAndCallSelectNumericZone( String input)
    {  HashMap<String, String> enteredDetails = new HashMap<>();
        if(!regexController.selectFromNumericZone(input , enteredDetails))
            System.out.println("invalid command");
        else {
            String opponentOrPlayer= enteredDetails.get("opponentOrPlayer");
            String zoneType= enteredDetails.get("zone type");
            int cardNumber = Integer.parseInt(enteredDetails.get("cardNumber"));
            duelMenuResponses = gamePlayController.selectNumericZone(cardNumber ,zoneType , opponentOrPlayer  );
            printResponse(duelMenuResponses);}
    }
    public void checkAndCallSelectNotNumericZone( String input)
    {   HashMap<String, String> enteredDetails = new HashMap<>();
        if(!regexController.selectFromNotNumericZone(input, enteredDetails))
            System.out.println("invalid command");
        else {
            String opponentOrPlayer= enteredDetails.get("opponentOrPlayer");
            String zoneType= enteredDetails.get("zone type");
            duelMenuResponses = gamePlayController.selectNotNumericZone(zoneType, opponentOrPlayer);
            printResponse(duelMenuResponses);
        }

    }

    //TODO : handle various uses of SCANNER
    public Integer scannerNum()
    {  Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        return Integer.parseInt(s);
    }
    public String scannerLine()
    {  Scanner scanner = new Scanner(System.in);
        String s= scanner.nextLine();
        return s;
    }

    public void checkAndCallDeselect(String input)
    { duelMenuResponses = gamePlayController.deSelect();
      printResponse(duelMenuResponses);
    }
    public void printResponse(DuelMenuResponses duelMenuResponses) {
        String output = "";
        switch (duelMenuResponses) {
            case NO_PLAYER_WITH_THIS_USERNAME_EXISTS:
                output = "there is no player with this username";
                break;
            case OPPONENT_PLAYER_HAS_NO_ACTIVE_DECK:
                output = secondUsername+" has no active deck";
                break;
            case CURRENT_PLAYER_HAS_NO_ACTIVE_DECK:
                output = MenuController.getUser().getUserName() + " has no active deck";
                break;
            case INVALID_OPPONENT_PLAYER_DECK:
                output = secondUsername+"'s deck is invalid";
                break;
            case INVALID_CURRENT_PLAYER_DECK:
                output = MenuController.getUser().getUserName() + "'s deck is invalid";
                break;
            case GAME_STARTED_SUCCESSFULLY:
                output = "GAME STARTED SUCCESSFULLY !";
                break;
        }



        System.out.println(output);
        }
    public void revealCard(String cardName) {
        System.out.print("opponent’s monster card was " + cardName + " and ");
    }

    public void lostInDefense(int damage) {
        System.out.println("no card is destroyed and you received " + damage + " battle damage");
    }

    public void lostInAttack(int damage) {
        System.out.println("Your monster card is destroyed and you received " + damage + " battle damage");
    }
    public void wonAttackInAttack(int damage) {
        System.out.println("your opponent’s monster is destroyed and your opponent receives " + damage + " battle damage");
    }
    public void receivedDamage(int damage) {
        System.out.println("you opponent receives " + damage + " battle damage");
    }
    public void showRivalTurn(String userName, String board)
    {
        System.out.println("now it will be " + userName+ "'s turn");
        System.out.println(board);

    }


        public  void printString(String string)
        {
            System.out.println(string);
        }


}
