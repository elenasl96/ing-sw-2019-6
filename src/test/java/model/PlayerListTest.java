package model;

import model.room.User;
import network.exceptions.InvalidUsernameException;
import network.Manager;
import org.junit.jupiter.api.Test;

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
}
