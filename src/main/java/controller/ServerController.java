package controller;

import model.decks.Powerup;
import model.decks.Weapon;
import model.exception.InvalidMoveException;
import model.exception.NotEnoughAmmoException;
import model.GameContext;
import model.Player;
import model.decks.WeaponTile;
import model.enums.Phase;
import model.moves.Move;
import model.room.*;
import model.enums.Character;
import network.ClientHandler;
import network.socket.SocketClientHandler;
import network.Manager;
import network.commands.request.*;
import network.commands.response.*;
import network.exceptions.InvalidUsernameException;
import network.commands.RequestHandler;
import network.commands.Response;
import network.exceptions.FullGroupException;
import network.exceptions.InvalidGroupNumberException;

import java.util.List;

import static controller.GameController.cardsToString;
import static model.enums.Phase.DISCONNECTED;
import static model.enums.Phase.WAIT;

//TODO Javadoc

/**
 * Handles the Requests coming from the SocketClientHandler via Socket
 * chain ViewClient -> ClientController -> SocketClient --network.socket--> SocketClientHandler -> ServerController
 * and sends the Response back to the SocketClientHandler
 * @see SocketClientHandler
 * @see RequestHandler
 * @see Group
 * @see User
 * @see Manager
 * @see network.commands.Request
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


    private static final String UPDATE_CONSOLE = "updateconsole";
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
        System.out.println(">>> Disconnection!");
        user.getPlayer().setPhase(DISCONNECTED);
        currentGroup.leave(user);
        if(currentGroup.getGame()!=null){
            int count = 0;
            for(Player p: currentGroup.getGame().getPlayers()){
                if(p.getPhase()!= Phase.DISCONNECTED){
                    count++;
                }
            }
            System.out.println(">>> Players remaining: "+ count);
            if(count<3) {
                List<Player> winners = currentGroup.getGame().getPlayers().findHighest(currentGroup.getGroupID());
                System.out.println(">>> The winners are: "+winners);
                for (Player winner : winners) {
                    winner.receiveUpdate(new Update("Congratulations! You win!"));
                    //TODO GUI update for the win
                }
            }
        }
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
            user = Manager.get().getUser(request.username);
            System.out.println(">>> Invalid Username");
            System.out.println(user.getPlayer());
            if(user.getPlayer().getPhase().equals(DISCONNECTED)){
                user.getPlayer().setPhase(WAIT);
                System.out.println(user.getPlayer());
                return new UserCreatedResponse(user);
            } else return new TextResponse("Invalid Username: already in use, choose another one");
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
     *          new TextResponse if not
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
            currentGroup.observe(clientHandler);
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
        if(update == null) return null;
        return new GameUpdateNotification(update);
    }

    @Override
    public Response handle(SpawnRequest spawnRequest) {
        if(spawnRequest.getSpawn()==null) {
            return new GameUpdateNotification(GameController.get().getSpawn(this.user.getPlayer(), currentGroup.getGroupID()));
        }else if(spawnRequest.isFirstTime()) {
            GameController.get().setSpawn(this.user.getPlayer(), spawnRequest.getSpawn(), currentGroup.getGroupID());
            return null;
        } else {
            GameController.get().setSpawn(this.user.getPlayer(), spawnRequest.getSpawn(), currentGroup.getGroupID());
            return null;
        }
    }

    @Override
    public Response handle(CardRequest cardRequest){
        Update update;
        switch (cardRequest.getCardType()) {
            case "weaponLayout":
                int cardNumber = cardRequest.getNumber()-3;
                StringBuilder updateString = new StringBuilder();
                Weapon weapon = this.user.getPlayer().getWeapons().get(cardNumber);
                updateString.append(weapon.getName()).append(";").append(weapon.getEffectsList().size());
                update = new Update(null, "layouteffect");
                update.setData(updateString.toString().toLowerCase().replace(" ",""));
                user.receiveUpdate(update);
                break;
            case "noCard":
                GameContext.get().getGame(currentGroup.getGroupID()).getCurrentPlayer()
                        .receiveUpdate(new Update(null,"turnbar"));
                GameController.get().updatePhase(currentGroup.getGroupID());
                break;
            case "weaponToReload":
                WeaponTile weaponsToReload = new WeaponTile();
                weaponsToReload.setWeapons(GameController.get().getWeaponToReload(user.getPlayer()));
                if (weaponsToReload.getWeapons().isEmpty()) {
                    user.receiveUpdate(new Update("You haven't weapons to reload",UPDATE_CONSOLE));
                    GameContext.get().getGame(currentGroup.getGroupID()).getCurrentPlayer()
                            .receiveUpdate(new Update(null,"turnbar"));
                    GameController.get().updatePhase(currentGroup.getGroupID());
                } else {
                    update = new Update("You can reload these weapons: " + cardsToString(weaponsToReload.getWeapons(),0),"choosecard");
                    update.setData(weaponsToReload.getStringIdWeapons().toLowerCase().replaceAll(" ",""));
                    user.receiveUpdate(update);
                    update = new Update("\n You have these ammos: " +
                            user.getPlayer().getAmmos().toString(),"reload");
                    update.setData(user.getPlayer().getAmmos().toString().replace("[","").replace("]","")
                            .replace(" ","").toLowerCase());
                    user.receiveUpdate(update);
                    return new AskInput("grabWeapon");
                }
            break;
            case "powerupToPlay":
                List<Powerup> powerupsToPlay = ShootController.get()
                        .getPowerupsToPlay(user.getPlayer());
                if(powerupsToPlay.isEmpty()){
                    user.receiveUpdate(new Update("You haven't powerups to play now",UPDATE_CONSOLE));
                    GameContext.get().getGame(currentGroup.getGroupID()).getCurrentPlayer()
                            .receiveUpdate(new Update(null,"turnbar")); //TODO check this (SCHERO) for GUI
                    GameController.get().updatePhase(currentGroup.getGroupID());
                }else{
                    update = new Update("You can play these powerups:" + cardsToString(powerupsToPlay, 0),"choosecard");
                    // update.setData(powerupsToPlay.getStringIdWeapons().toLowerCase().replaceAll(" ",""));
                    user.receiveUpdate(update);
                    return new AskInput("choosePowerup");
                }
            break;
            default:
                break;
        }
        return null;
    }

    @Override
    public Response handle(SendInput inputResponse) {
        Player p = user.getPlayer();
        switch(inputResponse.getInputType()){
            case "weapon chosen":
                try {
                    p.getCurrentPosition().getGrabbable().pickGrabbable(currentGroup.getGroupID(), Integer.parseInt(inputResponse.getInput()));
                    //p.setPhaseNotDone(false); senza questo per ora funziona
                    GameController.get().updatePhase(currentGroup.getGroupID());
                }catch (IndexOutOfBoundsException e){
                    user.receiveUpdate(new Update("Weapon index out of bounds",UPDATE_CONSOLE));
                    p.setPhaseNotDone(true);
                    user.receiveUpdate(new Update(p,true));
                }catch(NumberFormatException e){
                    user.receiveUpdate(new Update("Not a number",UPDATE_CONSOLE));
                    p.setPhaseNotDone(true);
                    user.receiveUpdate(new Update(p,true));
                }catch(NotEnoughAmmoException e){
                    user.receiveUpdate(new Update("Not enough ammos!",UPDATE_CONSOLE));
                    p.setPhaseNotDone(true);
                    user.receiveUpdate(new Update(p,true));
                }
                break;
            case "weaponGrabbed":
                try {
                    GameController.get().reloadWeapon(Integer.parseInt(inputResponse.getInput()), currentGroup.getGroupID());
                }catch (IndexOutOfBoundsException e){
                    p.setPhaseNotDone(true);
                    user.receiveUpdate(new Update(p,true));
                    user.receiveUpdate(new Update("Invalid Weapon",UPDATE_CONSOLE));
                }catch (NumberFormatException e){
                    user.receiveUpdate(new Update("Not a number",UPDATE_CONSOLE));
                    p.setPhaseNotDone(true);
                    user.receiveUpdate(new Update(p,true));

                }
                currentGroup.getGame().getCurrentPlayer().setPhase(Phase.RELOAD);
                currentGroup.getGame().getCurrentPlayer().getUser().receiveUpdate(new Update(currentGroup.getGame().getCurrentPlayer(), true));
                break;
            case "fieldsFilled":
                try{
                    GameController.get().playWeapon(this.user.getPlayer(), inputResponse.getInput(), currentGroup.getGroupID());
                    GameController.get().updatePhase(currentGroup.getGroupID());
                }catch(NullPointerException | IndexOutOfBoundsException e){
                    user.receiveUpdate(new Update("Invalid input!",UPDATE_CONSOLE));
                    p.getUser().receiveUpdate(new Update(p,true));
                }catch(NumberFormatException e){
                    user.receiveUpdate(new Update("Invalid Number Format!",UPDATE_CONSOLE));
                    p.getUser().receiveUpdate(new Update(p,true));
                }catch(InvalidMoveException e){
                    user.receiveUpdate(new Update(e.getMessage(),UPDATE_CONSOLE));
                    p.getUser().receiveUpdate(new Update(p,true));
                }
                p.setPhaseNotDone(false);
                break;
            case "powerupToPlay":
                try{
                    this.user.receiveUpdate(new Update(GameController.get().preparePowerup(currentGroup.getGroupID(), inputResponse.getInput(), user.getPlayer())));
                    return new AskInput("fillPowerup");
                }catch (IndexOutOfBoundsException e){
                    user.receiveUpdate(new Update("Powerup index out of bounds",UPDATE_CONSOLE));
                    p.setPhaseNotDone(true);
                    user.receiveUpdate(new Update(p,true));
                }catch (InvalidMoveException e){
                    user.receiveUpdate(new Update(e.getMessage(),UPDATE_CONSOLE));
                    p.setPhaseNotDone(true);
                    user.receiveUpdate(new Update(p,true));
                }
                break;
            case "powerupFilled":
                try{
                    GameController.get().playPowerup(this.user.getPlayer(), inputResponse.getInput(), currentGroup.getGroupID());
                    GameController.get().updatePhase(currentGroup.getGroupID());
                }catch(NullPointerException | IndexOutOfBoundsException e){
                    user.receiveUpdate(new Update("Invalid input!",UPDATE_CONSOLE));
                    p.getUser().receiveUpdate(new Update(p,true));
                }catch(NumberFormatException e){
                    user.receiveUpdate(new Update("Invalid Number Format!",UPDATE_CONSOLE));
                    p.getUser().receiveUpdate(new Update(p,true));
                }catch(InvalidMoveException e){
                    user.receiveUpdate(new Update(e.getMessage(),UPDATE_CONSOLE));
                    p.getUser().receiveUpdate(new Update(p,true));
                }
                p.setPhaseNotDone(false);
                break;
            default:
                break;
        }
        return null;
    }

    @Override
    public Response handle(ShootRequest shootRequest) {
        try {
            this.user.receiveUpdate(new Update(GameController.get().prepareWeapon(user.getPlayer(), shootRequest.getString(), currentGroup.getGroupID())));
            return new AskInput("fillFields");
            }catch(IndexOutOfBoundsException e) {
                user.receiveUpdate(new Update("Out of bound",UPDATE_CONSOLE));
            } catch(InvalidMoveException e){
                user.receiveUpdate(new Update(e.getMessage(),UPDATE_CONSOLE));
                user.getPlayer().setPhaseNotDone(true);
                //user.receiveUpdate(new Update(user.getPlayer(),true)); <-socket null exception
            }catch(NumberFormatException e){
                user.receiveUpdate(new Update("Not valid number",UPDATE_CONSOLE));
            }catch(NullPointerException e){
                user.receiveUpdate(new Update("Not valid effects",UPDATE_CONSOLE));
        }
        return null;
    }

    @Override
    public Response handle(MoveRequest moveRequest) {
        Player currentPlayer = GameContext.get().getGame(currentGroup.getGroupID()).getCurrentPlayer();
        Move move = moveRequest.getMove();
        try {
            if(move == null) {
                if (currentPlayer.getCurrentMoves().isEmpty())
                    throw new InvalidMoveException("No pending moves");
                move = currentPlayer.getCurrentMoves().get(0);
            }
            System.out.println(move);
            Response response = move.execute(currentGroup.getGame().getCurrentPlayer(), currentGroup.getGroupID());
            if(response != null){
               return response;
            }
            //go to next player and set phase
            GameContext.get().getGame(currentGroup.getGroupID()).getCurrentPlayer().setPhaseNotDone(false);
            GameController.get().updatePhase(currentGroup.getGroupID());
        } catch (InvalidMoveException e) {
            user.receiveUpdate(new Update(e.getMessage(), UPDATE_CONSOLE));
            user.getPlayer().setPhaseNotDone(false);
            GameController.get().updatePhase(currentGroup.getGroupID());
            user.receiveUpdate(new Update(GameContext.get().getGame(currentGroup.getGroupID()).getCurrentPlayer(), true));
        } catch (NullPointerException e){
            user.receiveUpdate(new Update(e.getMessage(), UPDATE_CONSOLE));
            user.getPlayer().setPhaseNotDone(true);
            user.receiveUpdate(new Update(GameContext.get().getGame(currentGroup.getGroupID()).getCurrentPlayer(), true));

        }
        return null;
    }
}
