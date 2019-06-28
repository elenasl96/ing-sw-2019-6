package network.commands.request;

import model.field.Coordinate;
import network.commands.Request;
import network.commands.RequestHandler;
import network.commands.Response;

public class AskWeaponsOnTileRequest implements Request {
    private Coordinate coordinate;

    public AskWeaponsOnTileRequest(String s){
        String[] input = s.split(" ");
        char letter = input[0].toUpperCase().charAt(0);
        int number = Integer.parseInt(input[1]);
        coordinate = new Coordinate(letter, number);
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    @Override
    public Response handle(RequestHandler handler) {
        return handler.handle(this);
    }
}
