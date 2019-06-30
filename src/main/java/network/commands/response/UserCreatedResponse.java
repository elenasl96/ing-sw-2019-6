package network.commands.response;

import model.room.User;
import network.commands.Response;
import network.commands.ResponseHandler;

/**
 * Sent as an update when the user is crated
 * @see network.commands.request.CreateUserRequest
 */
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
