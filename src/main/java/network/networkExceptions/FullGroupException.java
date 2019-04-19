package network.networkExceptions;

public class FullGroupException extends RuntimeException {
    public FullGroupException(){
        super("The group is full");
    }
}
