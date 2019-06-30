package network.commands.request;

import network.commands.Request;
import network.commands.RequestHandler;
import network.commands.Response;

/**
 * Sent to ask for the possible moves a player can make
 */
public class PossibleMovesRequest implements Request {
    @Override
    public Response handle(RequestHandler handler) {
        return handler.handle(this);
    }
}
