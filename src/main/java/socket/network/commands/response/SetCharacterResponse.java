package socket.network.commands.response;

import model.enums.Character;
import socket.network.commands.Response;
import socket.network.commands.ResponseHandler;

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
