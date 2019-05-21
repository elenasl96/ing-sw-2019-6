package model;

import model.decks.AmmoTile;
import model.decks.Powerup;
import model.decks.Weapon;
import model.enums.Character;
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
    private boolean firstPlayer;
    private boolean dead;
    private int deaths;
    private List<Player> shootable = new ArrayList<>();
    private List<Move> possibleMoves = new ArrayList<>();
    private transient List<Move> currentMoves = new ArrayList<>();
    private boolean phaseNotDone;

    //Constructors
    public Player(Boolean canSee, Boolean cardinal, Integer minDistance, Integer maxDistance){
        super(canSee, cardinal, minDistance, maxDistance);
    }

    public Player(User user) {
        this.user = user;
        this.name = user.getUsername();
        this.character = user.getCharacter();
        this.phase = Phase.WAIT;
        this.setDead(false);
        this.deaths = 0;
        this.phaseNotDone = false;
    }

    public Player(){
        super();
    }

    //getters and setters

    public User getUser(){
        return this.user;
    }

    public List<Move> getCurrentMoves() {
        return currentMoves;
    }

    public String getName() {
        return name;
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

    public List<Ammo> getAmmos() {
        return ammos;
    }

    public List<Powerup> getPowerups() {
        return powerups;
    }

    public List<Weapon> getWeapons() {
        return weapons;
    }

    public PlayerBoard getPlayerBoard(){
        return this.playerBoard;
    }

    @Override
    public List<PlayerBoard> getPlayerBoards(int groupId) {
        ArrayList<PlayerBoard> returns = new ArrayList<>();
        returns.add(playerBoard);
        return returns;
    }

    public void addPoints(int points) {
        this.points = this.points + points;
    }

    public boolean isFirstPlayer() {
        return firstPlayer;
    }

    void setFirstPlayer(){
        this.firstPlayer = true;
    }

    public boolean isDead() {
        return dead;
    }

    public void addDeath() {
        this.deaths = this.deaths + 1;
    }

    public boolean isOverkilled(){
        return this.getPlayerBoard().getDamage().size()==12;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public boolean isPhaseNotDone(){
        return this.phaseNotDone;
    }

    public void setPhaseNotDone(boolean b) {
        this.phaseNotDone = b;
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

    //To string

    public String powerupsToString(){
        StringBuilder string = new StringBuilder();
        int nCard = 0;
        string.append("\n========Powerups=========");
        for(Powerup p : powerups){
            string.append("\nNumber: ").append(nCard)
                    .append("\n").append(p);
            nCard++;
        }
        return string.toString();
    }

    public String weaponsToString(){
        StringBuilder string = new StringBuilder();
        int nCard = 0;
        string.append("\n=========Weapons=========");
        for(Weapon w : weapons){
            string.append("\nNumber: ").append(nCard)
                    .append("\n").append(w);
            nCard++;
        }
        return string.toString();
    }

    //Overriding standard methods

    @Override
    public String toString() {
        StringBuilder toString = new StringBuilder();
        toString.append("Player{ name = ").append(name)
                .append(", character = ").append(character.name())
                .append(", points = ").append(points)
                .append(", firstPlayer = ").append(firstPlayer)
                .append(", dead = ").append(dead)
                .append(", phase not done? = ").append(phaseNotDone);
        try {
            toString.append(", phase = ").append(phase.toString()).append('}');
        }catch(NullPointerException e ) {
            toString.append(", phase = null }");
        }
        return toString.toString();
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

    //TODO this method is used only in tests: refactor to delete it!
    public void setAmmos(List<Ammo> ammoList) {
        this.ammos = ammoList;
    }
}
