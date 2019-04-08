package exception;

public class InvalidMoveException extends Exception {
    public InvalidMoveException() {
        super("This movement is not allowed"); //TODO
    }
}
