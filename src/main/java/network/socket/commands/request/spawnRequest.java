package network.socket.commands.request;

import network.socket.commands.Request;
import network.socket.commands.RequestHandler;
import network.socket.commands.Response;

public class spawnRequest implements Request {
    public spawnRequest(String content) {

    }

    @Override
    public Response handle(RequestHandler handler) {
        return null;
    }
}
