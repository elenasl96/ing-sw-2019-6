package network.socket;


import model.clientRoom.Group;
import model.clientRoom.User;

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
    private boolean status;
    private ClientContext() {
    }

    public static synchronized ClientContext get() {
        if (instance == null) {
            instance = new ClientContext();
        }

        return instance;
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

    public synchronized boolean isStatus(){
        return status;
    }

    public synchronized void setStatus(boolean status) {
        this.status = status;
    }
}
