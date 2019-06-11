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
        for(Target t: targets){
            string.append(t.getFieldsToFill());
        }
        if(!string.toString().isEmpty())
            return "Damage Effect: " + string.toString();
        else
            return string.toString();
    }

    @Override
    public void fillFields(int groupID) {
       super.fillFields(groupID);

    }

    @Override
    public int setFieldsToFill(String[] inputMatrix, int index, int groupID) {
        index += super.setFieldsToFill(inputMatrix,index,groupID);
        return index;
    }
}
