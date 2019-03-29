package model;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private int numberPlayers;
    private Board board;
    private List<Player> players;
    private int phase;
    private int skullsNumber;

    public Game(int numberPlayers, Board board, List<Player> players, int phase) {
        this.numberPlayers = numberPlayers;
        this.board = board;
        this.players = players;
        this.phase = phase;
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

    public int getPhase() {
        return phase;
    }

    public void setPhase(int phase) {
        this.phase = phase;
    }
}
