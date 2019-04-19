package network.socket.commands.request;

import network.socket.commands.Request;
import network.socket.commands.RequestHandler;
import network.socket.commands.Response;

public class SetCharacterRequest implements Request {
    public int characterNumber;

    public SetCharacterRequest(int characterNumber){
        this.characterNumber = characterNumber;
    }

    @Override
    public Response handle(RequestHandler handler) {
        return handler.handle(this);
    }
}
