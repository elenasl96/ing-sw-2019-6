package model.moves;

import controller.GameController;
import model.exception.InvalidMoveException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//TODO javadoc
public abstract class Effect implements Move {
    private Boolean optionality;
    private List<Target> targets = new ArrayList<>();

    public Effect(){
    }

    public Effect(Stream<Target> targets, Boolean optionality){
        this.targets = targets.collect(Collectors.toCollection(ArrayList::new));
        this.optionality = optionality;
    }

    public List<Target> getTarget(){
        return this.targets;
    }

    public Boolean getOptionality() {
        return optionality;
    }

    public abstract String getFieldsToFill();

    public void fillFields(int groupID){
        for(Target t: targets){
            targets.add(t.fillFields(groupID));
            targets.remove(t);
        }
    }

    public int setFieldsToFill(String[] inputMatrix, int index, int groupID){
        for (int k=0; k<this.getTarget().size(); k++) {
            if(!this.getTarget().get(k).isFilled()) {
                if (k >= inputMatrix.length) throw new InvalidMoveException("fields missing");
                GameController.get().checkTarget(this.getTarget().get(k), inputMatrix[index], groupID);
                this.getTarget().get(k)
                        .setFieldsToFill(inputMatrix[index], groupID);
                index++;
            }
        }
        return index;
    }
}

