package model.enums;

public enum EffectType {
    BASIC(1),
    OPTIONAL(2),
    OPTIONAL1(3),
    OPTIONAL2(4),
    ALTERNATIVE(5),
    BEFORE_AFTER_BASIC(6),
    POWERUP(7),
    MOVE(8),
    OPTIONAL_VORTEX(9),
    BEFORE_BASIC(10),
    EVERY_TIME(11);

    private int number;

    EffectType(int number){
        this.number = number;
    }
}
