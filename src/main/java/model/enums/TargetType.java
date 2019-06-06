package model.enums;


public enum TargetType {
    NONE (0),
    VISIBLE(1),
    NOT_VISIBLE(2),
    BASIC_VISIBLE(3),
    OPTIONAL1_VISIBLE(4),
    CARDINAL(5),
    ME (6),
    NOT_MINE(7),
    ALL(8),
    SAME_DIRECTION(9),
    DIFFERENT_SQUARE(10);

    private int number;

    TargetType(int number){
        this.number = number;
    }
}
