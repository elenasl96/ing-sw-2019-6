package network.socket.commands.request;

import model.room.Command;
import network.socket.commands.Request;
import network.socket.commands.RequestHandler;
import network.socket.commands.Response;

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
