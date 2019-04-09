package model.decks;

import java.util.ArrayList;
import java.util.Random;

public class AmmoDeck {
    private Random random = new Random();
    private ArrayList<AmmoTile> ammoTiles = new ArrayList<>();

    public AmmoDeck(){
        //TODO Inizialization of AmmoDeck from static data
    }

    /**
     * random.nextInt() generates a random int which is used to retrieve a
     * random element from the deck
     * @return an ammo card randomly
     */
    public AmmoTile pickRandomCard(){
        return ammoTiles.get(random.nextInt());
    }

}
