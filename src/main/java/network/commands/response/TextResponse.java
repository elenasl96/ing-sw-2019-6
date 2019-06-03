package network.commands.response;

import network.commands.Response;
import network.commands.ResponseHandler;

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
