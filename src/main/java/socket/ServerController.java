package socket;

import model.enums.Character;
import socket.exceptions.FullGroupException;
import socket.exceptions.InvalidGroupNumberException;
import socket.exceptions.InvalidUsernameException;
import socket.model.Group;
import socket.model.Message;
import socket.model.User;
import socket.network.ClientHandler;
import socket.network.commands.*;
import socket.network.commands.request.*;
import socket.network.commands.response.*;

public class ServerController implements RequestHandler {
    // reference to the networking layer
    private final ClientHandler clientHandler;

    // pieces of the model
    private final Manager manager;
    private User user;
    private Group currentGroup;

    //constructor for tests
    public ServerController(User user){
        this.clientHandler = null;
        this.manager = Manager.get();
        this.user = user;
    }

    public ServerController(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
        manager = Manager.get();
    }

    public void connectionLost(){
        currentGroup.leave(user);
    }

    // ------ Request handling

    @Override
    public Response handle(SendMessageRequest request) {
        Message message = request.message;
        if (!message.getContent().startsWith(":q")) {
            currentGroup.sendMessage(message);
        } else {
            currentGroup.leave(user);
            clientHandler.stop();
            System.out.println (">>> Group " + currentGroup.getName() + " updated: " + currentGroup.users());
        }

        return null; // no response
    }

    @Override
    public Response handle(CreateUserRequest request) {
        try {
            user = manager.createUser(request.username);
        } catch (InvalidUsernameException e) {
            return new TextResponse("ERROR: " + e.getMessage(), StatusCode.KO);
        }
        // Listening to messages and sending them
        user.listenToMessages(clientHandler);
        return new UserCreatedResponse(user);
    }

    @Override
    public Response handle(ChooseGroupRequest chooseGroupRequest) {
        try{
            currentGroup = manager.getGroup(chooseGroupRequest.groupId);
            currentGroup.join(user);
            currentGroup.observe(clientHandler);
            System.out.println(">>> Group " + currentGroup.getName() + " updated: " + currentGroup.users());
            return new JoinGroupResponse(currentGroup);
        } catch(FullGroupException | InvalidGroupNumberException e){
            return new TextResponse("ERROR: " + e.getMessage(), StatusCode.KO);
        }
    }

    @Override
    public Response handle(SituationViewerRequest situationViewerRequest){
        manager.updateGroupSituation();
        return new SituationViewerResponse(manager.getGroupSituation(), StatusCode.OK);
    }

    @Override
    public Response handle(CreateGroupRequest createGroupRequest){
        currentGroup = manager.createGroup(createGroupRequest.getSkullNumber(), createGroupRequest.getFieldNumber() );
        System.out.println(">>> Group " + currentGroup.getName() + " created: " + currentGroup.users());
        return new JoinGroupResponse(currentGroup);
    }

    @Override
    public Response handle(SetCharacterRequest setCharacterRequest){
        Character character = Character.fromInteger(setCharacterRequest.characterNumber);
        Boolean taken = currentGroup.characterIsTaken(character);
        if (taken){
            return new SetCharacterResponse(Character.NOT_ASSIGNED);
        } else {
            user.setCharacter(character);
            return new SetCharacterResponse(character);
        }
    }

    @Override
    public Response handle(SendCommandRequest commandRequest) {
        //TODO command handling
        return null;
    }

    public Group getCurrentGroup() {
        return this.currentGroup;
    }
}
