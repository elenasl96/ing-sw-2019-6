package controller;

import model.Player;
import model.exception.InvalidMoveException;
import model.Ammo;
import model.GameContext;
import model.decks.Powerup;
import model.enums.Color;
import model.enums.Phase;
import model.exception.NotExistingFieldException;
import model.exception.NotExistingPositionException;
import model.field.Coordinate;
import model.moves.Run;
import model.room.Group;
import model.room.Update;
import model.room.User;
import network.exceptions.InvalidUsernameException;
import network.Manager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GameControllerTest {
    private ArrayList<User> users;

    @BeforeEach
    void start() throws NotExistingFieldException {
        Manager.get().reset();
        GameContext.get().reset();
        GameContext.get().createGame(0);
        Group group0 = Manager.get().getGroup(0);
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
        group0.getGame().getCurrentPlayer().setCurrentPosition(group0.getGame().getBoard().getField().getSquares().get(0));
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
                invalidRun.execute(GameContext.get().getGame(0).getCurrentPlayer(), 0) );
        Run run = new Run(new Coordinate('A', 2));
        GameContext.get().getGame(0).getCurrentPlayer().setCurrentPosition(
                GameContext.get().getGame(0).getBoard().getField().getSquares().get(0));
        assertDoesNotThrow(() -> run.execute(GameContext.get().getGame(0).getCurrentPlayer(), 0) );
    }

    @Test
    void isMyTurnTest(){
        assertTrue(GameController.get().isMyTurn(GameContext.get().getGame(0).getCurrentPlayer(), 0));
    }

    @Test
    void possibleMovesTest() throws InvalidMoveException {
        GameContext.get().getGame(0).getCurrentPlayer().setPhase(Phase.RELOAD);
        //TODO CHECK        GameController.get().possibleMoves(GameContext.get().getGame(0).getCurrentPlayer(), 0);
        GameContext.get().getGame(0).getCurrentPlayer().setPhase(Phase.FIRST);
        GameContext.get().getGame(0).getCurrentPlayer().getPowerups().add(new Powerup(0,"dummy", new Ammo(Color.BLUE)));
        GameController.get().possibleMoves(GameContext.get().getGame(0). getCurrentPlayer(), 0);
        GameContext.get().getGame(0).setFinalFrenzy(true);
        GameController.get().possibleMoves(GameContext.get().getGame(0).getCurrentPlayer(), 0);
        GameContext.get().getGame(0).setCurrentPlayer(GameContext.get().getGame(0).getPlayers().next());
        GameContext.get().getGame(0).getCurrentPlayer().setPhase(Phase.FIRST);
        GameController.get().possibleMoves(GameContext.get().getGame(0).getCurrentPlayer(), 0);

    }

    @Test
    void SpawnTest() throws NotExistingPositionException {
        //getFirstTimeSpawn
        Update possibleMovesUpdate = GameController.get().getSpawn(
                GameContext.get().getGame(0).getCurrentPlayer(), 0);
        assertFalse(possibleMovesUpdate.isPlayerChanges());
        assertFalse(GameContext.get().getGame(0).getCurrentPlayer().getPowerups().isEmpty());
        assertTrue(GameContext.get().getGame(0).getCurrentPlayer().isDead());
       //Not working Spawn
        GameController.get().setSpawn(
                GameContext.get().getGame(0).getCurrentPlayer(),
                5, 0);
        //Working spawn
        System.out.println(GameContext.get().getGame(0).getCurrentPlayer().getPowerups());

        TimerController.get().startTimer(0);
        GameController.get().setSpawn(
                GameContext.get().getGame(0).getCurrentPlayer(),
                0, 0);
    }

    @Test
    void powerupPlayableTest(){
        Player player = users.get(0).getPlayer();
        Powerup powerup = new Powerup().initializePowerup(3);
                player.getPowerups().add(powerup);
        assertTrue(ShootController.get().isPlayable(users.get(0).getPlayer(), powerup, 0));
    }
}
