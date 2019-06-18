package model.exception;

public class NothingGrabbableException extends InvalidMoveException {
    public NothingGrabbableException(){
        super("There's nothing to grab here!");
    }
}
