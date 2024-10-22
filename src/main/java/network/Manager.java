package network;

import controller.TimerController;
import model.Game;
import model.GameContext;
import network.exceptions.InvalidUsernameException;
import network.exceptions.InvalidGroupNumberException;
import model.room.Group;
import model.room.User;

import java.util.ArrayList;
import java.util.List;

/**
 * SINGLETON
 * It is a piece of the model that traces the instances available.
 * @see Group
 * @see User
 * @see InvalidUsernameException
 * @see InvalidGroupNumberException
 * @see Game
 * @see GameContext
 * @see TimerController
 */
public class Manager {

    private static Manager instance;
    private List<Group> groups = new ArrayList<>();
    private List<User> users = new ArrayList<>();
    /**
     * A String describing the current groups and users situation to be displayed when a
     * new user logs in
     */
    private String groupSituation;

    private Manager() {
        // create one group by default
        createGroup(5, 2);
    }

    /**
     * Gets an instance of the Manager, creates one if not present
     * @return the instance (singleton) of the Manager
     */
    public static synchronized Manager get() {
        if (instance == null) {
            instance = new Manager();
        }

        return instance;
    }

    /**
     * Gets the determined group, selected by groupID
     * @param groupID the groupID of the group you want to get
     * @return the group that has the corresponding groupID
     * @throws InvalidGroupNumberException if you ask for a group that is not stored in Manager
     */
    public synchronized Group getGroup(int groupID) {
        for(Group g : groups){
            if(g.getGroupID() == groupID){
                return g;
            }
        }
        throw new InvalidGroupNumberException("There's no group"+groupID);
    }

    /**
     * @param skullNumber   the skullNumber of the game
     * @param fieldNumber   the field number of the game (1,2,3)
     * @return  the created group
     */
    public synchronized Group createGroup(int skullNumber, int fieldNumber) {
        Group group = new Group(skullNumber, fieldNumber);
        GameContext.get().getGames().add(new Game());
        groups.add(group);
        TimerController.get().addGroup(group);
        return group;
    }

    /**
     * @param name  the username
     * @return the created user
     * @throws InvalidUsernameException if the username already exists
     */
    public synchronized User createUser(String name) throws InvalidUsernameException {
        User user = new User(name);

        if (users.contains(user)) {
            throw new InvalidUsernameException("Already Connected: " + name);
        }

        users.add(user);
        return user;
    }

    /**
     * Updates the groupSituation every time a new user logs in and asks for it
     */
    public synchronized void updateGroupSituation(){
        String situation = "";
        for(Group g : groups){
            situation = situation.concat(g.toString());
        }
        this.groupSituation = situation;
    }

    public synchronized String getGroupSituation(){return this.groupSituation;}

    /**
     * Completely resets the Manager to 0 groups and 0 users, without the default group
     */
    public void reset(){
        if(instance!=null) {
            //Restoring default values
            this.groups = null;
            this.groups = new ArrayList<>();
            Group.resetGroupID();
            createGroup(5, 2);
            this.users = null;
            this.users = new ArrayList<>();
        }
    }


    public List<Group> getGroups() {
        return groups;
    }

    public List<User> getUsers() {
        return users;
    }

    /**
     * Does a rapid check over the users to find the one with that username.
     * Doesn't throw exception since it is used only server side in a way that makes it impossible to throw exception
     * @param username  the username that was asked for
     * @return  the user found
     */
    public User getUser(String username) {
        for(User u : this.users){
            if(u.getUsername().equals(username)){
                return u;
            }
        } return null;
    }
}
