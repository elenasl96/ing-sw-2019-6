package model;

import model.decks.AmmoTile;
import model.decks.Powerup;
import model.decks.Weapon;
import model.enums.Character;
import model.enums.Color;
import model.enums.Phase;
import model.field.Square;
import model.moves.Move;

import java.util.ArrayList;

public class Player {
    private String name;
    private int id; //da 0 a numeroGiocatori-1
    private Character character;
    private Square currentPosition;
    private Phase phase;
    private ArrayList<Ammo> ammos = new ArrayList<Ammo>();
    private ArrayList<Powerup> powerups;
    private ArrayList<Weapon> weapons;
    private PlayerBoard playerBoard;
    private int points;
    private String motto;
    private int adrenalinelevel;
    private int stackPoint;
    private boolean firstPlayer;
    private boolean dead;
    private ArrayList<Player> shootable;
    transient private ArrayList<Move> possibleMoves = new ArrayList<Move>();

    //Costruttore

    public Player(int id, boolean firstPlayer) {
        this.id = id;
        this.firstPlayer = firstPlayer;
        this.name = null;
        this.character = Character.NOT_ASSIGNED;
        this.currentPosition = null;
        this.phase = Phase.WAIT;
        this.ammos.add(new Ammo(Color.BLUE));
        this.ammos.add(new Ammo(Color.YELLOW));
        this.ammos.add(new Ammo(Color.RED));
        this.powerups = new ArrayList<Powerup>();
        this.weapons = new ArrayList<Weapon>();
        this.playerBoard = new PlayerBoard();
        this.points = 0;
        this.motto = null;
        this.adrenalinelevel = 0;
        this.stackPoint = 0;
        this.dead = false;
        this.shootable = new ArrayList<Player>();
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    public Square getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(Square destination) {
        this.currentPosition = destination;
    }

    public Phase getPhase() {
        return phase;
    }

    public void setPhase(Phase phase) {
        this.phase = phase;
    }

    /*
    Some methods set for ArrayList types deleted ( method .add provided by ArrayList)
     */

    public void setAmmos(ArrayList<Ammo> ammos) {
        this.ammos = ammos;
    }

    public ArrayList<Ammo> getAmmos() {
        return ammos;
    }

    public ArrayList<Powerup> getPowerups() {
        return powerups;
    }

    public ArrayList<Weapon> getWeapons() {
        return weapons;
    }

    public PlayerBoard getPlayerBoard() {
        return playerBoard;
    }

    /*
    set PlayerBoard deleted as PlayerBoard not editable
     */

    public int getPoints() {
        return points;
    }

    public void addPoints(int points) {
        this.points = this.points + points;
    }

    public String getMotto() {
        return motto;
    }

    public void setMotto(String motto) {
        this.motto = motto;
    }

    public int getAdrenalinelevel() {
        return adrenalinelevel;
    }

    public void setAdrenalinelevel(int adrenalinelevel) {
        this.adrenalinelevel = adrenalinelevel;
    }

    public int getStackPoint() {
        return stackPoint;
    }

    public void addStackPoint(int stackPoint) {
        this.stackPoint = this.stackPoint + stackPoint;
    }

    public boolean isFirstPlayer() {
        return firstPlayer;
    }

    /*
    SetFirstPlayer deleted as attribute not editable
     */

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public ArrayList<Player> getShootable() {
        return shootable;
    }

    public void setShootable(ArrayList<Player> shootable) {
        this.shootable = shootable;
    }

    public ArrayList<Move> getPossibleMoves() {
        return possibleMoves;
    }

    public void setPossibleMoves(ArrayList<Move> possibleMoves) {
        this.possibleMoves = possibleMoves;
    }
}
