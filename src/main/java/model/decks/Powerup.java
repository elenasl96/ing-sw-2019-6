package model.decks;


import model.Ammo;
import model.Player;
import model.enums.Color;
import model.enums.EffectType;
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
    private int cost;
    private transient List<CardEffect> effects = new ArrayList<>();

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

    public int getId() {
        return id;
    }

    public Powerup initializePowerup(int id){
        Powerup powerup;
        switch (id){
            case 0: case 1: case 2:
                powerup = new Powerup(id,TARGETING_SCOPE, initializeAmmo(id));
                powerup.setCost(1);
                powerup.getEffects().add(new CardEffect(EffectType.BASIC, null, "player"));
                powerup.getEffects().get(0).getEffects().add(new DamageEffect(Stream.of(
                        new Player(DAMAGED, NONE, null, null)),
                        0, false));
                break;
            case 3: case 4: case 5:
                powerup = new Powerup(id,NEWTON, initializeAmmo(id));
                powerup.getEffects().add(new CardEffect(EffectType.BASIC, null, "player,square"));
                powerup.getEffects().get(0).getEffects().add(
                        new Movement(Stream.of(new Player(NONE, NONE, null, null)),
                                new Square(CARDINAL, NONE, 1, 2), false));
                break;
            case 6: case 7: case 8:
                powerup = new Powerup(id,TAGBACK_GRENADE, initializeAmmo(id));
                powerup.getEffects().add(new CardEffect(EffectType.BASIC, null, "none"));
                powerup.getEffects().get(0).getEffects().add(new MarkEffect(Stream.of(new Player()), 1, false));
                break;
            case 9: case 10: case 11:
                powerup = new Powerup(id,TELEPORTER, initializeAmmo(id));
                powerup.getEffects().add(new CardEffect(EffectType.BASIC, null, "square"));
                powerup.getEffects().get(0).getEffects().add(
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

    public String getEffectsLayout() {
        return this.getEffects().get(0).getDescription();
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setAmmo(Ammo ammo) {
        this.ammo = ammo;
    }

    public Ammo getAmmo() {
        return ammo;
    }

    @Override
    public String toString(){
        return "\nName: " + name + "\nAmmo: " + ammo.getColor().getName() + "\n" +
                "=========================";
    }

    public String getName() {
        return name;
    }

    public List<CardEffect> getEffects() {
        return effects;
    }
}
