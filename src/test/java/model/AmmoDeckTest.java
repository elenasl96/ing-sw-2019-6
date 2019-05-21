package model;

import model.decks.AmmoDeck;
import model.decks.AmmoTile;
import org.junit.jupiter.api.Test;

class AmmoDeckTest {

    private AmmoDeck ammoDeck = new AmmoDeck();

    @Test
    void DeckTest(){

        for(int i = 1; i <= 25; i++){
            AmmoTile picked = ammoDeck.pickCard();
            System.out.println(ammoDeck);
            ammoDeck.discardCard(picked);
        }
    }
}
