package network.commands.response;

import model.room.Group;
import network.commands.Response;
import network.commands.ResponseHandler;

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
