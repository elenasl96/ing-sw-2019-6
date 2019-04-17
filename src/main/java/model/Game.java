package model;

import socket.model.GameUpdateObserver;
import socket.model.User;

import java.util.LinkedList;
import java.util.List;

public class Game {
    private int numberPlayers;
    private Board board;
    private List<Player> players = new LinkedList<>();
    private transient List<GameUpdateObserver> observers = new LinkedList<>();
    private int turn;
    private int skullsNumber;
    private boolean done;
    private int fieldNumber;

    public List<GameUpdateObserver> getObservers() {
        return observers;
    }

    public void setObservers(List<GameUpdateObserver> observers) {
        this.observers = observers;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public int getFieldNumber() {
        return fieldNumber;
    }

    public void setFieldNumber(int fieldNumber) {
        this.fieldNumber = fieldNumber;
    }

    public Game(int skullNumber, int fieldNumber) {
        this.skullsNumber = skullNumber;
        this.numberPlayers = 0;
        this.board = new Board(fieldNumber);
        this.turn = 0;
        this.done = false;
        this.fieldNumber = fieldNumber;
    }


    public int getNumberPlayers() {
        return numberPlayers;
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

    public void addObserverGame(GameUpdateObserver observer) {
        this.observers.add(observer);
    }

    public void sendStartNotification() {
        for (GameUpdateObserver observer : observers) {
            observer.onStart();
        }
    }
}
