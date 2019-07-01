package network.commands.response;

import network.commands.Response;
import network.commands.ResponseHandler;

/**
 *
 */
public class RejoiningResponse implements Response {
    @Override
    public void handle(ResponseHandler handler) {
        handler.handle(this);
    }
}
