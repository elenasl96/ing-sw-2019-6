package controller;

import model.Player;
import model.enums.Character;
import model.enums.Phase;
import model.field.Coordinate;
import model.moves.MoveAndGrab;
import model.moves.MoveAndShoot;
import model.moves.Run;
import network.RemoteController;
import network.ClientContext;
import network.commands.request.*;
import network.commands.response.*;
import network.commands.Response;
import network.commands.ResponseHandler;
import model.room.Group;
import model.room.User;
import view.View;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import static model.enums.Phase.*;

//TODO javadoc
/**
 * CLIENT-SIDE controller
 *
 * It holds a reference to the view for sending sudden responses.
 * It holds a reference to the networking layer.
 */

public class ClientController extends UnicastRemoteObject implements ResponseHandler, Remote {
    private static final String ENDGAME = "endgame";
    /**
     * reference to networking layer
     */
    private final transient RemoteController client;

    /**
     * The view
     */
    private final transient View view;

    /**
     * A local variable keeping track if the game's over
     */
    private boolean gameNotDone;

    public ClientController(RemoteController socketClient, View view) throws RemoteException {
        super();
        this.client = socketClient;
        this.view = view;
        this.gameNotDone = true;
        view.setClientController(this);
    }

    /**
     * Creates a new user asking for a handle of a new CreateUserRequest
     * handles the next response(expected to be a UserCreatedResponse)
     * @param username  the username of the user, inserted in userInput()
     * @return the created user or null in case of failure
     * @see CreateUserRequest
     * @see #handle(UserCreatedResponse)
     * @see ClientContext#getCurrentUser()
     */
    public User createUser(String username) throws IOException {
            view.playMusic("WaitingRoom.wav");
            client.request(new CreateUserRequest(username));
            client.nextResponse().handle(this);
        return ClientContext.get().getCurrentUser();
    }

    public Group chooseGroup(int groupNumber) throws IOException {
            client.request(new ChooseGroupRequest(groupNumber));
            client.nextResponse().handle(this);
        return ClientContext.get().getCurrentGroup();
    }

    public String getSituation() throws IOException {
            client.request(new SituationViewerRequest());
            client.nextResponse().handle(this);
        return ClientContext.get().getCurrentSituation();
    }

    public int createGroup(int skullNumber, int fieldNumber) throws IOException {
            client.request(new CreateGroupRequest(skullNumber, fieldNumber));
            client.nextResponse().handle(this);
        return ClientContext.get().getCurrentGroup().getGroupID();
    }

    public synchronized Character setCharacter(int characterNumber) throws IOException {
        client.request(new SetCharacterRequest(characterNumber));
            client.nextResponse().handle(this);
        client.received();
        return ClientContext.get().getCurrentUser().getCharacter();
    }

