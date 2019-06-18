package model.moves;

import model.Player;
import model.enums.TargetType;
import model.field.Square;
import model.room.Update;

import java.io.Serializable;

//TODO javadoc
public abstract class Target implements Serializable {
    private TargetType targetType;
    private Integer minDistance;
    private Integer maxDistance;

    public Target(){
    }

    public Target(TargetType targetType, Integer minDistance, Integer maxDistance){
        this.targetType = targetType;
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


    public abstract Square getCurrentPosition();

    public abstract Target findRealTarget(String inputName, int groupID);

    //Checks
    public abstract boolean canBeSeen(Player player, int groupID);

    public abstract boolean isFilled();

    public abstract boolean sameAsMe(int groupID);

    //Fill target for shooting
    public abstract String getFieldsToFill();

    public abstract void setFieldsToFill(String input, int groupID);

    public abstract Target fillFields(int groupID);

    //Effects of shooting
    public abstract void addMarks(Player playerMarking, int groupID, int nMarks);

    public abstract void addDamages(Player playerDamaging, int damages, int groupId);
}
