package model;

import model.carte.Ammo;
import model.carte.Powerup;
import model.carte.Weapon;
import model.field.Square;
import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private Color color;
    private int id; //da 0 a numeroGiocatori-1
    private Character character;
    private Square currentPosition;
    private int phase;
    private ArrayList<Ammo> ammo;
    private ArrayList<Powerup> powerup;
    private ArrayList<Weapon> weapon;
    private PlayerBoard playerBoard;
    private int points;
    private String motto;
    private int adrenalinelevel;
    private int stackPoint;
    private boolean firstPlayer;
    private boolean dead;
    private ArrayList<Player> shootable;




    //Costruttore

    public Player(String name, Color color, int id, Character character, Square currentPosition, int phase, ArrayList<Ammo> ammo, ArrayList<Powerup> powerup, ArrayList<Weapon> weapon, PlayerBoard playerBoard, int points, String motto, int adrenalinelevel, int stackPoint, boolean firstPlayer, boolean dead, ArrayList<Player> shootable) {
        this.name = name;
        this.color = color;
        this.id = id;
        this.character = character;
        this.currentPosition = currentPosition;
        this.phase = phase;
        this.ammo = ammo;
        this.powerup = powerup;
        this.weapon = weapon;
        this.playerBoard = playerBoard;
        this.points = points;
        this.motto = motto;
        this.adrenalinelevel = adrenalinelevel;
        this.stackPoint = stackPoint;
        this.firstPlayer = firstPlayer;
        this.dead = dead;
        this.shootable = shootable;
    }

    //setter
    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setWeapon(ArrayList<Weapon> weapon) {
        this.weapon = weapon;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setCurrentPosition(Square currentPosition) {
        this.currentPosition = currentPosition;
    }

    public void setAdrenalinelevel(int adrenalinelevel) {
        this.adrenalinelevel = adrenalinelevel;
    }

    public void setAmmo(ArrayList<Ammo> ammo) {
        this.ammo = ammo;
    }

    public void setPowerup(ArrayList<Powerup> powerup) {
        this.powerup = powerup;
    }

    public void setShootable(ArrayList<Player> shootable) {
        this.shootable = shootable;
    }

    public void setPlayerBoard(PlayerBoard playerBoard) {
        this.playerBoard = playerBoard;
    }

    public void setStackPoint(int stackPoint) {
        this.stackPoint = stackPoint;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMotto(String motto) {
        this.motto = motto;
    }

    public void setPhase(int phase) {
        this.phase = phase;
    }

    public void setAdrenalinaLevel(int adrenalinaLevel) {
        this.adrenalinelevel = adrenalinaLevel;
    }

    public void setFirstPlayer(boolean firstPlayer) {
        this.firstPlayer = firstPlayer;
    }

    //getter
    public Color getColor() {
        return color;
    }

    public boolean isDead() {
        return dead;
    }

    public String getName() {
        return name;
    }

    public String getMotto() {
        return motto;
    }

    public PlayerBoard getPlayerBoard() {
        return playerBoard;
    }

    public Character getCharacter() {
        return character;
    }

    public int getPoints() {
        return points;
    }

    public int getStackPoint() {
        return stackPoint;
    }

    public int getId() {
        return id;
    }

    public int getPhase() {
        return phase;
    }

    public boolean isFirstPlayer() {
        return firstPlayer;
    }

    public ArrayList<Ammo> getAmmo() {
        return ammo;
    }

    public ArrayList<Player> getShootable() {
        return shootable;
    }

    public Square getCurrentPosition() {
        return currentPosition;
    }

    public List<Powerup> getPowerup() {
        return powerup;
    }

    public int getAdrenalinelevel() {
        return adrenalinelevel;
    }

    public ArrayList<Weapon> getWeapon() {
        return weapon;
    }
}
