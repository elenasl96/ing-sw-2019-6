package model.decks;

import model.exception.InvalidMoveException;
import model.Ammo;
import model.GameContext;
import model.enums.Color;

import java.util.ArrayList;
import java.util.List;

public abstract class AmmoTile implements Grabbable {
    private List<Ammo> ammos = new ArrayList<>();

    AmmoTile(Color color1, Color color2, Color color3){
        ammos.add(new Ammo (color1));
        ammos.add(new Ammo (color2));
        ammos.add(new Ammo (color3));
    }

    AmmoTile(Color color1, Color color2){
        Ammo ammoTemp1 = new Ammo (color1);
        Ammo ammoTemp2 = new Ammo (color2);
        ammos.add(ammoTemp1);
        ammos.add(ammoTemp2);
    }

    public List<Ammo> getAmmos() {
        return ammos;
    }

    @Override
    public void pickGrabbable(int groupID, int toPick) {
        throw new InvalidMoveException("No weapon to grab here!");
    }
}

class AmmoTileWithAmmo extends AmmoTile{
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

class AmmoTileWithPowerup extends AmmoTile{

    AmmoTileWithPowerup(Color color1, Color color2){
        super(color1, color2);
    }

    @Override
    public void pickGrabbable(int groupID, int toPick) {
        GameContext.get().getGame(groupID).getCurrentPlayer().fillAmmoFromTile(this);
        GameContext.get().getGame(groupID).getCurrentPlayer().getPowerups()
                .add(GameContext.get().getGame(groupID).getBoard().getPowerupsLeft().pickCard());
    }
}
