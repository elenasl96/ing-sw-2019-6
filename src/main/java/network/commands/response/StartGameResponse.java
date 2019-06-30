package network.commands.response;

import network.ClientContext;
import model.room.Group;
import network.commands.Response;
import network.commands.ResponseHandler;

/**
 * Sent as a starting response when the group is full or the timer has expired and the game is starting
 */
public class StartGameResponse implements Response {
    //Auto handling
    @Override
    public void handle(ResponseHandler handler) {
        Group currentGroup = ClientContext.get().getCurrentGroup();
        currentGroup.setFull();
        currentGroup.sendStartNotification();
    }
}
