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

/**
 * CLIENT-SIDE controller
 *
 * It holds a reference to the view for sending sudden responses.
 * It holds a reference to the networking layer.
 * After the initial login phase, it waits for any update and answers the view sending it's commands as responses
 *
 */

public class ClientController extends UnicastRemoteObject implements ResponseHandler, Remote {

    private final transient RemoteController client;
    private final transient View view;
    private boolean gameNotDone;
    private int weaponInUse;
    /**
     * Sets this clientController to the view, gameNotDone to true
     * @param socketClient  the RemoteController, communicating with the server
     * @param view          the view, interface with the physical user
     * @throws RemoteException  if something goes wrong in the connection
     */
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
     */
    public User createUser(String username) throws IOException {
            view.playMusic("WaitingRoom.wav");
            client.request(new CreateUserRequest(username));
            client.nextResponse().handle(this);
        return ClientContext.get().getCurrentUser();
    }

    /**
     * Joins an existing group asking for a handle of a new ChooseGroupRequest
     * handles the next response(expected to be a JoinGroupResponse)
     * @param groupNumber  the group you want to join, inserted in userInput()
     * @return the joined group or null in case of failure
     * @see ChooseGroupRequest
     * @see #handle(JoinGroupResponse)
     */
    public Group chooseGroup(int groupNumber) throws IOException {
        client.request(new ChooseGroupRequest(groupNumber));
        client.nextResponse().handle(this);
        return ClientContext.get().getCurrentGroup();
    }

    /**
     * Asks for the current groups situation, in a string that can be understood by the user
     * handles the next response(expected to be a SituationViewerResponse)
     * @return the current situation to be displayed or null in case of failure
     * @see SituationViewerRequest
     * @see #handle(SituationViewerResponse)
     */
    public String getSituation() throws IOException {
        client.request(new SituationViewerRequest());
        client.nextResponse().handle(this);
        return ClientContext.get().getCurrentSituation();
    }

    /**
     * Creates a new Group asking for a handle of a new CreateGroupRequest
     * handles the next response(expected to be a GroupCreatedResponse)
     * @param skullNumber
     * @param fieldNumber
     * @return the created group number or null in case of failure
     * @see ChooseGroupRequest
     * @see #handle(JoinGroupResponse)
     */
    public int createGroup(int skullNumber, int fieldNumber) throws IOException {
        client.request(new CreateGroupRequest(skullNumber, fieldNumber));
        client.nextResponse().handle(this);
        return ClientContext.get().getCurrentGroup().getGroupID();
    }

    /**
     * Sets the character requested asking for a handle of a new SetCharacterRequest
     * handles the next response(expected to be a SetCharacterResponse)
     * @param characterNumber
     * @return
     * @throws IOException
     */
    public synchronized Character setCharacter(int characterNumber) throws IOException {
        client.request(new SetCharacterRequest(characterNumber));
        client.nextResponse().handle(this);
        client.received();
        return ClientContext.get().getCurrentUser().getCharacter();
    }

    /**
     * Starts a Thread that waits for a new Response, it works like this:
     * - keeps calling for client.nextResponse()
     * - suddenly, nextResponse gives something != null: a new Update incoming
     * - handling the response received
     * - calling client.received() to signal that the response has been handled
     */
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

    /**
     * @param content inserted by the user, to be extracted as a MoveRequest
     * @throws RemoteException  if something goes wrong in communication
     */
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

    /**
     * Executes the lifeCycle, Client Side, with these steps:
     * - choosing the username
     * @see View#chooseUsernamePhase()
     * - choosing the group
     * @see View#chooseGroupPhase()
     * - choosing the character
     * @see View#chooseCharacterPhase()
     * - Creates the player clientSide
     * - starts the waiting phase
     * @see View#waitingPhase()
     * - if an update comes, moving my phase to one other than wait, tha gaming phase starts, depending
     * on my phase
     * @throws IOException if something goes wrong in communication
     */
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

    /**
     * @param textResponse Displays the testResponse content
     */
    @Override
    public void handle(TextResponse textResponse) {
        view.displayText(textResponse.toString());
    }

    /**
     * @param joinGroupResponse sets the group received as currentGroup
     */
    @Override
    public void handle(JoinGroupResponse joinGroupResponse) {
        ClientContext.get().setCurrentGroup(joinGroupResponse.group);
    }

    /**
     * @param userCreatedResponse   sets the user received as currentUser
     *                              and creates a new player
     */
    @Override
    public void handle(UserCreatedResponse userCreatedResponse) {
        ClientContext.get().setCurrentUser(userCreatedResponse.user);
        ClientContext.get().getCurrentUser().setPlayer(new Player());
    }

    /**
     * @param situationViewerResponse sets the response content as currentSituation
     */
    @Override
    public void handle(SituationViewerResponse situationViewerResponse){
        ClientContext.get().setCurrentSituation(situationViewerResponse.situation);
    }

    /**
     * @param setCharacterResponse sets the character received as current player's character
     */
    @Override
    public void handle(SetCharacterResponse setCharacterResponse) {
        ClientContext.get().getCurrentUser().setCharacter(setCharacterResponse.character);
        ClientContext.get().createPlayer();
    }

    /**
     * @param moveUpdateResponse updates the player and reacts as the phase changes
     */
    @Override
    public synchronized void handle(MoveUpdateResponse moveUpdateResponse) {
        ClientContext.get().getCurrentPlayer().setPhase(fromInteger(Integer.parseInt(moveUpdateResponse.getPhaseId())));
        ClientContext.get().getCurrentPlayer().setPhaseNotDone(moveUpdateResponse.getPhaseNotDone() == 1);
        view.setWait(ClientContext.get().getCurrentPlayer().getPhase().equalsTo(Phase.WAIT));
    }

    /**
     * @param askInput server way of asking input, the client handles it resending the informations to the server
     *                 as a SendInput kind of Request
     * @see SendInput
     */
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
                    String string = view.fillFields();
                    System.out.println("FILLFIELDSW" + string);
                    client.request(new SendInput(string, "fieldsFilled"));
                }catch (RemoteException e){
                    //nothing
                }
                break;
            case "fillPowerup":
                try{
                    String string = view.fillFields();
                    System.out.println("FILL P" + string);
                    client.request((new SendInput(string, "powerupFilled")));
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
                    weaponInUse = Integer.parseInt(view.cardChoose());
                    client.request(new CardRequest("weaponLayout", weaponInUse+""));
                } catch (RemoteException e) {
                    //nothing
                }
                break;
            case "effectsToPlay":
                try {
                    String string = weaponInUse + " " + view.askEffects();
                    System.out.println("W + EFFECTS:" + string);
                    client.request(new SendInput(string, "weaponToPlay"));
                } catch (RemoteException e) {
                    //nothing
                }
                break;
            default:
                break;
        }
    }

    /**
     * @param rejoiningResponse if a user joins again from disconnecting
     *                             the client reacts resetting the player and user
     */
    @Override
    public void handle(RejoiningResponse rejoiningResponse) {
        ClientContext.get().setRejoining(true);
        ClientContext.get().setPlayer(rejoiningResponse.getPlayer());
        ClientContext.get().setCurrentUser(rejoiningResponse.getUser());
        view.displayText("Welcome back");
    }

    /**
     * @param endGameNotification ends the game, closes everything
     * @see User#sendEndNotification()
     */
    @Override
    public void handle(EndGameNotification endGameNotification) {
       this.gameNotDone = false;
        ClientContext.get().getCurrentUser().sendEndNotification();
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
