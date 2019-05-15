package model.moves;

import controller.MoveRequestHandler;
import exception.InvalidMoveException;
import model.Player;
import model.enums.EffectType;
import network.socket.commands.Response;

public class DamageEffect extends Effect implements Move{
    private int damages;

    public DamageEffect(EffectType type, Target target, int damages, Boolean optionality){
        super(type, target, optionality);
        this.damages = damages;
    }

    public void execute(Player playerDamaging, int groupId){
        target.addDamages(playerDamaging, damages, groupId);
    }

    @Override
    public void handle(MoveRequestHandler moveRequestHandler, int groupId) throws InvalidMoveException {
        //TODO
    }
}
