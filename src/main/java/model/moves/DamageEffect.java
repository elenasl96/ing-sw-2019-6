package model.moves;

import controller.MoveRequestHandler;
import exception.InvalidMoveException;
import model.Player;
import model.enums.EffectType;
import model.field.Room;
import model.field.Square;
import network.socket.commands.Response;

import static java.lang.Math.min;

public class DamageEffect extends Effect implements Move{
    private int damages;

    public DamageEffect(EffectType type, Player player, int damages){
        super(type, player);
        this.damages = damages;
    }

    public DamageEffect(EffectType type, Square square, int damages){
        super(type, square);
        this.target = square;
        this.damages = damages;
    }

    public DamageEffect(EffectType type, Room room, int damages){
        super(type, room);
        this.target = room;
        this.damages = damages;
    }

    public void addDamages(Player playerDamaging, int damages){
    }

    public Response execute(Player playerDamaging, int groupId){
        this.addDamages(playerDamaging, damages);
        return null;
    }

    @Override
    public void handle(MoveRequestHandler moveRequestHandler, int groupId) throws InvalidMoveException {
        //TODO
    }
}
