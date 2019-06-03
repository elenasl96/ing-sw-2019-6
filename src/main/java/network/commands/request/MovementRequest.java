package network.commands.request;

import model.field.Coordinate;
import network.commands.Request;
import network.commands.RequestHandler;
import network.commands.Response;

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
