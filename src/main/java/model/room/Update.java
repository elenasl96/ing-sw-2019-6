package model.room;

import model.Player;

import java.io.Serializable;

public class Update implements Serializable {
    public boolean playerChanges;
    private Player player;
    public StringBuilder string;

    public Update(Player player){
        this.player = player;
        this.playerChanges = false;
    }

    public Update(Player player, boolean playerChanges){
        this.player = player;
        this.playerChanges = playerChanges;
    }

    public boolean isPlayerChanges(){
        return this.playerChanges;
    }

    public Player getPlayer(){
        return this.player;
    }

    @Override
    public String toString() {
        return string.toString();
    }
}
