package model;

import model.decks.AmmoTile;
import model.decks.PowerupDeck;
import model.enums.Character;
import model.enums.Color;
import model.enums.Phase;
import model.field.Coordinate;
import model.field.SpawnSquare;
import model.field.Square;
import model.moves.Move;
import model.moves.Pay;
import model.room.User;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

class PlayerTest {
    Player pg = new Player(new User("ugo"));


    @Test
    void nameTest() {
        Square sq = new SpawnSquare(Color.YELLOW, new Coordinate('B',2));
        Player pg2 = new Player (new User("2"));
        Pay pay = new Pay();

        pg.setCurrentPosition(sq);

        assertEquals(Phase.WAIT, pg.getPhase());
        assertEquals("ugo", pg.getName());


        pg.addPoints(2);
        pg.setPhase(Phase.FIRST);
        pg.setDead(true);

        assertTrue(pg.isDead());
        assertEquals(Phase.FIRST, pg.getPhase());
        assertTrue( pg.getPowerups().isEmpty());
        assertTrue(pg.getWeapons().isEmpty());
        assertEquals(0, pg.getPlayerBoards(0).get(0).getDeaths());
    }

    @Test
    void powerUpsTest(){
        PowerupDeck d = new PowerupDeck();
        pg.getPowerups().add(d.pickCard());
        pg.powerupsToString();
    }

    @Test
    void equalsTest(){
        Player p1 = new Player(new User("pippo"));
        Player p2 = new Player(new User("pippo"));

        assertEquals(p1, p1);
        assertNotEquals(null, p1);

        assertNotEquals(p1, null);
        assertEquals(p1, p2);

        Ammo ammo = new Ammo(Color.BLUE);

        assertNotEquals(p1,ammo);
    }
}
