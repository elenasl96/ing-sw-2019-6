package network.socket.commands.request;

import network.socket.commands.Request;
import network.socket.commands.RequestHandler;
import network.socket.commands.Response;

public class ReloadRequest implements Request {
    private int number;

    public ReloadRequest(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public Response handle(RequestHandler handler) {
        return handler.handle(this);
    }
}
