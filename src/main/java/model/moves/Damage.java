package model.moves;

import controller.MoveRequestHandler;
import exception.InvalidMoveException;
import model.Game;
import model.Player;
import network.socket.commands.Response;

import static java.lang.Math.min;

public class DamageEffect extends Effect implements Move{
    private int damages;

    public Response execute(Player playerDamaging){
        Game.get().
        playerDamaged.getPlayerBoard().addDamage(playerDamaging, min(damages, playerDamaged.getPlayerBoard().getNumDamageLeft()));
        //TODO
        return null;
    }

    @Override
    public void handle(MoveRequestHandler moveRequestHandler) throws InvalidMoveException {
        //TODO
    }

    public DamageEffect(int damages){
        this.damages = damages;
    }

    public Damage(Player playerDamaged, int numDamage){
        this.playerDamaged = playerDamaged;
        this.numDamage = numDamage;
    }
}
