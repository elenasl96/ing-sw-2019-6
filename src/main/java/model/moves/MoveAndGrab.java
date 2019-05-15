package model.moves;

import controller.MoveRequestHandler;
import exception.InvalidMoveException;
import model.Player;
import model.field.Coordinate;

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
    public void execute(Player p, int groupId) throws InvalidMoveException {
        this.movement.execute(p, groupId);
        this.grab.execute(p, groupId);
    }

    @Override
    public void handle(MoveRequestHandler moveRequestHandler, int groupId) throws InvalidMoveException {
        moveRequestHandler.handle(this, groupId);
    }

    public Movement getMovement() {
        return movement;
    }
}
