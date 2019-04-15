package socket.network.commands.request;

import socket.model.Command;
import socket.network.commands.Request;
import socket.network.commands.RequestHandler;
import socket.network.commands.Response;

public class SendCommandRequest implements Request {
    public final Command command;

    public SendCommandRequest(Command command) {
        this.command = command;
    }

    @Override
    public Response handle(RequestHandler handler) {
        return handler.handle(this);
    }
}
