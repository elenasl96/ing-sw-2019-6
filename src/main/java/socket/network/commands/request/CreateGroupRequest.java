package socket.network.commands.request;

import socket.network.commands.Request;
import socket.network.commands.RequestHandler;
import socket.network.commands.Response;

public class CreateGroupRequest implements Request {
    @Override
    public Response handle(RequestHandler handler) {
        return handler.handle(this);
    }
}
