package model.enums;


import model.Player;

public enum TargetType {
    //TODO X ELENINA: same as Effecttype: piccola spiegazione, io ho messo quelle che so

    NONE (0),

    /**
     * A target that the player can or can't see, determined by canBeSeen method
     * @see model.moves.Target#canBeSeen(Player, int)
     */
    VISIBLE(1),
    NOT_VISIBLE(2),
    /**
     * Visible and same as Basic card effect
     */
    BASIC_VISIBLE(3),
    OPTIONAL1_VISIBLE(4),
    /**
     * Indicates a Target which can be chosen only on a cardinal direction,
     * not diagonal.
     */
    CARDINAL(5),

    ME (6),
    NOT_MINE(7),
    ALL(8),
    SAME_DIRECTION(9),
    DIFFERENT_SQUARE(10),
    SAME(11);

    TargetType(int number){}
}
