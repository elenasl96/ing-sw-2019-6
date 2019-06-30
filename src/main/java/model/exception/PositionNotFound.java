package model.exception;

public class PositionNotFound extends InvalidMoveException {
    public PositionNotFound() {
        super("Position not found");
    }
}
