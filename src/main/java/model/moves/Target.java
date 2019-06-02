package model.moves;

import model.Player;
import model.PlayerBoard;
import model.enums.TargetType;

import java.io.Serializable;
import java.util.List;

public abstract class Target implements Serializable {
    TargetType targetType;
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
        for(PlayerBoard b : this.getPlayerBoards(groupId)){
            b.addDamage(playerDamaging, damages);
        }
    }

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

}
