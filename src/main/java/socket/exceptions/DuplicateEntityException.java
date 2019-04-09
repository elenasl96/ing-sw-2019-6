package socket.exceptions;

public class DuplicateEntityException extends IllegalArgumentException {
    public DuplicateEntityException(String s) {
        super(s);
    }
}
