package network.commands.request;

import network.commands.Request;
import network.commands.RequestHandler;
import network.commands.Response;

/**
 * Sent when choosing the character
 */
public class SetCharacterRequest implements Request {
    public final int characterNumber;

    public SetCharacterRequest(int characterNumber){
        this.characterNumber = characterNumber;
    }

    @Override
    public Response handle(RequestHandler handler) {
        return handler.handle(this);
    }
}
