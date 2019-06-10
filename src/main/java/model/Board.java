package model;

import model.decks.*;
import model.field.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The board of the game, holding reference to the card decks, field and the killshotTrack
 * @see Field
 * @see WeaponDeck
 * @see AmmoDeck
 * @see PowerupDeck
 */
public class Board implements Serializable {

    /**
     * Reference to the Field of the game
     */
    private Field field;

    /**
     * The killshot track, represented by an array of the players that took the respective skull
     */
    private List<Player> killshotTrack;

    /**
     * The deck of the remaining weapons
     */
    private transient WeaponDeck weaponsLeft;

    /**
     * The deck of the remaining ammo tile
     */
    private transient AmmoDeck ammosLeft;

    /**
     * The deck of the remaining powerups
     */
    private transient PowerupDeck powerupsLeft;

    /**
     * Constructor
     * @param fieldNumber   the number of field chosen by the group creator
     */
    public Board(int fieldNumber){
        this.killshotTrack = new ArrayList<>();
        this.weaponsLeft = new WeaponDeck();
        this.ammosLeft = new AmmoDeck();
        this.powerupsLeft = new PowerupDeck();
        this.field = new Field(fieldNumber);
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }
    
    List<Player> getKillshotTrack() {
        return killshotTrack;
    }

    public WeaponDeck getWeaponsLeft() {
        return weaponsLeft;
    }

    public AmmoDeck getAmmosLeft() {
        return ammosLeft;
    }

    public PowerupDeck getPowerupsLeft() {
        return powerupsLeft;
    }

    /**
     * Adds the player who killed on the kill shot track
     * @param pg player who killed
     */
    public void addKillshot(Player pg) {
        this.killshotTrack.add(pg);
    }
    
}