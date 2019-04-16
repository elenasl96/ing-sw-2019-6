package socket.model;

import controller.GameController;
import controller.TimerController;
import model.Game;
import socket.exceptions.FullGroupException;
import socket.exceptions.UserNotInGroupException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.*;

public class Group implements Serializable {
    private transient Game gameGroup;
    private static int uniqueGroupID = 0;
    private int groupID;
    private String groupName;
    private Set<User> users = new HashSet<>();
    private List<Message> messages = new LinkedList<>();
    private transient List<GroupChangeListener> listeners = new LinkedList<>();
    private boolean full = false;
    private int skullNumber;
    private int fieldNumber;
    private TimerController timerController;

    public Group(int skullNumber) {
        super();
        this.skullNumber = skullNumber;
        groupID = uniqueGroupID;
        groupName = "group" + groupID;
        uniqueGroupID++;
    }

    public void createGame(){
        this.setFull();
        this.gameGroup = new Game(skullNumber, fieldNumber);
        new GameController(new LinkedList<>(users));
        for(GroupChangeListener listener : listeners) {
            listener.onStart();
        }
    }

    public void sendMessage(Message message) throws UserNotInGroupException {
        checkUserInGroup(message.getSender());
        messages.add(message);

        for(User user: users) {
            user.receiveMessage(message);
        }
    }

    public void join(User user) throws FullGroupException {
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
        if (this.size() >= 5) return true;
        else return false;
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

    public void setTimerController(TimerController timerController){this.timerController = timerController;}
}
