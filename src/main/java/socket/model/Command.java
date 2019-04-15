package socket.model;

import model.moves.Move;

import java.io.Serializable;
import java.util.ArrayList;

public class Command implements Serializable {
    private User user;
    private String content;
    private ArrayList<Move> moves = new ArrayList<>();

    public Command(Group currentGroup, User currentUser, String command){
        this.user = currentUser;
        this.content = command;
    }

    public ArrayList<Move> getMoves() {
        return moves;
    }

    public void addMove(Move move) {
        this.moves.add(move);
    }

    public User getSender() {
        return user;
    }

    public String getContent() {
        return content;
    }
}
