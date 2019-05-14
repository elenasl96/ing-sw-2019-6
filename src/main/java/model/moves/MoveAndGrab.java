package model.moves;

import controller.MoveRequestHandler;
import exception.InvalidMoveException;
import model.Player;
import model.field.Coordinate;
import network.socket.commands.Response;

public class MoveAndGrab implements Move {
    private Movement movement;
    private Grab grab;

    public MoveAndGrab(Coordinate destination){
        this.movement = new Movement(destination);
        this.grab = new Grab();
    }

    public void setMaxSteps(int maxSteps){
        movement.setMaxSteps(maxSteps);
    }

    @Override
    public Response execute(Player p, int groupId) throws InvalidMoveException {
        this.movement.execute(p, groupId);
        this.grab.execute(p, groupId);
        return null;
    }

    @Override
    public void handle(MoveRequestHandler moveRequestHandler, int groupId) throws InvalidMoveException {
        moveRequestHandler.handle(this, groupId);
    }

    public Movement getMovement() {
        return movement;
    }
}
