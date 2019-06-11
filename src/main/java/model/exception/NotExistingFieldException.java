package model.exception;

public class NotExistingFieldException extends InvalidMoveException {

    public NotExistingFieldException(){
        super("This field doesn't exist yet");
    }
}
