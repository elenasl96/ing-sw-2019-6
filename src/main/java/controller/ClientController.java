package controller;

import model.enums.Character;
import model.enums.Phase;
import model.field.Coordinate;
import model.moves.MoveAndGrab;
import model.moves.Run;
import network.Client;
import network.socket.ClientContext;
import network.socket.ViewClient;
import network.socket.commands.Request;
import network.socket.commands.request.*;
import network.socket.commands.response.*;
import network.socket.commands.Response;
import network.socket.commands.ResponseHandler;
import model.room.Group;
import model.room.User;
import network.socket.launch.SocketClient;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import static model.enums.Phase.*;

/**
 * CLIENT-SIDE controller
 *
 * It holds a reference to the view for sending sudden responses.
 * It holds a reference to the networking layer.
 */

public class ClientController extends UnicastRemoteObject implements ResponseHandler, Remote {
    /**
     * reference to networking layer
     */
    final transient Client client; //made protected to extend class in tests
    //Removed the Thread since it can be local

    /**
     * The view
     */
    final transient ViewClient view;

    /**
     * A local variable keeping track if the game's over
     */
    private boolean gameNotDone;

    public ClientController(Client socketClient) throws RemoteException {
        super();
        this.client = socketClient;
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
    public User createUser(String username) {
        client.request(new CreateUserRequest(username));
        client.nextResponse().handle(this);
        return ClientContext.get().getCurrentUser();
    }

    public Group chooseGroup(int groupNumber){
        client.request(new ChooseGroupRequest(groupNumber));
        client.nextResponse().handle(this);
        return ClientContext.get().getCurrentGroup();
    }

    public String getSituation(){
        client.request(new SituationViewerRequest());
        client.nextResponse().handle(this);
        return ClientContext.get().getCurrentSituation();
    }

    public int createGroup(int skullNumber, int fieldNumber) {
        client.request(new CreateGroupRequest(skullNumber, fieldNumber));
        client.nextResponse().handle(this);
        return ClientContext.get().getCurrentGroup().getGroupID();
    }

    public synchronized Character setCharacter(int characterNumber) {
        client.request(new SetCharacterRequest(characterNumber));
        client.nextResponse().handle(this);
        return ClientContext.get().getCurrentUser().getCharacter();
    }

    public void startReceiverThread() {
        Thread receiver = new Thread(
                () -> {
                    while (gameNotDone) {
                        Response response = client.nextResponse();
                        if (response != null) {
                            response.handle(this);
                        }
                    }
                    Thread.currentThread().interrupt();
                }
        );
        receiver.start();
    }

    void chooseSpawn(Integer spawn) {
        client.request(new SpawnRequest(spawn));
    }

    private void sendCommand(String content){
        MoveRequest moveRequest = new MoveRequest();
        switch (content){
            case "0": case "1": case "2":
                client.request(new CardRequest("powerup", content));
                break;
            case "3": case "4": case "5":
                client.request(new CardRequest("weapon", content));
                break;
            case "run":
                Coordinate coordinate = view.getCoordinate();
                moveRequest.addMove(new Run(coordinate));
                client.request(moveRequest);
                break;
            case "grab":
                coordinate = view.getCoordinate();
                moveRequest.addMove(new MoveAndGrab(coordinate));
                client.request(moveRequest);
                break;
            default:
                view.displayText("Insert a valid move");
                break;
        }
    }

    public void run(){
        view.chooseUsernamePhase();
        view.chooseGroupPhase();
        view.chooseCharacterPhase();
        while(gameNotDone) {
            view.setWait(ClientContext.get().getCurrentPlayer().getPhase().equalsTo(WAIT));
            view.waitingPhase();
            gamingPhase();
        }
    }

    private void gamingPhase(){
        switch(ClientContext.get().getCurrentPlayer().getPhase()){
            case SPAWN:
                client.request(new SpawnRequest(null));
                client.request(new SpawnRequest(view.spawnPhase()));
                ClientContext.get().getCurrentPlayer().setPhase(WAIT);
                break;
            case FIRST: case SECOND:
                if(ClientContext.get().getCurrentPlayer().isPhaseNotDone()){
                    view.displayText("Phase not done yet");
                    client.request(new MoveRequest());
                }
                else {
                    client.request(new PossibleMovesRequest());
                    String command = view.movePhase();
                    this.sendCommand(command);
                }
                ClientContext.get().getCurrentPlayer().setPhase(WAIT);
                break;
            case RELOAD:
                chooseReload(view.reloadPhase());
                ClientContext.get().getCurrentPlayer().setPhase(WAIT);
                break;
            default:
                break;
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
        ClientContext.get().getCurrentPlayer().setPhaseNotDone(moveUpdateResponse.getPhaseNotDone() == 1);
        view.setWait(ClientContext.get().getCurrentPlayer().getPhase().equalsTo(Phase.WAIT));
    }

    @Override
    public void handle(AskInput askInput) {
        view.displayText("Asking input: "+ askInput.toString());
        view.displayText("Phase " + ClientContext.get().getCurrentPlayer().getPhase().toString());
        switch(askInput.getInputType()){
            case "damage":
            case "weapon choose":
                client.request(new SendInput(view.askNumber(), "weapon chosen"));
                break;
            case "grabWeapon":
                client.request(new SendInput(view.askNumber(), "weaponGrabbed"));
                break;
            default:
                break;
        }
    }

    private void chooseReload(Boolean reload) {
        if(reload)
            client.request(new CardRequest("weaponToReload"));
        else
            client.request(new CardRequest("noCard"));
    }

    @Override
    public boolean equals(Object o){
        return true;
    }

    @Override
    public int hashCode(){
        return 0;
    }
}
