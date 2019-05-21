package model;

import model.decks.AmmoDeck;
import model.decks.AmmoTile;
import org.junit.jupiter.api.Test;

class AmmoDeckTest {

    private AmmoDeck ammoDeck = new AmmoDeck();

    @Test
    void DeckTest(){

        for(int i = 1; i <= 50; i++){
            AmmoTile picked = ammoDeck.pickCard();
            ammoDeck.discardCard(picked);
        }
    }
}
