package view;

import controller.GamePlayController;
import controller.MenuController;
import controller.responses.DuelMenuResponses;
import model.Zone;

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


    @Override
    public void scanInput() {
        Scanner scanner = new Scanner(System.in);
        while (true){
            String input  =  scanner.nextLine();
             if(input.startsWith("duel") && input.contains(" --ai"))
                 checkAndCallNewAiDuel(input);
             else if(input.startsWith("duel"))
                 checkAndCallNewDuel(input);
             if(input.matches("select(.*)(\\d)(.*)"))
                 checkAndCallSelectNumericZone(input);
             else if(input.startsWith("select"))
                 checkAndCallSelectNotNumericZone(input);
             else if(input.equals("select -d")) checkAndCallDeselect(input);
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

}
