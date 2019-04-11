package socket.network.commands;

import socket.model.Group;

public class FullGroupResponse implements Response {
    public final Group group;

    public FullGroupResponse(Group group) {
        this.group = group;
    }

    @Override
    public void handle(ResponseHandler handler) {
        handler.handle(this);
    }
}
