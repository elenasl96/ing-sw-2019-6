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
import socket.network.commands.response.GeneralResponse;
import socket.network.commands.response.JoinGroupResponse;
import socket.network.commands.response.TextResponse;
import socket.network.commands.response.UserCreatedResponse;

public class ServerController implements RequestHandler {
    // reference to the networking layer
    private final ClientHandler clientHandler;

    // pieces of the model
    private final Manager manager;
    private User user;
    private Group currentGroup;

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
        try {
            currentGroup = manager.createGroup(createGroupRequest.getSkullNumber());
            currentGroup.join(user);
            currentGroup.observe(clientHandler);// --- deleting this will be fixed
            //the duplicated responses in the first player bug
            System.out.println(">>> Group " + currentGroup.getName() + " created: " + currentGroup.users());
            return new JoinGroupResponse(currentGroup);
        } catch (FullGroupException e){
            return new TextResponse("ERROR: " + e.getMessage(), StatusCode.KO);
        }
    }

    @Override
    public Response handle(SetCharacterRequest setCharacterRequest){
        Character character = Character.fromInteger(setCharacterRequest.characterNumber);
        boolean taken = false;
        for(User u: currentGroup.users()) {
            if(u.getCharacter() == character){
                taken = true;
            }
        }
        if (taken){
            return new GeneralResponse(false);
        }
        return new GeneralResponse(true);
    }

    @Override
    public Response handle(SendCommandRequest commandRequest) {
        //TODO command handling
        return null;
    }
}
