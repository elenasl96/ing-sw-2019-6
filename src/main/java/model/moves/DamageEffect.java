package model.moves;

import controller.MoveRequestHandler;
import exception.InvalidMoveException;
import model.Player;
import model.enums.EffectType;
import network.socket.commands.Response;
import network.socket.commands.response.AskInput;

import java.util.List;

public class DamageEffect extends Effect implements Move{
    private int damages;

    public DamageEffect(EffectType type, List<Target> target, int damages, Boolean optionality, Boolean different){
        super(type, target, optionality, different);
        this.damages = damages;
    }

    public int getDamages(){
        return this.damages;
    }

    @Override
    public Response execute(Player playerDamaging, int groupId){
        for ( Target t : targets){
            t.addDamages(playerDamaging, damages, groupId);
        }
        return null;
    }

}
