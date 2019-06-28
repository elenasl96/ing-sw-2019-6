package model.decks;


import model.Ammo;
import model.Player;
import model.enums.Color;
import model.enums.TargetType;
import model.field.Square;
import model.moves.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static model.enums.TargetType.*;
import static model.enums.TargetType.NONE;

/**
 * The powerup cards of the game, contains a list of moves and an ammo
 * @see Move
 * @see Ammo
 */
public class Powerup implements Serializable {
    private int id;
    private String name;
    private Ammo ammo;
    private List<Move> moves = new ArrayList<>();

    private static final String TARGETING_SCOPE = "targeting scope";
    private static final String NEWTON = "newton";
    private static final String TAGBACK_GRENADE = "tagback grenade";
    private static final String TELEPORTER = "teleporter";

    public Powerup(){

    }

    public Powerup(int id, String name, Ammo ammo){
        this.id = id;
        this.name = name;
        this.ammo = ammo;
    }

    public Powerup initializePowerup(int i){
        Powerup powerup;
        switch (i){
            case 0: case 1: case 2:
                powerup = new Powerup(id,TARGETING_SCOPE, initializeAmmo(id));
                powerup.addMove(new Pay());
                powerup.addMove(new DamageEffect(Stream.of(
                        new Player(TARGETING_SCOPE_TARGET, NONE, null, null)),
                        0, false));
                break;
            case 3: case 4: case 5:
                powerup = new Powerup(id,NEWTON, initializeAmmo(id));
                powerup.addMove(
                        new Movement(Stream.of(new Player(NONE, NONE, null, null)),
                                new Square(CARDINAL, NONE, 1, 2), false));
                break;
            case 6: case 7: case 8:
                powerup = new Powerup(id,TAGBACK_GRENADE, initializeAmmo(id));
                powerup.addMove(new MarkEffect(Stream.of(new Player()), 1, false));
                break;
            case 9: case 10: case 11:
                powerup = new Powerup(id,TELEPORTER, initializeAmmo(id));
                powerup.addMove(
                        new Movement(Stream.of(new Player(TargetType.MINE, NONE, null, null)),
                                new Square(ALL, NONE, null, null), false));
                break;
            default:
                powerup = null;
                break;
        }
        return powerup;
    }

    private Ammo initializeAmmo(int id) {
        int color = id%3;
        switch (color){
            case 0:
                return new Ammo(Color.YELLOW);
            case 1:
                return new Ammo(Color.BLUE);
            case 2:
                return new Ammo(Color.RED);
            default:
                return null;
        }
    }

    void addMove(Move move){
        this.moves.add(move);
    }

    public void setAmmo(Ammo ammo) {
        this.ammo = ammo;
    }

    public Ammo getAmmo() {
        return ammo;
    }

    @Override
    public String toString(){
        return "Name: " + name + "\nAmmo: " + ammo.getColor().getName() + "\n" +
                "=========================";
    }

    public String getName() {
        return name;
    }

    public List<Move> getMoves() {
        return moves;
    }
}
