package model.room;

import model.Player;

import java.io.Serializable;

public class Update implements Serializable {
    private Player player;

    public Update(Player player){
        this.player = player;
    }

    public Player getPlayer(){
        return this.player;
    }
}
