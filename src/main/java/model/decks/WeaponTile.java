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

    @Override
    public void useGrabbable(Player player) {
        //TODO
    }
}