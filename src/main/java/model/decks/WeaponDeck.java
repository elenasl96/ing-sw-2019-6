package model.decks;

import java.util.ArrayList;
import java.util.List;

public class WeaponDeck {
    private List<Weapon> weapons = new ArrayList<>();


    public WeaponDeck() {
        this.weapons.add(new Weapon("lock rifle", "Basic effect: Deal 2 damage and 1 mark to 1 target you can see. \n with second lock: Deal 1 mark to a different target you can see."));
    }

    public List<Weapon> getWeapons() {
        return weapons;
    }

    public void setWeapons(List<Weapon> weapons) {
        this.weapons = weapons;
    }
}
