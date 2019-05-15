package model.moves;

import model.Player;
import model.PlayerBoard;
import java.util.List;

public abstract class Target{
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

    public void addDamages(Player playerDamaging, int damages, int groupId){
        for(PlayerBoard b : this.getPlayerBoard(groupId)){
            b.addDamage(playerDamaging, damages);
        }
    }
}
