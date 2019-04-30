package network.socket;

import controller.GameController;
import exception.InvalidMoveException;
import model.decks.Powerup;
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
    private User user;
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
            return new TextResponse("ERROR: " + e.getMessage(), null);
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
            return new TextResponse("ERROR: " + e.getMessage(), null);
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
        if(!user.isMyTurn()){
            return new TextResponse("It's "+currentGroup.getGame().getCurrentPlayer().getName()+" turn", false);
        } else {
            StringBuilder content = new StringBuilder("These are the moves you can choose:");
            if(!currentGroup.getGame().isFinalFrenzy()){
                content.append("\nrun" +
                        "\ngrab" +
                        "\nshoot");
            } else {
                if(user.getPlayer().isFirstPlayer()){
                    content.append("\nshoot (move up to 2 squares, reload, shoot)" +
                            "\ngrab (move up to 3 squares, grab)");
                } else {
                    content.append("\nshoot (move up to 1 squares, reload, shoot)" +
                            "\nrun (move up to 4 squares)" +
                            "\ngrab (move up to 2 squares, grab)");
                }
            }
            if(!user.getPlayer().getPowerups().isEmpty()){
                content.append("\npowerup");
            }
            return new TextResponse(content.toString(), true);
        }
    }

    @Override
    public Response handle(MovementRequest movementRequest) {
        return null;
    }

    @Override
    public Response handle(MoveRequest moveRequest) {
        this.currentGroup.gameController.setCurrentPlayer(user.getPlayer());
        try {
            Move move = moveRequest.getMove();
            move.handle(currentGroup.gameController);
            Response response = move.execute(user.getPlayer());
            currentGroup.sendUpdate(response.toString());
            return response;
        } catch (InvalidMoveException e){
            //TODO
            return null;
        }
    }

    @Override
    public Response handle(SendCommandRequest commandRequest) {
        //I haven't programmed that path yet
        return null;
    }
}
