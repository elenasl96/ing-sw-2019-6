package model.moves;

import controller.MoveRequestHandler;
import exception.FullMarksException;
import exception.InvalidMoveException;
import model.Player;
import model.enums.EffectType;
import network.socket.commands.Response;

import java.util.Collections;

import static java.lang.Math.min;

public class MarkEffect extends Effect implements Move{
    private Player playerMarked;
    private int nMarks;

    public Response execute(Player p, int groupId) throws FullMarksException {
        int occurrences = Collections.frequency(playerMarked.getPlayerBoard(groupId).get(0).getMarks(), p);
        if(occurrences<3){
                playerMarked.getPlayerBoard(groupId).get(0).addMarks(p, min(3-occurrences, nMarks));
        } else{
            throw new FullMarksException();
        }
        return null;
    }

    public MarkEffect(){

    }

    public MarkEffect(EffectType type, Player playerMarked, int nMarks) {
        super(type, playerMarked);
        this.playerMarked = playerMarked;
        this.nMarks = nMarks;
    }

    @Override
    public void handle(MoveRequestHandler moveRequestHandler, int groupId) throws InvalidMoveException {
        //TODO
    }

}
