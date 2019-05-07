package model.decks;

import model.Ammo;
import model.enums.Color;
import model.moves.*;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PowerupDeck {
    private List<Powerup> powerups = new ArrayList<>();
    private List<Powerup> discard = new ArrayList<>();

    private static final String TARGETING_SCOPE = "targeting scope";
    private static final String NEWTON = "newton";
    private static final String TAGBACK_GRENADE = "tagback grenade";
    private static final String TELEPORTER = "teleporter";

    public PowerupDeck() {
        for (int i = 0; i == 0 || i == 12; i = i + 12) {
            this.powerups.add(new Powerup(TARGETING_SCOPE, new Ammo(Color.YELLOW)));
            this.powerups.get(i).addMove(new Pay());
            this.powerups.get(i).addMove(new Damage());

            this.powerups.add(new Powerup(TARGETING_SCOPE, new Ammo(Color.BLUE)));
            this.powerups.get(1 + i).addMove(new Pay());
            this.powerups.get(1 + i).addMove(new Damage());

            this.powerups.add(new Powerup(TARGETING_SCOPE, new Ammo(Color.RED)));
            this.powerups.get(2 + i).addMove(new Pay());
            this.powerups.get(2 + i).addMove(new Damage());

            this.powerups.add(new Powerup(NEWTON, new Ammo(Color.YELLOW)));
            //TODO

            this.powerups.add(new Powerup(NEWTON, new Ammo(Color.BLUE)));

            this.powerups.add(new Powerup(NEWTON, new Ammo(Color.RED)));

            this.powerups.add(new Powerup(TAGBACK_GRENADE, new Ammo(Color.YELLOW)));
            this.powerups.get(6 + i).addMove(new Mark());

            this.powerups.add(new Powerup(TAGBACK_GRENADE, new Ammo(Color.BLUE)));
            this.powerups.get(7 + i).addMove(new Mark());

            this.powerups.add(new Powerup(TAGBACK_GRENADE, new Ammo(Color.RED)));
            this.powerups.get(8 + i).addMove(new Mark());

            this.powerups.add(new Powerup(TELEPORTER, new Ammo(Color.YELLOW)));
            //TODO

            this.powerups.add(new Powerup(TELEPORTER, new Ammo(Color.BLUE)));

            this.powerups.add(new Powerup(TELEPORTER, new Ammo(Color.RED)));
        }
        shuffleDeck();
    }

    /**
     * random.nextInt() generates a random int which is used to retrieve a
     * random element from the deck
     *
     * @return a powerup card randomly
     */
    public Powerup pickCard() {

        Powerup powerupCard = powerups.get(0);
        powerups.remove(0);

        if (powerups.isEmpty()) {
            powerups = discard;
            discard = new ArrayList<>();
            shuffleDeck();
        }
        return powerupCard;
    }

    public void discardCard(Powerup card) {
        discard.add(card);
    }

    public List<Powerup> getPowerups() {
        return this.powerups;
    }

    public void shuffleDeck() {
        Collections.shuffle(powerups);
    }
}