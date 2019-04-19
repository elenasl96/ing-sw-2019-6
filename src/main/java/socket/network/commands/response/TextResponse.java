package socket.network.commands.response;

import socket.network.commands.Response;
import socket.network.commands.ResponseHandler;
import socket.network.commands.StatusCode;

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
