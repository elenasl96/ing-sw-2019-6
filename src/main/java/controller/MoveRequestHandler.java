package controller;

import exception.InvalidMoveException;
import model.moves.*;
import network.socket.commands.Response;

public interface MoveRequestHandler {
    Response handle(Movement movement, int groupID) throws InvalidMoveException;

    void handle(Run run, int groupID) throws InvalidMoveException;

    Response handle(MoveAndGrab moveAndGrab, int groupID) throws InvalidMoveException;

    Response handle(Grab grab, int groupID) throws InvalidMoveException;

    Response handle(DamageEffect damage, int groupID) throws InvalidMoveException;
}
