package model;

import model.decks.*;
import model.field.Room;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Board {
    private List<Room> field;
    private List<Player> killshotTrack;
    private WeaponDeck weaponsLeft;
    private AmmoDeck ammosLeft;
    private PowerupDeck powerupsLeft;
    private List<AmmoTile> ammosLeftover;
    private List<Powerup> powerupsLeftover;

    public Board(){
        this.killshotTrack = new ArrayList<Player>();
        this.weaponsLeft = new WeaponDeck();
        this.ammosLeft = new AmmoDeck();
        this.powerupsLeft = new PowerupDeck();
        this.ammosLeftover = new ArrayList<AmmoTile>();
        this.powerupsLeftover = new ArrayList<Powerup>();

    }

    public List<Room> getField() {
        return field;
    }

    /*
     setfield deleted as field not editable
    */

    public List<Player> getKillshotTrack() {
        return killshotTrack;
    }

    /**
     * Adds the player who killed on the kill shot track
     * @param pg player who killed
     */
    public void addKillshot(Player pg) {
        this.killshotTrack.add(pg);
    }

    public List<AmmoTile> getAmmosLeftover() {
        return ammosLeftover;
    }

    /**
     * add the ammo in ammos leftovers
     * @param ammoTileLeftover ammo discarded
     */
    public void addAmmoLeftover(AmmoTile ammoTileLeftover) {
        this.ammosLeftover.add(ammoTileLeftover);
    }

    public List<Powerup> getPowerupsLeftover() {
        return powerupsLeftover;
    }

    /**
     * add the powerup in powerups leftovers
     * @param powerupLeftover
     */
    public void addPowerupLeftover(Powerup powerupLeftover) {
        this.powerupsLeftover.add(powerupLeftover);
    }
}