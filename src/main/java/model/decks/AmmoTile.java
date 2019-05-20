package model.decks;

import exception.InvalidMoveException;
import model.Ammo;
import model.enums.Color;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class AmmoTile implements Serializable, Grabbable {
    private List<Ammo> ammos = new ArrayList<>();

    public AmmoTile(Color color1, Color color2, Color color3){
        ammos.add(new Ammo (color1));
        ammos.add(new Ammo (color2));
        ammos.add(new Ammo (color3));
    }

    public AmmoTile(Color color1, Color color2){
        Ammo ammoTemp1 = new Ammo (color1);
        Ammo ammoTemp2 = new Ammo (color2);
        ammos.add(ammoTemp1);
        ammos.add(ammoTemp2);
    }

    public List<Ammo> getAmmos() {
        return ammos;
    }

    public void setAmmos(List<Ammo> ammos) {
        this.ammos = ammos;
    }


    @Override
    public void pickGrabbable(int groupID, int toPick) throws InvalidMoveException {
        throw new InvalidMoveException("No weapon to grab here!");
    }
}
