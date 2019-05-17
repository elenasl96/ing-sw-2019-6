package model.moves;

import model.Player;
import model.PlayerBoard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class Target implements Serializable {
    private Boolean canSee;
    private Boolean cardinal;
    private Integer minDistance;
    private Integer maxDistance;

    public Target(){

    }

    public Target(Boolean canSee, Boolean cardinal, Integer minDistance, Integer maxDistance){
        this.canSee = canSee;
        this.cardinal = cardinal;
        this.minDistance = minDistance;
        this.maxDistance = maxDistance;
    }

    public abstract List<PlayerBoard> getPlayerBoard(int groupId);

    void addDamages(Player playerDamaging, int damages, int groupId){
        for(PlayerBoard b : this.getPlayerBoard(groupId)){
            b.addDamage(playerDamaging, damages);
        }
    }

    public static List<Target> addTargetList(Target targets){
        return Stream.of(targets).collect(Collectors.toCollection(ArrayList::new));
    }
}
