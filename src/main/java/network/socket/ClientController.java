package network.socket;

import model.decks.Weapon;
import model.enums.Character;
import model.enums.Phase;
import model.field.Coordinate;
import model.moves.MoveAndGrab;
import model.moves.Run;
import network.socket.commands.Request;
import network.socket.commands.request.*;
import network.socket.commands.response.*;
import network.socket.commands.Response;
import network.socket.commands.ResponseHandler;
import model.room.Group;
import model.room.Message;
import model.room.User;
import network.socket.launch.Client;

import static model.enums.Phase.*;

/**
 * CLIENT-SIDE controller
 *
 * It holds a reference to the view for sending sudden responses.
 * It holds a reference to the networking layer.
 */

public class ClientController implements ResponseHandler {
    /**
     * reference to networking layer
     */
    final Client client; //made protected to extend class in tests
    //Removed the Thread since it can be local

    /**
     * the view
     */
    final ViewClient view;

    private Thread receiver;

    private boolean gameNotDone;

    public ClientController(Client client) {
        this.client = client;
        this.view = new ViewClient(this);
        this.gameNotDone = true;
    }

    /**
     * Creates a new user asking for a handle of a new CreateUserRequest
     * handles the next response(expected to be a UserCreatedResponse)
     * @param username  the username of the user, inserted in userInput()
     * @return the created user or null in case of failure
     * @see CreateUserRequest
     * @see #handle(UserCreatedResponse)
     * @see ClientContext#getCurrentUser()
     * @see Client#request(Request)
     * @see Client#nextResponse()
     */
    User createUser(String username) {
        client.request(new CreateUserRequest(username));
        client.nextResponse().handle(this);
        return ClientContext.get().getCurrentUser();
    }

    Group chooseGroup(int groupNumber){
        client.request(new ChooseGroupRequest(groupNumber));
        client.nextResponse().handle(this);
        return ClientContext.get().getCurrentGroup();
    }

    String getSituation(){
        client.request(new SituationViewerRequest());
        client.nextResponse().handle(this);
        return ClientContext.get().getCurrentSituation();
    }

    int createGroup(int skullNumber, int fieldNumber) {
        client.request(new CreateGroupRequest(skullNumber, fieldNumber));
        client.nextResponse().handle(this);
        return ClientContext.get().getCurrentGroup().getGroupID();
    }

    synchronized Character setCharacter(int characterNumber) {
        client.request(new SetCharacterRequest(characterNumber));
        client.nextResponse().handle(this);
        return ClientContext.get().getCurrentUser().getCharacter();
    }

    void startReceiverThread() {
        receiver = new Thread(
                () -> {
                    while (gameNotDone) {
                        Response response = client.nextResponse();
                        if (response != null) {
                            try{
                                MoveUpdateResponse moveResponse = (MoveUpdateResponse) response;
                            } catch (ClassCastException e){
                                //niente
                            }
                            response.handle(this);
                        }
                    }
                    Thread.currentThread().interrupt();
                }
        );
        receiver.start();
    }

    void chooseSpawn(Integer spawn) {
        client.request(new SpawnRequest(ClientContext.get().getCurrentPlayer(), spawn));
    }

    void sendCommand(String content){
        MoveRequest moveRequest = new MoveRequest();
        switch (content){
            case "run":
                Coordinate coordinate = view.getCoordinate();
                moveRequest.addMove(new Run(coordinate));
                client.request(moveRequest);
                client.nextResponse().handle(this);
                break;
            case "grab":
                coordinate = view.getCoordinate();
                moveRequest.addMove(new MoveAndGrab(coordinate));
                client.request(moveRequest);
                client.nextResponse().handle(this);
                break;
            case "shoot":
                client.request(new CardRequest("weapon"));
                break;
            case "powerup":

            default:
                view.displayText("Insert a valid move");
                return;
        }
    }

    void sendMessage(String content) {
        Message m = new Message(ClientContext.get().getCurrentGroup(),
                ClientContext.get().getCurrentUser(), content);
        client.request(new SendMessageRequest(m));
    }

    void askPossibleMoves(){
        client.request(new PossibleMovesRequest());
    }

    public void run(){
        view.chooseUsernamePhase();
        view.chooseGroupPhase();
        view.chooseCharacterPhase();
        while(gameNotDone) {
            view.setWait(true);
            view.waitingPhase();
            gamingPhase();
        }
    }

    void gamingPhase(){
        while(!ClientContext.get().getCurrentPlayer().getPhase().equalsTo(WAIT)){
            switch(ClientContext.get().getCurrentPlayer().getPhase()){
                case SPAWN:
                    view.spawnPhase();
                    break;
                case FIRST: case SECOND:
                    view.movePhase();
                    break;
                case RELOAD:
                    view.reloadPhase();
                    break;
                default:
                    break;
            }
        }
    }
    // -------------------------- Response handling

    @Override
    public void handle(TextResponse textResponse) {
        view.displayText(textResponse.toString());
    }

    @Override
    public void handle(JoinGroupResponse joinGroupResponse) {
        ClientContext.get().setCurrentGroup(joinGroupResponse.group);
    }

    @Override
    public void handle(UserCreatedResponse userCreatedResponse) {
        ClientContext.get().setCurrentUser(userCreatedResponse.user);
    }

    @Override
    public void handle(SituationViewerResponse situationViewerResponse){
        ClientContext.get().setCurrentSituation(situationViewerResponse.situation);
    }

    @Override
    public void handle(SetCharacterResponse setCharacterResponse) {
        ClientContext.get().getCurrentUser().setCharacter(setCharacterResponse.character);
        ClientContext.get().createPlayer();
    }

    @Override
    public void handle(MoveUpdateResponse moveUpdateResponse) {
        view.displayText("moveupdateplayer"+ moveUpdateResponse.getPlayer());
        ClientContext.get().setPlayer(moveUpdateResponse.getPlayer());
        ClientContext.get().getCurrentPlayer().setPhase(fromInteger(moveUpdateResponse.getPhaseId()));
        if(ClientContext.get().getCurrentPlayer().getPhase()!=Phase.WAIT){
            view.setWait(false);
        }else{
            view.setWait(true);
        }
    }

}
