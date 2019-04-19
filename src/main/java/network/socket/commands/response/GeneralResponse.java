package network.socket.commands.response;

import network.socket.commands.Response;
import network.socket.commands.ResponseHandler;

public class GeneralResponse implements Response {
    public boolean status;

    public GeneralResponse(boolean status){this.status = status;}

    @Override
    public void handle(ResponseHandler handler) {
        handler.handle(this);
    }
}
