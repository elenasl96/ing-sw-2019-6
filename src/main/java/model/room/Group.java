package model.room;

import model.Game;
import model.GameContext;
import model.enums.Character;
import model.exception.NotExistingFieldException;
import network.Manager;
import network.exceptions.UserNotInGroupException;
import network.exceptions.FullGroupException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.*;

/**
 * The group of users that are playing the game
 * Every user chooses a group straight after the username
 * @see User
 * @see Game
 */
public class Group implements Serializable {
    /**
     * Every group has one unique group ID, that grows every time a new group is created
     * @see this#Group(int, int)
     */
    private static int uniqueGroupID = 0;
    /**
     * The ID of the specific group object, unique identifier
     */
    private int groupID;
    /**
     * It's "Group" followed by the groupID, is used to print the group situation
     * to the new user when choosing one
     */
    private String groupName;
    /**
     * The list of users in the group
     */
    private List<User> users = new ArrayList<>();
    /**
     * The observers of this group, specifically the View (Client Side) and the ClientHandler
     * (ServerSide)
     */
    private transient List<ModelObserver> listeners = new LinkedList<>();
    /**
     * The group can be full either if the game already started or if it reaches 5 users,
     * meaning the game starts automatically
     */
    private boolean full;
    /**
     * The field of the game, chose by the user that creates the group
     */
    private int fieldNumber;
    /**
     * The number of skulls of the game, chose by the user that creates the group
     */
    private int skullNumber;

    /**
     * The constructor creates a default group with growing unique group ID and full set false
     * @param skullNumber   the number of skulls that will be used in the game
     * @param fieldNumber   the number of the field used in the game
     */
    public Group(int skullNumber, int fieldNumber) {
        super();
        groupID = uniqueGroupID;
        groupName = "group" + groupID;
        uniqueGroupID++;
        this.skullNumber = skullNumber;
        this.fieldNumber = fieldNumber;
        this.full = false;
    }

    /**
     * Resets the unique Group ID o restart the series of groups from 0
     * Used in the reset method of manager and game controller
     * @see Manager#get()#reset()
     */
    public static void resetGroupID() {
        uniqueGroupID = 0;
    }

    /**
     * Sends update to all the observers
     * @param update    an Update object containing text or a player
     */
    public void sendUpdate(Update update){
        for(User user: users) {
            user.receiveUpdate(update);
        }
    }

    /**
     * Invoked when a user joins a group.
     * Notices the observers
     * @param user  the user joining
     * @throws FullGroupException if there were already 5 components or the game has started already (full group)
     * @see ModelObserver#onJoin(User)
     */
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

    /**
     * Invoked when a user leaves a group.
     * Notices the observers
     * @param user  the user joining
     * @throws UserNotInGroupException if the user wasn't in the group
     * @see ModelObserver#onLeave(User)
     */
    public void leave(User user){
        checkUserInGroup(user);
        for(ModelObserver listener : listeners)
            listener.onLeave(user);
    }

    /**
     * Add a ModelObserver to the list of listeners
     * Upon deserialization, observers are reset.
     * @param listener the modelObserver of the user joining the group
     */
    public void observe(ModelObserver listener) {
        listeners.add(listener);
    }

    private void checkUserInGroup(User user){
        if (!users.contains(user)) {
            throw new UserNotInGroupException(user, this);
        }
    }

    /**
     * @return the number of users in the group
     */
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

    /**
     * Creates a new Game: sets the group full, adds every listener to the games listeners,
     * fills the square with grabbable content, triggers onStart in ModelObservers
     */
    public void createGame() throws NotExistingFieldException {
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
                    square.setGrabbable(this.getGroupID())
        );
        //Triggers onStart in the Listeners
        this.sendStartNotification();
    }

    /**
     * @param character checks if that character is already taken from another player
     * @return  true if it's taken, false if it's free. Does not assign anything
     */
    public Boolean characterIsTaken(Character character) {
        for(User u: users) {
            if(u.getCharacter() == character){
                return true;
            }
        }
        return false;
    }

    /**
     * Triggers onStart in ModelObservers
     * @see ModelObserver#onStart()
     */
    public synchronized void sendStartNotification() {
        //Removing the view CLI from listening if there's also the GUI
        if(listeners.size()==2){
            listeners.remove(0);
        }
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

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        // upon deserialization, observers are reset
        listeners = new LinkedList<>();
    }

}
