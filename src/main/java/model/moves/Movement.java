package model.moves;

import exception.InvalidMoveException;
import model.Player;
import model.field.Square;

import java.util.List;

public class Movement implements Move{
    private Square destination;

    public void execute(Player p) {
         p.setCurrentPosition(destination);
    }

    public Movement(Square destination){
        this.destination = destination;
    }
}
