package network.socket.commands.request;

import model.Player;
import network.socket.commands.Request;
import network.socket.commands.RequestHandler;
import network.socket.commands.Response;

public class SpawnRequest implements Request {
    private Player sender;
    private Integer spawn;

    public SpawnRequest(Player sender, Integer spawn) {
        this.sender = sender;
        this.spawn = spawn;
    }

    public Integer getSpawn() {
        return spawn;
    }

    public Player getSender() {
        return sender;
    }

    @Override
    public Response handle(RequestHandler handler) {
        return handler.handle(this);
    }
}
