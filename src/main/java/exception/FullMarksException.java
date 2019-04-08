package exception;

public class FullMarksException extends Exception {
    public FullMarksException(){
        super("Maximum number of marks reached");
    }
}
