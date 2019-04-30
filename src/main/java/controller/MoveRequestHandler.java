package controller;

import exception.InvalidMoveException;
import model.moves.Damage;
import model.moves.Grab;
import model.moves.Movement;
import model.moves.Run;

public interface MoveRequestHandler {
    void handle(Movement movement) throws InvalidMoveException;
    void handle(Damage damage) throws InvalidMoveException;
    void handle(Grab grab) throws InvalidMoveException;

    void handle(Run run) throws InvalidMoveException;
}
