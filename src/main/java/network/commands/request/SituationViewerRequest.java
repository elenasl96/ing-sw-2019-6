package network.commands.request;

import network.commands.Request;
import network.commands.RequestHandler;
import network.commands.Response;

//TODO javadoc
public class SituationViewerRequest implements Request {
    public SituationViewerRequest(){
    //empty constructor
    }
    @Override
    public Response handle(RequestHandler handler) {
        return handler.handle(this);
    }
}
