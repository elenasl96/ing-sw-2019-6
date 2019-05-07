package model.moves;

import controller.MoveRequestHandler;
import exception.InvalidMoveException;
import model.Player;
import model.field.Coordinate;
import model.field.Field;
import model.room.Update;
import network.socket.commands.Response;
import network.socket.commands.response.MoveUpdateResponse;

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
    public Response execute(Player p) throws InvalidMoveException {
        this.movement.execute(p);
        return new MoveUpdateResponse(p, 0);
    }

    @Override
    public void handle(MoveRequestHandler moveRequestHandler) throws InvalidMoveException {
        moveRequestHandler.handle(this);
    }


}