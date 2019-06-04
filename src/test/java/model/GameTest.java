package model;
import model.room.Group;
import network.exceptions.InvalidUsernameException;
import network.Manager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GameTest {

    @BeforeEach
    void reset(){
        Manager.get().reset();
        GameContext.get().reset();
        Manager.get().createGroup(5,1);
        GameContext.get().createGame(1);
        Group group1 = Manager.get().getGroup(1);
        try {
            Manager.get().createUser("user1");
            Manager.get().createUser("user2");
            Manager.get().createUser("user3");
            Manager.get().createUser("user4");
        } catch (InvalidUsernameException e) {
            e.printStackTrace();
        }
        for(int i = 0; i<4; i++) {
            group1.join(Manager.get().getUsers().get(i));
        }
        group1.createGame();
    }

    @Test
    void test(){
        System.out.println("Number Players: "+ GameContext.get().getGame(0).getNumberPlayers());
    }
}