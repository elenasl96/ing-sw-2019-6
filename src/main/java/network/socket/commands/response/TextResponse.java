package network.socket.commands.response;

import network.socket.commands.Response;
import network.socket.commands.ResponseHandler;
import model.enums.StatusCode;

public class TextResponse implements Response {
    public final String content;
    public final StatusCode status;

    public TextResponse(String content, StatusCode status) {
        this.content = content;
        this.status = status;
    }

    public TextResponse(String content) {
        this.content = content;
        this.status = StatusCode.OK;
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
