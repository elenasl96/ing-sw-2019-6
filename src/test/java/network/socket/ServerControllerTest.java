package network.socket;

import model.room.Group;
import network.socket.commands.Response;
import network.socket.commands.request.*;
import network.socket.commands.response.*;
import org.junit.jupiter.api.Disabled;
import model.room.User;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ServerControllerTest {

    @Test
    void userCreatedTest(){
        //Creating users and server controllers
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

        SituationViewerResponse response2 = (SituationViewerResponse) serverController1.handle(new SituationViewerRequest());
        assertEquals("Group 0 has 0 players:\n" +
                "Group 1 has 2 players:\n" +
                "@1, @2, ", response2.situation.toString());

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

        //User join in full group
        TextResponse response = (TextResponse) serverController6.handle(new ChooseGroupRequest(1));
        assertEquals("ERROR: The group is full", response.toString());

        SituationViewerResponse response3 = (SituationViewerResponse) serverController1.handle(new SituationViewerRequest());
        assertEquals("Group 0 has 0 players:\n" +
                "Group 1 full\n", response3.situation.toString());

        //Set Character Request
        SetCharacterResponse response4 = (SetCharacterResponse) serverController1.handle(new SetCharacterRequest(1));
        SetCharacterResponse response5 = (SetCharacterResponse) serverController2.handle(new SetCharacterRequest(1));
        SetCharacterResponse response6 = (SetCharacterResponse) serverController2.handle(new SetCharacterRequest(2));
        SetCharacterResponse response7 = (SetCharacterResponse) serverController3.handle(new SetCharacterRequest(3));

        assertEquals("PG1", response4.character.name());
        assertEquals("NOT_ASSIGNED", response5.character.name());
        assertEquals("PG2", response6.character.name());
        assertEquals("PG3", response7.character.name());

        assertEquals("PG1", group1.users.get(1).getCharacter().name());
        assertEquals("PG2", group1.users.get(2).getCharacter().name());
        assertEquals("PG3", group1.users.get(3).getCharacter().name());

        //Possible moves request
        //TextResponse response8 = (TextResponse) serverController1.handle(new PossibleMovesRequest());
        //assertEquals("", response8.content.toString());
    }
}
