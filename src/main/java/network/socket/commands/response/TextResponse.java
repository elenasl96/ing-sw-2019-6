package network.socket.commands.response;

import network.socket.commands.Response;
import network.socket.commands.ResponseHandler;

public class TextResponse implements Response {
    public final String content;

    public TextResponse(String content) {
        this.content = content;
    }

    @Override
    public void handle(ResponseHandler handler) {
        handler.handle(this);
    }

    @Override
    public String toString() {
        return content;
    }
}
