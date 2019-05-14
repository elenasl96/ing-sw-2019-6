package model.moves;

import controller.MoveRequestHandler;
import exception.InvalidMoveException;
import model.Player;
import model.field.Room;
import model.field.Square;
import network.socket.commands.Response;

import static java.lang.Math.min;

public class DamageEffect extends Effect implements Move{
    private int damages;

    public DamageEffect(Player player, int damages){
        this.target = player;
        this.damages = damages;
    }

    public DamageEffect(Square square, int damages){
        this.target = square;
        this.damages = damages;
    }

    public DamageEffect(Room room, int damages){
        this.target = room;
        this.damages = damages;
    }

    public Response execute(Player playerDamaging){
        target.addDamages(playerDamaging, min(damages, target.getPlayerBoard(groupId).getNumDamageLeft()));
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


}
