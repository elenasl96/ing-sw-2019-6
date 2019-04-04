package model.decks;

import model.enums.Color;
import model.moves.Damage;
import model.moves.Pay;

import java.util.ArrayList;
import java.util.List;

public class PowerupDeck {
    private List<Powerup> powerups;

    public PowerupDeck() {
        this.powerups = new ArrayList<>();
    }

    public void generateDeck(){
        this.powerups.add(new Powerup("targetingScope", new Ammo(Color.YELLOW)));
        this.powerups.iterator().next().addMove(new Pay());
        this.powerups.iterator().next().addMove(new Damage());
    }
}
