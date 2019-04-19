package network.socket.commands.request;

import model.clientRoom.Message;
import network.socket.commands.Request;
import network.socket.commands.RequestHandler;
import network.socket.commands.Response;

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
