package socket.exceptions;

import exception.FullMarksException;
import exception.InvalidMoveException;

public class FullGroupException extends Exception {
    public FullGroupException(){
        super("The group is full");
    }
}
