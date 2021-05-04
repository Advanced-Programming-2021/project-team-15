package view;

import controller.DeckController;

import java.util.HashMap;
import java.util.Scanner;

public class DeckMenu extends Menu {
    private static DeckMenu deckMenu;
   // DeckMenuResponses responses;
    private DeckController deckController = new DeckController();
    private DeckMenu()
    {
        super("Deck Menu");
    }
    public static DeckMenu getInstance()
    {
        if(deckMenu==null)
            deckMenu = new DeckMenu();
        return deckMenu;
    }



    @Override
    public void scanInput() {
        Scanner scanner = new Scanner(System.in);
        while (true)
        {  String input = scanner.nextLine();
            if (input.equals("menu exit")) checkAndCallMenuExit();
            else if(input.startsWith("deck create")) checkAndCallCreateDeck(input);
            else if(input.startsWith("deck delete")) checkAndCallDeleteDeck(input);
            else if(input.startsWith("deck set-active")) checkAndCallSetActiveDeck(input);
            else if(input.startsWith("deck add-card")) checkAndCallAddOrRemoveCard(input , "add");
            else if(input.startsWith("deck rm-card"))  checkAndCallAddOrRemoveCard(input ,"remove");
            else if(input.equals("deck show --cards")
                //?
                else if(input.equals("deck show --all"))
                    //?
                    else if(input.startsWith("deck show")) checkAndCallShowThisDeck(input);
        else if (regexController.showMenuRegex(input)) checkAndCallShowCurrentMenu();
            else System.out.println("invalid command");
            if (super.isExit) {
                super.isExit = false;
                return;
            }

        }

    }
    public void checkAndCallAddOrRemoveCard(String input , String addOrRemove)
    {     HashMap<String, String> enteredDetails = new HashMap<>();
           if(!regexController.AddOrRemoveCardRegex(input , enteredDetails))
               System.out.println("invalid command");
           else {
               String deckName = enteredDetails.get("deck");
               String carName = enteredDetails.get("card");
               String deckType =enteredDetails.get("type");
                if(addOrRemove.equals("add"))
               //responses = deckController.addCardToDeck(carName ,deckName ,deckType);
                    else if(addOrRemove.equals("remove"))
               //responses = deckController.removeCardFromDeck(carName ,deckName ,deckType);
               //printResponse(responses);
           }


    }
    public void checkAndCallShowThisDeck(String input)
    { HashMap<String, String> enteredDetails = new HashMap<>();
        if(!regexController.showDeckRegex(input , enteredDetails))
            System.out.println("invalid command");
       else  {
           String deckName=  enteredDetails.get("deck");
           String deckType = enteredDetails.get("type");
            //?
        }

    }
    public void checkAndCallCreateDeck(String input)
    {  HashMap<String, String> enteredDetails = new HashMap<>();
        if(!regexController.createDeckRegex(input , enteredDetails))
            System.out.println("invalid command");
        else {
            String deckName = enteredDetails.get("name");
           // responses = deckController.createDeck(deckName);
           // printResponse(responses);
        }
    }
    public void checkAndCallDeleteDeck(String input)
    {  HashMap<String, String> enteredDetails = new HashMap<>();
        if(!regexController.deleteDeckRegex(input , enteredDetails))
            System.out.println("invalid command");
        else {
            String deckName = enteredDetails.get("name");
            // responses = deckController.removeDeck(deckName);
            // printResponse(responses);
        }
    }
    public void checkAndCallSetActiveDeck(String input)
    {  HashMap<String, String> enteredDetails = new HashMap<>();
        if(!regexController.setActiveDeckRegex(input , enteredDetails))
            System.out.println("invalid command");
        else {
            String deckName = enteredDetails.get("name");
            // responses = deckController.setActiveDeck(deckName);
            // printResponse(responses);
        }

    }

    //protected void printResponse( DeckMenuResponses deckMenuResponses)
    //{

    //}
}
