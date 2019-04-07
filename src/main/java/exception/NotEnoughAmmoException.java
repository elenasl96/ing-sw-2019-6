package exception;

public class NotEnoughAmmoException extends Exception {
    private String message;

    public NotEnoughAmmoException(){
        this.message = "Not Enaugh Ammo!";
    }

    public String getMessage(){
        return this.message;
    }
}
