package model;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private int numberPlayers;
    private Board board;
    private List<Player> players;
    private int turn;
    private int skullsNumber;

    public Game(){
        //Default constructor
        this.numberPlayers = 0;
        this.board = new Board();
        this.players = new ArrayList<Player>();
        this.turn = 0;
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

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int phase) {
        this.turn = phase;
    }
}