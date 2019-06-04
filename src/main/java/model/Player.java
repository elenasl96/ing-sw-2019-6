package model;

import model.decks.AmmoTile;
import model.decks.Powerup;
import model.decks.Weapon;
import model.enums.*;
import model.enums.Character;
import model.exception.InvalidMoveException;
import model.field.Edge;
import model.field.Field;
import model.field.Square;
import model.moves.Effect;
import model.moves.Move;
import model.moves.Target;
import model.room.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Player extends Target implements Serializable{
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
    private transient List<Square> reachSquares = new ArrayList<>();
    private transient List<Player> shootable = new ArrayList<>();
    private transient  List<Effect> currentEffects = new ArrayList<>();
    private transient List<Move> currentMoves = new ArrayList<>();
    private boolean phaseNotDone;

    //Constructors
    public Player(TargetType targetType, Integer minDistance, Integer maxDistance){
        super(targetType, minDistance, maxDistance);
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

    public Character getCharacter(){
        return this.character;
    }
    @Override
    public List<PlayerBoard> getPlayerBoards(int groupId) {
        ArrayList<PlayerBoard> returns = new ArrayList<>();
        returns.add(playerBoard);
        return returns;
    }

    @Override
    public String getFieldsToFill() {
        StringBuilder string = new StringBuilder();
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
            for(int i = 0; i < edges.size(); i++){
                if((edges.get(i).getSq1().equals(p.getCurrentPosition())&&
                        !edges.get(i).getSq2().getColor().equals(p.getCurrentPosition().getColor()) &&
                        this.getCurrentPosition().getColor().equals(edges.get(i).getSq2().getColor()))
                    || (edges.get(i).getSq2().equals(p.getCurrentPosition())&&
                        !edges.get(i).getSq1().getColor().equals(p.getCurrentPosition().getColor()) &&
                        this.getCurrentPosition().getColor().equals(edges.get(i).getSq1().getColor())))
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

    public void addEffectsToPlay(String[] weaponEffectsSplitted) {
        Weapon weapon = this.getWeapons().get(Integer.parseInt(weaponEffectsSplitted[0]) - 3);
        for(int i=1; i<weaponEffectsSplitted.length; i++){
            for(Effect e: weapon.getEffectsList().get(Integer.parseInt(weaponEffectsSplitted[i])).getEffects()){
                this.getCurrentEffects().add(e);
            }
        }
    }

    public List<Effect> getCurrentEffects() {
        return currentEffects;
    }

    /**
     * @param t the target the current Player wants to shoot to
     * @return
     */
    public boolean canSee(Target t, int groupID){
        return t.canBeSeen(this, groupID);
    }

    @Override
    public void setFieldsToFill(String inputName, int groupID) {
        if(this.getName() == null){
            if(this.checkTarget(inputName, groupID))
                this.name = inputName;
            else throw new InvalidMoveException("Wrong player distance");
        }
    }

    @Override
    protected boolean checkTarget(String inputName, int groupID) {
        Player target = GameContext.get().getGame(groupID).playerFromName(inputName);
        if(target != null){
            if(this.getMinDistance() != null) {
                if (getMinDistance() == 0) {
                    if(!this.getCurrentPosition()
                            .equals(GameContext.get().getGame(groupID).getCurrentPlayer().currentPosition))
                        return false;
                }else{
                    this.getCurrentPosition().createReachList(this.getMinDistance() - 1, this.reachSquares,
                            GameContext.get().getGame(groupID).getBoard().getField());
                    if (this.reachSquares.contains(GameContext.get().getGame(groupID).getCurrentPlayer().currentPosition))
                        return false;
                }
            }
            if(this.getMaxDistance() != null){
                this.reachSquares.clear();
                this.getCurrentPosition().createReachList(this.getMaxDistance(), this.reachSquares,
                        GameContext.get().getGame(groupID).getBoard().getField());
                if(!this.reachSquares.contains(GameContext.get().getGame(groupID).getCurrentPlayer().currentPosition))
                    return false;
            }
            switch (this.getTargetType()){
                case VISIBLE:
                    if(this.shootable.contains(target)) return true;
                    break;
                case NONE:
                    return true;
                case ME:
                    break;
                default:
                    return false;
            }
        }
        return false;
    }

    public void generateShootable(int groupID){
        for(Player p: GameContext.get().getGame(groupID).getPlayers()){
            if(p.canBeSeen(this, groupID)) this.shootable.add(p);
        }
    }

    public void setPlayerNumber(int number){
        this.playerNumber = number;
    }

    public int getPlayerNumber(){
        return this.playerNumber;
    }
}
