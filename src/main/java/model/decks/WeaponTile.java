package model.decks;

import model.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WeaponTile implements Grabbable, Serializable {
    private List<Weapon> weapons;


    public WeaponTile() {
        weapons = new ArrayList<>();
    }

    public void addWeapon(Weapon weapon) {
        weapons.add(weapon);
    }

    @Override
    public void useGrabbable(Player player) {
        //TODO
    }
}