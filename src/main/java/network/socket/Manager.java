package network.socket;

import model.clientRoom.UserManager;
import network.networkExceptions.InvalidUsernameException;
import network.networkExceptions.InvalidGroupNumberException;
import model.clientRoom.Group;
import model.clientRoom.User;

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
        defaultGroup = createGroup(5, 2);
    }

    public static synchronized Manager get() {
        if (instance == null) {
            instance = new Manager();
        }

        return instance;
    }

    public synchronized Group getGroup(int groupID) throws InvalidGroupNumberException {
        for(Group g : groups){
            if(g.getGroupID() == groupID){
                return g;
            }
        }
        throw new InvalidGroupNumberException("There's no group"+groupID);
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

    public synchronized Group createGroup(int skullNumber, int fieldNumber) {
        Group group = new Group(skullNumber, fieldNumber);
        groups.add(group);
        UserManager serverUser = new UserManager("Server" + group.getGroupID());
        group.join(serverUser);
        serverUser.createTimerController(group);
        return group;
    }

    public synchronized User createUser(String name) throws InvalidUsernameException {
        User user = new User(name);

        if (users.contains(user) || user.getUsername().contains("Server")||user.getUsername().contains("server")) {
            throw new InvalidUsernameException("Invalid username: " + name);
        }

        users.add(user);
        return user;
    }

    public synchronized void updateGroupSituation(){
        String situation = "";
        for(Group g : groups){
            situation = situation.concat(g.toString());
        }
        this.groupSituation = situation;
    }
    public synchronized String getGroupSituation(){return this.groupSituation;}
}
