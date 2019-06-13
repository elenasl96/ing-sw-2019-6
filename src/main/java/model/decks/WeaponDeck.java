package model.decks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WeaponDeck {
    private List<Weapon> weapons = new ArrayList<>();

    public WeaponDeck() {
        for(int i=0; i<=20; i++){
            weapons.add(new Weapon().initializeWeapon(i));
        }
        shuffleDeck();
    }

    private void shuffleDeck () {
        Collections.shuffle(weapons);
    }

    public Weapon pickCard(){

        if(!weapons.isEmpty()) {
            Weapon weaponCard=weapons.get(0);
            weapons.remove(0);
            return weaponCard;
        }

        return null;
    }
}
