package network.socket.commands.response;


import network.socket.ClientContext;
import model.room.Group;
import model.room.User;
import network.socket.commands.Response;
import network.socket.commands.ResponseHandler;

public class GroupChangeNotification implements Response {
    public final boolean inOut;
    public final User user;

    public GroupChangeNotification(boolean inOut, User user) {
        this.inOut = inOut;
        this.user = user;
    }

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
