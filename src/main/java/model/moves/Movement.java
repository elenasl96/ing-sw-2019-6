package model.moves;
import controller.MoveRequestHandler;
import exception.InvalidMoveException;
import exception.InvalidMovementException;
import model.GameContext;
import model.Player;
import model.field.Coordinate;
import model.field.Field;
import model.field.Square;
import model.room.Update;
import network.socket.commands.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * Implements the movement of the player of any steps on the board
 * @see Move
 */
public class Movement implements Move{
    private Coordinate coordinate;
    /**
     * The square where the player wants to move
     */
    private Square destination;
    /**
     * The field of the current game
     */
    private Field field;
    /**
     * The List of achievable Squares by the player
     */
    private List<Square> reachList = new ArrayList<>();
    /**
     * The maximum number of steps the player can do
     */
    private int maxSteps;
    private int maxStepsFrenzy;

    public Movement(int maxSteps){
        this.maxSteps = maxSteps;
    }

    /**
     * Implement the movement
     * @param p the player who wants to move
     * @throws InvalidMovementException if the destination is unreachable for the player
     */
    public void execute(Player p, int groupId) throws InvalidMovementException {
        if(!this.reachList.isEmpty()) this.reachList.clear();
        createReachList(p, maxSteps, p.getCurrentPosition());
        if(reachList.contains(this.destination)){
            p.setCurrentPosition(destination);
        }else throw new InvalidMovementException();
        GameContext.get().getGame(groupId)
                .sendUpdate(new Update(p.getName()+" moved to "+p.getCurrentPosition()));
    }

    @Override
    public void handle(MoveRequestHandler moveRequestHandler, int groupId) throws InvalidMoveException {
        moveRequestHandler.handle(this, groupId);
    }

    /**
     * Creates the List of reachable Squares p can do in maxSteps
     * @param p The maximum number of steps the player can do
     * @param maxSteps  the player who want to move
     * @param newCurrentPosition the Square you take in exam every round of the recursion
     */
    private void createReachList(Player p, int maxSteps, Square newCurrentPosition) {
        if (maxSteps != 0) {
            for (int i = 0; i < maxSteps; i++) {
                field.getEdges().forEach(edge -> {
                    if (edge.getSq1().equals(newCurrentPosition)){
                        this.reachList.add(edge.getSq2());
                        createReachList(p, maxSteps-1, edge.getSq2());
                    } else if (edge.getSq2().equals(newCurrentPosition)){
                        this.reachList.add(edge.getSq1());
                        createReachList(p, maxSteps-1, edge.getSq1());
                    }
                });
            }
        }
    }

    /**
     * The constructor
     * @param field the current game field
     */
    public Movement(Field field){
        this.field = field;
    }

    public Movement(Coordinate coordinate){
        this.coordinate = coordinate;
    }
    /**
     * Used getters and setters
     */
    Square getDestination() {
        return destination;
    }

    public void setDestination(Square destination) {
        this.destination = destination;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    List<Square> getReachList() {
        return reachList;
    }

    void setReachList(List<Square> reachList) {
        this.reachList = reachList;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    int getMaxSteps() {
        return maxSteps;
    }

    void setMaxSteps(int maxSteps) {
        this.maxSteps = maxSteps;
    }

    public int getMaxStepsFrenzy() {
        return maxStepsFrenzy;
    }

    public void setMaxStepsFrenzy(int maxStepsFrenzy) {
        this.maxStepsFrenzy = maxStepsFrenzy;
    }
}
