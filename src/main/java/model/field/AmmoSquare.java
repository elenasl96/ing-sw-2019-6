package model.field;

import model.decks.AmmoTile;
import model.enums.Color;

public class AmmoSquare extends Square{
    private AmmoTile ammo;

    public AmmoSquare(Color color) {
        super(color);
    }

    public AmmoTile getGrabbable() {
        return ammo;
    }

    public void setAmmo(AmmoTile ammo) {
        this.ammo = ammo;
    }
}
