package model.moves;
import controller.MoveRequestHandler;
import exception.InvalidMoveException;
import model.Player;

import java.io.Serializable;

public interface Move extends Serializable {
    void execute(Player player, int groupId) throws InvalidMoveException;
    void handle(MoveRequestHandler moveRequestHandler, int groupId) throws InvalidMoveException;
}
