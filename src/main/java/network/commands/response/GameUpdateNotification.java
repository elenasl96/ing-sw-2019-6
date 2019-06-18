package network.commands.response;

import model.room.Update;
import network.ClientContext;
import network.commands.Response;
import network.commands.ResponseHandler;

/**
 * This class handles the notification to all the views when a player performs an action
 * during his turn.
 */
public class GameUpdateNotification implements Response {
    private Update update;

    public GameUpdateNotification(Update update) {
        this.update = update;
    }

    /**
     * Sends update to the views
     * @param handler   the clientController
     */
    @Override
    public void handle(ResponseHandler handler) {
        ClientContext.get().getCurrentUser().receiveUpdate(update);
    }
}
