package network.commands.response;


import network.ClientContext;
import model.room.Group;
import model.room.User;
import network.commands.Response;
import network.commands.ResponseHandler;

/**
 * Sent when a user joins or leaves a group
 */
public class GroupChangeNotification implements Response {
    private final boolean inOut;
    public final User user;

    public GroupChangeNotification(boolean inOut, User user) {
        this.inOut = inOut;
        this.user = user;
    }

    //auto handled
    @Override
    public void handle(ResponseHandler handler) {
        Group currentGroup = ClientContext.get().getCurrentGroup();
        if (inOut) {
                currentGroup.join(user);
        } else {
            currentGroup.leave(user);
        }
    }
}
