package controller;

import model.MagicCard;
import model.MonsterCard;
import view.DuelMenu;

import java.util.Map;

import static controller.responses.DuelMenuResponses.SPELL_ACTIVATED;

public class MonsterEffectController {
    GamePlayController gamePlayController  =  GamePlayController.getInstance();
    EffectController effectController  = gamePlayController.getEffectController();
     DuelMenu duelMenu = DuelMenu.getInstance();
    public void commandKnight(Boolean x)
    { gamePlayController.getSelectedCard().setHidden(false);
        duelMenu.printResponse(SPELL_ACTIVATED);
        int amount = 400;
        if(!x)
            amount = -amount;
        Map<Integer, MonsterCard> map = gamePlayController.getCurrentPlayer().getMonsterCardZone().getZoneCards();
        for (Map.Entry<Integer, MonsterCard> entry : map.entrySet()) {
            if (entry.getValue() != null && entry.getValue()!=gamePlayController.getSelectedCard())
                entry.getValue().setGameATK(entry.getValue().getGameATK() + amount);
        }
    if(gamePlayController.getCurrentPlayer().getMonsterCardZone().getNumberOfCard()==2)
        gamePlayController.getAttackController().getCantBeAttacked().add((MonsterCard) gamePlayController.getSelectedCard());
    }






}
