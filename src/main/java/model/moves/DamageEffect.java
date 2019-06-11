package model.moves;

import controller.GameController;
import model.Player;
import model.exception.InvalidMoveException;
import network.commands.Response;
import java.util.stream.Stream;

public class DamageEffect extends Effect{
    private int damages;

    public DamageEffect(Stream<Target> targets, int damages, Boolean optionality){
        super(targets, optionality);
        this.damages = damages;
    }

    @Override
    public Response execute(Player playerDamaging, int groupId){
        for ( Target t : targets){
            t.addDamages(playerDamaging, damages, groupId);
        }
        return null;
    }

    @Override
    public String getFieldsToFill() {
        StringBuilder string = new StringBuilder();
        string.append("Damage Effect: ");
        for(Target t: targets){
            string.append(t.getFieldsToFill());
        }
        return string.toString();
    }

    @Override
    public void fillFields(String[] inputMatrix, int groupID) {
        int i = 0;
        for(Target t: targets){
            targets.add(t.fillFields(groupID));
            targets.remove(t);
            i++;
        }
    }

    @Override
    public int setFieldsToFill(String[] inputMatrix, int index, int groupID) {
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
