package network.socket;

import controller.GameController;
import exception.InvalidMoveException;
import model.GameContext;
import model.moves.Move;
import model.room.*;
import model.enums.Character;
import network.socket.commands.request.*;
import network.socket.commands.response.*;
import network.exceptions.InvalidUsernameException;
import network.socket.commands.RequestHandler;
import network.socket.commands.Response;
import network.exceptions.FullGroupException;
import network.exceptions.InvalidGroupNumberException;

/**
 * Handles the Requests coming from the ClientHandler via Socket
 * chain ViewClient -> ClientController -> Client --network.socket--> ClientHandler -> ServerController
 * and sends the Response back to the ClientHandler
 * @see ClientHandler
 * @see RequestHandler
 * @see Group
 * @see User
 * @see Manager
 * @see network.socket.commands.Request
 * @see Response
 */

public class ServerController implements RequestHandler {
    /**
     * reference to the networking layer
     */
    private final ClientHandler clientHandler;

    /**
     * the user and group the ServerController is related to
     */
    User user;
    private Group currentGroup;

    /**
     * the only connection with the MANAGER is here in ServerController
     */
    private final Manager manager;

    //constructor for tests
    ServerController(User user){
        this.clientHandler = null;
        this.manager = Manager.get();
        this.user = user;
    }


    ServerController(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
        manager = Manager.get();
    }

    /**
     * in case of connectionLost, the ServerController notifies the leaving of the user
     * @see ClientHandler#run()  for usage
     */
    void connectionLost(){
        currentGroup.leave(user);
    }

    // ------ Request handling

    /**
     * Provides sending a message to the Group
     * if the message starts with ":q" deletes the user and stops the connection
     * @param request   the SendMessageRequest
     * @return  no Response
     *          not void to Override interface method
     * @see SendMessageRequest
     * @see Group#leave(User)
     * @see ClientHandler#stop()
     */
    @Override
    public Response handle(SendMessageRequest request) {
        Message message = request.message;
        if (!message.getContent().startsWith(":q")) {
            currentGroup.sendMessage(message);
            System.out.println(">>> Message: " + message.toString());
        } else {
            currentGroup.leave(user);
            clientHandler.stop();
            System.out.println (">>> " + currentGroup.getName() + " updated: " + currentGroup.users());
        }
        return null;
    }

    /**
     * Creates a new User
     * @param request   CreateUserRequest
     * @return  new UserCreatedResponse if the creation is successful
     *          new TextResponse if the username is invalid
     * @see Manager#createUser(String)
     * @see User#listenToMessages(MessageReceivedObserver)
     * @see UserCreatedResponse
     * @see TextResponse
     * @see CreateUserRequest
     */
    @Override
    public Response handle(CreateUserRequest request) {
        try {
            user = manager.createUser(request.username);
            System.out.println(">>> Created user: " + user.getUsername());
        } catch (InvalidUsernameException e) {
            return new TextResponse("ERROR: " + e.getMessage());
        }
        // Listening to messages and sending them
        user.listenToMessages(clientHandler);
        System.out.println(">>> Returning new UserCreatedResponse");
        return new UserCreatedResponse(user);
    }

    /**
     * Makes the User created join the group he chose
     * @param chooseGroupRequest the ChooseGroupRequest
     * @return  new JoinGroupResponse if the join is successful
     *          new TextResponse
     * @see Group#join(User)
     * @see Group#observe(GroupChangeListener)
     * @see JoinGroupResponse
     * @see TextResponse
     * @see ChooseGroupRequest
     */
    @Override
    public Response handle(ChooseGroupRequest chooseGroupRequest) {
        try{
            currentGroup = manager.getGroup(chooseGroupRequest.groupId);
            currentGroup.join(user);
            System.out.println(">>> " + currentGroup.getName() + " updated: " + currentGroup.users());
            System.out.println(">>> Returning new JoinGroupResponse");
            return new JoinGroupResponse(currentGroup);
        } catch(FullGroupException | InvalidGroupNumberException e){
            return new TextResponse("ERROR: " + e.getMessage());
        }
    }

    /**
     * Is the first action performed by the ServerController when a Client connects
     * Sends the situation via Response, since it is inaccessible to the Client
     * @param situationViewerRequest    sent by the clientHandler
     * @return  new SituationViewerRequest with the updated situation to be displayed
     * @see SituationViewerRequest
     * @see Manager#updateGroupSituation()  called to get the current groups situation
     * @see SituationViewerResponse
     */
    @Override
    public Response handle(SituationViewerRequest situationViewerRequest){
        manager.updateGroupSituation();
        return new SituationViewerResponse(manager.getGroupSituation());
    }

    @Override
    public Response handle(CreateGroupRequest createGroupRequest){
        currentGroup = manager.createGroup(createGroupRequest.getSkullNumber(), createGroupRequest.getFieldNumber() );
        System.out.println(">>> " + currentGroup.getName() + " created: " + currentGroup.users());
        return new JoinGroupResponse(currentGroup);
    }

    @Override
    public Response handle(SetCharacterRequest setCharacterRequest){
        Character character = Character.fromInteger(setCharacterRequest.characterNumber);
        Boolean taken = currentGroup.characterIsTaken(character);
        if (taken){
            return new SetCharacterResponse(Character.NOT_ASSIGNED);
        } else {
            currentGroup.observe(clientHandler);
            user.setCharacter(character);
            int count = 0;
            for(User u: currentGroup.users()){
                if (u.getCharacter()!= Character.NOT_ASSIGNED) {
                    count++;
                }
            }
            if(count==3) {
                manager.setTimer(currentGroup);
            }
            return new SetCharacterResponse(character);
        }
    }

    @Override
    public Response handle(PossibleMovesRequest possibleMovesRequest) {
        Update update = GameController.get().possibleMoves(user.getPlayer(), currentGroup.getGroupID());
        return new GameUpdateNotification(update);
    }

    @Override
    public Response handle(SpawnRequest spawnRequest) {
        if(spawnRequest.getSpawn()==null) {
            return new GameUpdateNotification(GameController.get().getFirstTimeSpawn(this.user.getPlayer(), currentGroup.getGroupID()));
        }else {
            GameController.get().setSpawn(this.user.getPlayer(), spawnRequest.getSpawn(), currentGroup.getGroupID());
            return null;
        }
    }

    @Override
    public Response handle(CardRequest cardRequest) {
        return null;
    }

    @Override
    public Response handle(MovementRequest movementRequest) {
        return null;
    }

    @Override
    public void handle(MoveRequest moveRequest) {
        try {
            Move move = moveRequest.getMove();

            move.handle(GameController.get(), currentGroup.getGroupID());
            move.execute(currentGroup.getGame().getCurrentPlayer(), currentGroup.getGroupID());
        } catch (InvalidMoveException e) {
            user.receiveUpdate(new Update(e.getMessage()));
            user.receiveUpdate(new Update(GameContext.get().getGame(currentGroup.getGroupID()).getCurrentPlayer(), true));
        }
    }
}
