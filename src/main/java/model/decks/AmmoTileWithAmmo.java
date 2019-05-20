package model.decks;

import exception.InvalidMoveException;
import model.Board;
import model.GameContext;
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
    public void pickGrabbable(int groupID) {
        GameContext.get().getGame(groupID).getCurrentPlayer().fillAmmoFromTile(this);
    }
}
