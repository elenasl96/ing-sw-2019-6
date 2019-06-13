package model.field;

import model.Board;
import model.GameContext;
import model.decks.AmmoTile;
import model.decks.Grabbable;
import model.enums.Color;
import org.jetbrains.annotations.Nullable;

/**
 * The type of square containing an ammo tile
 * (opposed to the spawn square)
 * @see Square
 * @see AmmoTile
 */
public class AmmoSquare extends Square{
    /**
     * The Ammo Tile belonging to the specific AmmoSquare object
     * @see AmmoTile
     */
    private AmmoTile ammo;

    /**
     * Default constructor, sets ammos null;
     */
    public AmmoSquare() {
        super();
        this.ammo = null;
    }

    /**
     * Constructor
     * @param color the color of the Square
     * @param coord the coordinate of the Square
     * @see Square
     */
    public AmmoSquare(Color color, Coordinate coord) {
        super(color, coord);
    }

    /**
     * Overrides from superclass Square method,
     * returns the grabbable object, in this case its AmmoTile
     * @return the AmmoTile
     * @see AmmoTile
     * @see Square
     * nullable if the square was created via AmmoSquare() default constructor
     * @see this#AmmoSquare()
     */
    @Override
    @Nullable
    public AmmoTile getGrabbable() {
        return ammo;
    }

    /**
     * Overrides from superclass Square method,
     * resets the grabbable object
     * @param board the current game board, to pick the card from the ammos leftovers
     * @see Board
     * @see Square
     * @see Board#getAmmosLeft()
     */
    @Override
    public void setGrabbable(Board board) {
        ammo = board.getAmmosLeft().pickCard();
    }

    /**
     * Overrides from superclass Square method,
     * does the same of setGrabbable
     * @param weapon    for polymorphism, can be anything, stays unused
     * @param groupID   the current group ID
     * @see Square
     * @see Board
     * @see this#setGrabbable(Board)
     */
    @Override
    public void addGrabbable(Grabbable weapon, int groupID) {
        this.ammo = GameContext.get().getGame(groupID).getBoard().getAmmosLeft().pickCard();
    }
}
