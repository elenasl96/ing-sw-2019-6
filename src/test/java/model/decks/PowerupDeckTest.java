package model.decks;

import model.Player;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

public class PowerupDeckTest {

    @Test
    void setPowerupDeck(){
        PowerupDeck powerupDeck = new PowerupDeck();
        System.out.println(powerupDeck.toString());

        Player player = new Player();
        for(int i = 0; i< 12; i++) {
            player.getPowerups().add(powerupDeck.pickCard());
        }

        powerupDeck.discardCard(player, player.getPowerups().get(0));
        powerupDeck.discardCard(player, player.getPowerups().get(1));
        powerupDeck.discardCard(player, player.getPowerups().get(2));

        player.getPowerups().add(powerupDeck.pickCard());
        assertEquals(2, powerupDeck.getPowerups().size() );


    }
}
