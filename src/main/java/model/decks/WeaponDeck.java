package model.decks;

import model.Ammo;
import model.enums.Color;
import model.moves.Damage;
import model.moves.Mark;
import model.moves.Move;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WeaponDeck {
    private List<Weapon> weapons = new ArrayList<>();

    public WeaponDeck() {
        List<Move> temp = new ArrayList<>();
        List<Ammo> temp2 = new ArrayList<>();
        List<Move> temp3 = new ArrayList<>();
        List<Ammo> temp4 = new ArrayList<>();
        List<Move> temp5 = new ArrayList<>();
        List<Ammo> temp6 = new ArrayList<>();

        //Lock rifle
        temp.add(new Damage());
        temp.add(new Damage());
        temp.add(new Mark());
        temp2.add(new Ammo(Color.BLUE));
        temp2.add(new Ammo(Color.BLUE));
        temp3.add(new Mark());
        temp4.add(new Ammo(Color.RED));

        this.weapons.add(new Weapon(temp, temp2, temp3, temp4, null,
                null));
        //to a target you can see

        //Electroscythe
        temp.clear();
        temp.add(new Damage());
        temp2.clear();
        temp2.add(new Ammo(Color.BLUE));
        temp3.clear();
        temp4.clear();
        temp3.add(new Damage());
        temp3.add(new Damage());
        temp4.add(new Ammo(Color.BLUE));
        temp4.add(new Ammo(Color.RED));
        this.weapons.add(new Weapon(temp, temp2, null,null, temp3,
                temp4));
        //Valid for EVERY OTHER PLAYER ON YOUR SQUARE

        //Machine Gun

    }

    public void shuffleDeck () {
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

    public List<Weapon> getWeapons() {
        return weapons;
    }

    public void setWeapons(List<Weapon> weapons) {
        this.weapons = weapons;
    }
}
