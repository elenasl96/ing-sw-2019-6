package exception;

public class NotPlayersTurnException extends Exception {
    public NotPlayersTurnException() {
        super("Another player is moving"); //TODO
    }
}
