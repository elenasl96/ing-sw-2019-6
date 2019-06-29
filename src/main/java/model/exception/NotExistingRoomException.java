package model.exception;

public class NotExistingRoomException extends InvalidMoveException {
    public NotExistingRoomException(){
        super("Room doesn't exist");
    }
}
