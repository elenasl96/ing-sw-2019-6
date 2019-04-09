package socket.network.commands;

import chat.ClientContext;
import chat.model.Message;
import chat.model.User;

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
