package model.moves;

import controller.ShootController;
import model.GameContext;
import model.Player;
import model.decks.CardEffect;
import model.exception.*;

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

    public void fillFields(int groupID) throws NotExistingTargetException {
        List<Target> targetsToRemove = new ArrayList<>();
        List<Target> realTargets = new ArrayList<>();
        for(Target t : targets){
            if (t.isFilled())
                realTargets.add(t.fillFields(groupID));
            targetsToRemove.add(t);
        }
        targets.removeAll(targetsToRemove);
        targets.addAll(realTargets);
    }


    public int setFieldsToFill(Player player, String[] inputMatrix, int index, int groupID) throws TargetsException, NotExistingPositionException, SquareNotFoundException {
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
                    case LATEST_SQUARE:
                        this.targets.get(k).setFieldsToFill(
                                this.getPreviousEffect(groupID).getTarget().get(0).findRealTarget(null, groupID).getCurrentPosition().getCoord().toString(), groupID);
                        break;
                    default:
                        if ((inputMatrix == null || k >= inputMatrix.length) && !this.optionality)
                            throw new NotEnoughFieldsException();
                        if (inputMatrix != null && index<inputMatrix.length && !this.targets.get(k).isFilled()){
                                if(inputMatrix[index].equals("")) {
                                    if (!this.optionality)
                                        throw new NotEnoughFieldsException();
                                }else {
                                    ShootController.get().checkTarget(player, this.getTarget().get(k), inputMatrix[index], groupID);
                                    this.getTarget().get(k).setFieldsToFill(inputMatrix[index], groupID);
                                }
                            index++;
                            }

                }

            }
        }
        return index;
    }

    private Effect getPreviousEffect(int groupID) throws SquareNotFoundException {
        for(CardEffect c: GameContext.get().getGame(groupID).getCurrentPlayer().getCurrentCardEffects()){
            for(int i=0; i<c.getEffects().size(); i++){
                if(c.getEffects().get(i).equals(this) && i>0){
                    return c.getEffects().get(i-1);
                }
            }
        }
        throw new SquareNotFoundException();
    }

    public abstract boolean isFilled();

    public abstract boolean doesDamage();
}

