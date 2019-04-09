package socket.network.commands;

public class SituationViewerRequest implements Request{
    public SituationViewerRequest(){}
    @Override
    public Response handle(RequestHandler handler) {
        return handler.handle(this);
    }
}
