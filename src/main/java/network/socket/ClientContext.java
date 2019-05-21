package network.socket;


import model.Player;
import model.room.Group;
import model.room.User;

/**
 * SINGLETON (CLIENT SIDE)
 *
 * Context used at any client to record current user and group
 */
public class ClientContext {

    private static ClientContext instance;
    private User currentUser;
    private Group currentGroup;
    private String currentSituation;
    private Player currentPlayer;
    private ClientContext() {
    }

    public static synchronized ClientContext get() {
        if (instance == null) {
            instance = new ClientContext();
        }

        return instance;
    }

    public synchronized void createPlayer(){
        this.currentPlayer = new Player();
    }

    public synchronized User getCurrentUser() {
        return currentUser;
    }

    public synchronized void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public synchronized Group getCurrentGroup() {
        return currentGroup;
    }

    public synchronized void setCurrentGroup(Group currentGroup) {
        this.currentGroup = currentGroup;
    }

    public synchronized String getCurrentSituation() {
        return currentSituation;
    }

    public synchronized void setCurrentSituation(String currentSituation) {
        this.currentSituation = currentSituation;
    }

    public void setPlayer(Player player) {
        this.currentPlayer = player;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void reset(){
        if(instance!= null){
            this.currentPlayer = null;
            this.currentGroup = null;
            this.currentSituation = null;
            this.currentUser = null;
        }
    }
}
