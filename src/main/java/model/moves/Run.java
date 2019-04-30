package model.moves;

import controller.MoveRequestHandler;
import exception.InvalidMoveException;
import model.Player;
import model.field.Coordinate;
import model.field.Field;
import model.field.Square;
import network.socket.commands.Response;

public class Run implements Move {
    private Coordinate coordinate;
    private Movement movement;

    public Run(Coordinate destination){
        this.coordinate = destination;
    }

    public Coordinate getCoordinate(){return this.coordinate;}

    public void setDestination(Square destination){
        this.movement.setDestination(destination);
        movement.setMaxSteps(3);
    }

    public void finalFrenzy(){
        movement.setMaxSteps(4);
    }
    public void setField(Field field){
        this.movement.setField(field);
    }

    @Override
    public Response execute(Player p) throws InvalidMoveException {
        this.movement.execute(p);
        return null;
    }

    @Override
    public void handle(MoveRequestHandler moveRequestHandler) throws InvalidMoveException {
        moveRequestHandler.handle(this);
    }
}
