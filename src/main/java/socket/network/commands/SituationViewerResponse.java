package socket.network.commands;

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
