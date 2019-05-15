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

    /**
     *  a New game is added to the gameList:
     *      when the group is created, it adds a new setGame to the list, being in the situation that
     *      get(groupID) gets its specific game.
     */
    public synchronized void createGame(){
        this.games.add(new Game());
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
