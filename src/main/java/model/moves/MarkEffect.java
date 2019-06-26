package model.moves;

import model.Player;
import network.commands.Response;

import java.util.stream.Stream;

import static java.lang.Math.min;

//TODO javadoc
public class MarkEffect extends Effect implements Move{
    private int nMarks;

    public MarkEffect(Stream<Target> targets, int nMarks, Boolean optionality) {
        super(targets, optionality);
        this.nMarks = nMarks;
    }

    public Response execute(Player p, int groupId) {
        for(Target t : this.getTarget()){
            t.addMarks(p, groupId, this.nMarks);
        }
        return null;
    }

    @Override
    public String getFieldsToFill() {
        String string = super.getFieldsToFill();
        if(!super.getFieldsToFill().isEmpty())
            return "Mark Effect: " + string;
        else
            return string;
    }

    @Override
    public int setFieldsToFill(String[] inputMatrix, int index, int groupID) {
       index += super.setFieldsToFill(inputMatrix,index,groupID);
        return index;
    }

    @Override
    public boolean isFilled() {
        return true;
    }
}
