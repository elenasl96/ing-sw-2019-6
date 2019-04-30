package network.socket.commands.request;

import model.field.Coordinate;
import network.socket.commands.Request;
import network.socket.commands.RequestHandler;
import network.socket.commands.Response;

public class MovementRequest implements Request {
    private final Coordinate destination;

    public MovementRequest(Coordinate coordinate){
        this.destination = coordinate;
    }

    @Override
    public Response handle(RequestHandler handler) {
        return handler.handle(this);
    }
}
