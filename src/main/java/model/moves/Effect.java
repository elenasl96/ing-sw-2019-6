package model.moves;

import controller.GameController;
import controller.ShootController;
import model.GameContext;
import model.enums.TargetType;
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

    public String getFieldsToFill(){
        StringBuilder stringBuilder = new StringBuilder();
        for(Target t: this.getTarget()) {
            stringBuilder.append(t.getFieldsToFill());
        }
        return stringBuilder.toString();
    }

    public void fillFields(int groupID){
        for(Target t: targets){
            targets.add(t.fillFields(groupID));
            targets.remove(t);
        }
    }

    public int setFieldsToFill(String[] inputMatrix, int index, int groupID){
        for (int k=0; k<this.getTarget().size(); k++) {
            if(!this.getTarget().get(k).isFilled()) {
                switch (this.targets.get(k).getTargetType()) {
                    case MINE:
                        this.targets.get(k).setMine(groupID);
                        break;
                    case ALL:
                        this.targets.addAll(this.getTarget().get(k).findAllTargets(this.getTarget().get(k), groupID));
                        this.targets.remove(k);
                        break;
                    case BASIC_EQUALS:
                        this.targets.get(k).setFieldsToFill(null, groupID);
                        break;
                    default:
                        if (k >= inputMatrix.length && !this.optionality)
                            throw new InvalidMoveException("fields missing");
                        ShootController.get().checkTarget(this.getTarget().get(k), inputMatrix[index], groupID);
                        this.getTarget().get(k).setFieldsToFill(inputMatrix[index], groupID);
                        index++;
                }

            }
        }
        return index;
    }
}

