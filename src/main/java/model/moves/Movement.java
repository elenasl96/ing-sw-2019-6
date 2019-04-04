package model.moves;

import exception.InvalidMoveException;
import model.Player;
import model.field.Square;

import java.util.List;

public class Movement {

    public void move(Player player, Square destination){
        player.setCurrentPosition(destination);
    }

    public boolean isValid(Square position, Square destination, int steps) throws InvalidMoveException {
        do {
            if (position == destination)
                return true;
            else {
                position.getAdjacents().iterator().forEachRemaining( pos -> {
                    try {
                        isValid(pos, destination, steps-1);
                    } catch (InvalidMoveException e) {
                        e.printStackTrace();
                    }
                });//Non funzionerà mai nella vita: ricorsione con eccezioni è tipo ingestibile
            }
        } while (steps > 0);
        throw new InvalidMoveException();
    }
}
