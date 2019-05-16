package model.moves;

import controller.MoveRequestHandler;
import exception.InvalidMoveException;
import model.Player;
import model.enums.EffectType;

public class DamageEffect extends Effect implements Move{
    private int damages;

    public DamageEffect(EffectType type, Target target, int damages, Boolean optionality){
        super(type, target, optionality);
        this.damages = damages;
    }

    public int getDamages(){
        return this.damages;
    }

    @Override
    public void execute(Player playerDamaging, int groupId){
        target.addDamages(playerDamaging, damages, groupId);
    }

    @Override
    public void handle(MoveRequestHandler moveRequestHandler, int groupId) throws InvalidMoveException {
        moveRequestHandler.handle(this, groupId);
    }
}
