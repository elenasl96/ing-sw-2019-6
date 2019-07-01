package network.commands.response;

import model.Player;
import model.room.User;
import network.commands.Response;
import network.commands.ResponseHandler;

/**
 *
 */
public class RejoiningResponse implements Response {
    private Player player;
    private User user;

    public RejoiningResponse(Player player, User user){
        this.player = player;
        this.user = user;
    }

    public Player getPlayer() {
        return player;
    }

    public User getUser() {
        return user;
    }

    @Override
    public void handle(ResponseHandler handler) {
        handler.handle(this);
    }
}
