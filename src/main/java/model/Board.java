package model;

import model.decks.*;
import model.field.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Board implements Serializable {

    private Field field;
    private List<Player> killshotTrack;
    private transient WeaponDeck weaponsLeft;
    private transient AmmoDeck ammosLeft;
    private transient PowerupDeck powerupsLeft;

    public Board(int fieldNumber){
        this.killshotTrack = new ArrayList<>();
        this.weaponsLeft = new WeaponDeck();
        this.ammosLeft = new AmmoDeck();
        this.powerupsLeft = new PowerupDeck();
        this.field = new Field(fieldNumber, this);
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }
    /*
     setfield deleted as field not editable
    */

    public List<Player> getKillshotTrack() {
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

     /**
     * add the powerup in powerups leftovers
     * @param powerupLeftover   the leftover
     */
}