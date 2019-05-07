package model;

import model.decks.AmmoTile;
import model.decks.Powerup;
import model.decks.Weapon;
import model.enums.Character;
import model.enums.Color;
import model.enums.Phase;
import model.field.Square;
import model.moves.Move;
import model.room.User;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Player implements Serializable{
    private User user;
    private String name;
    private int id; //da 0 a numeroGiocatori-1
    private Character character = Character.NOT_ASSIGNED;
    private Square currentPosition;
    private Phase phase;
    private List<Ammo> ammos = new ArrayList<>();
    private List<Powerup> powerups = new ArrayList<>();
    private List<Weapon> weapons = new ArrayList<>();
    private PlayerBoard playerBoard = new PlayerBoard();
    private int points;
    private String motto;
    private int adrenalineLevel;
    private int stackPoint;
    private boolean firstPlayer;
    private boolean dead;
    private List<Player> shootable = new ArrayList<>();
    private List<Move> possibleMoves = new ArrayList<>();

    //Costruttore
    public Player(int id, User user) {
        this.user = user;
        this.name = user.getUsername();
        this.id = id;
        this.character = user.getCharacter();
        this.currentPosition = null;
        this.phase = Phase.WAIT;
        this.ammos.add(new Ammo(Color.BLUE));
        this.ammos.add(new Ammo(Color.YELLOW));
        this.ammos.add(new Ammo(Color.RED));
        this.points = 0;
        this.motto = null;
        this.adrenalineLevel = 0;
        this.stackPoint = 0;
        this.dead = false;
    }

    public Player(){
        super();
    }

    public User getUser(){
        return this.user;
    }

    public boolean equals(Object obj) {
        if(obj == null) return false;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Player)) {
            return false;
        }
        Player player = (Player) obj;
        return player.id == this.id;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
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

    public void setAmmos(List<Ammo> ammos) {
        this.ammos = ammos;
    }

    public List<Ammo> getAmmos() {
        return ammos;
    }

    public List<Powerup> getPowerups() {
        return powerups;
    }

    public List<Weapon> getWeapons() {
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

    public int getAdrenalineLevel() {
        return adrenalineLevel;
    }

    public void setAdrenalineLevel(int adrenalineLevel) {
        this.adrenalineLevel = adrenalineLevel;
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

    public void setFirstPlayer(boolean flag){
        this.firstPlayer = flag;
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public List<Player> getShootable() {
        return shootable;
    }

    public void setShootable(List<Player> shootable) {
        this.shootable = shootable;
    }

    public List<Move> getPossibleMoves() {
        return possibleMoves;
    }

    public void setPossibleMoves(List<Move> possibleMoves) {
        this.possibleMoves = possibleMoves;
    }

    public void refillAmmo(AmmoTile ammotile) {
        List<Ammo> refill=ammotile.getAmmos();
        for(Ammo a: refill) {
            if(Collections.frequency(ammos,a)<3)
            {
                ammos.add(a.clone());
            }
        }
    }

    public String powerupsToString(List<Powerup> powerups){
        StringBuilder string = new StringBuilder();
        int nCard = 0;
        string.append("\n========Powerups=========");
        for(Powerup p : powerups){
            string.append("\nNumber: " + nCard +"\n").append(p);
            nCard++;
        }
        return string.toString();
    }

    //Costruttore per i test
    public Player(int id, boolean firstPlayer, String name, Character character) {
        this.name = name;
        this.id = id;
        this.character = character;
        this.currentPosition = null;
        this.phase = Phase.WAIT;
        this.ammos.add(new Ammo(Color.BLUE));
        this.ammos.add(new Ammo(Color.YELLOW));
        this.ammos.add(new Ammo(Color.RED));
        this.points = 0;
        this.motto = null;
        this.adrenalineLevel = 0;
        this.stackPoint = 0;
        this.dead = false;
        this.firstPlayer = firstPlayer;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", character=" + character.name() +
                ", phase=" + phase.toString() +
                ", points=" + points +
                ", firstPlayer=" + firstPlayer +
                ", dead=" + dead +
                '}';
    }
}
