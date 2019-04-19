package model.clientRoom;

import model.Game;
import model.enums.Character;
import network.networkExceptions.UserNotInGroupException;
import network.networkExceptions.FullGroupException;

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
    private List<Message> messages = new LinkedList<>();
    private transient List<GroupChangeListener> listeners = new LinkedList<>();
    private boolean full = false;
    private int fieldNumber;
    private int skullNumber;

    public Group(int skullNumber, int fieldNumber) {
        super();
        groupID = uniqueGroupID;
        groupName = "group" + groupID;
        uniqueGroupID++;
        this.skullNumber = skullNumber;
        this.fieldNumber = fieldNumber;
    }

    public void sendMessage(Message message) throws UserNotInGroupException {
        checkUserInGroup(message.getSender());
        messages.add(message);

        for(User user: users) {
            user.receiveMessage(message);
        }
    }

    public void join(User user) throws FullGroupException {
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

    public void leave(User user) throws UserNotInGroupException {
        checkUserInGroup(user);
        users.remove(user);

        for(GroupChangeListener listener : listeners)
            listener.onLeave(user);

    }

    

    public void observe(GroupChangeListener listener) {
        listeners.add(listener);
    }

    private void checkUserInGroup(User user) throws UserNotInGroupException {
        if (!users.contains(user)) {
            throw new UserNotInGroupException(user, this);
        }
    }

    public List<Message> messages() {
        return new LinkedList<>(messages);
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

    public void setName(String name) {
        this.groupName = name;
    }

    public boolean in(User user) {return users.contains(user);}

    public boolean isFull() {
        return full;
    }

    public void setFull(){
        this.full = true;

    }

    public void setNotFull(){
        this.full = false;
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
        } situation = situation.concat("\n");
        return situation;
    }
    public int getGroupID(){return this.groupID;}

    public void setFieldNumber(int field) {
        this.fieldNumber = field;
    }

    public void createGame(User serverUser) {
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
}
