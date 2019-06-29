package model.decks;

import model.Player;

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
    void pickGrabbable(int groupID, int toPick);

    String getGrabbableType();
    boolean isGrabbable(Player player);
    String toStringForGUI();
}
