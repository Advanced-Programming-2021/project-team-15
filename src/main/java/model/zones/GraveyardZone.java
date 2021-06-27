package model.zones;

import controller.gamePlayController.GamePlayController;
import controller.gamePlayController.SpellEffectController;
import model.Player;
import model.cards.Card;
import model.cards.MagicCard;
import model.cards.MonsterCard;

import java.util.ArrayList;

public class GraveyardZone extends Zone {
    ArrayList<Card> zoneCards;
    private SpellEffectController spellEffectController = new SpellEffectController();

    public GraveyardZone() {
        super(ZoneType.GRAVEYARD);
        zoneCards = new ArrayList<>();
    }

    public void reset() {
        zoneCards.clear();
    }

    public void addCardToGraveyardZone(Card card) {
        if (card.getCardName().equals("Yami") && card.isActivated())
            spellEffectController.yami(false, (MagicCard) card);
        else if (card.getCardName().equals("Forest") && card.isActivated())
            spellEffectController.forest(false, (MagicCard) card);
        else if (card.getCardName().equals("Closed Forest") && card.isActivated())
            spellEffectController.closedForest(false, card);
        else if (card.getCardName().equals("Umiiruka") && card.isActivated())
            spellEffectController.forest(false, (MagicCard) card);
        GamePlayController.getInstance().getActivatedCards().values().remove(card);
        if (card instanceof MonsterCard) {
            ((MonsterCard) card).setGameDEF(((MonsterCard) card).getDefensePoint());
            ((MonsterCard) card).setGameATK(((MonsterCard) card).getAttackPoint());
            for (int i = 1; i <= 5; i++) {
                Player player = card.getOwner();
                MagicCard magicCard = (MagicCard) player.getMagicCardZone().getZoneCards().get(i);
                if (magicCard != null && magicCard.getCardName().equals("Closed Forest") && magicCard.isActivated())
                    GamePlayController.getSpellEffectController().closedForestForNewCardAddedToGraveyard((MonsterCard) card, magicCard);
            }
        }
        resetCard(card);
        card.setCardPlacedZone(this);
        zoneCards.add(card);
    }

    public void resetCard(Card card) {
        if (card instanceof MonsterCard)
            ((MonsterCard) card).setSummoned(false, false);
        card.setSet(false);
        card.setActivated(false);
    }

    public void removeCardFromGraveyardZone(Card card) {
        zoneCards.remove(card);
    }

    public ArrayList<Card> getZoneCards() {
        return zoneCards;
    }

    public void setZoneCards(ArrayList<Card> zoneCards) {
        this.zoneCards = zoneCards;
    }
}