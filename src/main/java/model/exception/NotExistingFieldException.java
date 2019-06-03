package model.exception;

public class NotExistingFieldException extends RuntimeException {

    public NotExistingFieldException(){
        super("This field doesn't exist yet");
    }
}
