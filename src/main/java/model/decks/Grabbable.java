package model.decks;

import model.Player;
import model.exception.NotEnoughAmmoException;
import model.exception.NothingGrabbableException;

import java.io.Serializable;

/**
 * Represents the grabbable object
 * @see AmmoTile
 * @see WeaponTile
 */
public interface Grabbable extends Serializable {
    /**
     * Picks the grabbable object from the tile
     * @param groupID   the groupID number
     * @param toPick    the number, in case of weapons, indicates the weapon to pick, from 0 to 2
     */
    void pickGrabbable(int groupID, int toPick) throws NotEnoughAmmoException, NothingGrabbableException;

    /**
     * @return a specific String containing the type of the grabbable object in the Square
     */
    String getGrabbableType();

    /**
     * @param player the player that wants to grab
     * @return  true if the square can be grabbed into
     */
    boolean isGrabbable(Player player);
    String toStringForGUI();
}
