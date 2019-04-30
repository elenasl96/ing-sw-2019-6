package model.moves;
import controller.MoveRequestHandler;
import exception.InvalidMoveException;
import model.Player;
import network.socket.commands.Response;

import java.io.Serializable;

public interface Move extends Serializable{
    Response execute(Player p) throws InvalidMoveException;
    void handle(MoveRequestHandler moveRequestHandler) throws InvalidMoveException;
}
