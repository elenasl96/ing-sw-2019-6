package model.exception;

public class CardinalTargetNotFoundException extends InvalidMoveException {
    public CardinalTargetNotFoundException(){
        super("Previous target not found");
    }
}

