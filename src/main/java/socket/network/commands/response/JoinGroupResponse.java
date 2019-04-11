package socket.network.commands.response;

import socket.model.Group;
import socket.network.commands.Response;
import socket.network.commands.ResponseHandler;

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
