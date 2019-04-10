package model.decks;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class AmmoDeck {
    private SecureRandom random = new SecureRandom();
    private List<AmmoTile> ammoTiles = new ArrayList<>();

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
