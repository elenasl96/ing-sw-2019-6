package socket;


import socket.exceptions.InvalidUsernameException;
import socket.model.Group;
import socket.model.Message;
import socket.model.User;
import socket.network.ClientHandler;
import socket.network.commands.*;

public class ServerController implements RequestHandler {
    // reference to the networking layer
    private final ClientHandler clientHandler;

    // pieces of the model
    private final ChatManager manager;
    private User user;
    private Group currentGroup;

    public ServerController(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
        manager = ChatManager.get();
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
            System.out.println("<<< Group " + currentGroup.getName() + " updated: " + currentGroup.users());
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
        currentGroup = manager.getDefaultGroup();
        currentGroup.join(user);

        currentGroup.observe(clientHandler);

        System.out.println(">>> Group " + currentGroup.getName() + " updated: " + currentGroup.users());
        return new JoinGroupResponse(currentGroup);
    }

    @Override
    public Response handle(SituationViewerRequest situationViewerRequest){
        manager.updateGroupSituation();
        return new SituationViewerResponse(manager.getGroupSituation(), StatusCode.OK);
    }
}
