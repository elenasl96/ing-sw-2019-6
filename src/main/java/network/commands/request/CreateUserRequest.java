package network.commands.request;

import network.commands.Request;
import network.commands.RequestHandler;
import network.commands.Response;

/**
 * Sent when asking to create a new user. Is the first request that is made by the user
 */
public class CreateUserRequest implements Request {
    public final String username;

    public CreateUserRequest(String username) {
        this.username = username;
    }

    @Override
    public Response handle(RequestHandler handler) {
        return handler.handle(this);
    }
}
