package controller;

import model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexController {
    LoginController loginController = new LoginController();
    MainMenuController mainMenuController =new MainMenuController();
    ProfileController profileController = new ProfileController();
    DeckController deckController = new DeckController();
    ShopController shopController = new ShopController();

    public Boolean createUserRegex(String input, HashMap<String,String> enteredDetails) {
        Matcher matcher = getCommandMatcher(input, "user create (\\S+) (\\S+) (\\S+) (\\S+) (\\S+) (\\S+)$");
        if(matcher.find()) {
           return isCreateUserCommandValid(matcher,enteredDetails);
        }
        else return false;
    }
        public Boolean isCreateUserCommandValid(Matcher matcher, HashMap<String,String> enteredDetails) {
         boolean commandValidation = true;
            CommandCase commandCase;
            HashMap<String ,String> inputParameters  = new HashMap();
            inputParameters.put(matcher.group(1) , matcher.group(2));
            inputParameters.put(matcher.group(3) ,matcher.group(4));
            inputParameters.put(matcher.group(5) , matcher.group(6));
            ArrayList<String> longCommandParameters = new ArrayList<>();
            longCommandParameters.add("--username");
            longCommandParameters.add("--nickname");
            longCommandParameters.add("--password");
            ArrayList<String> shortCommandParameters = new ArrayList<>();
            shortCommandParameters.add("-u");
            shortCommandParameters.add("-n");
            shortCommandParameters.add("-p");
            if(inputParameters.keySet().containsAll(longCommandParameters) &&  longCommandParameters.containsAll(inputParameters.keySet()))
                commandCase = CommandCase.LONG;
            else if(inputParameters.keySet().containsAll(shortCommandParameters) &&  shortCommandParameters.containsAll(inputParameters.keySet()))
                commandCase = CommandCase.SHORT;
            else {
                commandCase  = CommandCase.INVALID;
                commandValidation = false;
            }
            if (commandCase.equals(CommandCase.LONG)) {
                enteredDetails.put("username", inputParameters.get("--username"));
                enteredDetails.put("nickname", inputParameters.get("--nickname"));
                enteredDetails.put("password", inputParameters.get("--password"));
            }
                //loginController.registerUser(inputParameters.get("--username") , inputParameters.get("--nickname") , inputParameters.get("--password"));
            else if(commandCase.equals(CommandCase.SHORT)) {
                enteredDetails.put("username", inputParameters.get("-u"));
                enteredDetails.put("nickname", inputParameters.get("-n"));
                enteredDetails.put("password", inputParameters.get("-p"));
            }
                //loginController.registerUser(inputParameters.get("-u") , inputParameters.get("-n") , inputParameters.get("-p"));
            return commandValidation;
        }
        public Boolean showMenuRegex(String input)
        { Matcher matcher = getCommandMatcher(input, "menu ((show-current)|(current-show))$");
            if(matcher.find())
                return true;
            else return false;
        }
        public Boolean loginUserRegex(String input, HashMap<String, String> enteredDetails)
        {
            Matcher matcher = getCommandMatcher(input, "user login (\\S+) (\\S+) (\\S+) (\\S+)$");
            if(matcher.find()) {
                return isLoginUserCommandValid(matcher,enteredDetails);
            }
            else return false;
        }
        public Boolean isLoginUserCommandValid(Matcher matcher, HashMap<String, String> enteredDetails)
        {  boolean commandValidation = true;
            CommandCase commandCase;
            HashMap<String ,String> inputParameters  = new HashMap();
            inputParameters.put(matcher.group(1) , matcher.group(2));
            inputParameters.put(matcher.group(3) ,matcher.group(4));
            ArrayList<String> longCommandParameters = new ArrayList<>();
            longCommandParameters.add("--username");
            longCommandParameters.add("--password");
            ArrayList<String> shortCommandParameters = new ArrayList<>();
            shortCommandParameters.add("-u");
            shortCommandParameters.add("-p");
            if(inputParameters.keySet().containsAll(longCommandParameters) &&  longCommandParameters.containsAll(inputParameters.keySet()))
                commandCase = CommandCase.LONG;
            else if(inputParameters.keySet().containsAll(shortCommandParameters) &&  shortCommandParameters.containsAll(inputParameters.keySet()))
                commandCase = CommandCase.SHORT;
            else {
                commandCase  = CommandCase.INVALID;
                commandValidation = false;
            }
            if (commandCase.equals(CommandCase.LONG)) {
                enteredDetails.put("username",inputParameters.get("--username"));
                enteredDetails.put("password",inputParameters.get("--password"));
            }
//                loginController.loginUser(inputParameters.get("--username") , inputParameters.get("--password"));
            else if(commandCase.equals(CommandCase.SHORT)) {
                enteredDetails.put("username",inputParameters.get("-u"));
                enteredDetails.put("password",inputParameters.get("-p"));
            }
//                loginController.loginUser(inputParameters.get("-u") , inputParameters.get("-p"));
            return commandValidation;
        }
        public Boolean enterMenuRegex(String input) {
        Matcher matcher = getCommandMatcher(input , "menu enter (\\.+)$");
            if(matcher.find()) {
                mainMenuController.menuEnter(matcher.group(1));
                return true;
            }
            else return false;
        }
        public Boolean changeNicknameRegex(String input, HashMap<String, String> enteredDetails) {
            Matcher matcher = getCommandMatcher(input , "profile change --nickname (\\S+)$");
            if(matcher.find())
            {
                enteredDetails.put("nickname" , matcher.group(1));
                return true;
            }
            else return false;
        }
        public Boolean changePasswordRegex(String input, HashMap<String, String> enteredDetails) {
               if(input.contains(" --password")) {
                input = input.replaceAll(" --password","");
                Matcher matcher = getCommandMatcher(input, "profile change (\\S+) (\\S+) (\\S+) (\\S+)$");
                if (matcher.find())
                    return isChangePasswordCommandValid(matcher , enteredDetails);
                else return false;
            }
            else return false;

        }
        public Boolean isChangePasswordCommandValid(Matcher matcher, HashMap<String, String> enteredDetails)
        { boolean commandValidation = true;
            CommandCase commandCase;
            HashMap<String ,String> inputParameters  = new HashMap();
            inputParameters.put(matcher.group(1) , matcher.group(2));
            inputParameters.put(matcher.group(3) ,matcher.group(4));
            ArrayList<String> longCommandParameters = new ArrayList<>();
            longCommandParameters.add("--current");
            longCommandParameters.add("--new");
            ArrayList<String> shortCommandParameters = new ArrayList<>();
            shortCommandParameters.add("-c");
            shortCommandParameters.add("-n");
            if( longCommandParameters.containsAll(inputParameters.keySet()) && inputParameters.keySet().containsAll(longCommandParameters))
                commandCase = CommandCase.LONG;
            else if(inputParameters.keySet().containsAll(shortCommandParameters) &&  shortCommandParameters.containsAll(inputParameters.keySet()))
                commandCase = CommandCase.SHORT;
            else {
                commandCase  = CommandCase.INVALID;
                commandValidation = false;
            }
            if (commandCase.equals(CommandCase.LONG))
            {
                enteredDetails.put("current" ,inputParameters.get("--current"));
                enteredDetails.put("new" , inputParameters.get("--new"));
            }
            else if(commandCase.equals(CommandCase.SHORT))
            {   enteredDetails.put("current" ,inputParameters.get("-c"));
                enteredDetails.put("new" , inputParameters.get("-n"));
            }
            return commandValidation;
        }
        public Boolean createDeckRegex(String input)
        {
            Matcher matcher =  getCommandMatcher(input , "deck create (\\S+)$");
            if(matcher.find())
            {  deckController.createDeck(matcher.group(1));
                return true;
            }
            return false;
        }
    public Boolean deleteDeckRegex(String input)
    {
        Matcher matcher =  getCommandMatcher(input , "deck delete (\\S+)$");
        if(matcher.find())
        {  deckController.removeDeck(matcher.group(1));
            return true;
        }
        return false;
    }
    public Boolean setActiveDeckRegex(String input)
    {
        Matcher matcher  = getCommandMatcher(input , "deck set-active (\\S+)$");
                if(matcher.find())
                {
                    deckController.setActiveDeck(matcher.group(1));
                    return true;
                }
                return false;
    }
    public Boolean addCardToDeckRegex(String input , String addOrRemove)
    {
        String sideOrMain = "main";
        if(input.contains(" --side")) {
            sideOrMain = "side";
            input = input.replaceAll(" --side", "");
        }
            Matcher deckMatcher = getCommandMatcher(input , "deck (\\S+) (\\S+) (\\S+) (\\S+) (\\S+)$");
            if(deckMatcher.find())
                return isAddCardToDeckValid(deckMatcher  , addOrRemove , sideOrMain);
            else return false;
    }
    public Boolean showCardsOfUserRegex(String input)

        { if(input.equals("\\bdeck show --cards$"))
        {
            //show cards of the  user
            return true;}
        else return false;
        }

    public Boolean showAllDecksRegex(String input)
    {
        if(input.equals("\\bdeck show --all"))
        {
            deckController.showAllDecksOfTheUser();
            return true;
        }
        return false;
    }
    public Boolean showDeckRegex(String input)
    {
        String sideOrMain = "main";
        if(input.contains(" --side")) {
            sideOrMain = "side";
            input = input.replaceAll(" --side", "");
        }
        Matcher matcher = getCommandMatcher(input , "\\bdeck show --deck-name (\\S+)$");
        if(matcher.find()) {
            deckController.showThisDeckOfTheUser();  // voroodiezafe shavad
            return true;}
        else return false;
    }
    public Boolean buyItemRegex(String input)
    { Matcher matcher = getCommandMatcher(input , "\\bshop buy (\\S+)$");
        if(matcher.find())
        { shopController.buyItem(matcher.group(1));
            return true;
        }
       return false;
    }
    public Boolean showAllCards(String input)
    {  if(input.equals("shop show --all"))
    {
        shopController.showAllCards();
        return true;
    }
    return false;
    }



    public Boolean isAddCardToDeckValid(Matcher matcher , String addOrRemove  ,String sideOrMain)
    {   boolean commandValidation = true;
        CommandCase commandCase;
        HashMap<String ,String> inputParameters  = new HashMap();
        inputParameters.put(matcher.group(2) , matcher.group(3));
        inputParameters.put(matcher.group(4) ,matcher.group(5));
        ArrayList<String> longCommandParameters = new ArrayList<>();
        longCommandParameters.add("--card");
        longCommandParameters.add("--deck");
        ArrayList<String> shortCommandParameters = new ArrayList<>();
        shortCommandParameters.add("-c");
        shortCommandParameters.add("-d");
        if(inputParameters.keySet().containsAll(longCommandParameters) &&  longCommandParameters.containsAll(inputParameters.keySet()))
            commandCase = CommandCase.LONG;
        else if(inputParameters.keySet().containsAll(shortCommandParameters) &&  shortCommandParameters.containsAll(inputParameters.keySet()))
            commandCase = CommandCase.SHORT;
        else {
            commandCase  = CommandCase.INVALID;
            commandValidation = false;
        }

        if (commandCase.equals(CommandCase.LONG)) {
            if(addOrRemove.equals("add"))
            deckController.addCardToDeck(inputParameters.get("--card"), inputParameters.get("--deck"), sideOrMain);
            else deckController.removeCardFromDeck(inputParameters.get("--card"), inputParameters.get("--deck"), sideOrMain);
        }
        else if(commandCase.equals(CommandCase.SHORT)) {
            if(addOrRemove.equals("add"))
            deckController.addCardToDeck(inputParameters.get("-c"), inputParameters.get("-d"), sideOrMain);
            else deckController.removeCardFromDeck(inputParameters.get("-c"), inputParameters.get("-d"), sideOrMain);
        }
        return commandValidation;
    }



    private  Matcher getCommandMatcher(String command, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(command);
        return matcher;
    }
    enum CommandCase
    {  SHORT ,
        LONG ,
        INVALID
    }

}
