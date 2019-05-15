package controller;

import exception.InvalidMoveException;
import model.GameContext;
import model.enums.Phase;
import model.field.Coordinate;
import model.moves.Run;
import model.room.Group;
import model.room.User;
import network.exceptions.InvalidUsernameException;
import network.socket.Manager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GameControllerTest {
    private ArrayList<User> users;
    private Group group0 =  Manager.get().getGroup(0);;

    @BeforeEach
    void start(){
        Manager.get().reset();
        GameContext.get().reset();
        GameContext.get().createGame();
        users = new ArrayList<>();
        users.add(new User("user1"));
        users.add(new User("user2"));
        users.add(new User("user3"));
        users.add(new User("user4"));
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

    }

    @Test
    void constructorTest(){
        assertEquals(users.get(0), GameContext.get().getGame(0).getCurrentPlayer().getUser());
        assertEquals(Phase.SPAWN, GameContext.get().getGame(0).getCurrentPlayer().getPhase());
        assertEquals(Phase.WAIT, GameContext.get().getGame(0).getPlayers().get(1).getPhase());
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
}
