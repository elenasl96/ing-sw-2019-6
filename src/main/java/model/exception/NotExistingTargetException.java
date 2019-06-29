package model.exception;

import model.moves.Target;

public class NotExistingTargetException extends TargetsException {
    public NotExistingTargetException(String target) {
        super(target + "target doesn't exist.");
    }
}
