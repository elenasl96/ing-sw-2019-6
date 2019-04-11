package socket.network.commands.request;

import socket.model.Message;
import socket.network.commands.Request;
import socket.network.commands.RequestHandler;
import socket.network.commands.Response;

public class SendMessageRequest implements Request {
    public final Message message;

    public SendMessageRequest(Message message) {
        this.message = message;
    }

    @Override
    public Response handle(RequestHandler handler) {
        return handler.handle(this);
    }
}
