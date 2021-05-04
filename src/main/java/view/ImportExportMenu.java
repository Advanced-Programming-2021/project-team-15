package view;

public class ImportExportMenu extends Menu{
    private static ImportExportMenu importExportMenu;
    private ImportExportMenu() {
        super("ImportExport Menu");
    }
    public static ImportExportMenu getInstance() {
        if(importExportMenu==null)
            importExportMenu = new ImportExportMenu();
        return importExportMenu;
    }


    @Override
    public void scanInput() {

    }
}
