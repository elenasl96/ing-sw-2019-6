package model.room;

import model.Game;
import model.GameContext;
import model.enums.Character;
import network.exceptions.UserNotInGroupException;
import network.exceptions.FullGroupException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.*;

public class Group implements Serializable {
    //Group variables
    private static int uniqueGroupID = 0;
    private int groupID;
    private String groupName;

    private List<User> users = new ArrayList<>();
    private transient List<ModelObserver> listeners = new LinkedList<>();
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

    public static void resetGroupID() {
        uniqueGroupID = 0;
    }

    public void sendUpdate(Update update){
        for(User user: users) {
            user.receiveUpdate(update);
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
        for(ModelObserver listener : listeners)
            listener.onJoin(user);
    }

    public void leave(User user){
        checkUserInGroup(user);
        users.remove(user);

        for(ModelObserver listener : listeners)
            listener.onLeave(user);

    }

    public void observe(ModelObserver listener) {
        listeners.add(listener);
    }

    private void checkUserInGroup(User user){
        if (!users.contains(user)) {
            throw new UserNotInGroupException(user, this);
        }
    }

    public int size() {
        return users.size();
    }

    public List<User> users() {
        return this.users;
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
                situation = situation.concat(u.toString()+", ");
        }
        return situation;
    }
    public int getGroupID(){return this.groupID;}

    public void createGame() {
        this.setFull();
        //Makes every listener of this group an Observer of the game
        GameContext.get().createGame(this.groupID);
        for(ModelObserver listener : listeners){
            GameContext.get().getGame(this.getGroupID()).addObserverGame(listener);
        }
        GameContext.get().getGame(this.groupID).setGame(skullNumber, fieldNumber, users);
        //Fill the squares
        GameContext.get().getGame(this.getGroupID()).getBoard().getField().getSquares()
                .forEach(square->
                    square.setGrabbable(GameContext.get().getGame(this.getGroupID()).getBoard())
        );
        //Triggers onStart in the GroupChangeListeners
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
        for (ModelObserver listener : listeners) {
            listener.onStart();
        }
    }

    public Game getGame(){
        return GameContext.get().getGame(this.getGroupID());
    }

    public List<User> getUsers(){
        return this.users;
    }
}
