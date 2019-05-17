package network.socket;

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
 * It is still a piece of the model that traces the instances available.
 */
public class Manager {
    private static Manager instance;
    private List<Group> groups = new ArrayList<>();
    private List<User> users = new ArrayList<>();
    private String groupSituation;

    private Manager() {
        // create one group by default
        createGroup(5, 2);
    }

    public static synchronized Manager get() {
        if (instance == null) {
            instance = new Manager();
        }

        return instance;
    }

    public synchronized Group getGroup(int groupID) {
        for(Group g : groups){
            if(g.getGroupID() == groupID){
                return g;
            }
        }
        throw new InvalidGroupNumberException("There's no group"+groupID);
    }

    synchronized Group createGroup(int skullNumber, int fieldNumber) {
        Group group = new Group(skullNumber, fieldNumber);
        GameContext.get().getGames().add(new Game());
        groups.add(group);
        TimerController.get().addGroup(group);
        return group;
    }

    public synchronized User createUser(String name) throws InvalidUsernameException {
        User user = new User(name);

        if (users.contains(user)) {
            throw new InvalidUsernameException("Invalid username: " + name);
        }

        users.add(user);
        return user;
    }

    synchronized void updateGroupSituation(){
        String situation = "";
        for(Group g : groups){
            situation = situation.concat(g.toString());
        }
        this.groupSituation = situation;
    }

    synchronized String getGroupSituation(){return this.groupSituation;}

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
}
