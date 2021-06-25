package controller.menuController;

import controller.responses.ImportExportResponses;
import controller.utilizationController.DatabaseController;
import model.cards.Card;

import java.io.IOException;
import java.util.ArrayList;

public class ImportExportController extends MenuController {
    public static ArrayList<Card> userCards;
    private static ImportExportController importExportController;
    DatabaseController databaseController = DatabaseController.getInstance();
    private Card chosenCard;

    public ImportExportController(String menuName) {
        super(menuName);
        deserialize();
        if (userCards == null || userCards.size()==0)
            serialize();
        else Card.setAllCards(userCards);
    }

    public static ImportExportController getInstance() {
        if (importExportController == null)
            importExportController = new ImportExportController("ImportExport Menu");
        return importExportController;
    }

    public void serialize() {
        try {
            databaseController.serializeCards();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deserialize() {
        userCards = databaseController.deserializeCards();
    }

    public ImportExportResponses importCard(String cardName) {
        deserialize();
        chosenCard = null;
        for (Card card : userCards)
            if (card.getCardName().equals(cardName)) chosenCard = card;
        if (chosenCard == null) return ImportExportResponses.CARD_NOT_FOUND;
        Card.getAllCards().add(chosenCard);
        serialize();
        return ImportExportResponses.CARD_IMPORT_SUCCESSFUL;
    }

    public ImportExportResponses exportCard(String cardName) {
        deserialize();
        chosenCard = null;
        for (Card card : Card.getAllCards())
            if (card.getCardName().equals(cardName)) chosenCard = card;
        if (chosenCard == null) return ImportExportResponses.CARD_NOT_FOUND;
        Card.getAllCards().remove(chosenCard);
        serialize();
        return ImportExportResponses.CARD_EXPORT_SUCCESSFUL;
    }

    public Card getChosenCard() {
        return chosenCard;
    }
}
