package model.moves;

import model.Player;
import model.PlayerBoard;
import java.util.List;

public abstract class Target{
    private boolean canSee;
    private boolean cardinal;
    private Integer minDistance;
    private Integer maxDistance;

    public Target(){

    }

    public Target(boolean canSee, boolean cardinal, int minDistance, int maxDistance){
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
