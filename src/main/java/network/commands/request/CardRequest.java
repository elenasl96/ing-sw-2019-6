package network.commands.request;

import network.commands.Request;
import network.commands.RequestHandler;
import network.commands.Response;

import static java.lang.Integer.parseInt;

//TODO javadoc
public class CardRequest implements Request {
    private String cardType;
    private int number;

    public CardRequest(String cardType){
        this.cardType = cardType;
    }

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
