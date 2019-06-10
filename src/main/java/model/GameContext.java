package model;


import java.util.ArrayList;
import java.util.List;

/**
 * SINGLETON (SERVER SIDE)
 *
 * Context used to record the games and access them in every group, server side (for protection)
 */
public class GameContext {

    private static GameContext instance;

    private List<Game> games;

    private GameContext() {
        this.games = new ArrayList<>();
    }

    public static synchronized GameContext get() {
        if (instance == null) {
            instance = new GameContext();
        }
        return instance;
    }

    public synchronized Game getGame(int groupID){
        return games.get(groupID);
    }

    public synchronized void createGame(int groupID){
        this.games.add(groupID, new Game());
    }

    public void reset(){
        if(instance!= null){
            this.games = null;
            this.games = new ArrayList<>();
        }
    }

    public List<Game> getGames() {
        return games;
    }
}
