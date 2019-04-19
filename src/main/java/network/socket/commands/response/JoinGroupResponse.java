package network.socket.commands.response;

import model.clientRoom.Group;
import network.socket.commands.Response;
import network.socket.commands.ResponseHandler;

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
