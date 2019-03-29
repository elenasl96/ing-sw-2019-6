package model.decks;

import java.util.List;

public class WeaponDeck {
    private List<Weapon> weapons;

    //public WeaponDeck(){
        //this.weapons.add(new Weapon("Distruttore", ));
    //}

    public WeaponDeck(List<Weapon> weapons) {
        this.weapons = weapons;
    }

    public List<Weapon> getWeapons() {
        return weapons;
    }

    public void setWeapons(List<Weapon> weapons) {
        this.weapons = weapons;
    }
}
