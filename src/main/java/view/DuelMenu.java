package view;

import controller.GamePlayController;
import controller.MenuController;
import controller.responses.DuelMenuResponses;
import model.Game;
import model.Zone;

import javax.security.sasl.SaslServer;
import java.util.HashMap;
import java.util.Scanner;

public class DuelMenu extends Menu {
    private static DuelMenu duelMenu;

    public boolean isWeAreOnGame() {
        return weAreOnGame;
    }

    public void setWeAreOnGame(boolean weAreOnGame) {
        this.weAreOnGame = weAreOnGame;
    }

    private boolean weAreOnGame = false;
    DuelMenuResponses duelMenuResponses;
    GamePlayController gamePlayController = GamePlayController.getInstance();

    private DuelMenu() {
        super("Duel Menu");
    }

    public static DuelMenu getInstance() {
        if (duelMenu == null)
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

    private Boolean cantDoThisKindsOfMove = false;


    @Override
    public void scanInput() {
            while (true) {
                String input = scannerLine();
                if (input.equals("menu exit")) checkAndCallMenuExit();
                if (input.startsWith("duel") && input.contains(" --ai")) checkAndCallNewAiDuel(input);
                else if (input.startsWith("duel")) {
                    checkAndCallNewDuel(input);
                     if(weAreOnGame) break;
                }
                else if (regexController.showMenuRegex(input)) checkAndCallShowCurrentMenu();
                else System.out.println("invalid command");
                if (super.isExit) {
                    super.isExit = false;
                    return;
                }
            }
        while (true) {
            String input = scannerLine();
            if (cantDoThisKindsOfMove) System.out.println("it's not your turn to play this kind of moves");
            else if (input.matches("select(.*)(\\d)(.*)"))
                checkAndCallSelectNumericZone(input);
            else if (input.startsWith("select"))
                checkAndCallSelectNotNumericZone(input);
            else if (input.equals("select -d")) checkAndCallDeselect(input);
            else if(input.equals("summon"))   printResponse(gamePlayController.summonCommand());
            else if(input.equals("set"))  printResponse(gamePlayController.setCommand());


            else System.out.println("invalid command");
        }
    }

    public void checkAndCallNewDuel(String input) {
        HashMap<String, String> enteredDetails = new HashMap<>();
        if (!regexController.newDuelRegex(input, enteredDetails))
            System.out.println("invalid command");
        else {
            weAreOnGame = true;
            String secondPlayer = enteredDetails.get("second player");
            secondUsername = secondPlayer;
            int rounds = Integer.parseInt(enteredDetails.get("rounds"));
            duelMenuResponses = gamePlayController.startNewGame(secondPlayer, rounds);
            printResponse(duelMenuResponses);
        }
    }

    public void checkAndCallNewAiDuel(String input) {


    }

    public void checkAndCallSelectNumericZone(String input) {
        HashMap<String, String> enteredDetails = new HashMap<>();
        if (!regexController.selectFromNumericZone(input, enteredDetails))
            System.out.println("invalid command");
        else {
            String opponentOrPlayer = enteredDetails.get("opponentOrPlayer");
            String zoneType = enteredDetails.get("zone type");
            int cardNumber = Integer.parseInt(enteredDetails.get("cardNumber"));
            duelMenuResponses = gamePlayController.selectNumericZone(cardNumber, zoneType, opponentOrPlayer);
            printResponse(duelMenuResponses);
        }
    }

    public void checkAndCallSelectNotNumericZone(String input) {
        HashMap<String, String> enteredDetails = new HashMap<>();
        if (!regexController.selectFromNotNumericZone(input, enteredDetails))
            System.out.println("invalid command");
        else {
            String opponentOrPlayer = enteredDetails.get("opponentOrPlayer");
            String zoneType = enteredDetails.get("zone type");
            duelMenuResponses = gamePlayController.selectNotNumericZone(zoneType, opponentOrPlayer);
            printResponse(duelMenuResponses);
        }

    }

    //TODO : handle various uses of SCANNER
    public Integer scannerNum() {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        return Integer.parseInt(s);
    }

    public String scannerLine() {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        return s;
    }

    public void checkAndCallDeselect(String input) {
        duelMenuResponses = gamePlayController.deSelect();
        printResponse(duelMenuResponses);
    }

    public void printResponse(DuelMenuResponses duelMenuResponses) {
        switch (duelMenuResponses) {
            case NO_PLAYER_WITH_THIS_USERNAME_EXISTS:
                System.out.println("there is no player with this username");
                break;
            case OPPONENT_PLAYER_HAS_NO_ACTIVE_DECK:
                System.out.println(secondUsername + " has no active deck");
                break;
            case CURRENT_PLAYER_HAS_NO_ACTIVE_DECK:
                System.out.println(MenuController.getUser().getUserName() + " has no active deck");
                break;
            case INVALID_OPPONENT_PLAYER_DECK:
                System.out.println(secondUsername + "'s deck is invalid");
                break;
            case INVALID_CURRENT_PLAYER_DECK:
                System.out.println(MenuController.getUser().getUserName() + "'s deck is invalid");
                break;
            case GAME_STARTED_SUCCESSFULLY:
                playRPS();
               break;
            case INVALID_SELECTION:
                System.out.println("invalid selection");
                break;
            case CARD_SELECTED:
                System.out.println("card selected");
            break;
            case SELECTION_NO_CARD_FOUND:
                System.out.println("no card found in the given position");
                break;
            case NO_CARD_SELECTED:
                System.out.println("no card is selected yet");
                break;
            case CARD_DESELECTED:
                System.out.println("card deselected");
            break;
            case   RIVALS_TURN_AND_SHOW_DRAW_PHASE:
            {
                System.out.println("phase : end phase");
                System.out.println("it's "+gamePlayController.getCurrentPlayer()+"'s turn");
                System.out.println("phase : draw phase");
                gamePlayController.drawPhase();
            }
            break;
            case CANT_SUMMON_THIS_CARD:
                System.out.println("you can't summon this card");
                break;
            case NOT_ALLOWED_IN_THIS_PHASE:
                System.out.println("action not allowed in this phase");
                break;
            case MONSTER_ZONE_IS_FULL:
                System.out.println("monster card zone is full");
                break;
            case ALREADY_SUMMONED_SET:
                System.out.println("you already summoned/set on this turn");
                break;
            case CARD_SUMMONED:
                System.out.println("summoned successfully");
                break;
            case GET_ONE_NUMBER_TO_BE_TRIBUTE:
                System.out.println("this card needs one tribute");
                break;
            case GET_TWO_NUMBERS_TO_BE_TRIBUTE:
                System.out.println("this card needs two tributes");
                break;
            case ENTER_ONE_NUMBER:
                System.out.println("enter one number : ");
                break;
            case ENTER_FIRST_NUMBER:
                System.out.println("enter first number : ");
                break;
            case ENTER_SECOND_NUMBER:
                System.out.println("enter second number : ");
                break;
            case NOT_ENOUGH_CARD_TO_BE_TRIBUTE:
                System.out.println("there are not enough cards for tribute");
                break;
            case ONE_TRIBUTE_NO_MONSTER:
                System.out.println("there no monsters one this address");
                break;
            case TWO_TRIBUTE_NO_MONSTER:
                System.out.println("there is no monster on one of these addresses");
                break;
           


            default:
                break;
        }
    }


    public void playRPS() {
        while (true) {
            System.out.println("let's play rock paper scissors!");
            System.out.println(gamePlayController.getGame().getFirstPlayer() + " please choose rock, paper or scissors");
            String firstPlayerMove = scannerLine();
            String secondPlayerMove = scannerLine();
            if (gamePlayController.RPS(firstPlayerMove, secondPlayerMove)) {
                System.out.println("GAME STARTED!");
                System.out.println("now it will be " + gamePlayController.getGame().getFirstPlayer() + "'s turn");
                break;
            }
        }
    }
    public void newCardAddInDrawPhase(String cardName)
    {
        System.out.println("new card added to hand : " + cardName);
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

    public void showRivalTurn(String userName, String board) {
        System.out.println("now it will be " + userName + "'s turn");
        System.out.println(board);

    }


    public void printString(String string) {
        System.out.println(string);
    }


}
