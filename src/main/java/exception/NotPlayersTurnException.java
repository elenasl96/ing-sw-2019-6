package exception;

public class NotPlayersTurnException extends InvalidMoveException {
    public NotPlayersTurnException() {
        super("Another player is moving");
    }
}
