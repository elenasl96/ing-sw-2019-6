package network.socket.commands.response;

import network.socket.ClientContext;
import model.clientRoom.Group;
import network.socket.commands.Response;
import network.socket.commands.ResponseHandler;

public class StartGameResponse implements Response {
    @Override
    public void handle(ResponseHandler handler) {
        Group currentGroup = ClientContext.get().getCurrentGroup();
        currentGroup.setFull();
        currentGroup.sendStartNotification();
    }
}
