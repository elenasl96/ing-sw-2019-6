package model;

import model.enums.Character;
import org.junit.jupiter.api.Test;
import socket.model.Group;
import socket.model.User;

import static org.junit.jupiter.api.Assertions.*;


public class GroupTest {

    @Test
    public void testGroup(){
        User user1 = new User("elena");
        Group group1 = new Group(5, 2);
        assertEquals(0, group1.getGroupID());
        Group group2 = new Group(5, 2);
        assertEquals(1, group2.getGroupID());
        group1.join(user1);
        assertFalse(group1.characterIsTaken(Character.PG1));
        user1.setCharacter(Character.PG1);
        assertTrue(group1.users().contains(user1));
        assertTrue(group1.characterIsTaken(Character.PG1));



    }

}