    public synchronized void startReceiverThread() {
            Thread receiver = new Thread(
                    () -> {
                        while (gameNotDone) {
                            Response response;
                            try {
                                response = client.nextResponse();
                                if (response != null) {
                                    response.handle(this);
                                    client.received();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                                System.err.println(">>> An error occurred:" + e.getMessage());
                                gameNotDone = false;
                            }
                        }
                    }

            );
            receiver.start();
    }

    private void sendCommand(String content)  throws RemoteException{
        MoveRequest moveRequest = new MoveRequest();
        switch (content){
            case "shoot":
                moveRequest.addMove(new MoveAndShoot(null, -1));
                client.request(moveRequest);
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

    public void run() throws IOException {
        view.chooseUsernamePhase();
        if(!ClientContext.get().isRejoining()) {
            view.chooseGroupPhase();
            view.chooseCharacterPhase();
        }
        ClientContext.get().createPlayer();
        ClientContext.get().getCurrentPlayer().setPhase(WAIT);
        while(gameNotDone) {
            view.setWait(ClientContext.get().getCurrentPlayer().getPhase().equalsTo(WAIT));
            view.waitingPhase();
            try {
                gamingPhase();
            } catch (RemoteException e){
                //nothing
            }
        }
    }

    private void gamingPhase() throws RemoteException{
        switch(ClientContext.get().getCurrentPlayer().getPhase()){
            case SPAWN:
                client.request(new SpawnRequest(null));
                client.request(new SpawnRequest(view.spawnPhase()));
                ClientContext.get().getCurrentPlayer().setPhase(WAIT);
                break;
            case FIRST: case SECOND:
                client.request(new PossibleMovesRequest());
                if(!ClientContext.get().getCurrentPlayer().isPhaseNotDone()) {
                    String command = view.movePhase();
                    this.sendCommand(command);
                }
                ClientContext.get().getCurrentPlayer().setPhase(WAIT);
                break;
            case POWERUP1: case POWERUP2: case POWERUP3: case POWERUP_WAIT:
                choosePowerupEnd(view.choosePowerup());
                ClientContext.get().getCurrentPlayer().setPhase(WAIT);
                break;
            case RELOAD:
                chooseReload(view.reloadPhase());
                ClientContext.get().getCurrentPlayer().setPhase(WAIT);
                break;
            case DISCONNECTED:
                ClientContext.get().getCurrentPlayer().setPhase(WAIT);
                break;
            default:
                break;
        }
    }

    private void chooseReload(Boolean reload)  throws RemoteException{
        if(reload)
            client.request(new CardRequest("weaponToReload"));
        else
            client.request(new CardRequest("noCard"));
    }

    private void choosePowerupEnd(Boolean choosePowerup) throws RemoteException{
        if(choosePowerup)
            client.request(new CardRequest("powerupToPlay"));
        else
            client.request(new CardRequest("noCard"));
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
        ClientContext.get().getCurrentUser().setPlayer(new Player());
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
    public synchronized void handle(MoveUpdateResponse moveUpdateResponse) {
        ClientContext.get().getCurrentPlayer().setPhase(fromInteger(Integer.parseInt(moveUpdateResponse.getPhaseId())));
        ClientContext.get().getCurrentPlayer().setPhaseNotDone(moveUpdateResponse.getPhaseNotDone() == 1);
        view.setWait(ClientContext.get().getCurrentPlayer().getPhase().equalsTo(Phase.WAIT));
    }

    @Override
    public void handle(AskInput askInput) {
        switch(askInput.getInputType()){
            case "coordinate":
                try{
                    client.request(new MoveRequest(new MoveAndShoot(view.getCoordinate(), -1)));
                } catch (RemoteException e){
                    //nothing
                }
                break;
            case "damage":
            case "weapon choose":
                try{
                    client.request(new SendInput(view.cardChoose(), "weapon chosen"));
                } catch (RemoteException e){
                    //nothing
                }
                break;
            case "grabWeapon":
                try{
                    client.request(new SendInput(view.cardChoose(), "weaponGrabbed"));
                } catch (RemoteException e){
                    //nothing
                }
                break;
            case "fillFields":
                try{
                    client.request(new SendInput(view.fillFields(), "fieldsFilled"));
                }catch (RemoteException e){
                    //nothing
                }
                break;
            case "fillPowerup":
                try{
                    client.request((new SendInput(view.fillFields(), "powerupFilled")));
                }catch(RemoteException e){
                    //nothing
                }
                break;
            case "choosePowerup":
                try{
                    client.request(new SendInput(view.cardChoose(), "powerupToPlay"));
                }catch (RemoteException e){
                    //nothing
                }
                break;
            case "weaponToPlay":
                try {
                    ClientContext.get().getCurrentPlayer().setWeaponInUse(Integer.parseInt(view.cardChoose()));
                    client.request(new CardRequest("weaponLayout", ClientContext.get().getCurrentPlayer().getWeaponInUse()+""));
                } catch (RemoteException e) {
                    //nothing
                }
                break;
            case "effectsToPlay":
                try {
                    String string = ClientContext.get().getCurrentPlayer().getWeaponInUse() + " " + view.askEffects();
                    client.request(new SendInput(string, "weaponToPlay"));
                } catch (RemoteException e) {
                    //nothing
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void handle(RejoiningResponse rejoiningResponse) {
        ClientContext.get().setRejoining(true);
        ClientContext.get().setPlayer(rejoiningResponse.getPlayer());
        ClientContext.get().setCurrentUser(rejoiningResponse.getUser());
        view.displayText("Welcome back");
    }

    @Override
    public void handle(EndGameNotification endGameNotification) {
       this.gameNotDone = false;
        ClientContext.get().getCurrentGroup().sendEndNotification();
    }

    @Override
    public boolean equals(Object o){
        return super.equals(o);
    }

    @Override
    public int hashCode(){
        return super.hashCode();
    }
}
