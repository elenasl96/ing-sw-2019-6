package model;

import controller.GameController;
import model.room.User;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    ArrayList<User> users = new ArrayList<>();
    Game g = new Game(5, 1, users);
    GameController gc = new GameController(g);

    @Disabled
    void numberPlayers() {
        assertEquals(0, g.getNumberPlayers());
        g.setNumberPlayers(1);
        assertEquals(1, g.getNumberPlayers());
    }

    @Disabled
    void CurrentPlayer() {
    }

    @Disabled
    void Board() {
    }

    @Disabled
    void Players() {
    }

    @Disabled
    void turn() {
    }

    @Disabled
    void skullsNumber() {
    }

    @Disabled
    void isDone() {
    }

    @Test
    void isFinalFrenzy() {
    }
}