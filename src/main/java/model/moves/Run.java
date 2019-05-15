package model.moves;

import controller.MoveRequestHandler;
import exception.InvalidMoveException;
import model.Player;
import model.field.Coordinate;
import model.field.Field;

public class Run implements Move {
    private Movement movement;

    public Run(Coordinate destination){
        this.movement = new Movement(destination);
    }

    public void setMaxSteps(int maxSteps){
        movement.setMaxSteps(maxSteps);
    }

    public void setField(Field field){
        this.movement.setField(field);
    }

    public Movement getMovement() {
        return movement;
    }

    @Override
    public void execute(Player p, int groupId) throws InvalidMoveException {
        this.movement.execute(p, groupId);
    }

    @Override
    public void handle(MoveRequestHandler moveRequestHandler, int groupId) throws InvalidMoveException {
        moveRequestHandler.handle(this, groupId);
    }
}
