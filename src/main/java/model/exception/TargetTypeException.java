package model.exception;

import model.enums.TargetType;

public class TargetTypeException extends TargetsException {
    public TargetTypeException(TargetType targetType) {
        super("target isn't" + targetType);
    }
}
