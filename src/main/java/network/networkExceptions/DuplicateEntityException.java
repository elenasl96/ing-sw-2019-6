package network.networkExceptions;

public class DuplicateEntityException extends IllegalArgumentException {
    public DuplicateEntityException(String s) {
        super(s);
    }
}
