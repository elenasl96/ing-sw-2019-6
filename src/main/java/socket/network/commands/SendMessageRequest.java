package socket.network.commands;

import socket.model.Message;

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
