package model.moves;

import controller.MoveRequestHandler;
import exception.InvalidMoveException;
import model.Player;
import model.enums.EffectType;
import network.socket.commands.Response;

import java.util.List;

public class DamageEffect extends Effect implements Move{
    private int damages;

    public DamageEffect(EffectType type, List<Target> target, int damages, Boolean optionality){
        super(type, target, optionality);
        this.damages = damages;
    }

    public int getDamages(){
        return this.damages;
    }

    @Override
    public void execute(Player playerDamaging, int groupId){
        for ( Target t : targets){
            t.addDamages(playerDamaging, damages, groupId);
        }
    }

    @Override
    public Response handle(MoveRequestHandler moveRequestHandler, int groupId) throws InvalidMoveException {
        moveRequestHandler.handle(this, groupId);
        return null; //TODO
    }
}
