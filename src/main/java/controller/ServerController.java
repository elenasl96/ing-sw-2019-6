package controller;

import model.decks.Powerup;
import model.decks.Weapon;
import model.exception.*;
import model.GameContext;
import model.Player;
import model.decks.WeaponTile;
import model.enums.Phase;
import model.moves.Move;
import model.moves.MoveAndGrab;
import model.moves.MoveAndShoot;
import model.moves.Shoot;
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
import static controller.GameController.powerupToStringForGUI;
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
                    winner.receiveUpdate(new Update("Congratulations! You win!"), currentGroup.getGroupID());
                    //TODO GUI update for the win
                }
            } else {
                GameController.get().updatePhase(currentGroup.getGroupID());
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
            // Listening to messages and sending them
            user.listenToMessages(clientHandler);
            System.out.println(">>> Returning new UserCreatedResponse");
            return new UserCreatedResponse(user);
        } catch (InvalidUsernameException e) {
            user = Manager.get().getUser(request.username);
            System.out.println(">>> Invalid Username");
            System.out.println(user.getPlayer());
            if(user.getPlayer().getPhase().equals(DISCONNECTED)){
                user.getPlayer().setPhase(WAIT);
                System.out.println(">>> "+ user.getPlayer()+" rejoining");
                return new RejoiningResponse(user.getPlayer(), user);
            } else return new TextResponse("Invalid Username: already in use, choose another one");
        }
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
        try {
            Manager.get().updateGroupSituation();
            return new SituationViewerResponse(Manager.get().getGroupSituation());
        }catch (NullPointerException e){
            e.getStackTrace();
        }
        return null;
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
        try {
            Update update;
            update = GameController.get().possibleMoves(user.getPlayer(), currentGroup.getGroupID());
            user.receiveUpdate(update);
        } catch ( InvalidMoveException | RuntimeException e) {
            user.getPlayer().getCurrentMoves().clear();
            user.receiveUpdate(new Update(e.getMessage(), UPDATE_CONSOLE));
            user.receiveUpdate(new Update(user.getPlayer(), true));
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Response handle(SpawnRequest spawnRequest) {
        try {
            if (spawnRequest.getSpawn() == null) {
                user.receiveUpdate(GameController.get().getSpawn(this.user.getPlayer(), currentGroup.getGroupID()));
            } else {
                GameController.get().setSpawn(this.user.getPlayer(), spawnRequest.getSpawn(), currentGroup.getGroupID());
            }
        }catch (RuntimeException | InvalidMoveException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Response handle(CardRequest cardRequest){
        try {
            Update update;
            switch (cardRequest.getCardType()) {
                case "weaponLayout":
                    int cardNumber = cardRequest.getNumber();
                    StringBuilder updateString = new StringBuilder();
                    Weapon weapon = this.user.getPlayer().getWeapons().get(cardNumber);
                    updateString.append(weapon.getName()).append(";").append(weapon.getEffectsList().size());
                    update = new Update(null, "layouteffect");
                    update.setData(updateString.toString().toLowerCase().replace(" ", ""));
                    user.receiveUpdate(update);
                    return new AskInput("effectsToPlay");
                case "noCard":
                    user.receiveUpdate(new Update(null, "turnbar"));
                    GameController.get().updatePhase(currentGroup.getGroupID());
                    break;
                case "weaponToReload":
                    WeaponTile weaponsToReload = new WeaponTile();
                    weaponsToReload.setWeapons(GameController.get().getWeaponToReload(user.getPlayer()));
                    if (weaponsToReload.getWeapons().isEmpty()) {
                        user.receiveUpdate(new Update("You haven't weapons to reload", UPDATE_CONSOLE));
                        user.receiveUpdate(new Update(null, "turnbar"));
                        GameController.get().updatePhase(currentGroup.getGroupID());
                    } else {
                        update = new Update("You can reload these weapons: " + cardsToString(weaponsToReload.getWeapons(), 0), "choosecard");
                        update.setData(weaponsToReload.getStringIdWeapons().toLowerCase().replaceAll(" ", ""));
                        user.receiveUpdate(update);
                        update = new Update("\n You have these ammos: " +
                                user.getPlayer().getAmmos().toString(), "reload");
                        update.setData(user.getPlayer().getAmmos().toString().replace("[", "").replace("]", "")
                                .replace(" ", "").toLowerCase());
                        user.receiveUpdate(update);
                        return new AskInput("grabWeapon");
                    }
                    break;
                case "powerupToPlay":
                    List<Powerup> powerupsToPlay = ShootController.get()
                            .getPowerupsToPlay(user.getPlayer(), currentGroup.getGroupID());
                    if (powerupsToPlay.isEmpty()) {
                        user.receiveUpdate(new Update("You haven't powerups to play now", UPDATE_CONSOLE));
                        user.receiveUpdate(new Update(null, "turnbar")); //TODO check this (SCHERO) for GUI
                        GameController.get().updatePhase(currentGroup.getGroupID());
                    } else {
                        update = new Update("You can play these powerups:" + cardsToString(powerupsToPlay, 0), "choosecard");
                        update.setData(powerupToStringForGUI(powerupsToPlay));
                        user.receiveUpdate(update);
                        return new AskInput("choosePowerup");
                    }
                    break;
                default:
                    break;
            }
        }catch (RuntimeException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Response handle(SendInput inputResponse) {
        try {
            Player player = user.getPlayer();
            switch (inputResponse.getInputType()) {
                case "coordinate":
                    user.getPlayer().getCurrentMoves().get(0).execute(user.getPlayer(), currentGroup.getGroupID());
                    break;
                case "weapon chosen":
                    pickWeapon(player, inputResponse);
                    break;
                case "weaponGrabbed":
                    reloadWeapon(player, inputResponse);
                    break;
                case "fieldsFilled":
                    playWeapon(player, inputResponse);
                    break;
                case "powerupToPlay":
                    return preparePowerup(player, inputResponse);
                case "powerupFilled":
                    playPowerup(player, inputResponse);
                    break;
                case "weaponToPlay":
                    return prepareWeapon(player, inputResponse);
                default:
                    break;
            }
        }catch (RuntimeException | InvalidMoveException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Response handle(ShootRequest shootRequest) {
        return null;
    }

    private void playPowerup(Player player, SendInput inputResponse) {
        try {
            ShootController.get().playPowerup(this.user.getPlayer(), inputResponse.getInput(), currentGroup.getGroupID());
            GameController.get().updatePhase(currentGroup.getGroupID());
        } catch (RuntimeException | InvalidMoveException e) {
            user.getPlayer().getCurrentCardEffects().clear();
            user.receiveUpdate(new Update(e.getMessage(), UPDATE_CONSOLE));
            player.getUser().receiveUpdate(new Update(player, true));
        }
        player.setPhaseNotDone(false);
    }

    private Response preparePowerup(Player player, SendInput inputResponse) {
        try {
            String fields = GameController.get().preparePowerup(currentGroup.getGroupID(), inputResponse.getInput(), user.getPlayer());
            Update update = new Update(fields,"fillfields");
            update.setData(fields);
            this.user.receiveUpdate(update);
            return new AskInput("fillPowerup");
        } catch (RuntimeException e) {
            user.receiveUpdate(new Update("Powerup index out of bounds", UPDATE_CONSOLE));
            player.setPhaseNotDone(true);
            user.receiveUpdate(new Update(player, true));
        } catch (InvalidMoveException e) {
            user.receiveUpdate(new Update(e.getMessage(), UPDATE_CONSOLE));
            player.setPhaseNotDone(true);
            user.receiveUpdate(new Update(player, true));
        }
        return null;
    }

    private void playWeapon(Player player, SendInput inputResponse) {
        try {
            GameController.get().playWeapon(this.user.getPlayer(), inputResponse.getInput(), currentGroup.getGroupID());
            GameController.get().updatePhase(currentGroup.getGroupID());
        } catch (RuntimeException | InvalidMoveException e) {
            user.getPlayer().getCurrentCardEffects().clear();
            user.receiveUpdate(new Update(e.getMessage(), UPDATE_CONSOLE));
            player.getUser().receiveUpdate(new Update(player, true));
        }
        player.setPhaseNotDone(false);
    }

    private Response prepareWeapon(Player player, SendInput inputResponse) {
        try {
            String fields = GameController.get().prepareWeapon(user.getPlayer(), inputResponse.getInput(), currentGroup.getGroupID());
            Update update = new Update(fields,"fillfields");
            update.setData(fields);
            player.setPhaseNotDone(false);
            this.user.receiveUpdate(update);
            return new AskInput("fillFields");
       } catch (RuntimeException | InvalidMoveException e) {
            user.getPlayer().getCurrentCardEffects().clear();
            user.receiveUpdate(new Update(e.getMessage(), UPDATE_CONSOLE));
            player.getUser().receiveUpdate(new Update(player, true));
        }
        return null;
    }

    private void reloadWeapon(Player player, SendInput inputResponse) {
        try {
            GameController.get().reloadWeapon(Integer.parseInt(inputResponse.getInput()), currentGroup.getGroupID());
        } catch (RuntimeException | NotEnoughAmmoException e) {
            player.setPhaseNotDone(true);
            currentGroup.getGame().getCurrentPlayer().setPhase(Phase.RELOAD);
            user.receiveUpdate(new Update(e.getMessage(), UPDATE_CONSOLE));
            user.receiveUpdate(new Update(player, true));
        }
    }

    private void pickWeapon(Player player, SendInput inputResponse) {
        try {
            int weaponNumber = Integer.parseInt(inputResponse.getInput());
            player.getCurrentPosition().getGrabbable().pickGrabbable(currentGroup.getGroupID(), weaponNumber);
            //p.setPhaseNotDone(false); senza questo per ora funziona
            GameController.get().updatePhase(currentGroup.getGroupID());
        } catch (RuntimeException | NotExistingPositionException | NotEnoughAmmoException | NothingGrabbableException e) {
            user.getPlayer().getCurrentCardEffects().clear();
            user.receiveUpdate(new Update(e.getMessage(), UPDATE_CONSOLE));
            user.receiveUpdate(new Update(user.getPlayer(), true));
        }
    }

  /*  @Override
    public Response handle(ShootRequest shootRequest) {
        try {
                new MoveAndShoot();
                }catch(IndexOutOfBoundsException | InvalidMoveException e){
                user.getPlayer().getCurrentCardEffects().clear();
                user.receiveUpdate(new Update("Invalid input!", UPDATE_CONSOLE));
                user.receiveUpdate(new Update(user.getPlayer(), true));
            //user.receiveUpdate(new Update(user.getPlayer(),true)); <-socket null exception
                }catch(NumberFormatException e){
                user.getPlayer().getCurrentCardEffects().clear();
                user.receiveUpdate(new Update("Not Valid Number", UPDATE_CONSOLE));
                user.receiveUpdate(new Update(user.getPlayer(), true));
            }catch(NullPointerException e){
                user.getPlayer().getCurrentCardEffects().clear();
                user.receiveUpdate(new Update("Not Valid effects", UPDATE_CONSOLE));
                user.receiveUpdate(new Update(user.getPlayer(), true));
        }
        return null;
    }
*/
    @Override
    public Response handle(MoveRequest moveRequest) {
        try {
            Player currentPlayer = GameContext.get().getGame(currentGroup.getGroupID()).getCurrentPlayer();
            Move move = moveRequest.getMove();
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
        } catch (InvalidMoveException | RuntimeException e){
            user.getPlayer().getCurrentMoves().clear();
            user.getPlayer().setPhaseNotDone(false);
            user.receiveUpdate(new Update(e.getMessage(), UPDATE_CONSOLE));
            user.receiveUpdate(new Update(user.getPlayer(), true));
        }
        return null;
    }
}
