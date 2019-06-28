package model.decks;

import model.Ammo;
import model.Player;
import model.enums.Color;
import model.enums.TargetType;
import model.field.Square;
import model.moves.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static model.enums.TargetType.*;

//TODO javadoc
public class PowerupDeck {
    private List<Powerup> powerups = new ArrayList<>();
    private List<Powerup> discard = new ArrayList<>();

    public PowerupDeck() {
        for (int i = 0; i < 12; i++){
            powerups.add(new Powerup().initializePowerup(i));
        }
        shuffleDeck();
    }

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

    private void shuffleDeck() {
        Collections.shuffle(powerups);
    }

    @Override
    public String toString(){
        StringBuilder toString = new StringBuilder();
        for(int i = 1; i <= powerups.size(); i++) {
            toString.append(i).append(".\t").append(this.powerups.get(i).toString()).append("\n");
        }
        return toString.toString();
    }
}