package model.decks;

import java.util.List;

public class AmmoDeck {
    private List<AmmoTiles> ammoTiles;

    public AmmoDeck(List<AmmoTiles> ammoTiles) {
        ammoTiles = ammoTiles;
    }

    public List<AmmoTiles> getAmmoTiles() {
        return ammoTiles;
    }

    public void setAmmoTiles(List<AmmoTiles> ammoTiles) {
        this.ammoTiles = ammoTiles;
    }
}
