package model.room;

import network.exceptions.UserNotInGroupException;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GroupTest {

    private Group group;
    private User user1 = new User("1"),
            user2 = new User("2"),
            user3 = new User("3");

    @Test
    void exceptionTest(){
        group = new Group(8, 1);

        group.join(user1);
        group.join(user2);

        assertThrows(UserNotInGroupException.class, () -> {
            group.leave(user3);
        });

        try{
            group.leave(user3);
        } catch (UserNotInGroupException u){
            assertEquals("User not in group: 3 <> group0",u.getMessage());
        }
    }
}
