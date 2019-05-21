package model;

import controller.GameController;
import model.room.User;
import network.exceptions.InvalidUsernameException;
import network.socket.Manager;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PlayerListTest {
    @Test
    void test(){
        PlayerList list = new PlayerList();
        list.add(new Player(new User("1")));
        assertEquals("1",list.next().getName());

        int hash = list.hashCode();
        assertEquals(hash, list.hashCode());

        assertNotEquals(list, new PlayerList());
    }

    @Test
    void nextCurrentPlayerTest(){
        Manager.get().reset();
        GameContext.get().reset();
        GameContext.get().createGame(0);
        try {
            Manager.get().createUser("user1");
            Manager.get().createUser("user2");
            Manager.get().createUser("user3");
            Manager.get().createUser("user4");
        } catch (InvalidUsernameException e) {
            e.printStackTrace();
        }
        for(int i = 0; i<4; i++) {
            Manager.get().getGroup(0).join(Manager.get().getUsers().get(i));
        }
        Manager.get().getGroup(0).createGame();
        GameContext.get().getGame(0).getPlayers().nextCurrentPlayer(0);

        GameContext.get().getGame(0).setCurrentPlayer(GameContext.get().getGame(0).getPlayers().nextCurrentPlayer(0));
        GameContext.get().getGame(0).setCurrentPlayer(GameContext.get().getGame(0).getPlayers().nextCurrentPlayer(0));
        GameContext.get().getGame(0).setCurrentPlayer(GameContext.get().getGame(0).getPlayers().nextCurrentPlayer(0));
        GameContext.get().getGame(0).setCurrentPlayer(GameContext.get().getGame(0).getPlayers().nextCurrentPlayer(0));
        GameContext.get().getGame(0).setCurrentPlayer(GameContext.get().getGame(0).getPlayers().nextCurrentPlayer(0));
    }
}
