package model.moves;

import model.Game;
import model.enums.TargetType;
import model.exception.InvalidMoveException;
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
    private Square destination;
    /**
     * The List of achievable Squares by the player
     */
    private List<Square> reachList = new ArrayList<>();

    private int maxSteps;

    public Movement (Stream<Target> target, Target destination, boolean optionality){
        super(target, optionality);
        this.destination = (Square) destination;
        this.maxSteps = -1; //Default
    }

    public Movement(int maxSteps){
        this.maxSteps = maxSteps;
    }

    /**
     * Implements the movement. First checks if the coordinate is valid, then moves the player and
     * sends update to every user in the game
     * @param p the player who wants to move
     * @throws InvalidMovementException if the destination is unreachable for the player
     */
    @Override
    public Response execute(Player p, int groupID) {
        if(this.getTarget() != null && !this.getTarget().isEmpty()){
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
            this.destination = squareDestination;
        }
        if(!this.reachList.isEmpty()) this.reachList.clear();
        p.getCurrentPosition().createReachList(maxSteps, this.reachList, GameContext.get().getGame(groupID).getBoard().getField());
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

    public Movement(Coordinate coordinate){
        this.coordinate = coordinate;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    void setMaxSteps(int maxSteps) {
        this.maxSteps = maxSteps;
    }

    /**
     * @return  weapon implementation string useful to ask the client which fields he has to fill to attack
     */
    @Override
    public String getFieldsToFill() {
        String string = super.getFieldsToFill();
        string += destination.getFieldsToFill();
        if (string.isEmpty())
            return string;
        else return "Movement: " + string;
    }

    /**
     * weapon implementation method that fills the coordinates chose by the client
     */
    @Override
    public void fillFields(int groupID) {
        super.fillFields(groupID);
        this.destination = (Square) this.destination.findRealTarget(this.destination.getCoord().toString(), groupID);
        this.coordinate = this.destination.getCoord();
    }

    /**
     *
     * @param inputMatrix
     * @param index
     * @param groupID
     * @return
     */
    @Override
    public int setFieldsToFill(String[] inputMatrix, int index, int groupID) {
        if(this.destination.getCoord() == null) {
           if(inputMatrix == null) {
               if(this.destination.getTargetType().equals(TargetType.BASIC_EQUALS)){
                   destination = GameContext.get().getGame(groupID).getCurrentPlayer().getBasicTarget(groupID).getCurrentPosition();
               }else if(!this.getOptionality()) {
                   throw new InvalidMoveException("Not enough fields");
               }
           } else {
               index += super.setFieldsToFill(inputMatrix,index,groupID);
               if(inputMatrix[index]!=null) {
                   if(inputMatrix[index].equals("")){
                       if(!this.getOptionality())
                           throw new InvalidMoveException("Not enough fields");
                   }
                   else
                       try {
                           this.destination.setCoordinate(fillCoordinate(inputMatrix[index]));
                       }catch(NumberFormatException e){
                           throw new InvalidMoveException("Not valid square");
                       }
                   index++;
               }

           }
       }
       if(this.maxSteps == -1) {
           if (this.destination.getMaxDistance() != null) {
               this.maxSteps = this.destination.getMaxDistance();
           } else{
               this.maxSteps = 5;
           }
       }
        return index;
    }

    @Override
    public boolean isFilled() {
        return (destination!=null && destination.getCoord()!=null);
    }

    @Override
    public boolean doesDamage() {
        return false;
    }
}
