package model.enums;

import model.Player;

/**
 * The type of the targets for weapons and powerups
 */
public enum TargetType {
    /**
     * No particular type of target required
     */
    NONE (0),
    /**
     * A target that must be on the player square or not
     */
    MINE(1),
    NOT_MINE(2),

    /**
     * Indicate a Target which satisfy all the other conditions.
     * It doesn't require an input from the player.
     */
    ALL(3),

    /**
     * A target that the player can or can't see, determined by canBeSeen method
     * @see model.moves.Target#canBeSeen(Player, int)
     */
    VISIBLE(4),
    NOT_VISIBLE(5),

    /**
     * Visible from another target (basic or optional1)
     */
    BASIC_VISIBLE(6),
    OPTIONAL1_VISIBLE(7),

    /**
     * Indicates a Target which can be chosen only on a cardinal direction,
     * not diagonal.
     */
    CARDINAL(8),

    /**
     * A target that must be different or equals to another target of a card effect
     */
    BASIC_DIFFERENT(9),
    BASIC_EQUALS(10),
    BASIC_NOT_OPTIONAL(11),
    LATEST_SQUARE(12),
    DIFFERENT_LATEST_SQUARE(13),

    /*
    Types for powerups
     */
    DAMAGED(14),
    DAMAGING(15);

    TargetType(int number){}
}
