package model.field;

import model.decks.Grabbable;
import model.decks.WeaponTile;
import model.enums.Color;

import java.util.List;

public class SpawnSquare extends Square{
    private WeaponTile weapons;

    public SpawnSquare(Color color, WeaponTile wp) {
        super(color);
        this.weapons=wp;
    }

    @Override
    public Grabbable getGrabbable() {
        return weapons;
    }
}
