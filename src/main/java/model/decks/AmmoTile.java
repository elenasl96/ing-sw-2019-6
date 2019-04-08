package model.decks;

import model.Ammo;
import model.enums.Color;

import java.io.Serializable;
import java.util.List;

public abstract class AmmoTile implements Serializable {
    private List<Ammo> ammos;

    public AmmoTile(Color color1, Color color2, Color color3){
        Ammo ammoTemp1 = new Ammo (color1);
        Ammo ammoTemp2 = new Ammo (color2);
        Ammo ammoTemp3 = new Ammo (color3);
        ammos.add(ammoTemp1);
        ammos.add(ammoTemp2);
        ammos.add(ammoTemp3);
    }

    public AmmoTile(Color color1, Color color2){
        Ammo ammoTemp1 = new Ammo (color1);
        Ammo ammoTemp2 = new Ammo (color2);
        ammos.add(ammoTemp1);
        ammos.add(ammoTemp2);
    }
}
