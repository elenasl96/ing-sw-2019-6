package model;

import model.room.GameUpdateObserver;
import model.room.Update;
import model.room.User;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Game implements Serializable {
    private static int GameID;
    public static Game game;
    private int numberPlayers;
    private Board board;
    private PlayerList players = new PlayerList();
    private Player currentPlayer;
    private transient List<GameUpdateObserver> observers = new LinkedList<>();
    private transient int turn;
    private int skullsNumber;
    private transient boolean done;
    private transient boolean finalFrenzy;

    public static Game get(int groupNumber) {
        if(GameID != groupNumber){
            return Game.get(groupNumber);
        } else return ;
    }

    public List<GameUpdateObserver> getObservers() {
        return observers;
    }

    public void setObservers(List<GameUpdateObserver> observers) {
        this.observers = observers;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public Game(int skullNumber, int fieldNumber, List<User> users, int groupID) {
        GameID = groupID;

        this.skullsNumber = skullNumber;
        this.board = new Board(fieldNumber);
        this.turn = 0;
        this.done = false;
        this.finalFrenzy = false;

        //TODO test if sort works
        Collections.sort(users);
        this.numberPlayers = 0;
        for (User u: users){
            //adds a new player for user u to the list
            Player player = new Player(this.numberPlayers, u);
            this.players.add(player);
            u.setPlayer(player);

            //sends it to the ClientContext
            System.out.println(">>> It's Game sending "+this.players.get(numberPlayers)+" to "+u.getUsername());
            u.receiveUpdate(new Update(this.players.get(numberPlayers), true));
            this.numberPlayers++;
        }
        this.players.get(0).setFirstPlayer(true);
        this.currentPlayer = this.players.get(0);
    }

    public int getNumberPlayers() {
        return numberPlayers;
    }

    public Player getCurrentPlayer(){
        return this.currentPlayer;
    }

    public void setNumberPlayers(int numberPlayers) {
        this.numberPlayers = numberPlayers;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public PlayerList getPlayers() {
        return players;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int phase) {
        this.turn = phase;
    }

    public void setPlayers(PlayerList players) {
        this.players = players;
    }

    public int getSkullsNumber() {
        return skullsNumber;
    }

    public void setSkullsNumber(int skullsNumber) {
        this.skullsNumber = skullsNumber;
    }

    public boolean isDone() {
        return done;
    }

    public boolean isFinalFrenzy(){
        return this.finalFrenzy;
    }

    public void addObserverGame(GameUpdateObserver observer) {
        this.observers.add(observer);
    }

    /**
     * Sends to every observer (all the players) an update
     * @param update  assigned by the GameController for each and every move
     */
    public void sendUpdate(Update update) {
        for(GameUpdateObserver o: observers ){
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
