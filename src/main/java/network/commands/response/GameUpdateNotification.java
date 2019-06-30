package network.commands.response;

import model.room.Update;
import network.ClientContext;
import network.commands.Response;
import network.commands.ResponseHandler;

/**
 * Response sent for EVERY update sent to the View
 */
public class GameUpdateNotification implements Response {
    private Update update;

    public GameUpdateNotification(Update update) {
        this.update = update;
    }

    @Override
    public void handle(ResponseHandler handler) {
        ClientContext.get().getCurrentUser().receiveUpdate(update);
    }
}
