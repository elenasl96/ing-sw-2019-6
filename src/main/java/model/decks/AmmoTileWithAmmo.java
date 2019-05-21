package model.decks;

import model.GameContext;
import model.enums.Color;

public class AmmoTileWithAmmo extends AmmoTile implements Grabbable{
    /**
     * Calls the constructor of the superclass AmmoTile that creates a list of 3 ammos
     * @param color1    the first ammo color
     * @param color2    the second ammo color
     * @param color3    the third ammo color
     */
    AmmoTileWithAmmo(Color color1, Color color2, Color color3){
        super(color1, color2, color3);
    }

    @Override
    public void pickGrabbable(int groupID, int toPick) {
        GameContext.get().getGame(groupID).getCurrentPlayer().fillAmmoFromTile(this);
    }
}
