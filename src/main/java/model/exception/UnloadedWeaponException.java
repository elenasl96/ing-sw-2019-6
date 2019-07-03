package model.exception;

public class UnloadedWeaponException extends InvalidMoveException{
    public UnloadedWeaponException(){
        super("Weapon is unloaded");
    }
}
