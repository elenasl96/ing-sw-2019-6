package network.socket.commands.request;

import network.socket.commands.Request;
import network.socket.commands.RequestHandler;
import network.socket.commands.Response;

public class SituationViewerRequest implements Request {
    public SituationViewerRequest(){}
    @Override
    public Response handle(RequestHandler handler) {
        return handler.handle(this);
    }
}