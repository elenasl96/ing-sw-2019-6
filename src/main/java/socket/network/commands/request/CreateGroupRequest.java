package socket.network.commands.request;

import socket.network.commands.Request;
import socket.network.commands.RequestHandler;
import socket.network.commands.Response;

public class CreateGroupRequest implements Request {
    private final int skullNumber;

    public CreateGroupRequest(int skullNumber) {
        this.skullNumber = skullNumber;
    }

    public int getSkullNumber(){return this.skullNumber;}

    @Override
    public Response handle(RequestHandler handler) {
        return handler.handle(this);
    }
}
