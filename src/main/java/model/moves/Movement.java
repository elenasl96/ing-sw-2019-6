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

//TODO javadoc
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
    //TODO remove Field
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

    public Movement (Stream<Target> target, Target destination, boolean optionality){
        super(target, optionality);
        this.destination = (Square) destination;
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
    @Override
    public Response execute(Player p, int groupID) {
        if(this.getTarget() != null){
            p = (Player) this.getTarget().get(0);
        }
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
        p.getCurrentPosition().createReachList(maxSteps, this.reachList, field);
        if(reachList.contains(this.destination)){
            p.setCurrentPosition(destination);
        }else {
            throw new InvalidMovementException();
        }
        Update update = new Update(p.getName()+" moved to "+p.getCurrentPosition());
        update.setMove("movement");
        update.setData(p.getCharacter().getNum() + ";" + p.getCurrentPosition().toString().replace("[","").replace("]",""));
        GameContext.get().getGame(groupID).sendUpdate(update);
        return null;
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
        String string = super.getFieldsToFill();
        string += destination.getFieldsToFill();
        if (string.isEmpty())
            return string;
        else return "Movement: " + string;
    }

    @Override
    public void fillFields(int groupID) {
        super.fillFields(groupID);
        this.destination = (Square) this.destination.findRealTarget(this.destination.getCoord().toString(), groupID);
        this.coordinate = this.destination.getCoord();
    }

    @Override
    public int setFieldsToFill(String[] inputMatrix, int index, int groupID) {
       index += super.setFieldsToFill(inputMatrix,index,groupID);
       if(this.destination.getCoord() == null) {
           this.destination.setCoordinate(fillCoordinate(inputMatrix[index]));
       }
       if(this.maxSteps == -1) {
           if (this.destination.getMaxDistance() != null) {
               this.maxSteps = this.getDestination().getMaxDistance();
           } else{
               this.maxSteps = 5;
           }
       }

        return index;
    }
}
