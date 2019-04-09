package exception;

public class NotEnoughAmmoException extends InvalidMoveException {

    public NotEnoughAmmoException(){
        super("Not enough ammos!");
    }
}
