package exception;

public class FullPlayerBoardException extends InvalidMoveException{
    public FullPlayerBoardException(){ super("PlayerBoard is full"); }
}
