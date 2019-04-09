package model.moves;
import exception.InvalidMoveException;
import model.Player;

import java.io.Serializable;

public interface Move extends Serializable{
    void execute(Player p) throws InvalidMoveException;
}
