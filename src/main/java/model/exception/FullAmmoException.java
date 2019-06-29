package model.exception;

public class FullAmmoException extends Exception {
    public FullAmmoException(){
        super("You cannot have more ammos of that color");
    }
}
