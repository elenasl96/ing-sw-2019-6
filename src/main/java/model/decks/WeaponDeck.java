package model.decks;

import model.Ammo;
import model.Player;
import model.enums.Color;
import model.enums.WeaponStatus;
import model.field.Room;
import model.field.Square;
import model.moves.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static model.enums.EffectType.*;
import static model.enums.TargetType.*;

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
