package model.room;

import model.Player;
import model.enums.Character;
import model.enums.Phase;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class User implements Serializable, Comparable<User> {

    private String username;
    private transient List<MessageReceivedObserver> observers;
    private transient List<GameUpdateObserver> updateObservers;
    private static int uniqueUserID = 0;
    private int userID;
    private Character character = Character.NOT_ASSIGNED;

    public User(String username) {
        super();
        userID = uniqueUserID;
        this.username = username;
        this.observers = new LinkedList<>();
        this.updateObservers = new LinkedList<>();
        uniqueUserID++;
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

    public void setUsername(String username) {
        this.username = username;
    }

    public int getUserID(){
        return this.userID;
    }

    public void listenToMessages(MessageReceivedObserver observer) {
        observers.add(observer);
        updateObservers.add((GameUpdateObserver) observer);
    }

    public void receiveMessage(Message message) {
        for (MessageReceivedObserver observer : observers) {
            observer.onMessage(message);
        }
    }

    public void receiveUpdate(Update update){
        //The user's observers are only his specific ClientHandler and ViewClient
        for (GameUpdateObserver observer : updateObservers) {
            observer.onUpdate(update);
        }
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        // upon deserialization, observers are reset
        observers = new LinkedList<>();
        updateObservers = new LinkedList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return username != null ? username.equals(user.username) : user.username == null;
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
    } //TODO testing
}
