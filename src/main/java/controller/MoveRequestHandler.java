package controller;

import exception.InvalidMoveException;
import model.moves.*;
import network.socket.commands.Response;

public interface MoveRequestHandler {
    void handle(Movement movement, int groupID);

    void handle(Run run, int groupID);

    Response handle(MoveAndGrab moveAndGrab, int groupID);

    Response handle(Grab grab, int groupID);

    Response handle(DamageEffect damage, int groupID);
}
