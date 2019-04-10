package model.moves;
import exception.InvalidMovementException;
import model.Player;
import model.field.Field;
import model.field.Square;
import java.util.ArrayList;
import java.util.List;

/**
 * Implements the movement of the player of any steps on the board
 * @see Move
 */
public class Movement implements Move{
    /**
     * The square where the player wants to move
     */
    private Square destination;
    /**
     * The field of the current game
     */
    private Field field = new Field();
    /**
     * The List of achievable Squares by the player
     */
    private List<Square> reachList = new ArrayList<>();
    /**
     * The maximum number of steps the player can do
     */
    private int maxSteps;

    /**
     * Implement the movement
     * @param p the player who wants to move
     * @throws InvalidMovementException if the destination is unreachable for the player
     */
    public void execute(Player p) throws InvalidMovementException {
        if(!this.reachList.isEmpty()) this.reachList.clear();
        createReachList(p, maxSteps, p.getCurrentPosition());
        if(reachList.contains(this.destination)){
            p.setCurrentPosition(destination);
        }else throw new InvalidMovementException();
    }

    /**
     * Creates the List of reachable Squares p can do in maxSteps
     * @param p The maximum number of steps the player can do
     * @param maxSteps  the player who want to move
     * @param newCurrentPosition the Square you take in exam every round of the recursion
     */
    public void createReachList(Player p, int maxSteps, Square newCurrentPosition) {
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

    public Movement(int maxSteps) { this.maxSteps = maxSteps; }
    /**
     * Used getters and setters
     */
    public Square getDestination() {
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

    public List<Square> getReachList() {
        return reachList;
    }

    public void setReachList(List<Square> reachList) {
        this.reachList = reachList;
    }

    public int getMaxSteps() {
        return maxSteps;
    }

    public void setMaxSteps(int maxSteps) {
        this.maxSteps = maxSteps;
    }
}
