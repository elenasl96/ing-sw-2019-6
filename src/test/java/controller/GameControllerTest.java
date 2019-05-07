package controller;

import exception.InvalidMoveException;
import model.Game;
import model.enums.Phase;
import model.field.Coordinate;
import model.moves.Run;
import model.room.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GameControllerTest {

    private GameController gameController;
    private Game game;
    private ArrayList<User> users;

    @BeforeEach
    void start(){
        users = new ArrayList<>();
        users.add(new User("user1"));
        users.add(new User("user2"));
        users.add(new User("user3"));
        users.add(new User("user4"));

        game = new Game(5,1, users);
        gameController = new GameController(game);
    }

    @Test
    void constructorTest(){
        assertEquals(users.get(0), gameController.currentPlayer.getUser());
        assertEquals(Phase.SPAWN, gameController.currentPlayer.getPhase());
    }

    @Test
    void runMoveHandleTest(){
        gameController.currentPlayer.setFirstPlayer(false);
        Run invalidRun = new Run(new Coordinate('D', 4));
        assertThrows(InvalidMoveException.class, () ->
                gameController.handle(invalidRun) );
        Run run = new Run(new Coordinate('A', 2));
        gameController.currentPlayer.setCurrentPosition(
                game.getBoard().getField().getSquares().get(0));
        assertDoesNotThrow(() -> gameController.handle(run) );
    }
}
