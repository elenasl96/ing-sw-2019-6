package model;

import java.util.LinkedList;
import java.util.List;

public class Game {
    private int numberPlayers;
    private Board board;
    private List<Player> players = new LinkedList<>();
    private int turn;
    private int skullsNumber;
    private boolean done;

    public Game(int skullNumber, int fieldNumber) {
        this.skullsNumber = skullNumber;
        this.numberPlayers = 0;
        this.board = new Board(fieldNumber);
        this.turn = 0;
        this.done = false;
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
}
