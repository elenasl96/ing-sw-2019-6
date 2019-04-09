package socket.network.commands;

import socket.ClientContext;
import socket.model.Message;
import socket.model.User;

public class MessageNotification implements Response {
    public final Message message;

    public MessageNotification(Message message) {
        this.message = message;
    }

    @Override
    public void handle(ResponseHandler handler) {
        User userToNotify = ClientContext.get().getCurrentUser();
        userToNotify.receiveMessage(message);
    }
}
