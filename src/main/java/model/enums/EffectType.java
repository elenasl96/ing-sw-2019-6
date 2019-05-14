package model.enums;

public enum EffectType {
    BASIC(1),
    OPTIONAL(2),
    ALTERNATIVE(3),
    BEFORE_AFTER_BASIC(4);

    private int number;

    EffectType(int number){
        this.number = number;
    };
}
