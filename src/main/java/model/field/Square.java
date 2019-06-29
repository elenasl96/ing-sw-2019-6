package model.field;

import controller.ShootController;
import model.*;
import model.decks.AmmoTile;
import model.decks.Grabbable;
import model.enums.Color;
import model.enums.TargetType;
import model.exception.InvalidMoveException;
import model.exception.NotExistingPositionException;
import model.exception.NotExistingTargetException;
import model.moves.Target;
import model.room.Update;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static model.field.Coordinate.fillCoordinate;

//TODO javadoc
public class Square extends Target implements Serializable {
    private Color color;
    private Coordinate coord;
    /**
     * Used in weapons to decide if a player can reach a determined square or not with the attack
     */
    private List<Square> reachSquares = new ArrayList<>();

    public Square(){
        this.color = null;
        this.coord = null;
    }

    public Square(TargetType targetType, TargetType targetState, Integer minDistance, Integer maxDistance ){
        super(targetType, targetState, minDistance,maxDistance);
        this.color = null;
        this.coord = null;
    }

    public Square(Color color, Coordinate coord) {
        this.color = color;
        this.coord = coord;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Nullable
    public Grabbable getGrabbable(){
        return null;
    }

    public void setGrabbable(int groupID){
        //Do nothing
    }

    public Coordinate getCoord() {
        return coord;
    }

    /**
     * @param player    the player attacking
     * @param groupID   the groupID
     * @return          true if the square is either the same square, in the same room,
     *                  in an adjacent room to player's position
     */
    public boolean canBeSeen(Player player, int groupID) throws NotExistingPositionException {
        if(player.getCurrentPosition().getColor().equals(this.getColor()))
            return true;
        else {
            List<Edge> edges = GameContext.get().getGame(groupID).getBoard().getField().getEdges();
            for (Edge e: edges){
                if ((e.getSq1().equals(player.getCurrentPosition()) &&
                        !e.getSq2().getColor().equals(player.getCurrentPosition().getColor()) &&
                        this.getColor().equals(e.getSq2().getColor()))
                        || (e.getSq2().equals(player.getCurrentPosition()) &&
                        !e.getSq1().getColor().equals(player.getCurrentPosition().getColor()) &&
                        this.getColor().equals(e.getSq1().getColor())))
                    return true;
            }
        } return false;
    }

    public boolean isEmpty() {
        return this.getGrabbable()==null;
    }

    public void addGrabbable(Grabbable grabbable, int groupID) {
        //DO nothing
    }

    /**
     * Creates the List of reachable Squares p can do in maxSteps
     * @param reachList the squares reachable from this square
     * @param maxSteps  the player who want to move
     * @param field the field where is the square
     */
    public void createReachList(int maxSteps, List<Square> reachList, Field field) {
        reachList.add(this);
        if (maxSteps != 0) {
            for (int i = 0; i < maxSteps; i++) {
                field.getEdges().forEach(edge -> {
                    if (edge.getSq1().equals(this)){
                        reachList.add(edge.getSq2());
                        edge.getSq2().createReachList(maxSteps-1, reachList, field);
                    } else if (edge.getSq2().equals(this)){
                        reachList.add(edge.getSq1());
                        edge.getSq1().createReachList(maxSteps-1, reachList, field);
                    }
                });
            }
        }
    }

    public List<Square> getReachSquares() {
        return reachSquares;
    }

    public void setCoordinate(Coordinate fillCoordinate) {
        this.coord = fillCoordinate;
    }

    public void replaceAmmoTile(int groupID) {
        GameContext.get().getGame(groupID).getBoard().getAmmosLeft().discardCard((AmmoTile) this.getGrabbable());
        this.addGrabbable(GameContext.get().getGame(groupID).getBoard().getAmmosLeft().pickCard(), groupID);
    }
    @Override
    public void receiveUpdate(Update update) {
        //TODO send update to all users in that square
    }

    @Override
    public String getFieldsToFill() {
        if (
                this.coord == null ||
                this.getTargetType().equals(TargetType.MINE) ||
                this.getTargetType().equals(TargetType.ALL)
        ) return "Choose the square; ";
        else return "";
    }

    @Override
    public void setFieldsToFill(String input, int groupID) throws NotExistingTargetException, NotExistingPositionException {
        if(input == null && this.getTargetType().equals(TargetType.BASIC_EQUALS)){
            this.coord = GameContext.get().getGame(groupID).getCurrentPlayer().getBasicTarget(groupID).getCurrentPosition().getCoord();
        } else {
            this.coord = fillCoordinate(input);
        }
    }

    @Override
    public Target fillFields(int groupID) throws NotExistingTargetException {
        return this.findRealTarget(coord.toString(), groupID);
    }

    @Override
    public boolean isFilled() {
        return coord!=null;
    }

    @Override
    public Square getCurrentPosition() {
        return this;
    }

    @Override
    public Target findRealTarget(String coordinateString, int groupID) throws NotExistingTargetException {
        Coordinate coordinate = fillCoordinate(coordinateString);
        for(Square s: GameContext.get().getGame(groupID).getBoard().getField().getSquares()){
            if(s.getCoord().equals(coordinate)) return s;
        }
        throw new NotExistingTargetException(this.getCoord().toString());
    }

    @Override
    public boolean sameAsMe(int groupID) {
        return false;
    }

    @Override
    public String toString() {
        return this.coord.toString();
    }

    //Effects
    @Override
    public void addDamages(Player playerDamaging, int damages, int groupId) throws NotExistingPositionException {
        for(Player player: GameContext.get().getGame(groupId).getPlayers()){
            if(player.getCurrentPosition().equals(this) && !player.equals(playerDamaging))
                player.addDamages(playerDamaging, damages, groupId);
        }
    }

    @Override
    public void setMine(int groupID) throws NotExistingPositionException {
        this.coord = GameContext.get().getGame(groupID).getCurrentPlayer().getCurrentPosition().getCoord();
    }

    @Override
    public List<Target> findAllTargets(Target target, int groupID) {
        List<Target> targets = new ArrayList<>();
        for(Square s: GameContext.get().getGame(groupID).getBoard().getField().getSquares()){
            try{
                ShootController.get().checkMinDistance(target.getMinDistance(), s, groupID);
                ShootController.get().checkMaxDistance(target.getMaxDistance(), s, groupID);
                    targets.add(s);
            }catch(InvalidMoveException e){
                //next square
            }
        }
        return targets;
    }

    @Override
    public void addMarks(Player playerMarking, int groupID, int nMarks) throws NotExistingPositionException {
        for(Player player: GameContext.get().getGame(groupID).getPlayers()){
            if(player.getCurrentPosition().equals(this) && !player.equals(playerMarking))
                player.addMarks(playerMarking, groupID, nMarks);

        }
    }
}
