package model.exception;

public class InvalidInputException extends InvalidMoveException{
    public InvalidInputException(){
        super("Not valid input");
    }
}
