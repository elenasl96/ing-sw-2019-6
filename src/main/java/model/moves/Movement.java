package model.moves;

import model.exception.InvalidMovementException;
import model.GameContext;
import model.Player;
import model.field.Coordinate;
import model.field.Field;
import model.field.Square;
import model.room.Update;
import network.commands.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static model.field.Coordinate.fillCoordinate;

/**
 * Implements the movement of the player of any steps on the board
 * @see Move
 */
public class Movement extends Effect{
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

    public Movement (Stream<Target> target, Square destination, boolean optionality){
        super(target, optionality);
        this.destination = destination;
        this.maxSteps = -1; //Default (by Marti)
    }

    public Movement(int maxSteps){
        this.maxSteps = maxSteps;
    }

    /**
     * Implement the movement
     * @param p the player who wants to move
     * @throws InvalidMovementException if the destination is unreachable for the player
     */
    public Response execute(Player p, int groupID) {
        System.out.println("The square inserted is: "+coordinate);
        Square squareDestination = null;
        //Check if the coordinate is valid
        for(Square square: GameContext.get().getGame(groupID).getBoard().getField().getSquares()) {
            if (square.getCoord().equals(coordinate)){
                squareDestination = square;
                break;
            }
        } if(squareDestination == null){
            throw new InvalidMovementException();
        } else {
            this.setDestination(squareDestination);
            this.setField(GameContext.get().getGame(groupID).getBoard().getField());
        }
        if(!this.reachList.isEmpty()) this.reachList.clear();
        createReachList(maxSteps, p.getCurrentPosition());
        if(reachList.contains(this.destination)){
            p.setCurrentPosition(destination);
        }else {
            throw new InvalidMovementException();
        }
        Update update = new Update(p.getName()+" moved to "+p.getCurrentPosition());
        update.setMove("movement");
        update.setData(p.getCharacter().toString()+";"+p.getCurrentPosition());
        GameContext.get().getGame(groupID).sendUpdate(update);
        return null;
    }


    /**
     * Creates the List of reachable Squares p can do in maxSteps
     * @param maxSteps  the player who want to move
     * @param newCurrentPosition the Square you take in exam every round of the recursion
     */
    private void createReachList(int maxSteps, Square newCurrentPosition) {
        this.reachList.add(newCurrentPosition);
        if (maxSteps != 0) {
            for (int i = 0; i < maxSteps; i++) {
                field.getEdges().forEach(edge -> {
                    if (edge.getSq1().equals(newCurrentPosition)){
                        this.reachList.add(edge.getSq2());
                        createReachList(maxSteps-1, edge.getSq2());
                    } else if (edge.getSq2().equals(newCurrentPosition)){
                        this.reachList.add(edge.getSq1());
                        createReachList(maxSteps-1, edge.getSq1());
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

    public int getMaxSteps() {
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

    @Override
    public String getFieldsToFill() {
        StringBuilder string = new StringBuilder();
        string.append("Movement: ");
        for(Target t: targets){
            string.append(t.getFieldsToFill());
        }
        if(destination != null) string.append("destination (Letter, Number); ");
        return string.toString();
    }

    @Override
    public void fillFields(String[] inputMatrix, int groupID) {
        int i = 0;
        for(Target t: targets){
            t.setFieldsToFill(inputMatrix[i], groupID);
            i++;
        }
        if(this.coordinate == null)
            this.coordinate= fillCoordinate(inputMatrix[i]);
    }
}
