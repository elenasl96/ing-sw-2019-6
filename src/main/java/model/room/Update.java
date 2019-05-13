package model.room;

import model.Player;

import java.io.Serializable;

public class Update implements Serializable {
    private boolean playerChanges;
    private Player player;
    private int phaseID;
    private String string;

    public Update(Player player){
        this.player = player;
        this.phaseID = player.getPhase().getId();
        this.playerChanges = false;
    }

    public Update(String string){
        this.string = string;
        this.playerChanges = false;
    }

    public Update(Player player, boolean playerChanges){
        this.player = player;
        this.playerChanges = playerChanges;
    }

    public Update(Player player, boolean playerChanges, String string){
        this.player = player;
        this.phaseID = player.getPhase().getId();
        this.string = string;
        this.playerChanges = playerChanges;
    }

    public boolean isPlayerChanges(){
        return this.playerChanges;
    }

    public Player getPlayer(){
        return this.player;
    }

    public void setString(String string){
        this.string = string;
    }

    @Override
    public String toString() {
        return string;
    }
}
