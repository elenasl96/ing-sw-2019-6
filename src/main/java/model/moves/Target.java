package model.moves;

import model.Player;
import model.PlayerBoard;
import model.enums.TargetType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public static List<Target> addTargetList(Target targets){
        return Stream.of(targets).collect(Collectors.toCollection(ArrayList::new));
    }
}
