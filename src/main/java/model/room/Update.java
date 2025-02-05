package model.room;

import model.Player;

import java.io.Serializable;

public class Update implements Serializable {
    /**
     * If this parameter is set to true, the Player is send to the ClientController
     * To be updated in the ClientContext
     */
    private boolean playerChanges;
    private Player player;
    private String string;
    /**
     * both data and move are GUI reading only content
     */
    private String move;
    private String data;

    /**
     * @param player playerChanges is automatically false
     */
    public Update(Player player){
        this.player = player;
        this.playerChanges = false;
    }

    /**
     * @param string playerChanges is automatically false
     */
    public Update(String string){
        this.string = string;
        this.playerChanges = false;
    }

    public Update(String string, String move){
        this.string = string;
        this.move = move;
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

    public String getMove() {
        return move;
    }

    public void setMove(String move) {
        this.move = move;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return string;
    }
}