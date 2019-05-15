package model;
import model.room.Group;
import network.exceptions.InvalidUsernameException;
import network.socket.Manager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    private Group group0 = Manager.get().getGroup(0);

    @BeforeEach
    void reset(){
        Manager.get().reset();
        GameContext.get().reset();
        GameContext.get().createGame();
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
    void numberPlayers() {
        assertEquals(4, GameContext.get().getGame(0).getNumberPlayers());
    }
}