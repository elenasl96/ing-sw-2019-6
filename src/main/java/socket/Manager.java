package socket;

import controller.TimerController;
import socket.exceptions.FullGroupException;
import socket.exceptions.InvalidGroupNumberException;
import socket.exceptions.InvalidUsernameException;
import socket.model.Group;
import socket.model.User;
import socket.network.commands.StatusCode;
import socket.network.commands.response.TextResponse;

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

    public synchronized Group createGroup() {

        Group group = new Group();
        groups.add(group);
        User serverUser = new User("Server" + group.getGroupID());
        try {
            group.join(serverUser);
        } catch (FullGroupException e) {
            e.printStackTrace();
        }
        TimerController timerController = new TimerController(group, serverUser);
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

    public void updateGroupSituation(){
        String situation = "";
        for(Group g : groups){
            situation = situation.concat(g.toString());
        }
        this.groupSituation = situation;
    }
    public String getGroupSituation(){return this.groupSituation;}
}