package model.decks;

import model.Ammo;
import model.enums.Color;
import model.moves.Damage;
import model.moves.Pay;

import java.util.ArrayList;

public class PowerupDeck {
    private ArrayList<Powerup> powerups = new ArrayList<Powerup>();

    public PowerupDeck() {
        this.powerups.add(new Powerup("targetingScope", new Ammo(Color.YELLOW)));
        this.powerups.iterator().next().addMove(new Pay());
        this.powerups.iterator().next().addMove(new Damage());
    }

    public void generateDeck(){
        this.powerups.add(new Powerup("targetingScope", new Ammo(Color.YELLOW)));
        this.powerups.iterator().next().addMove(new Pay());
        this.powerups.iterator().next().addMove(new Damage());
    }

}
