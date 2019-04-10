package model.decks;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WeaponDeck {
    private List<Weapon> weapons = new ArrayList<>();
    private SecureRandom random = new SecureRandom();


    public WeaponDeck() {
        this.weapons.add(new Weapon("lock rifle", "Basic effect: Deal 2 damage and 1 mark to 1 target you can see. \n with second lock: Deal 1 mark to a different target you can see."));
    }

    /**
     * random.nextInt() generates a random int which is used to retrieve a
     * random element from the deck
     * @return a weapon card randomly
     */
    public Weapon pickRandomCard(){
        return weapons.get(random.nextInt());
    }

    public List<Weapon> getWeapons() {
        return weapons;
    }

    public void setWeapons(List<Weapon> weapons) {
        this.weapons = weapons;
    }
}
