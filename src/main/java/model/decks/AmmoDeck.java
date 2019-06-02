package model.decks;

import model.enums.Color;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The ammo tiles deck, contained in the board
 * @see model.Board
 * @see AmmoTile
 */
public class AmmoDeck {
    /**
     * The list of AmmoTiles contained in the deck
     * @see AmmoTile
     * @see ArrayList
     */
    private List<AmmoTile> ammoTiles = new ArrayList<>();
    /**
     * The pile of discarded ammo tiles
     * @see AmmoTile
     * @see ArrayList
     */
    private List<AmmoTile> discard = new ArrayList<>();

    /**
     * Default Constructor: adds 24 ammo tiles (two times 12 different ammo tiles)
     * @see AmmoTileWithAmmo
     * @see AmmoTileWithPowerup
     */
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
     * Shuffles the deck using Collections own method
     * @see Collections#shuffle(List)
     */
    private void shuffleDeck() {
        Collections.shuffle(ammoTiles);
    }
    /**
     * random.nextInt() generates a random int which is used to retrieve a
     * random element from the deck
     * If the deck is empty, shuffles automatically the discard deck and puts it in place
     * of the ammo tiles regular deck
     * @return an ammo card randomly
     */
    public AmmoTile pickCard(){

        AmmoTile ammoCard = ammoTiles.get(0);
        ammoTiles.remove(0);

        if(ammoTiles.isEmpty()) {
            ammoTiles = discard;
            discard = new ArrayList<>();
            shuffleDeck();
        }

        return ammoCard;
    }

    /**
     * Discards a card, generally picked
     * @param card the card that will be added to the discarded cards' deck
     */
    public void discardCard(AmmoTile card) {
        discard.add(card);
    }
}
