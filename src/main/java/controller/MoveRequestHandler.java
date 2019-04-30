package controller;

import exception.InvalidMoveException;
import model.moves.*;

public interface MoveRequestHandler {
    void handle(Movement movement) throws InvalidMoveException;
    void handle(Damage damage) throws InvalidMoveException;
    void handle(Grab grab) throws InvalidMoveException;

    void handle(Run run) throws InvalidMoveException;

    void handle(MoveAndGrab moveAndGrab) throws InvalidMoveException;
}
