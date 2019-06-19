package model;

import model.decks.AmmoTile;
import model.decks.CardEffect;
import model.decks.Powerup;
import model.decks.Weapon;
import model.enums.*;
import model.enums.Character;
import model.exception.FullMarksException;
import model.exception.InvalidMoveException;
import model.field.Edge;
import model.field.Field;
import model.field.Square;
import model.moves.Effect;
import model.moves.Move;
import model.moves.Target;
import model.room.Update;
import model.room.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.Math.min;
import static model.enums.TargetType.MINE;

//TODO javadoc
public class Player extends Target implements Serializable{
    private static final long serialVersionUID = 3763707889643123775L;
    private User user;
    private int playerNumber;
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
    private transient List<Square> visible = new ArrayList<>();
    private transient  List<CardEffect> currentCardEffects = new ArrayList<>();
    private transient List<Move> currentMoves = new ArrayList<>();
    private boolean phaseNotDone;
    private int weaponInUse;

    public int getWeaponInUse() {
        return weaponInUse;
    }

    public void setWeaponInUse(int weaponInUse) {
        this.weaponInUse = weaponInUse;
    }

    //Constructors
    public Player(TargetType targetType, TargetType targetState, Integer minDistance, Integer maxDistance){
        super(targetType, targetState, minDistance, maxDistance);
    }

