package model.exception;

public class InvalidMovementException extends InvalidMoveException {
    public InvalidMovementException(){super("You can't move there");}
}
