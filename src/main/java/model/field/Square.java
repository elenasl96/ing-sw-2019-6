package model.field;

import model.*;
import model.decks.Grabbable;
import model.decks.Weapon;
import model.enums.Color;
import model.enums.TargetType;
import model.exception.InvalidMoveException;
import model.moves.Target;
import model.room.Update;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public Square(TargetType targetType, Integer minDistance, Integer maxDistance ){
        super(targetType, minDistance,maxDistance);
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

    public List<PlayerBoard> getPlayerBoards(int groupId){
        Player currentPlayer = GameContext.get().getGame(groupId).getCurrentPlayer();
        ArrayList<PlayerBoard> boards = new ArrayList<>();
        for(Player p : GameContext.get().getGame(groupId).getPlayers().stream()
                .filter(p -> p.getCurrentPosition().coord.equals(this.coord) && !p.equals(currentPlayer))
                .collect(Collectors.toList())){
            boards.add(p.getPlayerBoards(groupId).get(0));
        }
        return boards;
    }

    @Override
    public void receiveUpdate(Update update) {
        //TODO send update to all users in that square
    }

    @Override
    public String getFieldsToFill() {
        if (this.coord == null) return "Choose the square; ";
        else return "";
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

    @Override
    public void setFieldsToFill(String input, int groupID) {
        this.coord = fillCoordinate(input);
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

    public boolean isEmpty() {
        return this.getGrabbable()==null;
    }

    public void addGrabbable(Weapon grabbable, int groupID) {
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
}
