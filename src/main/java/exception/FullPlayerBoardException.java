package exception;

public class FullPlayerBoardException extends Exception {
    public FullPlayerBoardException(){
        super("No space for other players"); //TODO
    }
}
