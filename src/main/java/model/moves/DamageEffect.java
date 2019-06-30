package model.moves;

import model.Player;
import model.exception.*;
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
    public Response execute(Player playerDamaging, int groupId) throws NotExistingPositionException {
        for ( Target t : this.getTarget()){
            t.addDamages(playerDamaging, damages, groupId);
        }
        return null;
    }

    @Override
    public String getFieldsToFill() {
        String string = super.getFieldsToFill();
        if(!super.getFieldsToFill().isEmpty())
            return "Damage Effect: " + string;
        else
            return string;
    }

    @Override
    public int setFieldsToFill(Player player, String[] inputMatrix, int index, int groupID) throws TargetsException, NotExistingPositionException, SquareNotFoundException {
        index += super.setFieldsToFill(player, inputMatrix,index,groupID);
        return index;
    }

    @Override
    public boolean isFilled() {
        return true;
    }

    @Override
    public boolean doesDamage() {
        return true;
    }
}
