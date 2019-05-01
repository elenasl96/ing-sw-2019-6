package network.socket;

import model.room.Group;
import network.socket.commands.request.ChooseGroupRequest;
import org.junit.jupiter.api.Disabled;
import model.room.User;
import network.socket.commands.request.CreateGroupRequest;
import network.socket.commands.request.CreateUserRequest;
import network.socket.commands.response.JoinGroupResponse;
import network.socket.commands.response.TextResponse;
import network.socket.commands.response.UserCreatedResponse;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ServerControllerTest {

    @Test
    void userCreatedTest(){
        User user1 = new User("1");
        User user2 = new User("2");
        User user3 = new User("3");
        User user4 = new User("4");
        User user5 = new User("5");
        User user6 = new User("6");
        ServerController serverController1 = new ServerController(user1);
        ServerController serverController2 = new ServerController(user2);
        ServerController serverController3 = new ServerController(user3);
        ServerController serverController4 = new ServerController(user4);
        ServerController serverController5 = new ServerController(user5);
        ServerController serverController6 = new ServerController(user6);


        UserCreatedResponse userResp1 = (UserCreatedResponse) serverController1.handle(new CreateUserRequest("1"));
        assertEquals("1", userResp1.user.getUsername());
        TextResponse userRespError = (TextResponse) serverController2.handle(new CreateUserRequest("1"));
        assertEquals("ERROR: Invalid username: 1", userRespError.content);
        serverController2.handle(new CreateUserRequest("2"));
        serverController3.handle(new CreateUserRequest("3"));
        serverController4.handle(new CreateUserRequest("4"));
        serverController5.handle(new CreateUserRequest("5"));
        serverController6.handle(new CreateUserRequest("6"));

        //Create group

        JoinGroupResponse createResp = (JoinGroupResponse) serverController1.handle(new CreateGroupRequest(5, 2));
        assertEquals(1, createResp.group.size()+1);
        Group group1 = createResp.group;

        //Choose Group
        TextResponse joinRespError = (TextResponse) serverController1.handle(new ChooseGroupRequest(2));
        assertEquals("ERROR: There's no group2", joinRespError.toString());

        serverController1.handle(new ChooseGroupRequest(1));
        serverController2.handle(new ChooseGroupRequest(1));
        serverController3.handle(new ChooseGroupRequest(1));
        serverController4.handle(new ChooseGroupRequest(1));
        serverController5.handle(new ChooseGroupRequest(1));
        assertEquals(5, group1.size());
        ArrayList<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);
        users.add(user5);
        assertTrue(group1.users().containsAll(users));
        serverController6.handle(new ChooseGroupRequest(1));

    }
}
