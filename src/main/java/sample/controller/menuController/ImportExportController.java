package sample.controller.menuController;

import sample.controller.responses.ImportExportResponses;
import sample.controller.utilizationController.DatabaseController;
import sample.model.cards.Card;
import sample.model.cards.MagicCard;
import sample.model.cards.MonsterCard;

import java.io.File;
import java.io.IOException;

public class ImportExportController extends MenuController {
    private static ImportExportController importExportController;
    DatabaseController databaseController = DatabaseController.getInstance();

    public ImportExportController(String menuName) {
        super(menuName);
    }

    public static ImportExportController getInstance() {
        if (importExportController == null)
            importExportController = new ImportExportController("ImportExport Menu");
        return importExportController;
    }

    public ImportExportResponses importCard(String cardName) throws IOException {
        File cardFile = new File("src/main/resources/Cards/"+cardName+".json");
        if (!cardFile.exists()) return ImportExportResponses.CARD_NOT_FOUND;
        if (Card.getCardByName(cardName)!=null) return ImportExportResponses.CARD_ALREADY_EXISTS;
        Card card = databaseController.deserializeCard(cardName);
        if (card.getCardType()== Card.CardType.MONSTER)
            databaseController.writeMonsterCardToCSV((MonsterCard) card);
        else if (card.getCardType()== Card.CardType.MAGIC)
            databaseController.writeMagicCardToCSV((MagicCard) card);
        else System.out.println("rid");
        Card.getAllCards().add(card);
        return ImportExportResponses.CARD_IMPORT_SUCCESSFUL;
    }

    public ImportExportResponses exportCard(String cardName) throws IOException {
        Card card = Card.getCardByName(cardName);
        if (card==null) return ImportExportResponses.CARD_NOT_FOUND;
        databaseController.serializeCard(card);
        return ImportExportResponses.CARD_EXPORT_SUCCESSFUL;
    }

}
