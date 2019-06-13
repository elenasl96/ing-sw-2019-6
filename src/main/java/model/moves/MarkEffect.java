package model.moves;

import model.exception.FullMarksException;
import model.Player;
import network.commands.Response;
import java.util.Collections;
import java.util.stream.Stream;

import static java.lang.Math.min;

public class MarkEffect extends Effect implements Move{
    private int nMarks;

    public MarkEffect(Stream<Target> targets, int nMarks, Boolean optionality) {
        super(targets, optionality);
        this.nMarks = nMarks;
    }

    public Response execute(Player p, int groupId) {
        for(Target t : this.getTarget()){
            int occurrences = Collections.frequency(t.getPlayerBoards(groupId).get(0).getMarks(), p);
            if(occurrences<3){
                t.getPlayerBoards(groupId).get(0).addMarks(p, min(3-occurrences, nMarks));
            } else{
                throw new FullMarksException();
            }
        }
        return null;
    }

    @Override
    public String getFieldsToFill() {
        StringBuilder string = new StringBuilder();
        string.append("Mark Effect: ");
        for(Target t: this.getTarget()){
            string.append(t.getFieldsToFill());
        }
        return string.toString();
    }

    @Override
    public int setFieldsToFill(String[] inputMatrix, int index, int groupID) {
       index += super.setFieldsToFill(inputMatrix,index,groupID);
        return index;
    }
}
