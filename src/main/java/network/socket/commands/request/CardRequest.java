package network.socket.commands.request;

import network.socket.commands.Request;
import network.socket.commands.RequestHandler;
import network.socket.commands.Response;

public class CardRequest implements Request {
    public String cardType;

    public CardRequest(String cardType) {
        this.cardType = cardType;
    }

    @Override
    public Response handle(RequestHandler handler) {
        return handler.handle(this);
    }
}
