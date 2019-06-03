package network.commands.request;

import network.commands.Request;
import network.commands.RequestHandler;
import network.commands.Response;

public class ShootRequest implements Request {
    String string;

    public ShootRequest(String string) {
        this.string = string;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    @Override
    public Response handle(RequestHandler handler) {
        return handler.handle(this);
    }
}
