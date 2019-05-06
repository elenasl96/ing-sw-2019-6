package model.room;

import model.Player;
import model.moves.Move;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Command implements Serializable {
    private Player player;
    private String content;
    private ArrayList<Move> moves = new ArrayList<>();

    public Command(Player player, String command){
        this.player = player;
        this.content = command;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public void addMove(Move move) {
        this.moves.add(move);
    }

    public Player getSender() {
        return player;
    }

    public String getContent() {
        return content;
    }
}
