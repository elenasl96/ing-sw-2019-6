import model.Ammo;
import model.Player;
import model.enums.Character;
import model.enums.Color;
import model.enums.Phase;
import model.field.SpawnSquare;
import model.field.Square;
import model.moves.Move;
import model.moves.Pay;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class PlayerTest {
    private Player john;

    @Before
    public void createPlayer(){

    }
    @Test
    public void nameTest() {
        Ammo a1 = new Ammo(Color.YELLOW);
        Player pg = new Player(1, true);
        Square sq = new SpawnSquare(Color.YELLOW, null);
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
        assertEquals(0, pg.getAdrenalinelevel());
        assertTrue(pg.isFirstPlayer());
        assertFalse(pg.isDead());
        assertTrue(pg.getShootable().isEmpty());
        assertTrue(pg.getPossibleMoves().isEmpty());

        mv.add(pay);
        pg.setPossibleMoves(mv);
        pg.setShootable(sh);
        pg.addStackPoint(2);
        pg.setAdrenalinelevel(2);
        pg.addPoints(2);
        pg.setPhase(Phase.FIRST);
        pg.setDead(true);


        assertTrue(pg.getPossibleMoves().get(0) instanceof Pay);
        assertEquals(2, pg.getShootable().get(0).getId());
        assertTrue(pg.isDead());
        assertEquals(2, pg.getStackPoint());
        assertEquals(2, pg.getAdrenalinelevel());
        assertEquals(2, pg.getPoints());
        assertEquals(Phase.FIRST, pg.getPhase());
        assertTrue( pg.getPowerups().isEmpty());
        assertTrue(pg.getWeapons().isEmpty());
        assertEquals(0, pg.getPlayerBoard().getDeaths());
    }
}
