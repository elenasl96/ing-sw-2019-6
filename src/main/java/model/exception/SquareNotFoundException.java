package model.exception;

public class SquareNotFoundException extends InvalidMoveException {
    public SquareNotFoundException(){
        super("Square not found");
    }
}
