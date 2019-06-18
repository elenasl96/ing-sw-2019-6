package model.moves;

import model.Player;
import network.commands.Response;
import java.util.stream.Stream;

//TODO javadoc
public class DamageEffect extends Effect{
    private int damages;

    public DamageEffect(Stream<Target> targets, int damages, Boolean optionality){
        super(targets, optionality);
        this.damages = damages;
    }

    @Override
    public Response execute(Player playerDamaging, int groupId){
        for ( Target t : this.getTarget()){
            t.addDamages(playerDamaging, damages, groupId);
        }
        return null;
    }

    @Override
    public String getFieldsToFill() {
        StringBuilder string = new StringBuilder();
        for(Target t: this.getTarget()){
            string.append(t.getFieldsToFill());
        }
        if(!string.toString().isEmpty())
            return "Damage Effect: " + string.toString();
        else
            return string.toString();
    }

    @Override
    public int setFieldsToFill(String[] inputMatrix, int index, int groupID) {
        index += super.setFieldsToFill(inputMatrix,index,groupID);
        return index;
    }
}
