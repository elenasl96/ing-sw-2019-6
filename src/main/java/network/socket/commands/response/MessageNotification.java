package network.socket.commands.response;

import model.clientRoom.User;
import network.socket.ClientContext;
import model.clientRoom.Message;
import network.socket.commands.Response;
import network.socket.commands.ResponseHandler;

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
