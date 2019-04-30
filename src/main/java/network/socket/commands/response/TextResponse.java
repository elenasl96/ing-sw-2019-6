package network.socket.commands.response;

import network.socket.commands.Response;
import network.socket.commands.ResponseHandler;

public class TextResponse implements Response {
    public Boolean status;
    public final String content;

    public TextResponse(String content, Boolean status) {
        this.content = content;
        this.status = status;
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
