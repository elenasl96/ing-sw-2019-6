package model.decks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class AmmoDeckTest {

    private AmmoDeck ammoDeck = new AmmoDeck();

    @Test
    void pickTest() {
        //Testing method pickCard
        AmmoTile picked = ammoDeck.pickCard();
        assertNotNull(picked);
    }

    @Test
    void discardTest() {
        //Testing method discardCard
        for(int i = 1; i <= 50 ; i++){
            assertDoesNotThrow(() -> ammoDeck.discardCard(ammoDeck.pickCard()));
        }
    }
}