    public Player(User user) {
        this.user = user;
        this.name = user.getUsername();
        this.character = user.getCharacter();
        this.phase = Phase.WAIT;
        this.setDead(false);
        this.deaths = 0;
        this.phaseNotDone = false;
        this.ammos.add(new Ammo(Color.YELLOW));
        this.ammos.add(new Ammo(Color.RED));
        this.ammos.add(new Ammo(Color.BLUE));
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

    @Override
    public Square getCurrentPosition() {
        if(this.currentPosition == null)
            throw new InvalidMoveException(this.name+" position doesn't exist");
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

    public String getStringIdWeapons() {
        StringBuilder stringbuilder = new StringBuilder("");
        for(Weapon w: weapons) {
            stringbuilder.append(w.getName()).append(";");
        }

        if(stringbuilder.toString()!=null) return stringbuilder.toString()
                .substring(0,stringbuilder.toString().length()-1);
        return null;
    }

    public String getStringIdPowerUp() {
        StringBuilder stringbuilder = new StringBuilder("");
        for(Powerup p : powerups) {
            stringbuilder.append(p.getName()).append(";");
        }
        if(stringbuilder.toString()!=null) return stringbuilder.toString()
                .substring(0,stringbuilder.toString().length()-1);
        return null;
    }

    public PlayerBoard getPlayerBoard(){
        return this.playerBoard;
    }

    public Character getCharacter(){
        return this.character;
    }

    public void setPlayerNumber(int number){
        this.playerNumber = number;
    }

    public int getPlayerNumber(){
        return this.playerNumber;
    }

    @Override
    public void receiveUpdate(Update update) {
        user.receiveUpdate(update);
    }

    @Override
    public String getFieldsToFill() {
        if (this.getName() == null) {
            return "Choose the player; ";
        } else return "";
    }

    public boolean canBeSeen(Player p, int groupID) {
        Field field = GameContext.get().getGame(groupID).getBoard().getField();
        if (this.getCurrentPosition().getColor().equals(p.getCurrentPosition().getColor())){
            return true;
        } else {
            List<Edge> edges= field.getEdges();
            for(Edge e: edges){
                if((e.getSq1().equals(p.getCurrentPosition())&&
                        !e.getSq2().getColor().equals(p.getCurrentPosition().getColor()) &&
                        this.getCurrentPosition().getColor().equals(e.getSq2().getColor()))
                    || (e.getSq2().equals(p.getCurrentPosition())&&
                        !e.getSq1().getColor().equals(p.getCurrentPosition().getColor()) &&
                        this.getCurrentPosition().getColor().equals(e.getSq1().getColor())))
                    return true;
            }
        }
        return false;
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

    public String fillAmmoFromTile(AmmoTile ammotile) {
        StringBuilder ammosFilled = new StringBuilder();
        List<Ammo> refill=ammotile.getAmmos();
        for(Ammo a: refill) {
            if(Collections.frequency(ammos,a)<3)
            {
                ammos.add(a.cloneAmmo());
                ammosFilled.append(a.toString());
            }
        }
        if(ammosFilled.toString().isEmpty()) throw new InvalidMoveException("You cannot have more ammos of that color");

        return ammosFilled.toString();
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

    public String weaponsToString(int index){
        StringBuilder string = new StringBuilder();
        string.append("\n=========Weapons=========");
        for(Weapon w : weapons){
            string.append("\nID: ").append(index).append(w);
            index++;
        }
        return string.toString();
    }

    public List<Square> getVisible() {
        return visible;
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

    public void addEffectsToPlay(String[] weaponEffectsSplitted) {
        Weapon weapon = this.getWeapons().get(Integer.parseInt(weaponEffectsSplitted[0]) - 3);
        for(int i=1; i<weaponEffectsSplitted.length; i++){
            this.getCurrentCardEffects().add(weapon.getEffectsList().get(Integer.parseInt(weaponEffectsSplitted[i])));
        }
    }

    public List<CardEffect> getCurrentCardEffects() {
        return currentCardEffects;
    }

    /**
     * @param t the target the current Player wants to shoot to
     * @return true value if the player can see the target, false otherwise
     */
    public boolean canSee(Target t, int groupID){
        return t.canBeSeen(this, groupID);
    }

    @Override
    public void setFieldsToFill(String inputName, int groupID) {
        this.name = inputName;
    }


    @Override
    public Player fillFields(int groupID) {
        return (Player) this.findRealTarget(this.name, groupID);
    }

    @Override
    public void addDamages(Player playerDamaging, int damages, int groupId) {
        int damagesReceived = this.getPlayerBoard().addDamage(playerDamaging, damages);
        Update update = new Update("You received " + damagesReceived + " damages " +
                "from " + playerDamaging.getName(),"damages");
        update.setData(damagesReceived + ";" + playerDamaging.getCharacter().getNum());
        this.receiveUpdate(update);
        if(this.getPlayerBoard().getDamage().size() == (11 | 12))
            this.dead = true;
    }

    @Override
    public void setMine(int groupID) {
        this.name = GameContext.get().getGame(groupID).getCurrentPlayer().getName();
    }

    @Override
    public void addMarks(Player playerMarking, int groupID, int nMarks) {
        int occurrences = Collections.frequency(this.getPlayerBoard().getMarks(), playerMarking);
        if(occurrences<3){
            int marksReceived = this.getPlayerBoard().addMarks(playerMarking, min(3-occurrences, nMarks));
            Update update = new Update("You received " + marksReceived + " marks " +
                    "from " + playerMarking.getName(),"markers");
            update.setData(marksReceived + ";" + playerMarking.getCharacter().getNum());
            this.receiveUpdate(update);
        } else{
            throw new FullMarksException();
        }
    }

    @Override
    public boolean isFilled() {
        return name!=null;
    }

    public void generateVisible(int groupID){
        for(Player p: GameContext.get().getGame(groupID).getPlayers()){
            if(p.canBeSeen(this, groupID)) this.visible.add(p.getCurrentPosition());
        }
    }

    public Target getBasicTarget(int groupID) {
        for(CardEffect c: currentCardEffects){
            for(Effect e: c.getEffects()){
                if(e.getTarget().size()==1)
                    return e.getTarget().get(0).findRealTarget(name, groupID);
            }
        }
        throw new InvalidMoveException("Not only one basic target");
    }

    @Override
    public Target findRealTarget(String inputName, int groupID) {
        for (Player p : GameContext.get().getGame(groupID).getPlayers()) {
            if (p.getName().equals(inputName)) return p;
        }
        throw new InvalidMoveException("Player "+inputName+" doesn't exist");
    }

    @Override
    public boolean sameAsMe(int groupID) {
        return this.equals(GameContext.get().getGame(groupID).getCurrentPlayer());
    }

}
