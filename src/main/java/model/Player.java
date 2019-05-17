package model;

import model.decks.AmmoTile;
import model.decks.Powerup;
import model.decks.Weapon;
import model.enums.Character;
import model.enums.Color;
import model.enums.Phase;
import model.field.Square;
import model.moves.Move;
import model.moves.Target;
import model.room.User;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Player extends Target implements Serializable{
    private User user;
    private String name;
    private Character character = Character.NOT_ASSIGNED;
    private Square currentPosition;
    private transient Phase phase;
    private List<Ammo> ammos = new ArrayList<>();
    private List<Powerup> powerups = new ArrayList<>();
    private List<Weapon> weapons = new ArrayList<>();
    private PlayerBoard playerBoard = new PlayerBoard();
    private int points;
    private int adrenalineLevel;
    private int stackPoint;
    private boolean firstPlayer;
    private boolean dead;
    private List<Player> shootable = new ArrayList<>();
    private List<Move> possibleMoves = new ArrayList<>();

    //Costruttore
    public Player(Boolean canSee, Boolean cardinal, Integer minDistance, Integer maxDistance){
        super(canSee, cardinal, minDistance, maxDistance);
    }

    public Player(User user) {
        this.user = user;
        this.name = user.getUsername();
        this.character = user.getCharacter();
        this.phase = Phase.WAIT;
    }

    public Player(){
        super();
    }

    public User getUser(){
        return this.user;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Player)) {
            return false;
        }
        Player player = (Player) obj;
        return player.name.equals(this.name);
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

    @Override
    public List<PlayerBoard> getPlayerBoard(int groupId) {
        ArrayList<PlayerBoard> returns = new ArrayList<>();
        returns.add(playerBoard);
        return returns;
    }

    int getPoints() {
        return points;
    }

    void addPoints(int points) {
        this.points = this.points + points;
    }

    int getAdrenalineLevel() {
        return adrenalineLevel;
    }

    void setAdrenalineLevel(int adrenalineLevel) {
        this.adrenalineLevel = adrenalineLevel;
    }

    int getStackPoint() {
        return stackPoint;
    }

    void addStackPoint(int stackPoint) {
        this.stackPoint = this.stackPoint + stackPoint;
    }

    public boolean isFirstPlayer() {
        return firstPlayer;
    }

    void setFirstPlayer(boolean flag){
        this.firstPlayer = flag;
    }

    public boolean isDead() {
        return dead;
    }

    void setDead(boolean dead) {
        this.dead = dead;
    }

    List<Player> getShootable() {
        return shootable;
    }

    void setShootable(List<Player> shootable) {
        this.shootable = shootable;
    }

    List<Move> getPossibleMoves() {
        return possibleMoves;
    }

    void setPossibleMoves(List<Move> possibleMoves) {
        this.possibleMoves = possibleMoves;
    }

    public void fillAmmoFromTile(AmmoTile ammotile) {
        List<Ammo> refill=ammotile.getAmmos();
        for(Ammo a: refill) {
            if(Collections.frequency(ammos,a)<3)
            {
                ammos.add(a.cloneAmmo());
            }
        }
    }

    public String powerupsToString(){
        StringBuilder string = new StringBuilder();
        int nCard = 0;
        string.append("\n========Powerups=========");
        for(Powerup p : powerups){
            string.append("\nNumber").append(nCard).append("\n").append(p);
            nCard++;
        }
        return string.toString();
    }

    //Costruttore per i test
    public Player(int id, boolean firstPlayer, String name, Character character) {
        this.name = name;
        this.character = character;
        this.currentPosition = null;
        this.phase = Phase.WAIT;
        this.ammos.add(new Ammo(Color.BLUE));
        this.ammos.add(new Ammo(Color.YELLOW));
        this.ammos.add(new Ammo(Color.RED));
        this.points = 0;
        this.adrenalineLevel = 0;
        this.stackPoint = 0;
        this.dead = false;
        this.firstPlayer = firstPlayer;
    }

    @Override
    public String toString() {
        StringBuilder toString = new StringBuilder();
        toString.append("Player{ name = ").append(name)
                .append(", character = ").append(character.name())
                .append(", points = ").append(points)
                .append(", firstPlayer = ").append(firstPlayer)
                .append(", dead = ").append(dead);
        try {
            toString.append(", phase = ").append(phase.toString()).append('}');
        }catch(NullPointerException e ) {
            toString.append(", phase = null }");
        }
        return toString.toString();
    }
}
