package network.socket.commands.request;

import network.socket.commands.Request;
import network.socket.commands.RequestHandler;
import network.socket.commands.Response;

public class ChooseGroupRequest implements Request {

    public final int groupId;

    public ChooseGroupRequest(int id){
        this.groupId = id;
    }

    @Override
    public Response handle(RequestHandler handler) {
        return handler.handle(this);
    }
}
