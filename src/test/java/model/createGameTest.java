package model;

import model.room.Group;
import model.room.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class createGameTest {

    @Test
    void createTest() {
        User user1 = new User("1");
        User user2 = new User("2");
        User user3 = new User("3");
        Group group = new Group(5,1);
        group.join(user1);
        group.join(user2);
        group.join(user3);
        group.createGame();
        assertEquals("1", group.users.get(0).getUsername());
        assertEquals("FIRST", group.users.get(0).getPlayer().getPhase().name());
        assertTrue(user1.isMyTurn());
        assertFalse(user2.isMyTurn());
    }
}
