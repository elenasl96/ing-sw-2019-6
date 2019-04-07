package exception;

public class NotEnaughAmmoException extends Exception {
    private String message;

    public NotEnaughAmmoException(){
        this.message = "Not Enaugh Ammo!";
    }

    public String getMessage(){
        return this.message;
    }
}
