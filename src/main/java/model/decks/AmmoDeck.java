package model.decks;

import model.enums.Color;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class AmmoDeck {
    private SecureRandom random = new SecureRandom();
    private List<AmmoTile> ammoTiles = new ArrayList<>();

    public AmmoDeck(){
        for(int i=0; i<2 ; i++) {
            this.ammoTiles.add(new AmmoTileWithAmmo(Color.YELLOW, Color.BLUE, Color.BLUE));
            this.ammoTiles.add(new AmmoTileWithAmmo(Color.YELLOW, Color.RED, Color.RED));
            this.ammoTiles.add(new AmmoTileWithAmmo(Color.RED, Color.BLUE, Color.BLUE));
            this.ammoTiles.add(new AmmoTileWithAmmo(Color.RED, Color.YELLOW, Color.YELLOW));
            this.ammoTiles.add(new AmmoTileWithAmmo(Color.BLUE, Color.YELLOW, Color.YELLOW));
            this.ammoTiles.add(new AmmoTileWithAmmo(Color.BLUE, Color.RED, Color.RED));

            this.ammoTiles.add(new AmmoTileWithPowerup(Color.YELLOW, Color.YELLOW));
            this.ammoTiles.add(new AmmoTileWithPowerup(Color.RED, Color.RED));
            this.ammoTiles.add(new AmmoTileWithPowerup(Color.BLUE, Color.BLUE));
            for(int j=0; j<2 ; j++) {
                this.ammoTiles.add(new AmmoTileWithPowerup(Color.YELLOW, Color.RED));
                this.ammoTiles.add(new AmmoTileWithPowerup(Color.YELLOW, Color.BLUE));
                this.ammoTiles.add(new AmmoTileWithPowerup(Color.RED, Color.BLUE));
            }
        }
    }

    /**
     * random.nextInt() generates a random int which is used to retrieve a
     * random element from the deck
     * @return an ammo card randomly
     */
    public AmmoTile pickRandomCard(){
        return ammoTiles.get(random.nextInt(ammoTiles.size()));
    }

}
