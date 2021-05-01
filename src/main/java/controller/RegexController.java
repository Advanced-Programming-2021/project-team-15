package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexController {
    LoginController loginController = new LoginController("Login Menu");

    public Boolean userRegex(String input)
    { Matcher matcher = getCommandMatcher(input, "user create (\\S+) (\\S+) (\\S+) (\\S+) (\\S+) (\\S+)$");
        if(matcher.find()) {
           return isCreateUserCommandValid(matcher);
        }
        else return false;
    }
        public Boolean isCreateUserCommandValid(Matcher matcher)
        {   boolean commandValidation = true;
            CommandCase commandCase;
            HashMap<String ,String> inputParameters  = new HashMap();
            inputParameters.put(matcher.group(1) , matcher.group(2));
            inputParameters.put(matcher.group(3) ,matcher.group(4));
            inputParameters.put(matcher.group(5) , matcher.group(6));
            ArrayList<String> longCommandParameters = new ArrayList<>();
            longCommandParameters.add("--username");
            longCommandParameters.add("--nickname");
            longCommandParameters.add("--password");
            ArrayList<String> shortCommand = new ArrayList<>();
            shortCommand.add("-u");
            shortCommand.add("-n");
            shortCommand.add("-p");
            if(inputParameters.keySet().containsAll(longCommandParameters) &&  longCommandParameters.containsAll(inputParameters.keySet()))
                commandCase = CommandCase.LONG;
            else if(inputParameters.keySet().containsAll(shortCommand) &&  shortCommand.containsAll(inputParameters.keySet()))
                commandCase = CommandCase.SHORT;
            else {
                commandCase  = CommandCase.INVALID;
                commandValidation = false;
            }
            if (commandCase.equals(CommandCase.LONG))
                loginController.registerUser(inputParameters.get("--username") , inputParameters.get("--nickname") , inputParameters.get("--password"));
            else if(commandCase.equals(CommandCase.SHORT))
                loginController.registerUser(inputParameters.get("-u") , inputParameters.get("-n") , inputParameters.get("-p"));
            return commandValidation;
        }
        public Boolean showMenuRegex(String input)
        { Matcher matcher = getCommandMatcher(input, "menu ((show-current)|(current-show))$");
            if(matcher.find())
                return true;
            else return false;
        }


    private static Matcher getCommandMatcher(String command, String regex) {
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
