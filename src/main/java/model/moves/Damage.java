package model.moves;

import controller.MoveRequestHandler;
import exception.InvalidMoveException;
import model.Player;
import network.socket.commands.Response;

import static java.lang.Math.min;

public class Damage implements Move{
    private Player playerDamaged;
    private int numDamage;

    public Response execute(Player playerDamaging){
        playerDamaged.getPlayerBoard().addDamage(playerDamaging, min(numDamage, playerDamaged.getPlayerBoard().getNumDamageLeft()));
        //TODO
        return null;
    }

    @Override
    public void handle(MoveRequestHandler moveRequestHandler) throws InvalidMoveException {

    }

    public Damage(){
    }

    public Damage(Player playerDamaged, int numDamage){
        this.playerDamaged = playerDamaged;
        this.numDamage = numDamage;
    }
}
