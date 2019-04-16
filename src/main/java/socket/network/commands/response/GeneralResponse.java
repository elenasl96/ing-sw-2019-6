package socket.network.commands.response;

import socket.network.commands.Response;
import socket.network.commands.ResponseHandler;

public class GeneralResponse implements Response {
    public boolean status;

    public GeneralResponse(){this.status = true;}

    @Override
    public void handle(ResponseHandler handler) {
        handler.handle(this);
    }
}
