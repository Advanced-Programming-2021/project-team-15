package view;

import controller.AttackController;
import controller.GamePlayController;
import controller.MenuController;
import controller.responses.DuelMenuResponses;
import utility.Utility;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DuelMenu extends Menu {
    private static DuelMenu duelMenu;
    private static GamePlayController gamePlayController;

    static {
        gamePlayController = GamePlayController.getInstance();
    }

    DuelMenuResponses duelMenuResponses;
    private boolean weAreOnGame = false;
    private String secondUsername;
    private Boolean cantDoThisKindsOfMove = false;

    private DuelMenu() {
        super("Duel Menu");
    }

    public static DuelMenu getInstance() {
        if (duelMenu == null)
            duelMenu = new DuelMenu();
        return duelMenu;
    }

    public boolean isWeAreOnGame() {
        return weAreOnGame;
    }

    public void setWeAreOnGame(boolean weAreOnGame) {
        this.weAreOnGame = weAreOnGame;
    }

    public Boolean getCantDoThisKindsOfMove() {
        return cantDoThisKindsOfMove;
    }

    public void setCantDoThisKindsOfMove(Boolean cantDoThisKindsOfMove) {
        this.cantDoThisKindsOfMove = cantDoThisKindsOfMove;
    }

    @Override
    public void scanInput() {
        while (true) {
            String input = Utility.getNextLine();
            if (input.equals("menu exit")) checkAndCallMenuExit();
            if (input.startsWith("duel") && input.contains(" --ai")) checkAndCallNewAiDuel(input);
            else if (input.startsWith("duel")) {
                checkAndCallNewDuel(input);
                if (weAreOnGame) break;
            } else if (regexController.showMenuRegex(input)) checkAndCallShowCurrentMenu();
            else System.out.println("invalid command");
            if (super.isExit) {
                super.isExit = false;
                return;
            }
        }
        while (true) {
            String input = Utility.getNextLine();
            if (cantDoThisKindsOfMove) System.out.println("it's not your turn to play this kind of moves");
            else if (input.matches("select(.*)(\\d)(.*)"))
                checkAndCallSelectNumericZone(input);
            else if (input.startsWith("select"))
                checkAndCallSelectNotNumericZone(input);
            else if (input.equals("select -d")) checkAndCallDeselect(input);
            else if (input.equals("summon")) printResponse(gamePlayController.summonCommand());
            else if (input.equals("set")) printResponse(gamePlayController.setCommand());
            else if(input.matches("set --position (attack|defense)"))
            {
                Matcher matcher  = Pattern.compile("set --position (attack|defense)").matcher(input);
                if(matcher.find())  printResponse(gamePlayController.setPosCommand(matcher.group(1))); }
            else if(input.equals("flip-summon"))  printResponse(gamePlayController.flipSummonCommand());
            else if(input.matches("attack (\\d+)"))
            { Matcher matcher  = Pattern.compile("attack (\\d+)").matcher(input);
                if(matcher.find()) printResponse(gamePlayController.normalAttack(Integer.parseInt(matcher.group(1)))); }
            else if(input.equals("attack direct")) printResponse(gamePlayController.directAttack());
            //else if(input.equals("activate effect")) printResponse(gamePlayController.activateSpellCard());




            else System.out.println("invalid command");
        }
    }

    public void checkAndCallNewDuel(String input) {
        HashMap<String, String> enteredDetails = new HashMap<>();
        if (!regexController.newDuelRegex(input, enteredDetails))
            System.out.println("invalid command");
        else {
            String secondPlayer = enteredDetails.get("second player");
            secondUsername = secondPlayer;
            int rounds = Integer.parseInt(enteredDetails.get("rounds"));
            duelMenuResponses = gamePlayController.startNewGame(secondPlayer, rounds);
            printResponse(duelMenuResponses);
            if (duelMenuResponses==DuelMenuResponses.GAME_STARTED_SUCCESSFULLY) weAreOnGame=true;
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
            case RIVALS_TURN_AND_SHOW_DRAW_PHASE: {
                System.out.println("phase : end phase");
                System.out.println("it's " + gamePlayController.getCurrentPlayer() + "'s turn");
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
            case CANT_SET_THIS_CARD :
                System.out.println("you can't set this card");
                break;
            case CANT_DO_THIS_ACTION_IN_THIS_PHASE:
                System.out.println("you can't do this action in this phase");
                break;
            case CARD_SET_SUCCESSFULLY:
                System.out.println("set successfully");
                break;
            case CANT_CHANGE_THIS_CARD_POSITION:
                System.out.println("you can't change this card position");
                break;
            case ALREADY_CHANGED_POSITION:
                System.out.println("you already changed this card position in turn");
                break;
            case MONSTER_CARD_POSITION_CHANGED_SUCCESSFULLY:
                System.out.println("monster card position changed successfully");
                break;
            case  CANT_FLIP_SUMMON:
                    System.out.println("you can't flip summon this card");
                    break;
            case FLIP_SUMMONED_SUCCESSFULLY:
                System.out.println("flip summoned successfully");
                break;
            case YOU_CANT_ATTACK_WITH_THIS_CARD:
                System.out.println("you can't attack with this card");
                break;
            case ALREADY_ATTACKED:
                System.out.println("this card already attacked");
                break;
            case NO_CARD_TO_ATTACK:
                System.out.println("there is no card to attack here");
                break;
            case  DESTROYED_OPPONENT_MONSTER_AND_OPPONENT_RECEIVED_DAMAGE:
             System.out.println("your opponent's monster is destroyed and your opponent receives "+ AttackController.getDamage()+ " battle damage");
             break;
            case BOTH_MONSTERS_ARE_DESTROYED:
                System.out.println("both you and your opponent monster cards are destroyed and no one receives damage");
                break;
            case DESTROYED_CURRENT_MONSTER_AFTER_ATTACK:
                System.out.println("your monster card is destroyed and you received "+AttackController.getDamage()+" battle damage");
                break;
            case DEFENCE_POSITION_MONSTER_DESTROYED:
                System.out.println("the defense position monster is destroyed");
                break;
            case NO_CARD_DESTROYED :
                System.out.println("no card is destroyed ");
                break;
            case NO_CARD_DESTROYED_CURRENT_DAMAGED:
                System.out.println("no card is destroyed and you received "+AttackController.getDamage()+" battle damage");
                break;
            case  CANT_ATTACK_DIRECTLY:
                System.out.println("you can't attack the opponent directly");
                break;
            case YOUR_OPPONENT_DAMAGED_DIRECT_ATTACK:
                System.out.println("your opponent receives "+AttackController.getDamage()+" battle damage");
                break;
            case  ACTIVATE_EFFECT_ONLY_ON_SPELL:
                System.out.println("activate effect is only for spell cards");
                break;
            case CANT_ACTIVATE_EFFECT_ON_THIS_TURN:
                System.out.println("you can't activate an effect on this turn");
                break;
            case YOU_ALREADY_ACTIVATED_THIS_CARD:
                System.out.println("you have already activated this card");
                break;
            case SPELL_ZONE_CARD_IS_FULL:
                System.out.println("spell card zone is full");
                break;
            case PREPARATIONS_OF_THIS_SPELL_ARE_NOT_DONE_YET:
                System.out.println("preparations of this spell are not done yet");
                break;
            case SPELL_ACTIVATED:
                System.out.println("spell activated");
                break;





            default:
                break;
        }
    }


    public void hiddenDefensePositionMonsterDestroyed(String name)
    {
        System.out.println("opponent's monster card was "+name+" and the defense position monster is destroyed");
    }
    public void hiddenDefensePosNoCardDestroyed(String name)
    {
        System.out.println("opponent's monster card was "+name+" and no card is destroyed");
    }
    public void hiddenDefensePosNoCardDestroyedWithDamage(String name)
    {
        System.out.println("opponent's monster card was "+name+" no card is destroyed and you received "+AttackController.getDamage()+" battle damage");
    }


    public void playRPS() {
        while (true) {
            System.out.println("let's play rock paper scissors!");
            System.out.println(gamePlayController.getGame().getFirstPlayer() + " please choose rock, paper or scissors");
            String firstPlayerMove = Utility.getNextLine();
            String secondPlayerMove = Utility.getNextLine();
            if (gamePlayController.RPS(firstPlayerMove, secondPlayerMove)) {
                System.out.println("GAME STARTED!");
                System.out.println("now it will be " + gamePlayController.getGame().getFirstPlayer() + "'s turn");
                break;
            }
        }
    }

    public void newCardAddInDrawPhase(String cardName) {
        System.out.println("new card added to hand : " + cardName);
    }



    public void showRivalTurn(String userName, String board) {
        System.out.println("now it will be " + userName + "'s turn");
        System.out.println(board);

    }


    public void printString(String string) {
        System.out.println(string);
    }
    public int getNum()
    {
        return Integer.parseInt(Utility.getNextLine());
    }
    public String getString()
    {
        return Utility.getNextLine();
    }


}
