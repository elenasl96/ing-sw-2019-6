package model.moves;

import model.Player;
import model.enums.TargetType;
import model.exception.NotExistingPositionException;
import model.exception.NotExistingTargetException;
import model.field.Square;
import model.room.Update;

import java.io.Serializable;
import java.util.List;

/**
 * The target can either be of a shooting or a powerup move,
 * implemented by Room, Square and Player
 */
public abstract class Target implements Serializable {
    private TargetType targetType;
    private TargetType targetState;
    private Integer minDistance;
    private Integer maxDistance;

    public Target(){
    }

    public Target(TargetType targetType, TargetType targetState, Integer minDistance, Integer maxDistance){
        this.targetType = targetType;
        this.targetState = targetState;
        this.minDistance = minDistance;
        this.maxDistance = maxDistance;
    }

    public abstract void receiveUpdate(Update update);

    public TargetType getTargetType() {
        return targetType;
    }

    public Integer getMinDistance() {
        return minDistance;
    }

    public Integer getMaxDistance() {
        return maxDistance;
    }


    public abstract Square getCurrentPosition() throws NotExistingPositionException;

    public abstract Target findRealTarget(String inputName, int groupID) throws NotExistingTargetException;

    //Checks
    public abstract boolean canBeSeen(Player player, int groupID) throws NotExistingPositionException;

    public abstract boolean isFilled();

    public abstract boolean sameAsMe(int groupID);

    //Fill target for shooting
    public abstract String getFieldsToFill();

    public abstract void setFieldsToFill(String input, int groupID) throws NotExistingTargetException, NotExistingPositionException;

    public abstract Target fillFields(int groupID) throws NotExistingTargetException;

    //Effects of shooting
    public abstract void addMarks(Player playerMarking, int groupID, int nMarks) throws NotExistingPositionException;

    public abstract void addDamages(Player playerDamaging, int damages, int groupId) throws NotExistingPositionException;

    public abstract void setMine(int groupID) throws NotExistingPositionException;

    public abstract List<Target> findAllTargets(Target target, int groupID);
}
