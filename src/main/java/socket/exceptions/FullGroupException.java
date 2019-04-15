package socket.exceptions;

public class FullGroupException extends Exception {
    public FullGroupException(){
        super("The group is full");
    }
}
