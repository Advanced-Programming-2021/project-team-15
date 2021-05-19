package view;

import controller.GamePlayController;
import controller.MenuController;
import controller.responses.DuelMenuResponses;
<<<<<<< Updated upstream
import model.Game;
import model.Zone;
=======
import utility.Utility;
>>>>>>> Stashed changes

import java.util.HashMap;
import java.util.Scanner;

public class DuelMenu extends Menu {
    private static DuelMenu duelMenu;
<<<<<<< Updated upstream

    public boolean isWeAreOnGame() {
        return weAreOnGame;
    }

    public void setWeAreOnGame(boolean weAreOnGame) {
        this.weAreOnGame = weAreOnGame;
    }

    private boolean weAreOnGame = false;
    DuelMenuResponses duelMenuResponses;
    GamePlayController gamePlayController = GamePlayController.getInstance();
=======
    private static GamePlayController gamePlayController;

    static {
        GamePlayController.getInstance();
    }

    DuelMenuResponses duelMenuResponses;
    private String secondUsername;
    private Boolean cantDoThisKindsOfMove = false;
>>>>>>> Stashed changes

    private DuelMenu() {
        super("Duel Menu");
    }

    public static DuelMenu getInstance() {
        if (duelMenu == null)
            duelMenu = new DuelMenu();
        return duelMenu;
    }
<<<<<<< Updated upstream

    private String secondUsername;
=======
>>>>>>> Stashed changes

    public Boolean getCantDoThisKindsOfMove() {
        return cantDoThisKindsOfMove;
    }

    public void setCantDoThisKindsOfMove(Boolean cantDoThisKindsOfMove) {
        this.cantDoThisKindsOfMove = cantDoThisKindsOfMove;
    }

<<<<<<< Updated upstream
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
=======
    @Override
    public void scanInput() {
        while (true) {
            String input = Utility.getNextLine();
            if (cantDoThisKindsOfMove)
                printString("it's not your turn to play this kind of moves");
            else if (input.startsWith("duel") && input.contains(" --ai"))
                checkAndCallNewAiDuel(input);
            else if (input.startsWith("duel"))
                checkAndCallNewDuel(input);
            if (input.matches("select(.*)(\\d)(.*)"))
>>>>>>> Stashed changes
                checkAndCallSelectNumericZone(input);
            else if (input.startsWith("select"))
                checkAndCallSelectNotNumericZone(input);
            else if (input.equals("select -d")) checkAndCallDeselect(input);
<<<<<<< Updated upstream
            else System.out.println("invalid command");
=======
            //continue
>>>>>>> Stashed changes
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
<<<<<<< Updated upstream

    public String scannerLine() {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        return s;
    }
=======
>>>>>>> Stashed changes

    public void checkAndCallDeselect(String input) {
        duelMenuResponses = gamePlayController.deSelect();
        printResponse(duelMenuResponses);
    }

    public void printResponse(DuelMenuResponses duelMenuResponses) {
        String output = "";
        switch (duelMenuResponses) {
            case NO_PLAYER_WITH_THIS_USERNAME_EXISTS:
                System.out.println("there is no player with this username");
                break;
            case OPPONENT_PLAYER_HAS_NO_ACTIVE_DECK:
<<<<<<< Updated upstream
                System.out.println(secondUsername + " has no active deck");
=======
                output = secondUsername + " has no active deck";
>>>>>>> Stashed changes
                break;
            case CURRENT_PLAYER_HAS_NO_ACTIVE_DECK:
                System.out.println(MenuController.getUser().getUserName() + " has no active deck");
                break;
            case INVALID_OPPONENT_PLAYER_DECK:
<<<<<<< Updated upstream
                System.out.println(secondUsername + "'s deck is invalid");
=======
                output = secondUsername + "'s deck is invalid";
>>>>>>> Stashed changes
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

            default:
                break;
        }
    }


<<<<<<< Updated upstream
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
=======
        System.out.println(output);
>>>>>>> Stashed changes
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
