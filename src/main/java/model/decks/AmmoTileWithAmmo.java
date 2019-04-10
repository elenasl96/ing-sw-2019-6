package model.decks;

import model.Player;
import model.enums.Color;

public class AmmoTileWithAmmo extends AmmoTile implements Grabbable{
    /**
     * Calls the constructor of the superclass AmmoTile that creates a list of 3 ammos
     * @param color1
     * @param color2
     * @param color3
     */
    public AmmoTileWithAmmo(Color color1, Color color2, Color color3){
        super(color1, color2, color3);
    }

    @Override
    public void useGrabbable(Player player) {
        player.refillAmmo(this);
    }
}
