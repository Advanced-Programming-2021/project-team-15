package controller;

import controller.responses.DeckMenuResponses;
import controller.responses.DuelMenuResponses;
import model.Card;
import model.MonsterCard;
import view.DuelMenu;

import java.util.Map;

import static controller.responses.DuelMenuResponses.*;

public class SpellEffectController extends GamePlayController {
    DuelMenu duelMenu = DuelMenu.getInstance();
    //TODO : FIRST 4 EFFECTS SHOULD HE CHECKED DUHHHHHHH

    public void yami() {
        effectController.addATK(MonsterCard.MonsterType.FIEND, true, 200);
        effectController.addATK(MonsterCard.MonsterType.SPELL_CASTER, true, 200);
        effectController.addATK(MonsterCard.MonsterType.FAIRY, true, -200);

    }

    public void forest() {
        effectController.addATK(MonsterCard.MonsterType.BEAST, true, 200);
        effectController.addDEF(MonsterCard.MonsterType.BEAST, true, 200);
        effectController.addATK(MonsterCard.MonsterType.INSECT, true, 200);
        effectController.addDEF(MonsterCard.MonsterType.INSECT, true, 200);
        effectController.addATK(MonsterCard.MonsterType.BEAST_WARRIOR, true, 200);
        effectController.addDEF(MonsterCard.MonsterType.BEAST_WARRIOR, true, 200);
    }

    public void closedForest() {
        int i = 0;
        for (Card card : currentPlayer.getGraveyardZone().getZoneCards()) {
            if (card instanceof MonsterCard)
                i++;
        }
        effectController.addATK(MonsterCard.MonsterType.BEAST, false, i * 100);
    }

    public void umiiruka()
    {
        effectController.addATK(MonsterCard.MonsterType.AQUA, true,500);
        effectController.addATK(MonsterCard.MonsterType.AQUA, true,-400);
    }
    public void swordOfDarkDestruction()
    {   int i = 0 ;
        int j= 0 ;
        Map<Integer , MonsterCard> map = currentPlayer.getMonsterCardZone().getZoneCards();
        for (Map.Entry<Integer , MonsterCard> entry :map.entrySet())
        {
            if(entry.getValue().getMonsterType()== MonsterCard.MonsterType.FIEND && !entry.getValue().getHidden())
                i++;
            else if(entry.getValue().getMonsterType()==MonsterCard.MonsterType.SPELL_CASTER  && !entry.getValue().getHidden())
                j++;
        }
        if(i==0 && j==0)
            duelMenu.printResponse(CANT_ACTIVATE_SPELL);
        else duelMenu.printResponse(ENTER_ONE_NUMBER);
    }
    public DuelMenuResponses swordOfDarkDestructionChoosing(int num)
    {  Map<Integer , MonsterCard> map = currentPlayer.getMonsterCardZone().getZoneCards();
        if((map.get(num).getMonsterType()!= MonsterCard.MonsterType.FIEND) &&
                (map.get(num).getMonsterType()!= MonsterCard.MonsterType.FAIRY) )
        return DuelMenuResponses.NOT_WANTED_TYPE;
        else {
            map.get(num).setAttackPoint( map.get(num).getAttackPoint()+400);
            map.get(num).setDefensePoint(map.get(num).getDefensePoint()-200);
            return CARD_EQUIPPED;
        }
    }





}
