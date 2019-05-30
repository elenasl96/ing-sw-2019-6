package model.moves;

import model.Player;
import network.socket.commands.Response;
import java.util.stream.Stream;

public class DamageEffect extends Effect implements Move{
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
}
