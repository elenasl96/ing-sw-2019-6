package network.socket.commands.response;

import model.room.Update;
import network.socket.ClientContext;
import network.socket.commands.Response;
import network.socket.commands.ResponseHandler;

/**
 * This class handles the notification to all the views when a player performs an action
 * during his turn.
 */
public class GameUpdateNotification implements Response {
    public Update update;

    public GameUpdateNotification(Update update) {
        this.update = update;
    }

    @Override
    public void handle(ResponseHandler handler) {
        ClientContext.get().getCurrentGroup().getGame().sendUpdate(update);
    }
}
