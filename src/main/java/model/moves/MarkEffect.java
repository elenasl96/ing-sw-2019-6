package model.moves;

import controller.MoveRequestHandler;
import exception.FullMarksException;
import exception.InvalidMoveException;
import model.Player;
import model.enums.EffectType;
import network.socket.commands.Response;

import java.util.Collections;
import java.util.List;

import static java.lang.Math.min;

public class MarkEffect extends Effect implements Move{
    private int nMarks;

    public MarkEffect(EffectType type, List<Target> playerMarked, int nMarks, Boolean optionality) {
        super(type, playerMarked, optionality);
        this.targets = playerMarked;
        this.nMarks = nMarks;
    }

    public void execute(Player p, int groupId) throws FullMarksException {
        for(Target t : targets){
            int occurrences = Collections.frequency(t.getPlayerBoard(groupId).get(0).getMarks(), p);
            if(occurrences<3){
                t.getPlayerBoard(groupId).get(0).addMarks(p, min(3-occurrences, nMarks));
            } else{
                throw new FullMarksException();
            }
        }

    }

    @Override
    public Response handle(MoveRequestHandler moveRequestHandler, int groupId) throws InvalidMoveException {
        return null;//TODO
    }

}
