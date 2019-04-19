package socket.network.commands.response;

import socket.ClientContext;
import socket.model.Group;
import socket.network.commands.Response;
import socket.network.commands.ResponseHandler;

public class StartGameResponse implements Response {
    @Override
    public void handle(ResponseHandler handler) {
        Group currentGroup = ClientContext.get().getCurrentGroup();
        currentGroup.setFull();
        currentGroup.sendStartNotification();
    }
}
