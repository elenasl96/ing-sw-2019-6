package socket;

import socket.exceptions.InvalidUsernameException;
import socket.model.Group;
import socket.model.User;

import java.util.HashSet;
import java.util.Set;

/**
 * SINGLETON
 * It is still a piece of the model that traces the instances available.
 */
public class Manager {
    private static Manager instance;
    private final Group defaultGroup;
    private Set<Group> groups = new HashSet<>();
    private Set<User> users = new HashSet<>();
    private String groupSituation;

    private Manager() {
        // create one group by default
        defaultGroup = createGroup();
    }

    public static synchronized Manager get() {
        if (instance == null) {
            instance = new Manager();
        }

        return instance;
    }
    public synchronized Group getGroup(int groupID){
        for(Group g : groups){
            if(g.getGroupID() == groupID){
                return g;
            }
        }
        return defaultGroup;
    }

    public synchronized Group getDefaultGroup() {
        return defaultGroup;
    }

    public synchronized Set<Group> groups() {
        return new HashSet<>(groups);
    }

    public synchronized Set<User> users() {
        return new HashSet<>(users);
    }

    public synchronized Group createGroup() {
        Group group = new Group();
        groups.add(group);
        return group;
    }

    public synchronized User createUser(String name) throws InvalidUsernameException {
        User user = new User(name);

        if (users.contains(user)) {
            throw new InvalidUsernameException("Username already in use: " + name);
        }

        users.add(user);
        return user;
    }

    public void updateGroupSituation(){
        String situation = "";
        for(Group g : groups){
            situation = situation.concat(g.toString());
        }
        this.groupSituation = situation;
    }
    public String getGroupSituation(){return this.groupSituation;}
}
