package model.decks;

import model.Player;

import java.util.List;

public class WeaponTile extends Grabbable{

    private List<Weapon> weapons;

    public WeaponTile(Weapon weapon1, Weapon weapon2, Weapon weapon3) {
        weapons.add(weapon1);
        weapons.add(weapon2);
        weapons.add(weapon3);
    }

    public void addWeapon(Weapon weapon) {
        weapons.add(weapon);
    }

    @Override
    public void useGrabbable(Player player) {
        //TODO
    }
}