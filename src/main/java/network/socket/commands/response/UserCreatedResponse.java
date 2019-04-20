package network.socket.commands.response;

import model.room.User;
import network.socket.commands.Response;
import network.socket.commands.ResponseHandler;

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
