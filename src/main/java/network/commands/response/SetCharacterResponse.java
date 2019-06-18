package network.commands.response;

import model.enums.Character;
import network.commands.ResponseHandler;
import network.commands.Response;

//TODO javadoc
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
