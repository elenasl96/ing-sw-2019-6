package network.commands.response;

import model.room.Group;
import network.commands.Response;
import network.commands.ResponseHandler;

/**
 * Sent after a ChooseGroupRequest when the user joins a group
 * @see network.commands.request.ChooseGroupRequest
 */
public class JoinGroupResponse implements Response {
    public final Group group;

    public JoinGroupResponse(Group group) {
        this.group = group;
    }

    @Override
    public void handle(ResponseHandler handler) {
        handler.handle(this);
    }
}
