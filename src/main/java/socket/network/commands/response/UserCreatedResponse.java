package socket.network.commands.response;

import socket.model.User;
import socket.network.commands.Response;
import socket.network.commands.ResponseHandler;

public class UserCreatedResponse implements Response {
    public final User user;

    public UserCreatedResponse(User user) {
        this.user = user;
    }

    @Override
    public void handle(ResponseHandler handler) {
        handler.handle(this);
    }
}
