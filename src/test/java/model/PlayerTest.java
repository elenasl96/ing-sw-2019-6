package model;

import model.Player;
import model.enums.Character;
import model.enums.Color;
import model.enums.Phase;
import model.field.SpawnSquare;
import model.field.Square;
import model.moves.Move;
import model.moves.Pay;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import static org.junit.Assert.*;

class PlayerTest {
    @Test
    void nameTest() {
        Player pg = new Player(1, true);
        Square sq = new SpawnSquare(Color.YELLOW);
        Player pg2 = new Player (2, false);
        ArrayList<Player> sh = new ArrayList<>();
        sh.add(pg2);
        ArrayList<Move> mv = new ArrayList<>();
        Pay pay = new Pay();

        pg.setName("ugo");
        pg.setCharacter(Character.PG1);
        pg.setMotto("ullallà");
        pg.setCurrentPosition(sq);

        assertEquals(0, pg.getStackPoint());
        assertEquals(Phase.WAIT, pg.getPhase());
        assertEquals("ugo", pg.getName());
        assertEquals("ullallà", pg.getMotto());
        assertEquals("PG1", pg.getCharacter().name());
        assertEquals("BLUE", pg.getAmmos().get(0).getColor().name());
        assertEquals(1, pg.getId());
        assertEquals("YELLOW", pg.getCurrentPosition().getColor().name());
        assertEquals(0, pg.getPoints());
        assertEquals(0, pg.getAdrenalineLevel());
        assertTrue(pg.isFirstPlayer());
        assertFalse(pg.isDead());
        assertTrue(pg.getShootable().isEmpty());
        assertTrue(pg.getPossibleMoves().isEmpty());

        mv.add(pay);
        pg.setPossibleMoves(mv);
        pg.setShootable(sh);
        pg.addStackPoint(2);
        pg.setAdrenalineLevel(2);
        pg.addPoints(2);
        pg.setPhase(Phase.FIRST);
        pg.setDead(true);


        assertTrue(pg.getPossibleMoves().get(0) instanceof Pay);
        assertEquals(2, pg.getShootable().get(0).getId());
        assertTrue(pg.isDead());
        assertEquals(2, pg.getStackPoint());
        assertEquals(2, pg.getAdrenalineLevel());
        assertEquals(2, pg.getPoints());
        assertEquals(Phase.FIRST, pg.getPhase());
        assertTrue( pg.getPowerups().isEmpty());
        assertTrue(pg.getWeapons().isEmpty());
        assertEquals(0, pg.getPlayerBoard().getDeaths());
    }

    @Test
    void equalsTest(){
        Player p1 = new Player(1, true);
        Player p2 = new Player(2, false);

        assertEquals(p1, p1);
        assertNotEquals(null, p1);

        assertNotEquals(p1, null);
        assertEquals(p1, p2);

        Ammo ammo = new Ammo(Color.BLUE);

        assertNotEquals(p1,ammo);
    }
}
