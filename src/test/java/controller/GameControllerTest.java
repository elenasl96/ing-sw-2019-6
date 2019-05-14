package controller;

import exception.InvalidMoveException;
import model.GameContext;
import model.enums.Phase;
import model.field.Coordinate;
import model.moves.Run;
import model.room.Group;
import model.room.User;
import network.socket.Manager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GameControllerTest {
    private ArrayList<User> users;

    @BeforeEach
    void start(){
        users = new ArrayList<>();
        users.add(new User("user1"));
        users.add(new User("user2"));
        users.add(new User("user3"));
        users.add(new User("user4"));
    }

    @Test
    void constructorTest(){
        assertEquals(users.get(0), GameContext.get().getGame(0).getCurrentPlayer().getUser());
        assertEquals(Phase.SPAWN, GameContext.get().getGame(0).getCurrentPlayer().getPhase());
    }

    @Test
    void runMoveHandleTest(){
        Run invalidRun = new Run(new Coordinate('D', 4));
        assertThrows(InvalidMoveException.class, () ->
                GameController.get().handle(invalidRun, 0) );
        Run run = new Run(new Coordinate('A', 2));
        GameContext.get().getGame(0).getCurrentPlayer().setCurrentPosition(
                GameContext.get().getGame(0).getBoard().getField().getSquares().get(0));
        assertDoesNotThrow(() -> GameController.get().handle(run, 0) );
    }

    @Test
    void spawnTest(){
        assertTrue(users.get(0).getPlayer().getPowerups().isEmpty());
        users.get(0).getPlayer().getPowerups().add(GameContext.get().getGame(0).getBoard().getPowerupsLeft().pickCard());
        assertFalse(users.get(0).getPlayer().getPowerups().isEmpty());
        System.out.println(users.get(0).getPlayer().getPowerups().toString());
    }
}
