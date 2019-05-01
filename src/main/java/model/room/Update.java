package model.room;

import model.Player;

import java.io.Serializable;

public class Update implements Serializable {
    public boolean playerChanges;
    private Player player;
    public String string;

    public Update(Player player){
        this.player = player;
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
