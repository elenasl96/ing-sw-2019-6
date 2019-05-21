package network.socket.commands.request;

import network.socket.commands.Request;
import network.socket.commands.RequestHandler;
import network.socket.commands.Response;

import static java.lang.Integer.parseInt;

public class CardRequest implements Request {
    private String cardType;
    private int number;

    public CardRequest(String cardType, String numberString) {
        this.number = parseInt(numberString);
        this.cardType = cardType;
    }

    public String getCardType(){
        return this.cardType;
    }

    public int getNumber(){
        return this.number;
    }
    @Override
    public Response handle(RequestHandler handler) {
        return handler.handle(this);
    }
}
