package sample.view;

import sample.controller.menuController.ImportExportController;
import sample.controller.responses.ImportExportResponses;
import sample.controller.utilizationController.UtilityController;

import java.io.IOException;
import java.util.HashMap;

public class ImportExportMenu extends Menu {
    private static ImportExportMenu importExportMenu;
    private final ImportExportController importExportController = ImportExportController.getInstance();

    private ImportExportMenu() {
        super("ImportExport Menu");
    }

    public static ImportExportMenu getInstance() {
        if (importExportMenu == null)
            importExportMenu = new ImportExportMenu();
        return importExportMenu;
    }

    @Override
    public void scanInput() throws IOException {
        while (true) {
            String input = UtilityController.getNextLine();
            if (input.startsWith("import") ||
                    input.startsWith("export")) checkAndCallImportExportCard(input);
            else if (input.equals("menu exit")) checkAndCallMenuExit();
            else if (regexController.showMenuRegex(input)) checkAndCallShowCurrentMenu();
            else if (input.startsWith("menu enter ")) System.out.println("Navigation is not possible hear");
            else System.out.println("invalid command");
            if (super.isExit) {
                super.isExit = false;
                return;
            }
        }
    }

    public void checkAndCallImportExportCard(String input) throws IOException {
        HashMap<String, String> enteredDetails = new HashMap<>();
        if (!regexController.importExportRegex(input, enteredDetails))
            System.out.println("invalid command");
        else {
            String cardName = enteredDetails.get("name");
            if (enteredDetails.get("action").equals("import"))
                printResponse(importExportController.importCard(cardName));
            else printResponse(importExportController.exportCard(cardName));
        }
    }

    private void printResponse(ImportExportResponses importExportResponses) {
        String output = "";
        switch (importExportResponses) {
            case CARD_NOT_FOUND:
                output = "card with this name doesn't exist";
                break;
            case CARD_EXPORT_SUCCESSFUL:
                output = "card exported successfully";
                break;
            case CARD_IMPORT_SUCCESSFUL:
                output = "card imported successfully";
                break;
            case CARD_ALREADY_EXISTS:
                output = "card with this name already exists";
                break;
        }
        System.out.println(output);
    }
}