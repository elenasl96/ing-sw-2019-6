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
    SAME_TARGET(8);

    private int number;

    TargetType(int number){
        this.number = number;
    }
}
