package model.decks;

import java.util.List;

public class PowerupDeck {
    private List<Powerup> powerups;

    public PowerupDeck(List<Powerup> powerups) {
        this.powerups = powerups;
    }

    public List<Powerup> getPowerups() {
        return powerups;
    }

    public void setPowerups(List<Powerup> powerups) {
        this.powerups = powerups;
    }
}
