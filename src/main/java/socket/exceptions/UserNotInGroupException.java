package socket.exceptions;


import socket.model.Group;
import socket.model.User;

public class UserNotInGroupException extends RuntimeException {

    private final User user;
    private final Group group;

    public UserNotInGroupException(User user, Group group) {
        this.user = user;
        this.group = group;
    }

    @Override
    public String getMessage() {
        String message = "User not in group: ";
        return message + user.getUsername() + " <> " + group.getName();
    }

}
