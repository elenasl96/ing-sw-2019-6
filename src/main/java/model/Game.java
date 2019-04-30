package model;

import model.room.GameUpdateObserver;
import model.room.User;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class Game implements Serializable {
    private int numberPlayers;
    private Board board;

    private List<Player> players = new LinkedList<>();
    private Player currentPlayer;
    private transient List<GameUpdateObserver> observers = new LinkedList<>();
    private transient int turn;
    private int skullsNumber;
    private transient boolean done;
    private transient boolean finalFrenzy;

    public List<GameUpdateObserver> getObservers() {
        return observers;
    }

    public void setObservers(List<GameUpdateObserver> observers) {
        this.observers = observers;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public Game(int skullNumber, int fieldNumber) {
        this.skullsNumber = skullNumber;
        this.numberPlayers = 0;
        this.board = new Board(fieldNumber);
        this.turn = 0;
        this.done = false;
        this.finalFrenzy = false;
    }

    public void addPlayer(User user){
        Player player;
        if(this.numberPlayers == 0){
            player = new Player(this.numberPlayers, true, user.getUsername(), user.getCharacter());
        }
        else {
            player = new Player(this.numberPlayers, false, user.getUsername(), user.getCharacter());
        }
        this.players.add(player);
        this.currentPlayer = players.get(0);
        user.setPlayer(player);
        this.numberPlayers++;
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

    public List<Player> getPlayers() {
        return players;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int phase) {
        this.turn = phase;
    }

    public void setPlayers(List<Player> players) {
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

    public void sendUpdate(String content) {
        for(GameUpdateObserver o: observers ){
            o.onUpdate(content);
        }
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void setFinalFrenzy(boolean finalFrenzy) {
        this.finalFrenzy = finalFrenzy;
    }
}
