package exception;

public class NotExistingFieldException extends Exception {

    public NotExistingFieldException(){
        super("This field doesn't exist yet");
    }
}
