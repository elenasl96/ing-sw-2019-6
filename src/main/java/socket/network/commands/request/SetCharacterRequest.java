package socket.network.commands.request;

import socket.network.commands.Request;
import socket.network.commands.RequestHandler;
import socket.network.commands.Response;

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
