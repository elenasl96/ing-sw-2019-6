package network.commands.request;

import network.commands.Request;
import network.commands.RequestHandler;
import network.commands.Response;

public class PossibleMovesRequest implements Request {
    @Override
    public Response handle(RequestHandler handler) {
        return handler.handle(this);
    }
}