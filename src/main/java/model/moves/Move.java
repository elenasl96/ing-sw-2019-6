package model.moves;
import controller.MoveRequestHandler;
import exception.InvalidMoveException;
import model.Player;
import network.socket.commands.Response;

import java.io.Serializable;

public interface Move extends Serializable {
    void execute(Player player, int groupId) throws InvalidMoveException;
    Response handle(MoveRequestHandler moveRequestHandler, int groupId) throws InvalidMoveException;
}
