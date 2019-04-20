package network.exceptions;

public class FullGroupException extends RuntimeException {
    public FullGroupException(){
        super("The group is full");
    }
}
