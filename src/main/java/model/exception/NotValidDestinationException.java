package model.exception;

import model.field.Square;

public class NotValidDestinationException extends InvalidMoveException {
    public NotValidDestinationException(Square destination) {
        super(destination + "is not valid destination");
    }

}
