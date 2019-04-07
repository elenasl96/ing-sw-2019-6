package model;

import model.decks.*;
import model.field.Room;

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


    public List<Room> getField() {
        return field;
    }

    public void setField(List<Room> field) {
        this.field = field;
    }

    public List<Player> getKillshotTrack() {
        return killshotTrack;
    }

    public void setKillshotTrack(List<Player> killshotTrack) {
        this.killshotTrack = killshotTrack;
    }


    public List<AmmoTile> getAmmosLeftover() {
        return ammosLeftover;
    }

    public void setAmmosLeftover(List<AmmoTile> ammosLeftover) {
        this.ammosLeftover = ammosLeftover;
    }


    public List<Powerup> getPowerupsLeftover() {
        return powerupsLeftover;
    }

    public void setPowerupsLeftover(List<Powerup> powerupsLeftover) {
        this.powerupsLeftover = powerupsLeftover;
    }
}