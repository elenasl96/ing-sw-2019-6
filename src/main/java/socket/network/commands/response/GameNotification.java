package socket.network.commands.response;

import model.Player;
import model.moves.Move;
import socket.ClientContext;
import socket.model.Group;
import socket.model.User;
import socket.network.commands.Response;
import socket.network.commands.ResponseHandler;

/**
 * This class handles the notification to all the views when a player performs an action
 * during his turn.
 */
public class GameNotification implements Response {
    /**
     * The player performing the action
     */
    public Player player;
    /**
     * The action performed
     */
    public Move move;

    public GameNotification(Player player, Move move) {
        this.player = player;
        this.move = move;
    }

    @Override
    public void handle(ResponseHandler handler) {

    }
}
