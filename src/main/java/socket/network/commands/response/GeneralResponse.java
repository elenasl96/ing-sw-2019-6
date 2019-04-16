package socket.network.commands.response;

import socket.network.commands.Response;
import socket.network.commands.ResponseHandler;

public class GeneralResponse implements Response {
    public boolean status;

    public GeneralResponse(boolean status){this.status = status;}

    @Override
    public void handle(ResponseHandler handler) {
        handler.handle(this);
    }
}
