package network.socket;

import model.room.Group;
import model.room.User;
import network.exceptions.InvalidUsernameException;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ManagerTest {

    @Test
    void resetTest(){
        try {
            Manager.get().createUser("1");
            Manager.get().createUser("2");
            Manager.get().createGroup(5,1);
        } catch (InvalidUsernameException e) {
            e.printStackTrace();
        }

        Manager.get().reset();

        assertEquals(1, Manager.get().getGroups().size());
        assertEquals(0, Manager.get().getGroups().iterator().next().getGroupID());
        assertTrue(Manager.get().getUsers().isEmpty());
    }
}
