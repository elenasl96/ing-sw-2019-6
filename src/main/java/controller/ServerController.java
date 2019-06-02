package controller;

import exception.InvalidMoveException;
import model.GameContext;
import model.Player;
import model.decks.WeaponTile;
import model.enums.Phase;
import model.moves.Move;
import model.room.*;
import model.enums.Character;
import network.ClientHandler;
import network.socket.SocketClientHandler;
import network.socket.Manager;
import network.socket.commands.request.*;
import network.socket.commands.response.*;
import network.exceptions.InvalidUsernameException;
import network.socket.commands.RequestHandler;
import network.socket.commands.Response;
import network.exceptions.FullGroupException;
import network.exceptions.InvalidGroupNumberException;

/**
 * Handles the Requests coming from the SocketClientHandler via Socket
 * chain ViewClient -> ClientController -> SocketClient --network.socket--> SocketClientHandler -> ServerController
 * and sends the Response back to the SocketClientHandler
 * @see SocketClientHandler
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

    //constructor for tests
    public ServerController(User user){
        this.clientHandler = null;
        this.user = user;
    }

    public User getUser(){
        return this.user;
    }

    public ServerController(ClientHandler socketClientHandler) {
        this.clientHandler = socketClientHandler;
    }

    /**
     * in case of connectionLost, the ServerController notifies the leaving of the user
     * @see SocketClientHandler#run()  for usage
     */
    public void connectionLost(){
        currentGroup.leave(user);
    }

    // ------ Request handling

    /**
     * Creates a new User
     * @param request   CreateUserRequest
     * @return  new UserCreatedResponse if the creation is successful
     *          new TextResponse if the username is invalid
     * @see Manager#createUser(String)
     * @see UserCreatedResponse
     * @see TextResponse
     * @see CreateUserRequest
     */
    @Override
    public Response handle(CreateUserRequest request) {
        try {
            user = Manager.get().createUser(request.username);
            System.out.println(">>> Created user: " + user.getUsername());
        } catch (InvalidUsernameException e) {
            return new TextResponse("ERROR: " + e.getMessage());
        }
        // Listening to messages and sending them
        user.listenToMessages((ModelObserver) clientHandler);
        System.out.println(">>> Returning new UserCreatedResponse");
        return new UserCreatedResponse(user);
    }

    /**
     * Makes the User created join the group he chose
     * @param chooseGroupRequest the ChooseGroupRequest
     * @return  new JoinGroupResponse if the join is successful
     *          new TextResponse
     * @see Group#join(User)
     * @see Group#observe(ModelObserver)
     * @see JoinGroupResponse
     * @see TextResponse
     * @see ChooseGroupRequest
     */
    @Override
    public Response handle(ChooseGroupRequest chooseGroupRequest) {
        try{
            currentGroup = Manager.get().getGroup(chooseGroupRequest.groupId);
            currentGroup.join(user);
            System.out.println(">>> " + currentGroup.getName() + " updated: " + currentGroup.users());
            System.out.println(">>> Returning new JoinGroupResponse");
            return new JoinGroupResponse(currentGroup);
        } catch(FullGroupException | InvalidGroupNumberException e){
            return new TextResponse("ERROR: " + e.getMessage());
        }
    }

    /**
     * Is the first action performed by the ServerController when a SocketClient connects
     * Sends the situation via Response, since it is inaccessible to the SocketClient
     * @param situationViewerRequest    sent by the socketClientHandler
     * @return  new SituationViewerRequest with the updated situation to be displayed
     * @see SituationViewerRequest
     * @see Manager#updateGroupSituation()  called to get the current groups situation
     * @see SituationViewerResponse
     */
    @Override
    public Response handle(SituationViewerRequest situationViewerRequest){
        Manager.get().updateGroupSituation();
        return new SituationViewerResponse(Manager.get().getGroupSituation());
    }

    @Override
    public Response handle(CreateGroupRequest createGroupRequest){
        currentGroup = Manager.get().createGroup(createGroupRequest.getSkullNumber(), createGroupRequest.getFieldNumber() );
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
            currentGroup.observe((ModelObserver) clientHandler);
            user.setCharacter(character);
            int count = 0;
            for(User u: currentGroup.users()){
                if (u.getCharacter()!= Character.NOT_ASSIGNED) {
                    count++;
                }
            }
            if(count==3) {
                TimerController.get().startTimer(currentGroup.getGroupID());
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
            return new GameUpdateNotification(GameController.get().getSpawn(this.user.getPlayer(), currentGroup.getGroupID()));
        }else {
            GameController.get().setSpawn(this.user.getPlayer(), spawnRequest.getSpawn(), currentGroup.getGroupID());
            return null;
        }
    }

    @Override
    public Response handle(CardRequest cardRequest){
        switch (cardRequest.getCardType()) {
            case "noCard":
                GameController.get().updatePhase(currentGroup.getGroupID());
                break;
            case "weaponToReload":
                WeaponTile weaponsToReload = new WeaponTile();
                weaponsToReload.setWeapons(GameController.get().getWeaponToReload(user.getPlayer()));
                if (weaponsToReload.getWeapons().isEmpty()) {
                    user.receiveUpdate(new Update("You haven't weapons to reload"));
                    GameController.get().updatePhase(currentGroup.getGroupID());
                } else {
                    user.receiveUpdate(new Update("You can reload these weapons: " +
                            weaponsToReload.toString() +
                            "\n You have these ammos: " +
                            user.getPlayer().getAmmos().toString()));
                    return new AskInput("grabWeapon");
                }
            break;
            case "powerupToPlay":
                GameController.get().playPowerup(currentGroup.getGroupID(), user.getPlayer(), user.getPlayer().getPowerups().get(cardRequest.getNumber()));
            break;
            default:
                break;
        }
        return null;
    }

    @Override
    public Response handle(SendInput inputResponse) {
        Player p = GameContext.get().getGame(currentGroup.getGroupID()).getCurrentPlayer();
        switch(inputResponse.getInputType()){
            case "player damaged":
                break;
            case "number damages":
                break;
            case "weapon chosen":
                try {
                    p.getCurrentPosition().getGrabbable().pickGrabbable(currentGroup.getGroupID(), Integer.parseInt(inputResponse.getInput()));
                }catch (IndexOutOfBoundsException e){
                    System.out.println(">>> Weapon index out of bounds");
                    p.setPhaseNotDone(true);
                    p.getUser().receiveUpdate(new Update(p,true));
                }catch(NumberFormatException e){
                    p.getUser().receiveUpdate(new Update("Not a number"));
                }
                GameController.get().updatePhase(currentGroup.getGroupID());
                break;
            case "weaponGrabbed":
                try {
                    GameController.get().reloadWeapon(Integer.parseInt(inputResponse.getInput()), currentGroup.getGroupID());
                }catch (IndexOutOfBoundsException e){
                    user.receiveUpdate(new Update("Invalid Weapon"));
                }catch (NumberFormatException e){
                    user.receiveUpdate(new Update("Not a number"));
                }
                currentGroup.getGame().getCurrentPlayer().setPhase(Phase.RELOAD);
                currentGroup.getGame().getCurrentPlayer().getUser().receiveUpdate(new Update(currentGroup.getGame().getCurrentPlayer(), true));
                break;
            case "fieldsFilled":
                try{
                    GameController.get().playWeapon(this.user.getPlayer(), inputResponse.getInput());
                }catch(NullPointerException e){
                    //TODO
                }
                break;
            default:
                break;
        }

        return null;
    }

    @Override
    public Response handle(ShootRequest shootRequest) {
        try {
            this.user.receiveUpdate(new Update(GameController.get().prepareWeapon(user.getPlayer(), shootRequest.getString())));
            return new AskInput("fillFields");
        }catch(IndexOutOfBoundsException|InvalidMoveException e){
            user.receiveUpdate(new Update("Not valid weapon"));
        }
        return null;
    }


    @Override
    public Response handle(MovementRequest movementRequest) {
        return null;
    }

    @Override
    public Response handle(MoveRequest moveRequest) {
        Move move = moveRequest.getMove();
        if(move == null){
            move = GameContext.get().getGame(currentGroup.getGroupID()).getCurrentPlayer().getCurrentMoves().get(0);
        }
        System.out.println(move);
        try {
            Response response = move.execute(currentGroup.getGame().getCurrentPlayer(), currentGroup.getGroupID());
            if(response != null){
                System.out.println("notNULL");
               return response;
            }
            //go to next player and set phase
            GameController.get().updatePhase(currentGroup.getGroupID());
        } catch (InvalidMoveException e) {
            user.receiveUpdate(new Update(e.getMessage()));
            user.receiveUpdate(new Update(GameContext.get().getGame(currentGroup.getGroupID()).getCurrentPlayer(), true));
        }
        return null;
    }



}
