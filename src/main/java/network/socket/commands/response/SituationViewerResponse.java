package network.socket.commands.response;

import network.socket.commands.ResponseHandler;
import network.socket.commands.Response;
import model.enums.StatusCode;

public class SituationViewerResponse implements Response {
    public final String situation;
    public final StatusCode status;

    public SituationViewerResponse(String situation, StatusCode status) {
        this.situation = situation;
        this.status = status;
    }


    @Override
    public void handle(ResponseHandler handler) {
        handler.handle(this);
    }
}
