package model.moves;

import model.Player;
import model.PlayerBoard;
import model.enums.TargetType;
import model.field.Square;
import model.room.Update;

import java.io.Serializable;
import java.util.List;

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

    public abstract List<PlayerBoard> getPlayerBoards(int groupId);

    void addDamages(Player playerDamaging, int damages, int groupId){
        List<PlayerBoard> boards = this.getPlayerBoards(groupId);
        for(PlayerBoard b : boards){
            this.receiveUpdate(new Update("You received " + b.addDamage(playerDamaging, damages)+ " damages."));
        }
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

    public abstract String getFieldsToFill();

    public abstract boolean canBeSeen(Player player, int groupID);

    public abstract boolean isFilled();

    public abstract Square getCurrentPosition();

    public abstract Target findRealTarget(String inputName, int groupID);

    public abstract boolean sameAsMe(int groupID);

    public abstract void setFieldsToFill(String input, int groupID);

    public abstract Target fillFields(int groupID);
}
