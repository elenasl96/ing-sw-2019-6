package network.socket.commands.response;

import model.enums.Character;
import network.socket.commands.ResponseHandler;
import network.socket.commands.Response;

public class SetCharacterResponse implements Response {
    public final Character character;
    public SetCharacterResponse(Character character) {
        this.character = character;
    }

    @Override
    public void handle(ResponseHandler handler) {
        handler.handle(this);
    }
}
