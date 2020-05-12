package pers.dafagame.cardLogics.brnn;

import java.util.List;

public class CardsAndType {
    public List<BrnnCard> cards;

    public BrnnCardsType type;

    public List<BrnnCard> getCards() {
        return cards;
    }

    public void setCards(List<BrnnCard> cards) {
        this.cards = cards;
    }

    public BrnnCardsType getType() {
        return type;
    }

    public void setType(BrnnCardsType type) {
        this.type = type;
    }
}
