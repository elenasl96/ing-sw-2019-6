package network.commands.response;

import network.ClientContext;
import model.room.Group;
import network.commands.Response;
import network.commands.ResponseHandler;

public class StartGameResponse implements Response {
    @Override
    public void handle(ResponseHandler handler) {
        Group currentGroup = ClientContext.get().getCurrentGroup();
        currentGroup.setFull();
        currentGroup.sendStartNotification();
    }
}
