package model;

import model.decks.*;
import model.field.*;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Character.isUpperCase;

public class Board {
    private Field field;
    private List<Player> killshotTrack;
    private WeaponDeck weaponsLeft;
    private AmmoDeck ammosLeft;
    private PowerupDeck powerupsLeft;
    private List<AmmoTile> ammosLeftover;
    private List<Powerup> powerupsLeftover;

    public Board(){
        this.killshotTrack = new ArrayList<>();
        this.weaponsLeft = new WeaponDeck();
        this.ammosLeft = new AmmoDeck();
        this.powerupsLeft = new PowerupDeck();
        this.ammosLeftover = new ArrayList<>();
        this.powerupsLeftover = new ArrayList<>();
    }

    public Field getField() {
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
     * @param powerupLeftover   the leftover
     */
    public void addPowerupLeftover(Powerup powerupLeftover) {
        this.powerupsLeftover.add(powerupLeftover);
    }
}