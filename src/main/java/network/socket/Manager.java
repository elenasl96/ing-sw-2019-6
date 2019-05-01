package network.socket;

import model.room.UserManager;
import network.exceptions.InvalidUsernameException;
import network.exceptions.InvalidGroupNumberException;
import model.room.Group;
import model.room.User;

import java.util.HashSet;
import java.util.Set;

/**
 * SINGLETON
 * It is still a piece of the model that traces the instances available.
 */
public class Manager {
    private static Manager instance;
    private Set<Group> groups = new HashSet<>();
    private Set<User> users = new HashSet<>();
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

    synchronized Group getGroup(int groupID) {
        for(Group g : groups){
            if(g.getGroupID() == groupID){
                return g;
            }
        }
        throw new InvalidGroupNumberException("There's no group"+groupID);
    }

    synchronized Group createGroup(int skullNumber, int fieldNumber) {
        Group group = new Group(skullNumber, fieldNumber);
        groups.add(group);
        UserManager serverUser = new UserManager("Server" + group.getGroupID());
        group.join(serverUser);
        serverUser.createTimerController(group);
        return group;
    }

    synchronized User createUser(String name) throws InvalidUsernameException {
        User user = new User(name);

        if (users.contains(user) || user.getUsername().contains("Server")||user.getUsername().contains("server")) {
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

    synchronized void setTimer(Group group){
        group.getServerUser().playTimer();
    }

    void reset(){
        //Restoring default values
        this.groups = null;
        this.groups = new HashSet<>();
        Group.resetGroupID();
        createGroup(5, 2);
        this.users = null;
        this.users = new HashSet<>();
    }

    Set<Group> getGroups() {
        return groups;
    }

    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
