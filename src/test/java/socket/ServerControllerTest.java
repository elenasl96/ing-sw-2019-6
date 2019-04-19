package socket;

import org.junit.jupiter.api.Test;
import org.w3c.dom.Text;
import socket.model.User;
import socket.network.commands.Response;
import socket.network.commands.request.CreateGroupRequest;
import socket.network.commands.request.CreateUserRequest;
import socket.network.commands.request.SetCharacterRequest;
import socket.network.commands.response.JoinGroupResponse;
import socket.network.commands.response.SetCharacterResponse;
import socket.network.commands.response.TextResponse;
import socket.network.commands.response.UserCreatedResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ServerControllerTest {

    @Test
    public void userCreatedTest(){
        User user1 = new User("1");
        User user2 = new User("2");
        User user3 = new User("3");
        User user4 = new User("4");
        User user5 = new User("5");
        User user6 = new User("6");
        ServerController serverController1 = new ServerController(user1);
        ServerController serverController2 = new ServerController(user2);

        UserCreatedResponse resp1 = (UserCreatedResponse) serverController1.handle(new CreateUserRequest("1"));
        assertEquals("1", resp1.user.getUsername());
        TextResponse resp2 = (TextResponse) serverController2.handle(new CreateUserRequest("1"));
        assertEquals("ERROR: Invalid username: 1", resp2.content);

        //Create group
        JoinGroupResponse resp3 = (JoinGroupResponse) serverController1.handle(new CreateGroupRequest(5, 2));
        assertEquals(1, resp3.group.getGroupID());
        assertEquals(1, resp3.group.size());
        assertEquals("group1", resp3.group.getName());
        assertEquals(1, serverController1.getCurrentGroup().getGroupID());


        //Choose character
        //SetCharacterResponse resp4 = (SetCharacterResponse) serverController2.handle(new SetCharacterRequest(1));
        //assertEquals("PG1", resp4.character.name());

    }
}
