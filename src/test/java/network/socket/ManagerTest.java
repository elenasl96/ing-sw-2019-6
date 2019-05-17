package network.socket;

import model.room.Group;
import model.room.User;
import network.exceptions.InvalidUsernameException;
import network.exceptions.UserNotInGroupException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

class ManagerTest {

    @BeforeEach
    void reset(){
        Manager.get().reset();
    }

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

    @Test
    void createUserTest(){
        try {
            Manager.get().createUser("1");
        } catch (InvalidUsernameException e) {
            e.printStackTrace();
        }
        assertFalse(Manager.get().getUsers().isEmpty());
        assertEquals("1",Manager.get().getUsers().iterator().next().getUsername());
        assertThrows(InvalidUsernameException.class, () -> Manager.get().createUser("1"));
    }

    @Test
    void TimerControllerTest(){

        Group group = Manager.get().createGroup(8,1);

        group.join(new User("1"));
        group.join(new User("2"));
        group.join(new User("3"));

        User user = new User("4");
        group.join(user);
        group.leave(user);
    }

    @Test
    void exceptionTest(){
        Group group = Manager.get().createGroup(8,1);

        group.join(new User("1"));
        group.join(new User("2"));
        group.join(new User("3"));

        User user = new User("4");

        assertThrows(UserNotInGroupException.class, () -> {
            group.leave(user);
        });

        try{
            group.leave(user);
        } catch (UserNotInGroupException u){
            Assertions.assertEquals("User not in group: 4 <> group1",u.getMessage());
        }
    }
}
