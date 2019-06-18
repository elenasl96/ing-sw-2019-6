package network.commands.response;

import network.commands.ResponseHandler;
import network.commands.Response;

//TODO javadoc
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
