package network.commands.request;

import network.commands.Request;
import network.commands.RequestHandler;
import network.commands.Response;

/**
 * Asks for the current groups situation
 */
public class SituationViewerRequest implements Request {

    @Override
    public Response handle(RequestHandler handler) {
        return handler.handle(this);
    }
}
