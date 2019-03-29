package model.field;

import model.decks.Ammo;
import model.enums.Color;

import java.util.List;

public class AmmoSquare extends Square{
    private Ammo ammo;

    public AmmoSquare(Color color, List<Square> adjacents, Ammo ammo) {
        super(color, adjacents);
        this.ammo = ammo;
    }

    public Ammo getAmmo() {
        return ammo;
    }

    public void setAmmo(Ammo ammo) {
        this.ammo = ammo;
    }
}
