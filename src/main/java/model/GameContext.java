package model;


import model.room.Group;
import model.room.User;

import java.util.ArrayList;

/**
 * SINGLETON (CLIENT SIDE)
 *
 * Context used at any client to record current user and group
 */
public class GameContext {

    private static GameContext instance;
    private ArrayList<Game> games;
    private GameContext() {
    }

    public static synchronized GameContext get() {
        if (instance == null) {
            instance = new GameContext();
        }
        return instance;
    }

    synchronized Game getGame(int groupID){
        return games.get(groupID);
    }

    synchronized void createGame(){
        this.games.add(new Game());
    }

    public synchronized User getCurrentUser() {
        return currentUser;
    }

    synchronized void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public synchronized Group getCurrentGroup() {
        return currentGroup;
    }

    synchronized void setCurrentGroup(Group currentGroup) {
        this.currentGroup = currentGroup;
    }

    synchronized String getCurrentSituation() {
        return currentSituation;
    }

    synchronized void setCurrentSituation(String currentSituation) {
        this.currentSituation = currentSituation;
    }

    public void setPlayer(Player player) {
        this.currentPlayer = player;
    }

    Player getCurrentPlayer() {
        return currentPlayer;
    }

    void reset(){
        if(instance!= null){
            this.currentPlayer = null;
            this.currentGroup = null;
            this.currentSituation = null;
            this.currentUser = null;
        }
    }
}
