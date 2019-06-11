package model.moves;

import model.Player;
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
}
