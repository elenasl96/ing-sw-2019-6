package model.room;

import model.Game;
import model.enums.Character;
import network.exceptions.UserNotInGroupException;
import network.exceptions.FullGroupException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.*;

public class Group implements Serializable {
    private static int uniqueGroupID = 0;
    private int groupID;
    private String groupName;
    private transient Game game;
    private Set<User> users = new HashSet<>();
    private transient List<GroupChangeListener> listeners = new LinkedList<>();
    private boolean full = false;
    protected int fieldNumber;
    protected int skullNumber;

    public Group(int skullNumber, int fieldNumber) {
        super();
        groupID = uniqueGroupID;
        groupName = "group" + groupID;
        uniqueGroupID++;
        this.skullNumber = skullNumber;
        this.fieldNumber = fieldNumber;
    }

    public void sendMessage(Message message){
        checkUserInGroup(message.getSender());

        for(User user: users) {
            user.receiveMessage(message);
        }
    }

    public void join(User user){
        if(this.size()==5){
            this.setFull();
        }
        if(this.isFull()) {
            throw new FullGroupException();
        }
        users.add(user);
        for(GroupChangeListener listener : listeners)
            listener.onJoin(user);
    }

    public void leave(User user){
        checkUserInGroup(user);
        users.remove(user);

        for(GroupChangeListener listener : listeners)
            listener.onLeave(user);

    }

    

    public void observe(GroupChangeListener listener) {
        listeners.add(listener);
    }

    private void checkUserInGroup(User user){
        if (!users.contains(user)) {
            throw new UserNotInGroupException(user, this);
        }
    }

    public int size() {
        return users.size()-1;
    }

    public Set<User> users() {
        return new HashSet<>(users);
    }
    

    public String getName() {
        return groupName;
    }

    public boolean in(User user) {return users.contains(user);}

    public boolean isFull() {
        return full;
    }

    public void setFull(){
        this.full = true;

    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        // upon deserialization, observers are reset
        listeners = new LinkedList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Group groupCast = (Group) o;

        return groupID == groupCast.groupID;
    }

    @Override
    public int hashCode() {
        return groupID;
    }

    @Override
    public String toString(){
        String situation = "";
        if(this.users == null) return "";
        else if(this.full) situation = situation.concat("Group "+this.groupID+" full\n");
        else {
            situation = situation.concat("Group "+this.groupID+" has "+this.size()+" players:\n");
            for(User u: this.users)
                if(!u.getUsername().contains("Server"))
                    situation = situation.concat(u.toString()+", ");
        }
        return situation;
    }
    public int getGroupID(){return this.groupID;}

    public void createGame() {
        this.setFull();
        this.game = new Game(skullNumber, fieldNumber);
        for(GroupChangeListener listener : listeners){
            game.addObserverGame((GameUpdateObserver) listener);
        }
        this.sendStartNotification();
    }

    public Boolean characterIsTaken(Character character) {
        for(User u: users) {
            if(u.getCharacter() == character){
                return true;
            }
        }
        return false;
    }

    public void sendStartNotification() {
        for (GroupChangeListener listener : listeners) {
            listener.onStart();
        }
    }

    public UserManager getServerUser(){
        for(User u: users){
            if(u.getUsername().contains("Server")){
                return (UserManager) u;
            }
        }
        return null;
    }
}
