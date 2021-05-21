package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexController {
    MainMenuController mainMenuController = new MainMenuController();
    ShopController shopController = new ShopController();

    public Boolean createUserRegex(String input, HashMap<String, String> enteredDetails) {
        Matcher matcher = getCommandMatcher(input, "user create (\\S+) (\\S+) (\\S+) (\\S+) (\\S+) (\\S+)$");
        if (matcher.find()) {
            return isCreateUserCommandValid(matcher, enteredDetails);
        } else return false;
    }

    public Boolean isCreateUserCommandValid(Matcher matcher, HashMap<String, String> enteredDetails) {
        boolean commandValidation = true;
        CommandCase commandCase;
        HashMap<String, String> inputParameters = new HashMap();
        inputParameters.put(matcher.group(1), matcher.group(2));
        inputParameters.put(matcher.group(3), matcher.group(4));
        inputParameters.put(matcher.group(5), matcher.group(6));
        ArrayList<String> longCommandParameters = new ArrayList<>();
        longCommandParameters.add("--username");
        longCommandParameters.add("--nickname");
        longCommandParameters.add("--password");
        ArrayList<String> shortCommandParameters = new ArrayList<>();
        shortCommandParameters.add("-u");
        shortCommandParameters.add("-n");
        shortCommandParameters.add("-p");
        if (inputParameters.keySet().containsAll(longCommandParameters) && longCommandParameters.containsAll(inputParameters.keySet()))
            commandCase = CommandCase.LONG;
        else if (inputParameters.keySet().containsAll(shortCommandParameters) && shortCommandParameters.containsAll(inputParameters.keySet()))
            commandCase = CommandCase.SHORT;
        else {
            commandCase = CommandCase.INVALID;
            commandValidation = false;
        }
        if (commandCase.equals(CommandCase.LONG)) {
            enteredDetails.put("username", inputParameters.get("--username"));
            enteredDetails.put("nickname", inputParameters.get("--nickname"));
            enteredDetails.put("password", inputParameters.get("--password"));
        }
        //loginController.registerUser(inputParameters.get("--username") , inputParameters.get("--nickname") , inputParameters.get("--password"));
        else if (commandCase.equals(CommandCase.SHORT)) {
            enteredDetails.put("username", inputParameters.get("-u"));
            enteredDetails.put("nickname", inputParameters.get("-n"));
            enteredDetails.put("password", inputParameters.get("-p"));
        }
        //loginController.registerUser(inputParameters.get("-u") , inputParameters.get("-n") , inputParameters.get("-p"));
        return commandValidation;
    }

    public Boolean showMenuRegex(String input) {
        Matcher matcher = getCommandMatcher(input, "menu ((show-current)|(current-show))$");
        if (matcher.find())
            return true;
        else return false;
    }

    public Boolean loginUserRegex(String input, HashMap<String, String> enteredDetails) {
        Matcher matcher = getCommandMatcher(input, "user login (\\S+) (\\S+) (\\S+) (\\S+)$");
        if (matcher.find()) {
            return isLoginUserCommandValid(matcher, enteredDetails);
        } else return false;
    }

    public Boolean removeUserRegex(String input, HashMap<String, String> enteredDetails) {
        Matcher matcher = getCommandMatcher(input, "user remove (\\S+) (\\S+) (\\S+) (\\S+)$");
        if (matcher.find()) {
            return isLoginUserCommandValid(matcher, enteredDetails);
        } else return false;
    }

    public Boolean isLoginUserCommandValid(Matcher matcher, HashMap<String, String> enteredDetails) {
        boolean commandValidation = true;
        CommandCase commandCase;
        HashMap<String, String> inputParameters = new HashMap();
        inputParameters.put(matcher.group(1), matcher.group(2));
        inputParameters.put(matcher.group(3), matcher.group(4));
        ArrayList<String> longCommandParameters = new ArrayList<>();
        longCommandParameters.add("--username");
        longCommandParameters.add("--password");
        ArrayList<String> shortCommandParameters = new ArrayList<>();
        shortCommandParameters.add("-u");
        shortCommandParameters.add("-p");
        if (inputParameters.keySet().containsAll(longCommandParameters) && longCommandParameters.containsAll(inputParameters.keySet()))
            commandCase = CommandCase.LONG;
        else if (inputParameters.keySet().containsAll(shortCommandParameters) && shortCommandParameters.containsAll(inputParameters.keySet()))
            commandCase = CommandCase.SHORT;
        else {
            commandCase = CommandCase.INVALID;
            commandValidation = false;
        }
        if (commandCase.equals(CommandCase.LONG)) {
            enteredDetails.put("username", inputParameters.get("--username"));
            enteredDetails.put("password", inputParameters.get("--password"));
        }
        else if (commandCase.equals(CommandCase.SHORT)) {
            enteredDetails.put("username", inputParameters.get("-u"));
            enteredDetails.put("password", inputParameters.get("-p"));
        }
        return commandValidation;
    }

    public Boolean enterMenuRegex(String input, HashMap<String, String> enteredDetails) {
        Matcher matcher = getCommandMatcher(input, "menu enter (.+)$");
        if (matcher.find()) {
            enteredDetails.put("menuName", matcher.group(1));
            return true;
        } else return false;
    }

    public Boolean changeNicknameRegex(String input, HashMap<String, String> enteredDetails) {
        Matcher matcher = getCommandMatcher(input, "profile change --nickname (\\S+)$");
        if (matcher.find()) {
            enteredDetails.put("nickname", matcher.group(1));
            return true;
        } else return false;
    }

    public Boolean increaseMoneyRegex(String input, HashMap<String, String> enteredDetails) {
        Matcher matcher = getCommandMatcher(input, "increase --money ([\\d]+)$");
        if (matcher.find()) {
            enteredDetails.put("amount", matcher.group(1));
            return true;
        } else return false;
    }

    public Boolean changePasswordRegex(String input, HashMap<String, String> enteredDetails) {
        if (input.contains(" --password")) {
            input = input.replaceAll(" --password", "");
            Matcher matcher = getCommandMatcher(input, "profile change (\\S+) (\\S+) (\\S+) (\\S+)$");
            if (matcher.find())
                return isChangePasswordCommandValid(matcher, enteredDetails);
            else return false;
        } else return false;

    }

    public Boolean isChangePasswordCommandValid(Matcher matcher, HashMap<String, String> enteredDetails) {
        boolean commandValidation = true;
        CommandCase commandCase;
        HashMap<String, String> inputParameters = new HashMap();
        inputParameters.put(matcher.group(1), matcher.group(2));
        inputParameters.put(matcher.group(3), matcher.group(4));
        ArrayList<String> longCommandParameters = new ArrayList<>();
        longCommandParameters.add("--current");
        longCommandParameters.add("--new");
        ArrayList<String> shortCommandParameters = new ArrayList<>();
        shortCommandParameters.add("-c");
        shortCommandParameters.add("-n");
        if (longCommandParameters.containsAll(inputParameters.keySet()) && inputParameters.keySet().containsAll(longCommandParameters))
            commandCase = CommandCase.LONG;
        else if (inputParameters.keySet().containsAll(shortCommandParameters) && shortCommandParameters.containsAll(inputParameters.keySet()))
            commandCase = CommandCase.SHORT;
        else {
            commandCase = CommandCase.INVALID;
            commandValidation = false;
        }
        if (commandCase.equals(CommandCase.LONG)) {
            enteredDetails.put("current", inputParameters.get("--current"));
            enteredDetails.put("new", inputParameters.get("--new"));
        } else if (commandCase.equals(CommandCase.SHORT)) {
            enteredDetails.put("current", inputParameters.get("-c"));
            enteredDetails.put("new", inputParameters.get("-n"));
        }
        return commandValidation;
    }

    public Boolean createDeckRegex(String input, HashMap<String, String> enteredDetails) {
        Matcher matcher = getCommandMatcher(input, "deck create (\\S+)$");
        if (matcher.find()) {
            enteredDetails.put("name", matcher.group(1));
            return true;
        }
        return false;
    }

    public Boolean deleteDeckRegex(String input, HashMap<String, String> enteredDetails) {
        Matcher matcher = getCommandMatcher(input, "deck delete (\\S+)$");
        if (matcher.find()) {
            enteredDetails.put("name", matcher.group(1));
            return true;
        }
        return false;
    }

    public Boolean setActiveDeckRegex(String input, HashMap<String, String> enteredDetails) {
        Matcher matcher = getCommandMatcher(input, "deck set-active (\\S+)$");
        if (matcher.find()) {
            enteredDetails.put("name", matcher.group(1));
            return true;
        }
        return false;
    }

    public Boolean AddOrRemoveCardRegex(String input, HashMap<String, String> enteredDetails) {
        String sideOrMain = "main";
        if (input.contains(" --side")) {
            sideOrMain = "side";
            input = input.replaceAll(" --side", "");
        }
        Matcher deckMatcher = getCommandMatcher(input, "deck (\\S+) (--\\w{4}) ([\\w'\\-, ]+) (--\\w{4}) ([\\w ]+)$");
        if (deckMatcher.find()) {
            return isAddOrRemoveCardValid(deckMatcher, sideOrMain, enteredDetails);
        } else return false;
    }


    public Boolean showDeckRegex(String input, HashMap<String, String> enteredDetails) {
        String sideOrMain = "main";
        if (input.contains(" --side")) {
            sideOrMain = "side";
            input = input.replaceAll(" --side", "");
        }
        Matcher matcher = getCommandMatcher(input, "deck show --deck-name (\\S+)$");
        if (matcher.find()) {
            enteredDetails.put("type", sideOrMain);
            enteredDetails.put("deck", matcher.group(1));
            return true;
        } else return false;
    }

    public Boolean buyItemRegex(String input, HashMap<String, String> enteredDetails) {
        Matcher matcher = getCommandMatcher(input, "shop buy ([\\w'\\-, ]+)$");
        if (matcher.find()) {
            enteredDetails.put("name", matcher.group(1));
            return true;
        }
        return false;
    }


    public Boolean isAddOrRemoveCardValid(Matcher matcher, String sideOrMain, HashMap<String, String> enteredDetails) {
        boolean commandValidation = true;
        CommandCase commandCase;
        HashMap<String, String> inputParameters = new HashMap();
        inputParameters.put(matcher.group(2), matcher.group(3));
        inputParameters.put(matcher.group(4), matcher.group(5));
        ArrayList<String> longCommandParameters = new ArrayList<>();
        longCommandParameters.add("--card");
        longCommandParameters.add("--deck");
        ArrayList<String> shortCommandParameters = new ArrayList<>();
        shortCommandParameters.add("-c");
        shortCommandParameters.add("-d");
        if (inputParameters.keySet().containsAll(longCommandParameters) && longCommandParameters.containsAll(inputParameters.keySet())) {
            if (!inputParameters.get("--deck").contains(" "))
                commandCase = CommandCase.LONG;
            else {
                commandCase = CommandCase.INVALID;
                commandValidation = false;
            }
        } else if (inputParameters.keySet().containsAll(shortCommandParameters) && shortCommandParameters.containsAll(inputParameters.keySet())) {
            if (!inputParameters.get("--deck").contains(" "))
                commandCase = CommandCase.SHORT;
            else {
                commandCase = CommandCase.INVALID;
                commandValidation = false;
            }
        } else {
            commandCase = CommandCase.INVALID;
            commandValidation = false;
        }


        if (commandCase.equals(CommandCase.LONG)) {
            enteredDetails.put("card", inputParameters.get("--card"));
            enteredDetails.put("deck", inputParameters.get("--deck"));
            enteredDetails.put("type", sideOrMain);
        } else if (commandCase.equals(CommandCase.SHORT)) {
            enteredDetails.put("card", inputParameters.get("-c"));
            enteredDetails.put("deck", inputParameters.get("-d"));
            enteredDetails.put("type", sideOrMain);
        }
        return commandValidation;
    }

    public Boolean newDuelRegex(String input, HashMap<String, String> enteredDetails) {
        if (input.contains(" --new")) {
            input = input.replaceAll(" --new", "");
            Matcher matcher = getCommandMatcher(input, "\\bduel (\\S+) (\\S+) (\\S+) (\\S+)$");
            if (matcher.find())
                return isNewDuelCommandValid(matcher, enteredDetails);
            else return false;
        } else return false;

    }

    public Boolean isNewDuelCommandValid(Matcher matcher, HashMap<String, String> enteredDetails) {
        boolean commandValidation = true;
        CommandCase commandCase;
        HashMap<String, String> inputParameters = new HashMap();
        inputParameters.put(matcher.group(1), matcher.group(2));
        inputParameters.put(matcher.group(3), matcher.group(4));
        ArrayList<String> longCommandParameters = new ArrayList<>();
        longCommandParameters.add("--second-player");
        longCommandParameters.add("--rounds");
        ArrayList<String> shortCommandParameters = new ArrayList<>();
        shortCommandParameters.add("-s");
        shortCommandParameters.add("-r");
        if (longCommandParameters.containsAll(inputParameters.keySet()) && inputParameters.keySet().containsAll(longCommandParameters))
            commandCase = CommandCase.LONG;
        else if (inputParameters.keySet().containsAll(shortCommandParameters) && shortCommandParameters.containsAll(inputParameters.keySet()))
            commandCase = CommandCase.SHORT;
        else {
            commandCase = CommandCase.INVALID;
            commandValidation = false;
        }
        if (commandCase.equals(CommandCase.LONG)) {
            enteredDetails.put("rounds", inputParameters.get("--rounds"));
            enteredDetails.put("second player", inputParameters.get("--second-player"));
        } else if (commandCase.equals(CommandCase.SHORT)) {
            enteredDetails.put("rounds", inputParameters.get("-r"));
            enteredDetails.put("second player", inputParameters.get("-s"));
        }
        return commandValidation;
    }

    public Boolean newGameAiRegex(String input) {
        if (input.contains(" --new") && input.contains(" --ai")) {
            input = input.replaceAll(" --new", "");
            input = input.replaceAll(" --ai", "");
            Matcher matcher = getCommandMatcher(input, "\\bduel --rounds (\\d+)$");
//            if(matcher.find())
//            {
//                // startNewAiGame()  ?
//            }
//            else
            return false;
        } else return false;
    }

    public Boolean selectFromNumericZone(String input, HashMap<String, String> enteredDetails) {
        String opponentOrPlayer = "player";
        if (input.contains(" --opponent")) {
            opponentOrPlayer = "opponent";
            input = input.replaceAll(" --opponent", "");
        }
        Matcher matcher = getCommandMatcher(input, "select --(\\w+) (\\d+)$");
        if (matcher.find()) {
            enteredDetails.put("zone type", matcher.group(1));
            enteredDetails.put("opponentOrPlayer", opponentOrPlayer);
            enteredDetails.put("cardNumber", matcher.group(2));
            return true;
        } else return false;
    }


    public Boolean selectFromNotNumericZone(String input, HashMap<String, String> enteredDetails) {
        String opponentOrPlayer = "player";
        if (input.contains(" --opponent")) {
            opponentOrPlayer = "opponent";
            input = input.replaceAll(" --opponent", "");
        }
        Matcher matcher = getCommandMatcher(input, "select --(\\w+)");
        if (matcher.find()) {
            enteredDetails.put("zone type", matcher.group(1));
            enteredDetails.put("opponentOrPlayer", opponentOrPlayer);
            return true;
        } else return false;
    }


    private Matcher getCommandMatcher(String command, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(command);
        return matcher;
    }

    enum CommandCase {
        SHORT,
        LONG,
        INVALID
    }

}
