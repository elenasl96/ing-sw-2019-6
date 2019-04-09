package exception;

public class FullMarksException extends InvalidMoveException {
    public FullMarksException(){
        super("Maximum number of marks reached");
    }
}
