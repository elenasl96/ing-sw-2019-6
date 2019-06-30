package model;

import controller.GameController;
import model.decks.PowerupDeck;
import model.enums.Color;
import model.enums.Phase;
import model.exception.InvalidMoveException;
import model.exception.NotExistingFieldException;
import model.field.Coordinate;
import model.field.SpawnSquare;
import model.field.Square;
import model.moves.Pay;
import model.room.Group;
import model.room.User;
import network.exceptions.InvalidUsernameException;
import network.Manager;
import org.junit.jupiter.api.Test;

import static controller.GameController.cardsToString;
import static org.junit.Assert.*;

class PlayerTest {
    private Player pg = new Player(new User("ugo"));

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
        assertEquals(0, pg.getPlayerBoard().getDeaths());
    }

    @Test
    void powerUpsTest(){
        PowerupDeck d = new PowerupDeck();
        pg.getPowerups().add(d.pickCard());
        cardsToString(pg.getPowerups(), 0);
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

    @Test
    void canSeeTest() throws InvalidMoveException {
        GameContext.get().reset();
        Manager.get().reset();
        Manager.get().reset();
        GameContext.get().reset();
        GameContext.get().createGame(0);
        Group group0 = Manager.get().getGroup(0);
        try {
            Manager.get().createUser("user1");
            Manager.get().createUser("user2");
            Manager.get().createUser("user3");
            Manager.get().createUser("user4");
        } catch (InvalidUsernameException e) {
            e.printStackTrace();
        }
        for(int i = 0; i<4; i++) {
            group0.join(Manager.get().getUsers().get(i));
        }
        group0.createGame();
        group0.getGame().getCurrentPlayer().setCurrentPosition(group0.getGame().getBoard().getField().getSquares().get(0));
        group0.getGame().getPlayers().get(3).setCurrentPosition(group0.getGame().getBoard().getField().getSquares().get(1));

        assertTrue(group0.getGame().getCurrentPlayer().canSee(group0.getGame().getPlayers().get(3), group0.getGroupID()));
    }
}
