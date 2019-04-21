package network.socket.commands.response;

import network.socket.commands.ResponseHandler;
import network.socket.commands.Response;

public class SituationViewerResponse implements Response {
    public final String situation;

    public SituationViewerResponse(String situation) {
        this.situation = situation;
    }
    @Override
    public void handle(ResponseHandler handler) {
        handler.handle(this);
    }
}
