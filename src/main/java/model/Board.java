package model;

import model.decks.Ammo;
import model.decks.Powerup;
import model.decks.Weapon;
import model.field.Room;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private List<Room> field;
    private List<Player> killshotTrack;
    private List<Weapon> weaponsLeft;
    private List<Ammo> ammosLeft;
    private List<Ammo> ammosLeftover;
    private List<Powerup> powerupsLeft;
    private List<Powerup> powerupsLeftover;


    public Board(){
        //Default constructor
        this.field = new ArrayList<Room>();
        this.killshotTrack = new ArrayList<Player>();
        this.weaponsLeft = new ArrayList<Weapon>();
        this.ammosLeft = new ArrayList<Ammo>();
        this.ammosLeftover = new ArrayList<Ammo>();
        this.powerupsLeft = new ArrayList<Powerup>();
        this.powerupsLeftover = new ArrayList<Powerup>();
    }

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

    public List<Weapon> getWeaponsLeft() {
        return weaponsLeft;
    }

    public void setWeaponsLeft(List<Weapon> weaponsLeft) {
        this.weaponsLeft = weaponsLeft;
    }

    public List<Ammo> getAmmosLeft() {
        return ammosLeft;
    }

    public void setAmmosLeft(List<Ammo> ammosLeft) {
        this.ammosLeft = ammosLeft;
    }

    public List<Ammo> getAmmosLeftover() {
        return ammosLeftover;
    }

    public void setAmmosLeftover(List<Ammo> ammosLeftover) {
        this.ammosLeftover = ammosLeftover;
    }

    public List<Powerup> getPowerupsLeft() {
        return powerupsLeft;
    }

    public void setPowerupsLeft(List<Powerup> powerupsLeft) {
        this.powerupsLeft = powerupsLeft;
    }

    public List<Powerup> getPowerupsLeftover() {
        return powerupsLeftover;
    }

    public void setPowerupsLeftover(List<Powerup> powerupsLeftover) {
        this.powerupsLeftover = powerupsLeftover;
    }
}