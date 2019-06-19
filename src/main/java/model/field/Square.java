package model.field;

import controller.ShootController;
import model.*;
import model.decks.AmmoTile;
import model.decks.Grabbable;
import model.enums.Color;
import model.enums.TargetType;
import model.exception.InvalidMoveException;
import model.moves.Target;
import model.room.Update;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static model.field.Coordinate.fillCoordinate;

//TODO javadoc
public class Square extends Target implements Serializable {
    private Color color;
    private Coordinate coord;
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

    public Grabbable getGrabbable(){
        return null;
    }

    public void setGrabbable(Board board){
        //DO nothing
    }

    public Coordinate getCoord() {
        return coord;
    }

    public boolean canBeSeen(Player player, int groupID) {
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
    public void setFieldsToFill(String input, int groupID) {
        if(input == null && this.getTargetType().equals(TargetType.BASIC_EQUALS)){
            this.coord = ((Square) GameContext.get().getGame(groupID).getCurrentPlayer().getBasicTarget(groupID)).getCoord();
        }else{
            this.coord = fillCoordinate(input);
        }
    }

    @Override
    public Target fillFields(int groupID) {
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
    public Target findRealTarget(String coordinateString, int groupID) {
        Coordinate coordinate = fillCoordinate(coordinateString);
        for(Square s: GameContext.get().getGame(groupID).getBoard().getField().getSquares()){
            if(s.getCoord().equals(coordinate)) return s;
        }
        throw new InvalidMoveException("Square " + coordinate + " doesn't exist.");
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
    public void addDamages(Player playerDamaging, int damages, int groupId) {
        GameContext.get().getGame(groupId).getPlayers()
                .stream()
                .filter(player -> player.getCurrentPosition().equals(this) && !player.equals(playerDamaging))
                .forEach(player -> player.addDamages(playerDamaging, damages, groupId));
    }

    @Override
    public void setMine(int groupID) {
        this.coord = GameContext.get().getGame(groupID).getCurrentPlayer().getCurrentPosition().getCoord();
    }

    @Override
    public List<Target> findAllTargets(Target target, int groupID) {
        List<Target> targets = new ArrayList<>();
        for(Square s: GameContext.get().getGame(groupID).getBoard().getField().getSquares()){
            try{
                ShootController.get().checkMinDistance(target, s, groupID);
                ShootController.get().checkMaxDistance(target, s, groupID);
                targets.add(s);
            }catch(InvalidMoveException e){
                //next square
            }
        }
        return targets;
    }

    @Override
    public void addMarks(Player playerMarking, int groupID, int nMarks) {
        GameContext.get().getGame(groupID).getPlayers()
                .stream()
                .filter(player -> getCurrentPosition().equals(this) && !player.equals(playerMarking))
                .forEach(player -> player.addMarks(playerMarking, groupID, nMarks));
    }
}
