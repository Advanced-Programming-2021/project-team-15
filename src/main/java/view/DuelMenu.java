package view;

public class DuelMenu extends Menu{
    private static DuelMenu duelMenu;
    private DuelMenu()
    {
        super("Duel Menu");
    }
    public static DuelMenu getInstance()
    {
        if(duelMenu==null)
            duelMenu  =new DuelMenu();
        return duelMenu;
    }

    @Override
    public void scanInput() {

    }
}
