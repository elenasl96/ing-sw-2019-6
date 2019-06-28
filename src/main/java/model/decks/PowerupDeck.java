package model.decks;

import model.Ammo;
import model.Player;
import model.enums.Color;
import model.enums.TargetType;
import model.field.Square;
import model.moves.*;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static model.enums.TargetType.*;

//TODO javadoc
public class PowerupDeck {
    private List<Powerup> powerups = new ArrayList<>();
    private List<Powerup> discard = new ArrayList<>();

    private static final String TARGETING_SCOPE = "targeting scope";
    private static final String NEWTON = "newton";
    private static final String TAGBACK_GRENADE = "tagback grenade";
    private static final String TELEPORTER = "teleporter";

    public PowerupDeck() {
        for (int i = 0; i == 0 || i == 12; i = i + 12) {
            this.powerups.add(new Powerup(TARGETING_SCOPE + Color.YELLOW.getAbbr(), new Ammo(Color.YELLOW)));
            this.powerups.get(i).addMove(new Pay());
            this.powerups.get(i).addMove(new DamageEffect(Stream.of(new Player(TARGETING_SCOPE_TARGET, NONE, null, null)), 0, false));

            this.powerups.add(new Powerup(TARGETING_SCOPE + Color.BLUE.getAbbr(), new Ammo(Color.BLUE)));
            this.powerups.get(1 + i).addMove(new Pay());
            this.powerups.get(1 + i).addMove(new DamageEffect(Stream.of(new Player(TARGETING_SCOPE_TARGET, NONE, null, null)), 0, false));

            this.powerups.add(new Powerup(TARGETING_SCOPE + Color.RED.getAbbr(), new Ammo(Color.RED)));
            this.powerups.get(2 + i).addMove(new Pay());
            this.powerups.get(2 + i).addMove(new DamageEffect(Stream.of(new Player(TARGETING_SCOPE_TARGET, NONE, null, null)), 0, false));

            this.powerups.add(new Powerup(NEWTON + Color.YELLOW.getAbbr(), new Ammo(Color.YELLOW)));
            this.powerups.get(3+i).addMove(
                    new Movement(Stream.of(new Player(NONE, NONE, null, null)),
                            new Square(CARDINAL, NONE, 1, 2), false));

            this.powerups.add(new Powerup(NEWTON + Color.BLUE.getAbbr(), new Ammo(Color.BLUE)));
            this.powerups.get(4+i).addMove(
                    new Movement(Stream.of(new Player(NONE, NONE, null, null)),
                            new Square(CARDINAL, NONE, 1, 2), false));

            this.powerups.add(new Powerup(NEWTON + Color.RED.getAbbr(), new Ammo(Color.RED)));
            this.powerups.get(5+i).addMove(
                    new Movement(Stream.of(new Player(NONE, NONE, null, null)),
                            new Square(CARDINAL, NONE, 1, 2), false));

            this.powerups.add(new Powerup(TAGBACK_GRENADE + Color.YELLOW.getAbbr(), new Ammo(Color.YELLOW)));
            this.powerups.get(6 + i).addMove(new MarkEffect(Stream.of(new Player()), 1, false));

            this.powerups.add(new Powerup(TAGBACK_GRENADE + Color.BLUE.getAbbr(), new Ammo(Color.BLUE)));
            this.powerups.get(7 + i).addMove(new MarkEffect(Stream.of(new Player()), 1, false));

            this.powerups.add(new Powerup(TAGBACK_GRENADE + Color.RED.getAbbr(), new Ammo(Color.RED)));
            this.powerups.get(8 + i).addMove(new MarkEffect(Stream.of(new Player()), 1, false));

            this.powerups.add(new Powerup(TELEPORTER + Color.YELLOW.getAbbr(), new Ammo(Color.YELLOW)));
            this.powerups.get(9 + i).addMove(
                    new Movement(Stream.of(new Player(TargetType.MINE, NONE, null, null)),
                            new Square(ALL, NONE, null, null), false));

            this.powerups.add(new Powerup(TELEPORTER + Color.BLUE.getAbbr(), new Ammo(Color.BLUE)));
            this.powerups.get(10 + i).addMove(
                    new Movement(Stream.of(new Player(TargetType.MINE, NONE, null, null)),
                            new Square(ALL, NONE, null, null), false));

            this.powerups.add(new Powerup(TELEPORTER + Color.RED.getAbbr(), new Ammo(Color.RED)));
            this.powerups.get(11 + i).addMove(
                    new Movement(Stream.of(new Player(TargetType.MINE, NONE, null, null)),
                            new Square(ALL, NONE, null, null), false));
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