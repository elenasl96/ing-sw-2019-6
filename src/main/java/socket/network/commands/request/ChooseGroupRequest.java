package socket.network.commands.request;

import socket.network.commands.Request;
import socket.network.commands.RequestHandler;
import socket.network.commands.Response;

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
