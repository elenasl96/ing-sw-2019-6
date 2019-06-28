package model;

import model.enums.Phase;
import model.room.ModelObserver;
import model.room.Update;
import model.room.User;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * The class of each single game, Server Side
 * @see Board
 * @see Player
 * @see GameContext
 * @see model.room.Group
 */
public class Game implements Serializable {
    private int numberPlayers;
    private Board board;
    private PlayerList players = new PlayerList();
    private Player currentPlayer;
    private transient List<ModelObserver> observers = new LinkedList<>();
    private int skullsNumber;
    private transient boolean done;
    private transient boolean finalFrenzy;

    /**
     * Empty Constructor to avoid null pointer exception
     */
    public Game(){
        //no need to initiate any variable
    }

    /**
     * Constructor de facto, called by the group, sets the all game up.
     * Creates the board, sets the skullNumber as passed by parameters,
     * creates a player for every user, setting the first one as the
     * creator of the game. Sends an update to every user once the player
     * is created and starts the game, setting the first player to FIRST_SPAWN phase
     * @param skullNumber   the skullNumber of the game
     * @param fieldNumber   the fieldNumber of the game
     * @param users         the users of the group, that will join the game
     * @see Phase
     * @see Player
     * @see model.room.Group
     */
    public void setGame(int skullNumber, int fieldNumber, List<User> users) {
        this.skullsNumber = skullNumber;
        this.board = new Board(fieldNumber);
        Collections.sort(users);
        int cont = 0;
        for (User u: users){
            //adds a new player for user u to the list
            Player player = new Player(u);
            player.setPlayerNumber(cont);
            this.players.add(player);
            u.setPlayer(player);

            //sends it to the ClientContext
            System.out.println(">>> It's setGame sending "+this.players.get(numberPlayers)+" to "+u.getUsername());
            u.receiveUpdate(new Update(this.players.get(numberPlayers), true));
            this.numberPlayers++;
            cont++;
        }
        this.players.get(0).setFirstPlayer();
        this.currentPlayer = this.players.get(0);
        currentPlayer.setPhase(Phase.FIRST_SPAWN);
        System.out.println(">>> Sending Update to currentPlayer:" + currentPlayer.getUser().getUserID());
        currentPlayer.getUser().receiveUpdate(new Update(currentPlayer, true));
        Update update = new Update("It's "+currentPlayer.getName()+"'s turn");
        System.out.println(">>> Sending broadcast update from GameController: "+update.toString());
        this.sendUpdate(update);
    }

    int getNumberPlayers() {
        return numberPlayers;
    }

    public Player getCurrentPlayer(){
        return this.currentPlayer;
    }

    public Board getBoard() {
        return board;
    }

    public PlayerList getPlayers() {
        return players;
    }

    public boolean isFinalFrenzy(){
        return this.finalFrenzy;
    }

    /**
     * Adds a game observer, as specified by the Observer pattern
     * The observers of the game are instances of interface ModelObserver
     * @see ModelObserver
     * @param observer  The observer of the game, that will be added to the list
     */
    public void addObserverGame(ModelObserver observer) {
        this.observers.add(observer);
    }

    /**
     * Sends to every observer (all the players) an update
     * @param update  assigned by the GameController for each and every move
     */
    public void sendUpdate(Update update) {
        for(ModelObserver o: observers ){
            System.out.println(">>> It's Game here sending an update to "+o);
            o.onUpdate(update);
        }
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void setFinalFrenzy(boolean finalFrenzy) {
        this.finalFrenzy = finalFrenzy;
    }
}