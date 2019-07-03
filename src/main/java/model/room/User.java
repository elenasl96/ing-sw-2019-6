package model.room;

import model.Player;
import model.enums.Character;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class User implements Serializable, Comparable<User> {

    private String username;
    /**
     * PATTERN OBSERVER
     */
    private transient List<ModelObserver> updateObservers = new LinkedList<>();

    private static int uniqueUserID = 0;

    private int userID;

    private Player player;

    private Character character = Character.NOT_ASSIGNED;

    public User(String username) {
        super();
        userID = uniqueUserID;
        this.username = username;
        this.updateObservers = new LinkedList<>();
        uniqueUserID++;
        this.player = new Player(this);
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    public Character getCharacter() {
        return character;
    }

    public String getUsername() {
        return username;
    }

    public int getUserID(){
        return this.userID;
    }

    /**
     * @param observer adds the observer to this user's observers
     */
    public void listenToMessages(ModelObserver observer) {
        if(this.updateObservers == null)
            this.updateObservers = new LinkedList<>();
        updateObservers.add(observer);
    }

    /**
     * Synchronized to avoid the sending of concurrent updates in the wrong order
     * @param update sends the update directly to this user
     */
    public synchronized void receiveUpdate(Update update){
        //The user's observers are only his specific SocketClientHandler and ViewClient
        for (ModelObserver observer : updateObservers) {
            if(observer != null)
                observer.onUpdate(update);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return username != null ? username.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "@" + username;
    }

    @Override
    public int compareTo(@NotNull User u) {
        return Integer.compare(this.getUserID(), u.getUserID());
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public List<ModelObserver> getUpdateObservers() {
        return updateObservers;
    }

    /*
    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        // upon deserialization, observers are reset
        updateObservers = new LinkedList<>();
    }

     */

}
