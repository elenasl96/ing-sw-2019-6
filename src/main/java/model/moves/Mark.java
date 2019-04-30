package model.moves;

import controller.MoveRequestHandler;
import exception.FullMarksException;
import exception.InvalidMoveException;
import model.Player;
import network.socket.commands.Response;

import java.util.Collections;

import static java.lang.Math.min;

public class Mark implements Move{
    private Player playerMarked;
    private int nMarks;

    public Response execute(Player p) throws FullMarksException {
        int occurrences = Collections.frequency(playerMarked.getPlayerBoard().getMarks(), p);
        if(occurrences<3){
                playerMarked.getPlayerBoard().addMarks(p, min(3-occurrences, nMarks));
        } else{
            throw new FullMarksException();
        }
        return null;
    }

    @Override
    public void handle(MoveRequestHandler moveRequestHandler) throws InvalidMoveException {

    }

    public Mark(){}
    public Mark(Player playerMarked, int nMarks) {
        this.playerMarked = playerMarked;
        this.nMarks = nMarks;
    }

}
