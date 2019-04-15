package socket.exceptions;

import exception.FullMarksException;
import exception.InvalidMoveException;

public class FullGroupException extends RuntimeException {
    public FullGroupException(){
        super("The group is full");
    }
}
