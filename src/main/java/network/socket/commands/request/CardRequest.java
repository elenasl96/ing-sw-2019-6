package network.socket.commands.request;

import network.socket.commands.Request;
import network.socket.commands.RequestHandler;
import network.socket.commands.Response;

import static java.lang.Integer.parseInt;

public class CardRequest implements Request {
    public String cardType;
    public int number;

    public CardRequest(String cardType, String numberString) {
        this.number = parseInt(numberString);
        this.cardType = cardType;
    }

    @Override
    public Response handle(RequestHandler handler) {
        return handler.handle(this);
    }
}
