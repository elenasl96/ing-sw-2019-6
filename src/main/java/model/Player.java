package model;

import controller.ShootController;
import model.decks.AmmoTile;
import model.decks.CardEffect;
import model.decks.Powerup;
import model.decks.Weapon;
import model.enums.*;
import model.enums.Character;
import model.exception.*;
import model.field.Edge;
import model.field.Field;
import model.field.Square;
import model.moves.Move;
import model.moves.Target;
import model.room.Update;
import model.room.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.Math.min;
import static model.enums.Phase.DISCONNECTED;
import static model.enums.Phase.POWERUP_WAIT;
import static model.enums.TargetType.BASIC_EQUALS;

public class Player extends Target implements Serializable{
    private static final long serialVersionUID = 3763707889643123775L;
    //Room ID
    private User user;
    private String name;
    //Game attributes
    private Character character = Character.NOT_ASSIGNED;
    private transient Square currentPosition;
    private transient Phase phase;
    private transient List<Ammo> ammos = new ArrayList<>();
    private transient List<Powerup> powerups = new ArrayList<>();
    private transient List<Weapon> weapons = new ArrayList<>();
    private transient PlayerBoard playerBoard = new PlayerBoard();
    private transient int points;
    /**
     * True if the character is the firstPlayer
     * for FinalFrenzy mode
     */
    private boolean firstPlayer;
    private boolean dead;
    private int deaths;
    private int adrenalineLevel = 0;
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
        this.setDead(true);
        this.deaths = 0;
        this.phaseNotDone = false;
        this.ammos.add(new Ammo(Color.YELLOW));
        this.ammos.add(new Ammo(Color.RED));
        this.ammos.add(new Ammo(Color.BLUE));
    }

    public Player(){
        super();
    }

    public User getUser(){
        return this.user;
    }

    public List<Move> getCurrentMoves() {
        return currentMoves;
    }

    @Override
    public String getName() {
        return name;
    }

    /**
     * @return the player's current position in the game
     * @throws NotExistingPositionException if the position was null
     */
    @Override
    public Square getCurrentPosition() throws NotExistingPositionException {
        if(this.currentPosition == null)
            throw new NotExistingPositionException(this);
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

    /**
     * @return the weapons in a format usable for the GUI
     */
    public String getStringIdWeapons() {
        StringBuilder stringbuilder = new StringBuilder();
        for(Weapon w: weapons) {
            stringbuilder.append(w.getName()).append(";");
        }

        return stringbuilder.toString()
                .substring(0,stringbuilder.toString().length()-1);
    }

    /**
     * @return the powerups in a format usable for the GUI
     */
    public String getStringIdPowerUp() {
        StringBuilder stringbuilder = new StringBuilder();
        for(Powerup p : powerups) {
            stringbuilder.append(p.getName()).append(p.getAmmo().getColor().getAbbr()).append(";");
        }
        return stringbuilder.toString()
                .substring(0,stringbuilder.toString().length()-1);
    }

    public PlayerBoard getPlayerBoard(){
        return this.playerBoard;
    }

    public Character getCharacter(){
        return this.character;
    }

    /**
     * @see User#receiveUpdate(Update)
     */
    @Override
    public void receiveUpdate(Update update, int groupID) {
        user.receiveUpdate(update);
    }

    /**
     * @return a String asking the user what player he wants to attack
     */
    @Override
    public String getFieldsToFill() {
        if (this.getName() == null) {
            return "Choose the player; ";
        } else return "";
    }

    /**
     * @param p         the player attacking
     * @param groupID   the groupID
     * @return          true if this player is either in the same square, same room,
     *                  an adjacent room to p's position
     */
    @Override
    public boolean canBeSeen(Player p, int groupID) throws NotExistingPositionException {
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

    /**
     * @param ammotile  refills the ammos from a picked ammo tile
     * @return      the string to send to the client as update
     */
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
        if(ammosFilled.toString().isEmpty())
            return "You already have 3 ammos of that color";
        return ammosFilled.toString();
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
        if(player.name != null) {
            return player.name.equals(this.name);
        }
        return false;
    }

    int getPoints() {
        return points;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    //self explanatory
    public void addEffectsToPlay(String[] weaponEffectsSplitted) {
        Weapon weapon = this.getWeapons().get(Integer.parseInt(weaponEffectsSplitted[0]));
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
    boolean canSee(Target t, int groupID) throws InvalidMoveException {
        return t.canBeSeen(this, groupID);
    }

    @Override
    public void setFieldsToFill(String inputName, int groupID) throws NotExistingTargetException {
        if(inputName == null && this.getTargetType().equals(BASIC_EQUALS)){
           this.name = GameContext.get().getGame(groupID).getCurrentPlayer().getBasicTarget(groupID).getName();
        } else {
            this.name = inputName;
        }
    }


    /**
     * @param groupID   the group ID
     * @return          the target of the attack
     */
    @Override
    public Target fillFields(int groupID) throws NotExistingTargetException {
        return this.findRealTarget(this.name, groupID);
    }

    //Self explanatory
    @Override
    public void addDamages(Player playerDamaging, int damages, int groupId) {
        Update updateDamages;
        Update updateMarks;
        int damagesReceived;
        int marksRemoved;
        marksRemoved = this.getPlayerBoard().deleteMarks(playerDamaging);
        damagesReceived = this.getPlayerBoard().addDamage(playerDamaging, damages + marksRemoved);
        updateDamages = new Update(
                "You received " + damagesReceived + " damages " +
                "from " + playerDamaging.getName(),"damages");
        updateDamages.setData(damagesReceived + ";" + playerDamaging.getCharacter().getNum());
        this.receiveUpdate(updateDamages, groupId);
        if(marksRemoved>0) {
            updateMarks = new Update(
                    "Your " + marksRemoved + " marks from "
                            + playerDamaging.getName() + " are removed", "removemark");
            updateMarks.setData(playerDamaging.getCharacter().getNum()+"");
            this.receiveUpdate(updateMarks, groupId);
        }
        this.updateAdrenaline();
        if(this.getPlayerBoard().getDamage().size() >= 11) {
            setDead(true);
            this.getUser().receiveUpdate(new Update("You are dead! Wait for spawn","deathplayerboard"));
            GameContext.get().getGame(groupId).sendUpdate(new Update(null,"killshottrack"));
        }
        this.setPhase(POWERUP_WAIT);
        this.receiveUpdate(new Update(this, true), groupId);
    }

    //self explanatory
    private void updateAdrenaline() {
        if(this.getPlayerBoard().getDamage().size() >= 3)
            this.adrenalineLevel = 1;
        if(this.getPlayerBoard().getDamage().size() >= 6)
            this.adrenalineLevel = 2;
    }

    public int getAdrenalineLevel() {
        return adrenalineLevel;
    }

    @Override
    public void setMine(int groupID) {
        this.name = GameContext.get().getGame(groupID).getCurrentPlayer().getName();
    }

    /**
     * @param target    the shooting target
     * @param groupID   the groupID
     * @return     A list of all the shootable targets
     */
    @Override
    public List<Target> findAllTargets(Target target, int groupID) {
        List<Target> targets = new ArrayList<>();
        for(Player p: GameContext.get().getGame(groupID).getPlayers()){
            try{
                ShootController.get().checkMinDistance(target.getMinDistance(), p.getCurrentPosition(), groupID);
                ShootController.get().checkMaxDistance(target.getMaxDistance(), p.getCurrentPosition(), groupID);
                targets.add(p);
            }catch(InvalidMoveException e){
                //next player
            }
        }
        return targets;
    }

    //Self explanatory
    @Override
    public void addMarks(Player playerMarking, int groupID, int nMarks) {
        int occurrences = Collections.frequency(this.getPlayerBoard().getMarks(), playerMarking);
        int marksReceived = this.getPlayerBoard().addMarks(playerMarking, min(3-occurrences, nMarks));
        Update update = new Update("You received " + marksReceived + " marks " +
                "from " + playerMarking.getName(),"markers");
        update.setData(marksReceived + ";" + playerMarking.getCharacter().getNum());
        this.receiveUpdate(update, groupID);
    }

    @Override
    public boolean isFilled() {
        return name!=null;
    }

    public void generateVisible(int groupID) throws NotExistingPositionException {
        for(Player p: GameContext.get().getGame(groupID).getPlayers()){
            if(p.canBeSeen(this, groupID)) this.visible.add(p.getCurrentPosition());
        }
    }

    public Target getBasicTarget(int groupID) throws NotExistingTargetException {
        for(CardEffect c: currentCardEffects){
            if(c.getEffectType().equals(EffectType.BASIC)) {
                return c.getEffects().get(0).getTarget().get(0).findRealTarget(null, groupID);
            }
        }
        throw new NotExistingTargetException("basic target");
    }

    @Override
    public Target findRealTarget(String inputName, int groupID) throws NotExistingTargetException {
        if(inputName == null){
            inputName = this.name;
        }
        for (Player p : GameContext.get().getGame(groupID).getPlayers()) {
            if (p.getName().equals(inputName)) return p;
        }
        throw new NotExistingTargetException(this.getName());
    }

    @Override
    public boolean sameAsMe(int groupID) {
        return this.equals(GameContext.get().getGame(groupID).getCurrentPlayer());
    }
}
